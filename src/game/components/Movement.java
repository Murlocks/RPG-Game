package game.components;

import com.apollo.Component;
import com.apollo.annotate.InjectComponent;
import com.apollo.components.Transform;

public class Movement extends Component{
	@InjectComponent
	Transform transform;
	
	private float vx, vy;
	
	private int direction = 0;	//serves same function as direction variable in PlayerSpatial.java
	private boolean moving = false;	//is true if entity is moving. set true by player input in Core.java
	private boolean animate = false;	//controls whether or not to play animation, set false when moving is false (but after next iteration of update in Core.java *very important!)
	
	public Movement(){
		this.vy = 0;
		this.vx = 0;
	}
	
	public Movement(float vx, float vy){
		this.vx = vx;
		this.vy = vy;
	}

	@Override
	public void update(int delta){
		transform.addX(delta * vx);
		transform.addY(delta * vy);
	}
	
	//set+get functions for all private variables within component
	
	public void setVectors(float vx, float vy){
		this.vx = vx;
		this.vy = vy;
	}

	public float getVx(){
		return vx;
	}

	public void setVx(float vx){
		this.vx = vx;
	}

	public float getVy(){
		return vy;
	}

	public void setVy(float vy){
		this.vy = vy;
	}
	
	public void setDirection(int direction){
		this.direction = direction;
	}
	
	public int getDirection(){
		return direction;
	}
	
	public void setMoving(boolean moving){
		this.moving = moving;
	}
	
	public boolean isMoving(){
		return moving;
	}
	
	public void setAnimate(boolean animate){	
		this.animate = animate;
	}
	
	public boolean getAnimate(){	
		return this.animate;
	}
}