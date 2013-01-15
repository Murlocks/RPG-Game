package game.managers;

import game.Groups;
import game.Tags;
import game.components.Movement;
import game.components.Npc;

import com.apollo.Entity;
import com.apollo.annotate.InjectManager;
import com.apollo.components.Transform;
import com.apollo.managers.GroupManager;
import com.apollo.managers.Manager;
import com.apollo.managers.TagManager;
import com.apollo.utils.Bag;

public class CollisionManager extends Manager{
	
	private Bag<Entity> npcs;
	private Entity player;
	
	@InjectManager
	GroupManager groupManager;
	
	@InjectManager
	TagManager tagManager;
	
	@Override
	public void initialize(){
		npcs = groupManager.getEntityGroup(Groups.RenderedNpc);	//gets all rendered npcs
		player = tagManager.getEntity(Tags.Player);	//gets player entity		
	}
	
	/**private boolean collision(int delta){
		if(npcs.isEmpty()) return false;
		for (int i = 0; i < npcs.size(); i++){	//loop through all npcs
			//collision checks
			/**if (npcs.get(i).getComponent(Transform.class).getX() < player.getComponent(Transform.class).getX() + 33 + (player.getComponent(Movement.class).getVx()*delta)){		//check if npc is to the left of player
				if (npcs.get(i).getComponent(Transform.class).getX() + 33 > player.getComponent(Transform.class).getX() + (player.getComponent(Movement.class).getVx()*delta)){		//check if npc's right bound is greater than the player's left bount (true = possible collision)
					if (npcs.get(i).getComponent(Transform.class).getY() + 25 < player.getComponent(Transform.class).getY() + 48 + (player.getComponent(Movement.class).getVy()*delta)){	//check if npc is below player
						if (npcs.get(i).getComponent(Transform.class).getY() + 20 > player.getComponent(Transform.class).getY() + (player.getComponent(Movement.class).getVy()*delta)){		//check if npc's top bound is greater than player's lower bound (true = collision)
							player.getComponent(Movement.class).setVectors(0, 0);
							return true;
						}
					}
				}
			}
		}
		return false;
	}*/
	
	private boolean collision(Entity player, Entity npc, int delta){
		
		float[] nR = new float[2];
		nR[0] = npc.getComponent(Npc.class).getrX();
		nR[1] = npc.getComponent(Npc.class).getrY() - 24;
		
		float nX = npc.getComponent(Npc.class).getcX();
		float nY = npc.getComponent(Npc.class).getcY();
		
		float pX = player.getComponent(Transform.class).getX()+ 16 + (player.getComponent(Movement.class).getVx()*delta);
		float pY = player.getComponent(Transform.class).getY()+ 24 + (player.getComponent(Movement.class).getVy()*delta);
		
		if(Math.abs(pX - nX) > (16 + nR[0]))	return false;
		if(Math.abs(pY - nY) > (24 + nR[1]))	return false;
		return true;
	}
	
	@Override
	public void update(int delta){
		npcs = groupManager.getEntityGroup(Groups.RenderedNpc);
		for (int i = 0; i < npcs.size(); i++){
			if(collision(player, npcs.get(i), delta)){
				player.getComponent(Movement.class).setVectors(0, 0);
				tagManager.register(Tags.InteractingNpc, npcs.get(i));
			}
		}
	}

}
