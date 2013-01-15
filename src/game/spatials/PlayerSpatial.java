package game.spatials;

import game.components.Movement;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.apollo.components.Transform;
import com.apollo.components.spatial.Spatial;

public class PlayerSpatial extends Spatial<Graphics>{

	private Animation[] player;	//array for sprite's animations, one for each direction
	private int direction = 0;	//directions are as follow: 0 = down, 1 = left, 2 = right, 3 = up
	
	private int spriteX = 33;	//dimensions for each
	private int spriteY = 48;	//sprite frame on sheet
	
	@InjectComponent
	Transform location;		//gives access to Player entity's transform component
	
	@InjectComponent
	Movement movement;		//gives access to Player entity's transform component
	
	public PlayerSpatial() throws SlickException{
		SpriteSheet sprite = new SpriteSheet("sprites/miku.png",spriteX,spriteY);
		//initialize animation array, loads 4 frames for each direction.
		player = new Animation[4]; //initialize the animation array to size 4, one for each direction. refer to direction variable for each respective direction
		for (int i=0;i<4;i++){
			player[i] = new Animation(true);	
			for (int frame=0;frame<4;frame++){
				player[i].addFrame(sprite.getSprite(frame,i), 150);
			}
		}
	}
	
	@Override
	public Layer getLayer(){
		return Layers.Player;	//returns enum type Layers.Player, called by renderManager when rendering. Necessary for determining the order in which objects are rendered, refer to Layers.java for specified order
	}
	
	@Override
	public void render(Graphics g){
		this.direction = movement.getDirection();
		//animate is true when key is pressed, set false when moving is false (moving set false after every iteration of update in main class)
		if (!movement.getAnimate()){
			player[direction].restart(); //works better than stop() and start() functions. sprite will always stop on the same frame(standing still frame).
			g.drawAnimation(player[direction], location.getX(), location.getY());
		}else if(movement.getAnimate()){
			g.drawAnimation(player[direction], location.getX(), location.getY());
		}
	}

}
