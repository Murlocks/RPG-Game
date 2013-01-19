package game;

import java.awt.font.LineBreakMeasurer;
import java.util.HashMap;

import game.components.MapMovement;
import game.components.Npc;
import game.data.DialogChoice;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import com.apollo.Entity;
import com.apollo.World;
import com.apollo.managers.RenderManager;
import com.apollo.managers.TagManager;

import TWLSlick.BasicTWLGameState;
import TWLSlick.TWLStateBasedGame;

public class Interaction extends BasicTWLGameState{
	
	public static final int ID = 3;
	
	public TWLStateBasedGame game;
	private Game gameCore;
	private World world;
	private TagManager tagManager;
	private RenderManager<Graphics> renderManager;
	
	private Image playerPortrait;
	
	private int origDirection;
	
	//counter variables
	private int textCounter = 0;
	private int spaceCounter = 0;
	
	private final static int textSpeed = 25;
	
	private int dialogNum = 0;
	private String currentDialog;
	private int currentLetter;	//prints the dialog string up to this integer
	private String[] line;	//holds the dialog spread over the 5 lines of space
	
	private int currentChoice = 0;
	
	private boolean donePrinting = false;
	private boolean showChoices = false;

	//limits the times a player can press the space bar
	private boolean spacePause = false;

	@Override
	public int getID() {
		return ID;
	}
	
	private void untranslateGraphics(Graphics g){
		Entity map = tagManager.getEntity(Tags.Map);
		g.translate(map.getComponent(MapMovement.class).getCamX(), map.getComponent(MapMovement.class).getCamY());
	}
	
	public void setOrigDirection(int direction){
		this.origDirection = direction;
	}
	
	public void resetDialog(){
		//initialize the line array
		clearText();
		currentLetter = 0;
		currentChoice = 0;
		dialogNum = 0;
		currentDialog = "";	//the "raw" dialog text
		donePrinting = false;
		showChoices = false;
	}
	
	public void clearDialog(){
		clearText();
		currentLetter = 0;
		currentChoice = 0;
		currentDialog = "";	//the "raw" dialog text
		donePrinting = false;
		showChoices = false;
		spacePause = true;
	}
	
