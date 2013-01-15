package game.spatials;

import game.components.Npc;
import game.data.NpcType;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.apollo.annotate.InjectManager;
import com.apollo.components.Transform;
import com.apollo.components.spatial.Spatial;
import com.apollo.managers.RenderManager;

public class NpcSpatial extends Spatial<Graphics>{
	
	@InjectComponent
	Npc npc;
	
	@InjectComponent
	Transform location;
	
	@InjectManager
	RenderManager<Graphics> renderManager;
	
	public int direction = 0;
	private SpriteSheet sprite;
	private NpcType npcType;
	public Layers currentLayer = Layers.Npc;	//default layer
	
	private void setSprite(String spriteName, int spriteX, int spriteY) throws SlickException{
		npc.setBoundingBox(spriteX, spriteY);
		sprite = new SpriteSheet("sprites/"+spriteName+".png", spriteX, spriteY);	//creates a new sprite sheet with the image named spriteName, and defines each subimage to be spriteX by spriteY dimensions
	}
	
	public SpriteSheet getSprite(){
		return sprite;
	}

	@Override
	public Layer getLayer(){
		return currentLayer;
	}
	
	private void checkLayer(){
		if(npc.behindPlayer){
			if(currentLayer != Layers.Npc){
				currentLayer = Layers.Npc;
			}
		}else{
			if(currentLayer != Layers.NpcFront){
				currentLayer = Layers.NpcFront;
			}
		}
	}
	
	@Override
	public void render(Graphics g){
		if(npc.isDraw()){
			checkLayer();
			npcType = npc.getNpcType();
			direction = npc.getDirection();
			try {
				setSprite(npcType.getType(), npcType.getX(), npcType.getY());
			} catch (SlickException e){
				e.printStackTrace();
			}
			//subimage order on sprite sheet: up, left, right, down
			switch(direction){
			case 0:	g.drawImage(sprite.getSprite(0, 0), location.getX(), location.getY());	break;
			case 1:	g.drawImage(sprite.getSprite(0, 1), location.getX(), location.getY());	break;
			case 2:	g.drawImage(sprite.getSprite(0, 2), location.getX(), location.getY());	break;
			case 3:	g.drawImage(sprite.getSprite(0, 3), location.getX(), location.getY());	break;
			}
		}
	}
	
}
