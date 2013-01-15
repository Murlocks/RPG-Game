package game.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import com.apollo.Component;
import com.apollo.Entity;
import com.apollo.components.Transform;

public class MapMovement extends Component {
	
	public float cameraX, cameraY;
	private GameContainer gc;
	private TiledMap map;
	public int mapWidth, mapHeight;
	public int tileWidth, tileHeight;
	private int numTilesX, numTilesY;
	//constructor: initializes all map values
	public MapMovement(GameContainer gc, TiledMap mapp){
		this.gc = gc;
	    this.map = mapp;
	    this.numTilesX = map.getWidth();
	    this.numTilesY = map.getHeight();
	      
	    this.tileWidth = map.getTileWidth();
	    this.tileHeight = map.getTileHeight();
	      
	    this.mapWidth = this.numTilesX * this.tileWidth;
	    this.mapHeight = this.numTilesY * this.tileHeight;
	      
	    this.gc = gc;
	}
	//center on point
	public void centerOn(float x, float y){
		//center camera
		cameraX = x - gc.getWidth() / 2;
		cameraY = y - gc.getHeight() / 2;
		//don't let camera scroll off page      
		if(cameraX < 0) cameraX = 0;
		if(cameraX + gc.getWidth() > mapWidth) cameraX = mapWidth - gc.getWidth();
		      
		if(cameraY < 0) cameraY = 0;
		if(cameraY + gc.getHeight() > mapHeight) cameraY = mapHeight - gc.getHeight();
	}
	//center on rectangle
	public void centerOn(float x, float y, float height, float width){
		this.centerOn(x + width / 2, y + height / 2);
	}
	//center on a polygon
	public void centerOn(Shape shape){
		 this.centerOn(shape.getCenterX(), shape.getCenterY());
	}
	//center on player
	public void updateMap(Entity player){
		centerOn(player.getComponent(Transform.class).getX(), player.getComponent(Transform.class).getY(), 33, 48);
	}
	public float getCamX(){
		return cameraX;
	}
	public float getCamY(){
		return cameraY;
	}
}
