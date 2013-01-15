package game;

import game.builders.NpcBuilder;
import game.components.MapMovement;
import game.components.Movement;
import game.components.Npc;
import game.data.GameSave;
import game.managers.CollisionManager;
import game.managers.InteractionManager;
import game.managers.NpcManager;
import game.spatials.MapSpatial;
import game.spatials.PlayerSpatial;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

import TWLSlick.BasicTWLGameState;
import TWLSlick.TWLStateBasedGame;

import com.apollo.Entity;
import com.apollo.EventHandler;
import com.apollo.World;
import com.apollo.components.Transform;
import com.apollo.managers.GroupManager;
import com.apollo.managers.RenderManager;
import com.apollo.managers.TagManager;
import com.apollo.utils.Bag;

public class Core extends BasicTWLGameState{
	
	public static final int WIDTH = 640;	//GameContainer's width
	public static final int HEIGHT = 480;	//GameContainer's height
	
	public static final int ID = 0;	//unique ID for ingame state
	
	private GameContainer gc;	//window that the game is being displayed on
	
	private TWLStateBasedGame game;	//get game application controlling states
	
	private World world;	//stores and holds all the entities and their components
	private RenderManager<Graphics> renderManager;	//renders entities with a Spatial component
	private TagManager tagManager;	//manages tags, which are specially marked, unique entities (such as player and world)
	private GroupManager groupManager;
	private NpcManager npcManager;
	private CollisionManager collisionManager;
	private InteractionManager interactionManager;
	
	private GameSave save;
	
	public GameSave getSave() {
		return save;
	}

	//returns unique ingame state ID
	public int getID(){
		return ID;
	}
	
	//initializes the GameContainer and calls createWorld()
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException{
		this.gc = gc;
		this.game = (TWLStateBasedGame) game;
		createWorld();
	}
	
	public void saveGame(String name){
		System.out.println("trying to save");
		Entity player = tagManager.getEntity(Tags.Player);
		this.save = new GameSave(name);
		this.save.setSave(player.getComponent(Transform.class).getX(), player.getComponent(Transform.class).getY());
	}
	
	public void loadGame(GameSave save){
		Entity player = tagManager.getEntity(Tags.Player);
		player.getComponent(Transform.class).setLocation(save.getSaveX(), save.getSaveY());
	}
	
	public World getWorld(){
		return this.world;
	}
	
	private void updateEntities(){
		groupManager.getEntityGroup(Groups.RenderedNpc).clear();
		Bag<Entity> ents = world.getEntityManager().getEntitiesByComponentType(Npc.class);
		Entity map = tagManager.getEntity(Tags.Map);
		float mapMinX = map.getComponent(MapMovement.class).getCamX() - 50;
		float mapMaxX = map.getComponent(MapMovement.class).getCamX() + WIDTH + 50;
		float mapMinY = map.getComponent(MapMovement.class).getCamY() - 50;
		float mapMaxY = map.getComponent(MapMovement.class).getCamY() + HEIGHT + 50;
		
		for (int i = 0; i < ents.size(); i++){
			if(ents.get(i).getComponent(Transform.class).getX() < mapMinX || ents.get(i).getComponent(Transform.class).getX() > mapMaxX ){
				ents.get(i).getComponent(Npc.class).setDraw(false);
			}else if(ents.get(i).getComponent(Transform.class).getY() < mapMinY || ents.get(i).getComponent(Transform.class).getY() > mapMaxY ){
				ents.get(i).getComponent(Npc.class).setDraw(false);
			}else{
				ents.get(i).getComponent(Npc.class).setDraw(true);
				groupManager.getEntityGroup(Groups.RenderedNpc).add(ents.get(i));
			}
		}	
	}
	
	//creates a world instance, initializes managers, and creates a player and map entity.
	private void createWorld() throws SlickException{
		world = new World();
		renderManager = new RenderManager<Graphics>(gc.getGraphics());
		tagManager = new TagManager();
		groupManager = new GroupManager();
		npcManager = new NpcManager();
		collisionManager = new CollisionManager();
		interactionManager = new InteractionManager();
		
		world.setManager(renderManager);
		world.setManager(tagManager);
		world.setManager(groupManager);
		world.setManager(npcManager);
		world.setManager(collisionManager);
		world.setManager(interactionManager);
		
		world.setEntityBuilder("Npc", new NpcBuilder());
		
		createPlayer();
		createMap();
	}
	
	//creates a world entity with special MapMovement and MapSpatial components and tags the entity with Tags.Map
	private void createMap() throws SlickException{
		Entity map = new Entity(world);
		map.setComponent(new MapSpatial(gc, new TiledMap("world/Map1.tmx")));
		map.setComponent(new MapMovement(gc, new TiledMap("world/Map1.tmx")));
		world.addEntity(map);
		tagManager.register(Tags.Map, map);
	}

