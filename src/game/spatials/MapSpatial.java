package game.spatials;

import game.components.MapMovement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.apollo.components.spatial.Spatial;

public class MapSpatial extends Spatial<Graphics>{
	
	TiledMap map;
	GameContainer gc;
	
	@InjectComponent
	MapMovement movement;

	public MapSpatial(GameContainer gcc, TiledMap mapp) throws SlickException{
		this.map = mapp;			//location: "world/test.tmx"
		this.gc = gcc;
	}
	
	public void centerCamera(float playerX, float playerY){
		movement.centerOn((int)playerX, (int)playerY, 33, 48);
	}
	
	@Override
	public Layer getLayer() {
		return Layers.Map;
	}

	public void drawMap(int offsetX, int offsetY) {
	       //calculate the offset to the next tile (needed by TiledMap.render())
		int tileOffsetX = (int) - (movement.cameraX % movement.tileWidth);
	    int tileOffsetY = (int) - (movement.cameraY % movement.tileHeight);
	      
	       //calculate the index of the leftmost tile that is being displayed
	    int tileIndexX = (int) (movement.cameraX / movement.tileWidth);
	    int tileIndexY = (int) (movement.cameraY / movement.tileHeight);
	      
	       //finally draw the section of the map on the screen
	    map.render(tileOffsetX + offsetX, tileOffsetY + offsetY, tileIndexX, tileIndexY, (gc.getWidth()  - tileOffsetX) / movement.tileWidth  + 1, (gc.getHeight() - tileOffsetY) / movement.tileHeight + 1);
	}
	
	public void drawMap(){
		drawMap(0,0);
	}
	
	//draw the game container relative to the camera's location
	public void translateGraphics(){
		gc.getGraphics().translate(-movement.cameraX, -movement.cameraY);
	}
	
	//draw elements onto the game container with original coordinates (for HUD elements)
	public void untranslateGraphics(){
		gc.getGraphics().translate(movement.cameraX, movement.cameraY);
	}
	
	@Override
	public void render(Graphics arg0) {
		drawMap();
		translateGraphics();
	}

}
