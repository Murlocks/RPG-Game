package game;

import game.data.GameSave;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import com.thoughtworks.xstream.XStream;

import TWLSlick.BasicTWLGameState;
import TWLSlick.RootPane;
import TWLSlick.TWLStateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.ToggleButton;

public class SaveMenu extends BasicTWLGameState{
	
	public final static int ID = 2;

	public TWLStateBasedGame game;
	private Game gameCore;
	
	private String fileName;
	private File file;
	
	private BufferedWriter fileOut;
	private BufferedReader fileIn;
	

	private ToggleButton[] slotButtons;
	private Button saveButton;
	private Button loadButton;
	
    @Override
    protected RootPane createRootPane() {
    	final RootPane saveGui = super.createRootPane();
    	final SaveMenu saveMenu = this;
		
		slotButtons = new ToggleButton[4];
		for( int i=0;i<4;i++){
		slotButtons[i] = new ToggleButton();
		slotButtons[i].setTheme("slotbutton");
		slotButtons[i].setText("Save Slot "+ (i + 1));
		}
		
		slotButtons[0].addCallback(new Runnable(){
			public void run(){
				saveMenu.setSaveFile("save1");
				ToggleButton button = (ToggleButton) saveMenu.getRootPane().getChild(saveMenu.getRootPane().getChildIndex(slotButtons[1]));
				button.setActive(false);
				button = (ToggleButton) saveMenu.getRootPane().getChild(saveMenu.getRootPane().getChildIndex(slotButtons[2]));
				button.setActive(false);
				button = (ToggleButton) saveMenu.getRootPane().getChild(saveMenu.getRootPane().getChildIndex(slotButtons[3]));
				button.setActive(false);
			}
		});
		saveGui.add(slotButtons[0]);
		
		slotButtons[1].addCallback(new Runnable(){
			public void run(){
				saveMenu.setSaveFile("save2");
				saveMenu.getRootPane().reapplyTheme();
				ToggleButton button = (ToggleButton) saveMenu.getRootPane().getChild(saveMenu.getRootPane().getChildIndex(slotButtons[0]));
				button.setActive(false);
				button = (ToggleButton) saveMenu.getRootPane().getChild(saveMenu.getRootPane().getChildIndex(slotButtons[2]));
				button.setActive(false);
				button = (ToggleButton) saveMenu.getRootPane().getChild(saveMenu.getRootPane().getChildIndex(slotButtons[3]));
				button.setActive(false);
			}
		});
		saveGui.add(slotButtons[1]);
		
		slotButtons[2].addCallback(new Runnable(){
			public void run(){
				saveMenu.setSaveFile("save3");
				saveMenu.getRootPane().reapplyTheme();
				ToggleButton button = (ToggleButton) saveMenu.getRootPane().getChild(saveMenu.getRootPane().getChildIndex(slotButtons[0]));
				button.setActive(false);
				button = (ToggleButton) saveMenu.getRootPane().getChild(saveMenu.getRootPane().getChildIndex(slotButtons[1]));
				button.setActive(false);
				button = (ToggleButton) saveMenu.getRootPane().getChild(saveMenu.getRootPane().getChildIndex(slotButtons[3]));
				button.setActive(false);
			}
		});
		saveGui.add(slotButtons[2]);
		
		slotButtons[3].addCallback(new Runnable(){
			public void run(){
				saveMenu.setSaveFile("save4");
				ToggleButton button = (ToggleButton) saveMenu.getRootPane().getChild(saveMenu.getRootPane().getChildIndex(slotButtons[0]));
				button.setActive(false);
				button = (ToggleButton) saveMenu.getRootPane().getChild(saveMenu.getRootPane().getChildIndex(slotButtons[1]));
				button.setActive(false);
				button = (ToggleButton) saveMenu.getRootPane().getChild(saveMenu.getRootPane().getChildIndex(slotButtons[2]));
				button.setActive(false);
			}
		});
		saveGui.add(slotButtons[3]);
		
		saveButton = new Button();
		saveButton.setTheme("button");
		saveButton.setText("Save!");
		saveButton.addCallback(new Runnable(){
			public void run(){
				try {
					saveMenu.save();
					saveMenu.getRootPane().reapplyTheme();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		saveGui.add(saveButton);
		
		loadButton = new Button();
		loadButton.setTheme("button");
		loadButton.setText("Load!");
		loadButton.addCallback(new Runnable(){
			public void run(){
				try {
					saveMenu.load();
					saveMenu.getRootPane().reapplyTheme();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		saveGui.add(loadButton);
		
		return saveGui;
    }
    
    @Override
    protected void layoutRootPane() {
    	int x = 30;
		int y = 60;
		
		for(Button button : slotButtons){
			button.setPosition(x, y);
			button.adjustSize();
			y += button.getHeight() + 10;
		}
		
		saveButton.setPosition(x, y);
		saveButton.adjustSize();
		y += saveButton.getHeight() + 10;
		
		loadButton.setPosition(x, y);
		loadButton.adjustSize();
		y += loadButton.getHeight() + 10;
    }
	
	public void setSaveFile(String name){
		this.fileName = "saves/"+name+".a 	wesome";
		this.file = new File(fileName);
	}
	
	public void createSaveFile(String name) throws IOException{
		String fileName = "saves/"+name+".awesome";
		File file = new File(fileName);
		if(!file.exists()){
			file.createNewFile();
		}
	}
	
	public void save() throws IOException{
		XStream xstream = new XStream();
		this.fileOut = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));	//allows us to write to the save file
		gameCore.saveGame(this.fileName);	//calls core's saveGame function which creates a GameSave file with the name fileName
		fileOut.write(xstream.toXML(gameCore.getSave()));	//turns that GameSave created into xml file 
		fileOut.close();	//closes the stream so the file is saved and refreshed
		System.out.println("file saved!");	
		JOptionPane.showMessageDialog(null, "Game successfully saved", "Success!", JOptionPane.PLAIN_MESSAGE);	//shows a message that the game was saved
		
	}
	
	public void load() throws SlickException, IOException{
		System.out.println(file.getAbsolutePath());
		XStream xstream = new XStream();
		StringBuffer stringBuffer = new StringBuffer();
		//read from the save file xml and write it into stringBuffer 
		this.fileIn = new BufferedReader(new FileReader(this.file));
		String line;
		while((line = fileIn.readLine()) != null){
			stringBuffer.append(line);
		}
		GameSave save = (GameSave)xstream.fromXML(stringBuffer.toString());	//converts the save xml file into a GameSave object named save
		gameCore.loadGame(save);	//calls core object's loadGame function passing the save object
		JOptionPane.showMessageDialog(null, "Game successfully loaded", "Success!", JOptionPane.PLAIN_MESSAGE);	//show a dialog box
		game.enterState(Game.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));	//change state to ingame
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException{
		this.game = (TWLStateBasedGame)game;
		this.gameCore = (Game)game.getState(Game.ID);
			try {
				createSaveFile("save1");
				createSaveFile("save2");
				createSaveFile("save3");
				createSaveFile("save4");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException{	
		g.drawString("Select a save slot and choose to load or save the game file.", 30, 30);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException{
		if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) game.enterState(Game.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
	}

	@Override
	public int getID(){
		return ID;
	}

}
