package game.data;

import java.util.HashMap;

public class Level {

	private HashMap<Integer, Layer> Layers;
	private int currentItemNum;
	private float cameraX, cameraY;
	private String version;
	private String name;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HashMap<Integer, Layer> getLayers() {
		return Layers;
	}
	public void setLayers(HashMap<Integer, Layer> layers) {
		Layers = layers;
	}
	public int getCurrentItemNum() {
		return currentItemNum;
	}
	public void setCurrentItemNum(int currentItemNum) {
		this.currentItemNum = currentItemNum;
	}
	public float getCameraX() {
		return cameraX;
	}
	public void setCameraX(float cameraX) {
		this.cameraX = cameraX;
	}
	public float getCameraY() {
		return cameraY;
	}
	public void setCameraY(float cameraY) {
		this.cameraY = cameraY;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
}