	private void clearText(){
		line = new String[5];
		for(int i=0;i<5;i++){
			line[i] = "";	//make all of them blank
		}
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException{
		this.game = (TWLStateBasedGame)game;
		this.gameCore = (Game) game.getState(Game.ID);
		this.world = gameCore.getWorld();
		this.tagManager = world.getManager(TagManager.class);
		this.renderManager = world.getManager(RenderManager.class);
		this.playerPortrait = new Image("portraits/player portrait.png");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException{
		gc.getInput().disableKeyRepeat();
		Entity npc = tagManager.getEntity(Tags.InteractingNpc);
		//spaceCounter times 200ms intervals
		//textCounter times shorter 50ms intervals
		spaceCounter += delta;
		textCounter += delta;
		if(textCounter/textSpeed>1){
			textCounter = 0; 
			currentDialog = npc.getComponent(Npc.class).getDialog(dialogNum);
			if(currentLetter<currentDialog.length()){
				if(currentLetter<60){
					line[0] = currentDialog.substring(0, currentLetter+1);
				}else if(currentLetter>=60&&currentLetter<120){
					line[0] = currentDialog.substring(0, 60);
					line[1] = currentDialog.substring(60, currentLetter+1);
				}else if(currentLetter>=120&&currentLetter<180){
					line[0] = currentDialog.substring(0, 60);
					line[1] = currentDialog.substring(60, 120);
					line[2] = currentDialog.substring(120, currentLetter+1);
				}else if(currentLetter>=240&&currentLetter<300){
					line[0] = currentDialog.substring(0, 60);
					line[1] = currentDialog.substring(60, 120);
					line[2] = currentDialog.substring(120, 180);
					line[3] = currentDialog.substring(180, currentLetter+1);
				}else if(currentLetter>=300&&currentLetter<360){
					line[0] = currentDialog.substring(0, 60);
					line[1] = currentDialog.substring(60, 120);
					line[2] = currentDialog.substring(120, 180);
					line[3] = currentDialog.substring(180, 240);
					line[4] = currentDialog.substring(240, currentLetter+1);
				}
				currentLetter++;
			}else{
				donePrinting = true;
			}
		}
		if(spaceCounter/300>1){
			spaceCounter = 0; 
			spacePause = false;
		}
		if(gc.getInput().isKeyDown(Input.KEY_SPACE)){
			//check to see if there is dialog left to display
			if(currentLetter<currentDialog.length()&&!spacePause){
				currentLetter = currentDialog.length() - 1;
				spacePause = true;
			}else if(!spacePause){
				//after all text has been displayed
				if(currentChoice!=0){
					dialogNum = npc.getComponent(Npc.class).getDialogChoices(dialogNum).get(currentChoice).getPointsTo();	//set the dialog to be displayed to the choice
					//reset the dialog without changing the dialogNum again
					clearText();
					currentLetter = 0;
					currentChoice = 0;
					currentDialog = "";	//the "raw" dialog text
					donePrinting = false;
					showChoices = false;
					spacePause = true;
				}
				//check if there are choices to show
				if(npc.getComponent(Npc.class).hasDialogChoices(dialogNum)){
					showChoices = true;
				}
				if(!spacePause&&!npc.getComponent(Npc.class).hasDialogChoices(dialogNum)){
					//check to see if there is another dialog page to display, even if it doesn't have choices
					if(dialogNum<npc.getComponent(Npc.class).getDialog().size()-1){
						dialogNum++;
						//reset the dialog
						clearText();
						currentLetter = 0;
						currentChoice = 0;
						currentDialog = "";	//the "raw" dialog text
						donePrinting = false;
						showChoices = false;
						spacePause = true;
					}else{
						npc.getComponent(Npc.class).setDirection(origDirection);
						game.enterState(Game.ID, new FadeOutTransition(new Color(0.0f, 0.0f, 0.0f, 0.6f)), new FadeInTransition(Color.transparent));
					}
				}
			}
		}
		if(gc.getInput().isKeyDown(Input.KEY_ESCAPE)){
			npc.getComponent(Npc.class).setDirection(origDirection);
			game.enterState(Game.ID, new FadeOutTransition(new Color(0.0f, 0.0f, 0.0f, 0.6f)), new FadeInTransition(Color.transparent));
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_W)){
			if(npc.getComponent(Npc.class).getDialogChoices(dialogNum)!=null){
				if(currentChoice>1){
					currentChoice--;
					//pause = true;
				}
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_S)){
			if(npc.getComponent(Npc.class).getDialogChoices(dialogNum)!=null){
				if(currentChoice<npc.getComponent(Npc.class).getDialogChoices(dialogNum).size()){
					currentChoice++;
					//pause = true;
				}
			}
		}	
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException{
		renderManager.render(g);
		untranslateGraphics(g);	//allows us to draw the gui elements based on "normal" coordinates
		//draw gui elements
		g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.6f));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.black);
		g.fillRect(0, Game.HEIGHT*2/3, Game.WIDTH, Game.HEIGHT);
		g.setColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
		Entity npc = tagManager.getEntity(Tags.InteractingNpc);
		if(npc != null){
			g.drawImage(playerPortrait, 0, (Game.HEIGHT*2/3)-playerPortrait.getHeight());
			g.drawImage(npc.getComponent(Npc.class).getPortrait(), Game.WIDTH-npc.getComponent(Npc.class).getPortrait().getWidth(), (Game.HEIGHT*2/3)-npc.getComponent(Npc.class).getPortrait().getHeight());
			g.drawString(npc.getComponent(Npc.class).getName(), 10, (Game.HEIGHT*2/3)+6);
			//print the dialog
			if(!showChoices){
				for(int i=0;i<5;i++){
					g.drawString(line[i], 48, (Game.HEIGHT*2/3)+(i*20)+30);
				}
				if(donePrinting){
					g.drawString("Press space bar to continue", 190, (Game.HEIGHT*2/3)+130);
				}
			}else{
				HashMap<Integer, DialogChoice> choices = npc.getComponent(Npc.class).getDialogChoices(dialogNum);
				if(choices!=null){
					for(int i = 1; i<choices.size()+1; i++){
						g.drawString(choices.get(i).getChoice(), 48, (Game.HEIGHT*2/3)+(i*20)+30);
					}
					if(currentChoice!=0){
						g.fillRect(40, (Game.HEIGHT*2/3)+(currentChoice*20)+30, 5, 5);
					}
				}
			}
		}
	}
}
