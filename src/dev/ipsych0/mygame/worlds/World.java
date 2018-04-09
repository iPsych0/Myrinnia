package dev.ipsych0.mygame.worlds;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.character.CharacterUI;
import dev.ipsych0.mygame.crafting.CraftingUI;
import dev.ipsych0.mygame.entities.EntityManager;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.ItemManager;
import dev.ipsych0.mygame.mapeditor.MapLoader;
import dev.ipsych0.mygame.mapeditor.MiniMap;
import dev.ipsych0.mygame.quests.QuestManager;
import dev.ipsych0.mygame.states.State;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.utils.Text;
import dev.ipsych0.mygame.utils.Utils;

public abstract class World implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Variables
	protected Handler handler;
	protected MapLoader mapLoader;
	protected int width, height;
	protected int[][][] tiles;
	protected int spawnX, spawnY;
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
	protected QuestManager questManager;
	protected CharacterUI characterUI;
	
	// Actual code ---v
	
	public World(Handler handler){
		if(State.getState() == handler.getGame().gameState){
			this.handler = handler;
			
			// World-specific classes
			this.player = handler.getPlayer();
			this.inventory = handler.getInventory();
			this.equipment = handler.getEquipment();
			this.chatWindow = handler.getChatWindow();
			this.questManager = handler.getQuestManager();
			this.characterUI = handler.getCharacterUI();
			
			entityManager = new EntityManager(handler, player);
			itemManager = new ItemManager(handler);
			mapLoader = handler.getMapLoader();
			miniMap = new MiniMap(handler, "res/worlds/testmap.tmx", 220, 100, 400, 400);
			craftingUI = handler.getCraftingUI();
		}
	}
	
	public void tick() {
		itemManager.tick();
		entityManager.tick();
		inventory.tick();
		equipment.tick();
		craftingUI.tick();
		chatWindow.tick();
		questManager.tick();
		characterUI.tick();
	}
	
	public void render(Graphics g) {
		// Set variables for rendering only the tiles that show on screen
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tiles.TILEWIDTH);
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tiles.TILEWIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tiles.TILEHEIGHT);
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tiles.TILEHEIGHT + 1);
		
		// Render the tiles
		
		for (int i = 0; i < layers.length; i++) {
			for(int y = yStart; y < yEnd; y++){
				for(int x = xStart; x < xEnd; x++){
					if(getTile(i,x,y) != Tiles.invisible) {
						getTile(i,x,y).render(g, (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
								(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()));
					}
				}
			}
		}
		
		// Items
		
		itemManager.render(g);
		
		// Entities & chat
		entityManager.render(g);
		chatWindow.render(g);
		entityManager.postRender(g);
		
		/* Uncomment to 
		if(nightTime) {
			renderNight(g);
		}
		*/
		
		renderHPandFPS(g);
		
		// Inventory & Equipment
		inventory.render(g);
		equipment.render(g);
		
		
		// MiniMap
		miniMap.render(g);
		craftingUI.render(g);
		
		questManager.render(g);
		characterUI.render(g);
	}
	
	public Tiles getTile(int layer, int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tiles.blackTile;
			
		Tiles t = Tiles.tiles[tiles[layer][x][y]];
		if(t == null)
			return Tiles.invisible;
		return t;
	}
	
	protected boolean standingOnTile(Rectangle box) {
		if(box.intersects(player.getCollisionBounds(0, 0))) {
			return true;
		}else {
			return false;
		}
	}
	
	protected void goToWorld(int worldID, int x, int y) {
		player.setX(x);
		player.setY(y);
		handler.setWorld(handler.getWorldHandler().getWorlds().get(worldID));
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
		g.drawImage(Assets.hpOverlay, 0, 0, 292, 96, null);
		Text.drawString(g, "HP: " + handler.roundOff((double)handler.getPlayer().getHealth() /
				(double)handler.getPlayer().getMAX_HEALTH() * 100) + "%", 146, 34, false, Color.YELLOW, Assets.font14);		
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

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
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

	public ChatWindow getChatWindow() {
		return chatWindow;
	}

	public void setChatWindow(ChatWindow chatWindow) {
		this.chatWindow = chatWindow;
	}

	public QuestManager getQuestManager() {
		return questManager;
	}

	public void setQuestManager(QuestManager questManager) {
		this.questManager = questManager;
	}
}
