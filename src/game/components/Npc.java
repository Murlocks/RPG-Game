package game.components;

import java.util.HashMap;

import org.newdawn.slick.Image;

import game.data.DialogChoice;
import game.data.NpcType;

import com.apollo.Component;
import com.apollo.annotate.InjectComponent;
import com.apollo.components.Transform;

public class Npc extends Component{	
	
	@InjectComponent
	Transform transform;
	
	private NpcType npcType;
	private int direction = 0;	//tells NpcSpatial what direction the npc is facing
	private String name = "";
	
	private boolean interacting = false;
	
	private float rX, rY;
	private float cX, cY;
	
	public boolean behindPlayer = false;
	
	private boolean draw = true;
	
	private HashMap<String, NpcType> types = new HashMap<String, NpcType>();
	
	public Npc(){
		initializeTypes();
	}
	
	private void initializeTypes(){
		//initialize all different types of NPCs with their respective sprite and dimensions
		types.put("Test", new NpcType("miku", 33, 48));
		setDialogs();
		setDialogChoices();
	}
	
	private void setDialogs(){
		//String[] s = {"lalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalala"};
		//types.get("Test").setDialog(s);
		//fix this later so you can easily add dialogs, perhaps use some sort of outside database?
		HashMap<Integer, String> s = new HashMap<Integer, String>();
		s.put(0, "test");
		s.put(1, "test lolfdafadsfsdfdsafdsfdsfsdafsdafdsfdsfsaffsda");
		s.put(2, "test page 2!!");
		types.get("Test").setDialog(s);
	}
	
	private void setDialogChoices(){
		HashMap<Integer, HashMap<Integer, DialogChoice>> s = new HashMap<Integer, HashMap<Integer, DialogChoice>>();
		HashMap<Integer, DialogChoice> c = new HashMap<Integer, DialogChoice>();
		c.put(1, new DialogChoice(2, "Choice 1"));
		c.put(2, new DialogChoice(2, "Choice 2"));
		c.put(3, new DialogChoice(2, "Choice 3"));
		s.put(1, c);
		types.get("Test").setDialogChoices(s);
	}
	
	public HashMap<Integer, String> getDialog(){
		return npcType.getDialog();
	}
	
	public String getDialog(int i){
		//return npcType.getDialog(i);
		return npcType.getDialog().get(i);
	}
	
	public HashMap<Integer, DialogChoice> getDialogChoices(int i){
		return npcType.getDialogChoices().get(i);
	}
	
	public boolean hasDialogChoices(int i){
		if(npcType.getDialogChoices()!=null){
			return npcType.getDialogChoices().containsKey(i);
		}else{
			return false;
		}
	}
	
	public void setType(String type){
		this.npcType = types.get(type);
	}
	
	public NpcType getNpcType(){
		return npcType;
	}
	
	public void setDirection(int direction){
		this.direction = direction;
	}
	
	public int getDirection(){
		return this.direction;
	}
	
	public void setBoundingBox(int spriteWidth, int spriteHeight){
		cX = transform.getX() + (spriteWidth / 2);
		cY = transform.getY() + (spriteHeight / 2);
		rX = spriteWidth / 2;
		rY = spriteHeight / 2;
	}

	public float getrX() {
		return rX;
	}
	
	public float getrY() {
		return rY;
	}

	public float getcX() {
		return cX;
	}
	
	public float getcY() {
		return cY;
	}

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public boolean isInteracting() {
		return interacting;
	}

	public void setInteracting(boolean interacting) {
		this.interacting = interacting;
	}
	
	public Image getPortrait(){
		return this.npcType.getPortrait();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
