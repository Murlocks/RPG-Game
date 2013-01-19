package game;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import TWLSlick.BasicTWLGameState;

public class Menu extends BasicTWLGameState{
	
	public static final int ID = 1;
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException{
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException{
		g.drawString("Press 'E' to start game or 'Esc' to exit the game", 50, 100);
		g.drawString("Press 'S' to access the save/load menu", 50, 200);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException{
		if(gc.getInput().isKeyDown(Input.KEY_E))	game.enterState(Game.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		if(gc.getInput().isKeyDown(Input.KEY_ESCAPE))	gc.exit();
		if(gc.getInput().isKeyDown(Input.KEY_S))	game.enterState(SaveMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
	}

	@Override
	public int getID(){
		return ID;
	}
}
