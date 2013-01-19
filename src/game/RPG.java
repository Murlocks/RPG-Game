package game;

import java.net.URL;

import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import TWLSlick.TWLStateBasedGame;

import game.Game;

public class RPG extends TWLStateBasedGame {

	public RPG() {
		super("RPG");
	}
	
	@Override
	protected URL getThemeURL() {
		return RPG.class.getResource("savegui.xml");
	}
	
	//routines for when the user chooses to close the game
	public boolean closeRequested() {
		//check if user is inside the game
		if(this.getCurrentStateID() == 0){
			if(JOptionPane.showConfirmDialog(null, "Would you like to save your game?", "Leaving already?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				this.enterState(SaveMenu.ID);
			}
		}
		return true;
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		addState(new Menu());
		addState(new Game());
		addState(new SaveMenu());
		addState(new Interaction());
		this.enterState(Menu.ID);
	}

	public static void main(String[] argv) throws SlickException{
		AppGameContainer container = new AppGameContainer(new RPG(), Game.WIDTH, Game.HEIGHT, false); //create instance of the game
		//set the settings on the game's container or window
		container.setTargetFrameRate(100);	//self explanatory, caps fps
		container.setVSync(false);	//caps fps, helps for smoother animations but causes screan tearing sometimes
		container.setShowFPS(true);	//shows fps
		container.start();	//start the game
	}
}
