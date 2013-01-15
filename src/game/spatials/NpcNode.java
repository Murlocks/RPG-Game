package game.spatials;

import game.Tags;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import com.apollo.Entity;
import com.apollo.Layer;
import com.apollo.annotate.InjectManager;
import com.apollo.components.Transform;
import com.apollo.components.spatial.Node;
import com.apollo.managers.TagManager;

public class NpcNode extends Node<Graphics>{
	
	@InjectManager
	TagManager tagManager;

	@Override
	public Layer getLayer(){
		return Layers.Gui;
	}
	
	@Override
	protected void attachChildren(){
		addChild(new NpcSpatial());
	}

	@Override
	public void render(Graphics g){
		Entity npc = tagManager.getEntity(Tags.InteractingNpc);
		if(npc!=null){
			//g.drawRect(npc.getComponent(Transform.class).getX()+14, npc.getComponent(Transform.class).getY(), 4, 4);
			float x = npc.getComponent(Transform.class).getX();
			float y = npc.getComponent(Transform.class).getY();
			float[] points = {x+16, y+4, x+12, y-6, x+16, y, x+20, y-4};
			g.fill(new Polygon(points));
		}
	}

}
