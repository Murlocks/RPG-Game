package game.builders;

import game.Groups;
import game.components.Npc;
import game.spatials.NpcNode;

import com.apollo.Entity;
import com.apollo.EntityBuilder;
import com.apollo.World;
import com.apollo.components.Transform;
import com.apollo.managers.GroupManager;

public class NpcBuilder implements EntityBuilder{
	
	String spriteName;	//location of sprite in the project

	//called by world when you use the createEntity function
	public Entity buildEntity(final World world){
		final Entity e = new Entity(world);	//creates a new entity within world
		e.setComponent(new Transform());	//gives new npc entity a location component
		e.setComponent(new Npc());		//gives new npc entity a npc component which defines which npc it should be (interacts with spatial to render correct sprite)
		e.setComponent(new NpcNode());	//gives new npc entity a spatial component so it can be rendered
		world.getManager(GroupManager.class).setGroup(e, Groups.Npc);	//adds new entity to Npc group
		return e;	//returns new entity
	}
	
}
