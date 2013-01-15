package game.data;

public class GameSave {
	
	private String saveName;
	private float pX,pY;
	
	public GameSave(String name){
		this.saveName = name;
	}
	
	public GameSave(String name, float x, float y){
		this.saveName = name;
	}
	
	public void setSave(float x, float y){
		this.pX = x;
		this.pY = y;
	}
	
	public String getSaveName(){
		return this.saveName;
	}
	
	public float getSaveX(){
		return this.pX;
	}
	
	public float getSaveY(){
		return this.pY;
	}
}
