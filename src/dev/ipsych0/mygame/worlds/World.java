package dev.ipsych0.mygame.worlds;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.crafting.CraftingUI;
import dev.ipsych0.mygame.entities.EntityManager;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
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
import dev.ipsych0.mygame.utils.Text;
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
	protected String[] layers;
	private Color night = new Color(0, 13, 35);
	
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
			
			// World-specific classes
			entityManager = new EntityManager(handler, handler.getPlayer());
			itemManager = new ItemManager(handler);
			mapLoader = new MapLoader();
			miniMap = new MiniMap(handler, mapLoader, "res/worlds/testmap.tmx", 220, 100, 400, 400);
			craftingUI = new CraftingUI(handler, 0, 180);
			
			
			// Dit is hoe ik items in de world zelf spawn
			itemManager.addItem(Item.woodItem.createNew(400, 400, 5));
			
			// World Animations
			sparkles = new Animation(250, Assets.sparkles);
			
		}
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public boolean checkSolidLayer(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height)
			return true;
		for(int i = 0; i < layers.length; i++) {
			if(getTile(i, x, y).isSolid()) {
				return true;
			}
		}
		return false;
	}
	
	
	public Tiles getTile(int layer, int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tiles.blackTile;
			
		Tiles t = Tiles.tiles[tiles[layer][x][y]];
		if(t == null)
			return Tiles.invisible;
		return t;
	}
	
	protected void renderNight(Graphics g) {
		float alpha = 0.6f;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		((Graphics2D) g).setComposite(ac);
		g.setColor(night);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		alpha = 1.0f;
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		((Graphics2D) g).setComposite(ac);
	}
	
	protected void renderHPandFPS(Graphics g) {
		g.setFont(Assets.font14);
		g.setColor(Creature.hpColor);
		g.drawImage(Assets.hpOverlay, 0, 0, 292, 96, null);
		g.drawString("HP: " + handler.roundOff((double)handler.getPlayer().getHealth() / (double)handler.getPlayer().getMAX_HEALTH() * 100) + "%", 146, 34);
		
		Text.drawString(g, "Lv. ", 36, 28, false, Color.YELLOW, Assets.font20);
		Text.drawString(g, Integer.toString(handler.getPlayer().getAttackLevel()), 45, 64, true, Color.YELLOW, Assets.font32);
		
//		g.drawString("FPS: " + String.valueOf(handler.getGame().getFramesPerSecond()), 2, 140);
	}

	protected void loadWorld(String path){
		layers = mapLoader.groundTileParser(path);
		tiles = new int[layers.length][width][height];
		
		for (int i = 0; i < layers.length; i++) {
			// Splits worlds files by spaces and puts them all in an array
			layers[i] = layers[i].replace("\n", "").replace("\r", "");
			layers[i] = layers[i].replace(" ", "").replace("\r", "");
			String[] tokens = layers[i].split(",");
			
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

	public String[] getLayers() {
		return layers;
	}

	public void setLayers(String[] layers) {
		this.layers = layers;
	}

	public ChatWindow getChatWindow() {
		return chatWindow;
	}

	public void setChatWindow(ChatWindow chatWindow) {
		this.chatWindow = chatWindow;
	}
}
