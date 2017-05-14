package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.EntityManager;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.creatures.Scorpion;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.entities.statics.Rock;
import dev.ipsych0.mygame.entities.statics.TeleportShrine1;
import dev.ipsych0.mygame.entities.statics.TeleportShrine2;
import dev.ipsych0.mygame.entities.statics.Tree;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.hpscreen.StatScreen;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.ItemManager;
import dev.ipsych0.mygame.mapeditor.MapLoader;
import dev.ipsych0.mygame.mapeditor.MiniMap;
import dev.ipsych0.mygame.states.State;
import dev.ipsych0.mygame.tiles.Ambiance;
import dev.ipsych0.mygame.tiles.Terrain;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.utils.Utils;

public class World {
	
	// Variables
	private Handler handler;
	private MapLoader mapLoader;
	private int width, height;
	private int[][] tiles;
	private int[][] terrain;
	private int[][] ambiance;
	private int spawnX, spawnY;
	private Animation sparkles;
	
	// Entities
	
	private EntityManager entityManager;
	
	// Items
	
	private ItemManager itemManager;
	private InventoryWindow inventory;
	
	// Equipment
	
	private EquipmentWindow equipment;
	
	// NPC ChatWindow
	
	private ChatWindow chatWindow;
	
	// HP Interface
	
	private StatScreen statScreen;
	
	// MiniMap
	private MiniMap miniMap;
	
	// Actual code ---v
	
	public World(Handler handler, String path){
		if(State.getState() == handler.getGame().gameState){
			
			mapLoader = new MapLoader();
			loadGroundTiles(path);
			loadTerrainTiles(path);
			loadAmbianceTiles(path);
			
			this.handler = handler;
			itemManager = new ItemManager(handler);
			entityManager = new EntityManager(handler, new Player(handler, 100, 100));
			inventory = new InventoryWindow(handler, 658, 112);
			equipment = new EquipmentWindow(handler, 658, 466);
			statScreen = new StatScreen(handler, 658, 0);
			miniMap = new MiniMap(handler, path, 0, 0, 400, 400);
			
			
			entityManager.addEntity(new Lorraine(handler, 732, 640));
			
			entityManager.addEntity(new Tree(handler, 160, 128));
			entityManager.addEntity(new Tree(handler, 128, 128));
			entityManager.addEntity(new Tree(handler, 96, 192));
			entityManager.addEntity(new Tree(handler, 96, 160));
			
			entityManager.addEntity(new Rock(handler, 448, 576));
			
			entityManager.addEntity(new Scorpion(handler, 160, 576));
			entityManager.addEntity(new Scorpion(handler, 128, 800));
			entityManager.addEntity(new Scorpion(handler, 128, 888));
			entityManager.addEntity(new Scorpion(handler, 128, 944));
			entityManager.addEntity(new Scorpion(handler, 190, 944));
			entityManager.addEntity(new Scorpion(handler, 190, 888));
			entityManager.addEntity(new Scorpion(handler, 190, 800));
			
			entityManager.addEntity(new TeleportShrine2(handler, 200, 200));
			entityManager.addEntity(new TeleportShrine1(handler, 200, 168));
			
			entityManager.getPlayer().setX(spawnX);
			entityManager.getPlayer().setY(spawnY);
			
			// Dit is hoe ik items in de world zelf spawn
			// itemManager.addItem(Item.woodItem.createNew(512, 576));
			
			chatWindow = new ChatWindow(handler, 228, 320);
			chatWindow.sendMessage("Welcome to Myrinnia!");
			
			// World Animations
			sparkles = new Animation(250, Assets.sparkles);
		}
	}
	
	public void tick(){
		itemManager.tick();
		entityManager.tick();
		inventory.tick();
		chatWindow.tick();
		equipment.tick();
		statScreen.tick();
		sparkles.tick();
		miniMap.tick();
	}
	
