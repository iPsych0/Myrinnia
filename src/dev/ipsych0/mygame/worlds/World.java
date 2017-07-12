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
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemManager;
import dev.ipsych0.mygame.mapeditor.MapLoader;
import dev.ipsych0.mygame.mapeditor.MiniMap;
import dev.ipsych0.mygame.states.State;
import dev.ipsych0.mygame.statscreen.StatScreen;
import dev.ipsych0.mygame.tiles.Ambiance;
import dev.ipsych0.mygame.tiles.Terrain;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.utils.Utils;

public abstract class World {
	
	// Variables
	protected Handler handler;
	protected MapLoader mapLoader;
	protected int width, height;
	protected int[][] tiles;
	protected int[][] terrain;
	protected int[][] ambiance;
	protected int spawnX, spawnY;
	protected Animation sparkles;
	
	// Entities
	
	protected EntityManager entityManager;
	
	// Items
	
	protected ItemManager itemManager;
	
	// MiniMap
	protected MiniMap miniMap;
	
	protected InventoryWindow inventory;
	protected EquipmentWindow equipment;
	protected StatScreen statScreen;
	
	// Actual code ---v
	
	public World(Handler handler){
		if(State.getState() == handler.getGame().gameState){
			this.handler = handler;
			
			
			entityManager = new EntityManager(handler, handler.getPlayer());
			
			// Create inv & equipmentscreen
			inventory = new InventoryWindow(handler, 828, 0);
			equipment = new EquipmentWindow(handler, 828, 372);
			statScreen = new StatScreen(handler, 827, 481);
			itemManager = new ItemManager(handler);
			miniMap = new MiniMap(handler, "res/worlds/testmap.tmx", 220, 100, 400, 400);
			
			
			// Dit is hoe ik items in de world zelf spawn
			 itemManager.addItem(Item.woodItem.createNew(400, 400, 5));
			
			// World Animations
			sparkles = new Animation(250, Assets.sparkles);
		}
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	
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
		
		width = mapLoader.getMapWidth(path);
		height = mapLoader.getMapHeight(path);
		
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

	public InventoryWindow getInventory() {
		return inventory;
	}

	public void setInventory(InventoryWindow inventory) {
		this.inventory = inventory;
	}

	public EquipmentWindow getEquipment() {
		return equipment;
	}

	public void setEquipment(EquipmentWindow equipment) {
		this.equipment = equipment;
	}

	public StatScreen getStatScreen() {
		return statScreen;
	}

	public void setStatScreen(StatScreen statScreen) {
		this.statScreen = statScreen;
	}
}
