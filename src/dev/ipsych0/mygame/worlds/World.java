package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.crafting.CraftingUI;
import dev.ipsych0.mygame.entities.EntityManager;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemManager;
import dev.ipsych0.mygame.mapeditor.MapLoader;
import dev.ipsych0.mygame.mapeditor.MiniMap;
import dev.ipsych0.mygame.states.State;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.utils.Utils;

public abstract class World {
	
	// Variables
	protected Handler handler;
	protected MapLoader mapLoader;
	protected int width, height;
	protected int[][][] tiles;
	protected int spawnX, spawnY;
	protected Animation sparkles;
	protected int worldID;
	protected String[] file;
	
	// Entities
	
	protected EntityManager entityManager;
	
	// Items
	
	protected ItemManager itemManager;
	
	// MiniMap
	protected MiniMap miniMap;
	
	protected InventoryWindow inventory;
	protected EquipmentWindow equipment;
	protected CraftingUI craftingUI;
	protected Player player;
	
	// Actual code ---v
	
	public World(Handler handler){
		if(State.getState() == handler.getGame().gameState){
			this.handler = handler;
			
			entityManager = new EntityManager(handler, handler.getPlayer());
			
			// Create inv & equipmentscreen
			inventory = new InventoryWindow(handler, 828, 0);
			equipment = new EquipmentWindow(handler, 828, 372);
			itemManager = new ItemManager(handler);
			miniMap = new MiniMap(handler, "res/worlds/testmap.tmx", 220, 100, 400, 400);
			craftingUI = new CraftingUI(handler, 0, 180);
			
			
			// Dit is hoe ik items in de world zelf spawn
			itemManager.addItem(Item.woodItem.createNew(400, 400, 5));
			
			// World Animations
			sparkles = new Animation(250, Assets.sparkles);
			
		}
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	
	public Tiles getTile(int layer, int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tiles.blackTile;
			
		Tiles t = Tiles.tiles[tiles[layer][x][y]];
		if(t == null)
			return Tiles.invisible;
		return t;
	}

	public void loadWorld(String path){
		file = mapLoader.groundTileParser(path);
		tiles = new int[file.length][width][height];
		
		for (int i = 0; i < file.length; i++) {
			// Splits worlds files by spaces and puts them all in an array
			file[i] = file[i].replace("\n", "").replace("\r", "");
			file[i] = file[i].replace(" ", "").replace("\r", "");
			String[] tokens = file[i].split(",");
			
			for (int y = 0; y < height; y++){
				for (int x = 0; x < width; x++){
					// Loads in the actual tiles to the tiles[][][]
					tiles[i][x][y] = Utils.parseInt(tokens[(x + y * width)]);
				}
			}
		}
		
		
	}
	
	public World getWorldByID(int worldID) {
		return handler.getWorldHandler().getWorlds().get(worldID);
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

	public CraftingUI getCraftingUI() {
		return craftingUI;
	}

	public void setCraftingUI(CraftingUI craftingUI) {
		this.craftingUI = craftingUI;
	}

	public int getWorldID() {
		return worldID;
	}

	public void setWorldID(int worldID) {
		this.worldID = worldID;
	}

	public String[] getFile() {
		return file;
	}

	public void setFile(String[] file) {
		this.file = file;
	}
}