	//creates a player entity with Transform, Movement, and PlayerSpatial components and tags the entity with Tags.Player
	private void createPlayer() throws SlickException{
		Entity player = new Entity(world);
		player.setComponent(new Transform(0,0));	//starting location
		player.setComponent(new Movement());
		player.setComponent(new PlayerSpatial());
		player.addEventHandler("INTERACT", new EventHandler(){
			@Override
			public void handleEvent() {
					
			}
		});
		world.addEntity(player);
		tagManager.register(Tags.Player, player);
	}
	
	//update is called over and over by the AppGameContainer in main, delta is the time in between each update
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException{
		world.update(delta); //update the world's entities with time delta
		Entity player = tagManager.getEntity(Tags.Player); //gets the player entity and is set to the local player entity created
		Entity map = tagManager.getEntity(Tags.Map);	//get the map entity and sets it to local map
		//bounds the player to the edge of the map, only lets player move when playerY+displacement from movement<height of the map
		if(gc.getInput().isKeyDown(Input.KEY_S)&&(player.getComponent(Transform.class).getY()+(player.getComponent(Movement.class).getVy()*delta)+48)<map.getComponent(MapMovement.class).mapHeight){
			player.getComponent(Movement.class).setDirection(0);
			player.getComponent(Movement.class).setMoving(true);
		}
		//bounds the player to the edge of the map, only lets player move when playerX-displacement from movement>=0)
		if(gc.getInput().isKeyDown(Input.KEY_A)&&(player.getComponent(Transform.class).getX()-(player.getComponent(Movement.class).getVx()*delta))>=0){
			player.getComponent(Movement.class).setDirection(1);
			player.getComponent(Movement.class).setMoving(true);
		}
		//bounds the player to the edge of the map, only lets player move when playerX+displacement from movement<width of the map
		if(gc.getInput().isKeyDown(Input.KEY_D)&&(player.getComponent(Transform.class).getX()+(player.getComponent(Movement.class).getVx()*delta)+33)<map.getComponent(MapMovement.class).mapWidth){
			player.getComponent(Movement.class).setDirection(2);
			player.getComponent(Movement.class).setMoving(true);
		}
		//bounds the player to the edge of the map, only lets player move when playerY-displacement from movement>=0)
		if(gc.getInput().isKeyDown(Input.KEY_W)&&(player.getComponent(Transform.class).getY()-(player.getComponent(Movement.class).getVy()*delta))>=0){
			player.getComponent(Movement.class).setDirection(3);
			player.getComponent(Movement.class).setMoving(true);
		}
		if(gc.getInput().isKeyDown(Input.KEY_SPACE)){
			if(tagManager.getEntity(Tags.InteractingNpc)!=null){
				Entity npc = tagManager.getEntity(Tags.InteractingNpc);
				((Interaction) game.getState(Interaction.ID)).setOrigDirection(npc.getComponent(Npc.class).getDirection());
				if (!npc.getComponent(Npc.class).isInteracting()){
					switch (player.getComponent(Movement.class).getDirection()){
					case 0:	npc.getComponent(Npc.class).setDirection(3);	break;
					case 3:	npc.getComponent(Npc.class).setDirection(0);	break;
					case 1:	npc.getComponent(Npc.class).setDirection(2);	break;
					case 2:	npc.getComponent(Npc.class).setDirection(1);	break;
					}
					npc.getComponent(Npc.class).setInteracting(true);
					((Interaction) game.getState(Interaction.ID)).resetDialog();
					this.game.enterState(Interaction.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
				}
			}
		}
		if(gc.getInput().isKeyDown(Input.KEY_ESCAPE))	game.enterState(Menu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));	//fades into the menu
		if(!player.getComponent(Movement.class).isMoving()){
		    player.getComponent(Movement.class).setVectors(0, 0);	//if not moving, set movement vector to 0,0
		    player.getComponent(Movement.class).setAnimate(false);	//don't animate because player is not moving
		}else{
			switch (player.getComponent(Movement.class).getDirection()){
			case 0: player.getComponent(Movement.class).setVectors(0, 0.175f); break;	//makes player move down
			case 1: player.getComponent(Movement.class).setVectors(-0.175f, 0); break;	//makes player move left
			case 2: player.getComponent(Movement.class).setVectors(0.175f, 0); break;	//makes player move right
			case 3: player.getComponent(Movement.class).setVectors(0, -0.175f); break;	//makes player move up
			}
			player.getComponent(Movement.class).setAnimate(true);	//set animate true because player is moving
		}
		interactionManager.update(delta);
		player.getComponent(Transform.class).update(delta);
		player.getComponent(Movement.class).setMoving(false);	//sets moving to false, necessary because there is no method to call when input is released so moving must be set false after ever update loop
		map.getComponent(MapMovement.class).updateMap(player);	//updates the map to render relative to the position of the player
		updateEntities();
	}
	
	//renders all entities with spatials
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException{
		renderManager.render(g);	//renders the world with graphics g
	}
}
