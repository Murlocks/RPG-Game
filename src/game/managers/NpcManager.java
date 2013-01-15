package game.managers;

import org.newdawn.slick.Graphics;

import game.Groups;
import game.Tags;
import game.components.Npc;

import TWLSlick.TWLStateBasedGame;

import com.apollo.Entity;
import com.apollo.EventHandler;
import com.apollo.annotate.InjectManager;
import com.apollo.components.Transform;
import com.apollo.managers.EntityManager;
import com.apollo.managers.GroupManager;
import com.apollo.managers.Manager;
import com.apollo.managers.RenderManager;
import com.apollo.managers.TagManager;
import com.apollo.utils.Bag;

public class NpcManager extends Manager{

	private Bag<Entity> npcs;
	private Entity player;

	//import world's managers
	@InjectManager	
	GroupManager groupManager;
	
	@InjectManager	
	TagManager tagManager;
	
	@InjectManager	
	EntityManager entityManager;
	
	@InjectManager	
	RenderManager<Graphics> renderManager;
	
	@Override
	public void initialize(){
		//create an input: reads textfile or st to spawn NPCs with proper spatials and locations
		
		//testing new npc system
		/**world.addEntity(createNpc(200, 200, 0, "Test"));
		world.addEntity(createNpc(240, 200, 0, "Test"));
		world.addEntity(createNpc(400, 400, 2, "Test"));
		world.addEntity(createNpc(200, 500, 3, "Test"));
		world.addEntity(createNpc(350, 200, 1, "Test"));
		world.addEntity(createNpc(400, 300, 1, "Test"));
		world.addEntity(createNpc(600, 400, 2, "Test"));
		world.addEntity(createNpc(100, 500, 3, "Test"));
		world.addEntity(createNpc(400, 200, 0, "Test"));
		world.addEntity(createNpc(100, 300, 1, "Test"));
		world.addEntity(createNpc(200, 400, 2, "Test"));
		world.addEntity(createNpc(400, 500, 3, "Test"));
		world.addEntity(createNpc(200, 200, 0, "Test"));
		world.addEntity(createNpc(400, 300, 1, "Test"));
		world.addEntity(createNpc(600, 400, 2, "Test"));
		world.addEntity(createNpc(200, 500, 3, "Test"));
		*/
		
		int x = 1;
		
		for (int i = 2; i < 50; i++){
			for (int j = 2; j < 50; j++){
				world.addEntity(createNpc((j*100), (i*100), 0, "Test", "Hatsune Miku "+ x));
				x++;
			}
		}
		
		npcs = groupManager.getEntityGroup(Groups.RenderedNpc);	//adds all rendered npcs into a bag(basically an object array)
		player = tagManager.getEntity(Tags.Player);	//gets player entity
	}
	
	private Entity createNpc(float x, float y, int direction, String type, String name){
		final Entity npc = world.createEntity("Npc");
		npc.getComponent(Transform.class).setLocation(x,y);
		npc.getComponent(Npc.class).setType(type);
		npc.getComponent(Npc.class).setName(name);
		npc.getComponent(Npc.class).setDirection(direction);
		return npc;
	}
	
	public boolean behindPlayer(Entity player, Entity npc){
		float[] nR = new float[2];
		nR[0] = npc.getComponent(Npc.class).getrX();
		nR[1] = npc.getComponent(Npc.class).getrY() - 24;
		
		float nX = npc.getComponent(Npc.class).getcX();
		float nY = npc.getComponent(Npc.class).getcY() + 20;
		
		float pX = player.getComponent(Transform.class).getX()+ 16;
		float pY = player.getComponent(Transform.class).getY()+ 24;
		
		if(Math.abs(pX - nX) > (16 + nR[0]))	return false;
		if(Math.abs(pY - nY) > (24 + nR[1]))	return false;
		return true;
	}
	
	@Override
	public void update(int delta){
		npcs = groupManager.getEntityGroup(Groups.RenderedNpc);
		for (int i = 0; i < npcs.size(); i++){
			if(behindPlayer(player, npcs.get(i))){
				npcs.get(i).getComponent(Npc.class).behindPlayer = true;
			}else{
				npcs.get(i).getComponent(Npc.class).behindPlayer = false;
			}
			renderManager.added(npcs.get(i));
		}
	}
}
