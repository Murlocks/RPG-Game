package game.data;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class NpcType {
	//Basic Structure for NpcTypes, type which will hold the sprites name and x+y hold the sprite's dimensions
	private String type;
	private int x, y;
	private HashMap<Integer, String> dialog;
	private HashMap<Integer, HashMap<Integer, DialogChoice>> dialogChoices;
	
	private Image portrait;

	public NpcType(String type, int x, int y){
		this.setType(type);
		this.setX(x);
		this.setY(y);
		try {
			this.setPortrait(new Image("portraits/"+type+" portrait.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Image getPortrait() {
		return portrait;
	}

	public void setPortrait(Image portrait) {
		this.portrait = portrait;
	}
	
	//testing hashmap system for dialogs/choices

	public HashMap<Integer, String> getDialog() {
		return dialog;
	}

	public void setDialog(HashMap<Integer, String> d) {
		this.dialog = d;
	}

	public HashMap<Integer, HashMap<Integer, DialogChoice>> getDialogChoices() {
		return dialogChoices;
	}

	public void setDialogChoices(HashMap<Integer, HashMap<Integer, DialogChoice>> dialogChoices) {
		this.dialogChoices = dialogChoices;
	}
}
