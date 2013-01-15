package game.managers;

import game.Tags;
import game.components.Movement;
import game.components.Npc;

import com.apollo.Entity;
import com.apollo.annotate.InjectManager;
import com.apollo.components.Transform;
import com.apollo.managers.Manager;
import com.apollo.managers.TagManager;

public class InteractionManager extends Manager {
	
	private Entity player;
	
	@InjectManager
	TagManager tagManager;
	
	private boolean facingNpc(Entity npc){
		if(npc!=null){
			/**switch (npc.getComponent(Npc.class).getDirection()){
			case 0: if(player.getComponent(Movement.class).getDirection()==3)	return true; break;
			case 3: if(player.getComponent(Movement.class).getDirection()==0)	return true; break;
			case 1: if(player.getComponent(Movement.class).getDirection()==2)	return true; break;
			case 2: if(player.getComponent(Movement.class).getDirection()==1)	return true; break;
			}*/
			
			float pX = player.getComponent(Transform.class).getX();
			float pY = player.getComponent(Transform.class).getY();
			float nX = npc.getComponent(Transform.class).getX();
			float nY = npc.getComponent(Transform.class).getY();
			
			if(pX<nX&&player.getComponent(Movement.class).getDirection()==2){
				return true;
			}
			if(pX>nX&&player.getComponent(Movement.class).getDirection()==1){
				return true;
			}
			if(pY<nY&&player.getComponent(Movement.class).getDirection()==0){
				return true;
			}
			if(pY>nY&&player.getComponent(Movement.class).getDirection()==3){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void initialize(){
		player = tagManager.getEntity(Tags.Player);
	}

	@Override
	public void update(int delta){
		Entity npc = tagManager.getEntity(Tags.InteractingNpc);
		if(npc!=null){
			if(player.getComponent(Movement.class).isMoving()){
				npc.getComponent(Npc.class).setInteracting(false);
			}
			if(!facingNpc(npc)){
				tagManager.unregister(Tags.InteractingNpc);
			}
		}
	}
}