	public void render(Graphics g){
		// Set variables for rendering only the tiles that show on screen
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tiles.TILEWIDTH);
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tiles.TILEWIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tiles.TILEHEIGHT);
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tiles.TILEHEIGHT + 1);
		
		// Render the tiles
		for(int y = yStart; y < yEnd; y++){
			for(int x = xStart; x < xEnd; x++){
				getTile(x,y).render(g, (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
						(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		
		// Render the terrain tiles
		for(int y = yStart; y < yEnd; y++){
			for(int x = xStart; x < xEnd; x++){
				getTerrain(x,y).render(g, (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
						(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		
		// Render the ambiance tiles
		for(int y = yStart; y < yEnd; y++){
			for(int x = xStart; x < xEnd; x++){
				if(getAmbiance(x, y) == Ambiance.sparkleTile){
					g.drawImage(sparkles.getCurrentFrame(), (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
							(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()), null);
				}
				getAmbiance(x,y).render(g, (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
						(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		
		// Items
		
		itemManager.render(g);
		
		// Entities
		entityManager.render(g);
		
		// Inventory
		inventory.render(g);
		
		// NPC ChatWindow
		
		chatWindow.render(g);
		
		// Equipment
		
		equipment.render(g);
		
		// HP Screen
		statScreen.render(g);
		
		// MiniMap
		miniMap.render(g);
	}
	
	
	public Tiles getTile(int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tiles.blackTile;
			
		
		Tiles t = Tiles.tiles[tiles[x][y]];
		if(t == null)
			return Tiles.blackTile;
		return t;
	}
	
	public Terrain getTerrain(int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Terrain.blackTile;
			
		
		Terrain t = Terrain.terrain[terrain[x][y]];
		if(t == null)
			return Terrain.invisible;
		return t;
	}
	
	public Ambiance getAmbiance(int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Ambiance.blackTile;
			
		
		Ambiance t = Ambiance.ambiance[ambiance[x][y]];
		if(t == null)
			return Ambiance.invisible;
		return t;
	}

	public void loadGroundTiles(String path){
		String file = mapLoader.groundTileParser(path);
		
		// Splits worlds files by spaces and puts them all in an array
		file = file.replace("\n", "").replace("\r", "");
		String[] tokens = file.split(",");
		
		width = 50;
		height = 50;
		spawnX = 256;
		spawnY = 160;
		
		tiles = new int[width][height];
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				// Loads in the actual tiles, +4 to skip the first 4 pieces of metadata
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width)]);
			}
		}
	}
	
	public void loadTerrainTiles(String path){
		String file = mapLoader.terrainTileParser(path);
		
		// Splits worlds files by spaces and puts them all in an array
		file = file.replace("\n", "").replace("\r", "");
		String[] tokens = file.split(",");
		
		terrain = new int[width][height];
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				// Loads in the actual tiles, +4 to skip the first 4 pieces of metadata
				terrain[x][y] = Utils.parseInt(tokens[(x + y * width)]);
			}
		}
	}
	
	public void loadAmbianceTiles(String path){
		String file = mapLoader.ambianceTileParser(path);
		
		// Splits worlds files by spaces and puts them all in an array
		file = file.replace("\n", "").replace("\r", "");
		String[] tokens = file.split(",");
		
		ambiance = new int[width][height];
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				// Loads in the actual tiles, +4 to skip the first 4 pieces of metadata
				ambiance[x][y] = Utils.parseInt(tokens[(x + y * width)]);
			}
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public ChatWindow getChatWindow() {
		return chatWindow;
	}

	public void setChatWindow(ChatWindow chatWindow) {
		this.chatWindow = chatWindow;
	}

	public InventoryWindow getInventory() {
		return inventory;
	}

	public void setInventory(InventoryWindow inventory) {
		this.inventory = inventory;
	}

	public StatScreen getStatScreen() {
		return statScreen;
	}

	public void setStatScreen(StatScreen statScreen) {
		this.statScreen = statScreen;
	}
}
