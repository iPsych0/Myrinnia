package dev.ipsych0.mygame.worlds;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.crafting.CraftingUI;
import dev.ipsych0.mygame.entities.EntityManager;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;
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
	protected ChatWindow chatWindow;
	
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
			chatWindow = new ChatWindow(handler, 0, 608); //228,314
			chatWindow.sendMessage("Welcome back!");
			
			
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
	
	protected void renderNight(Graphics g) {
		/* UNCOMMENT FOR NIGHT-TIME!!!
		float alpha = 0.4f;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		((Graphics2D) g).setComposite(ac);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		alpha = 1.0f;
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		((Graphics2D) g).setComposite(ac);
		*/
	}
	
	protected void renderHPandFPS(Graphics g) {
		g.setFont(Assets.font14);
		g.setColor(Creature.hpColor);
		g.drawImage(Assets.hpOverlay, 0, 0, 292, 96, null);
		g.drawString("HP: " + Handler.roundOff((double)handler.getPlayer().getHealth() / (double)handler.getPlayer().getMAX_HEALTH() * 100, 2) + "%", 146, 34);
		
		Text.drawString(g, "Lv. ", 36, 28, false, Color.YELLOW, Assets.font20);
		Text.drawString(g, Integer.toString(handler.getPlayer().getAttackLevel()), 42, 64, true, Color.YELLOW, Assets.font32);
		
		g.drawString("FPS: " + String.valueOf(handler.getGame().getFramesPerSecond()), 2, 140);
	}

	protected void loadWorld(String path){
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

	public ChatWindow getChatWindow() {
		return chatWindow;
	}

	public void setChatWindow(ChatWindow chatWindow) {
		this.chatWindow = chatWindow;
	}
}
