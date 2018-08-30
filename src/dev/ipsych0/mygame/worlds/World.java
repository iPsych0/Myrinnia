package dev.ipsych0.mygame.worlds;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.abilities.AbilityManager;
import dev.ipsych0.mygame.bank.BankUI;
import dev.ipsych0.mygame.character.CharacterUI;
import dev.ipsych0.mygame.crafting.CraftingUI;
import dev.ipsych0.mygame.entities.EntityManager;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.hpoverlay.HPOverlay;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.ItemManager;
import dev.ipsych0.mygame.quests.QuestManager;
import dev.ipsych0.mygame.shop.ShopWindow;
import dev.ipsych0.mygame.skills.SkillsUI;
import dev.ipsych0.mygame.states.State;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.utils.MapLoader;
import dev.ipsych0.mygame.utils.Text;
import dev.ipsych0.mygame.utils.Utils;

public abstract class World implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Variables
	protected int width, height;
	protected int[][][] tiles;
	protected int spawnX, spawnY;
	protected String[] layers;
	private Color night = new Color(0, 13, 35);
	protected String worldPath;
	
	// Entities
	
	protected EntityManager entityManager;
	
	// Items
	
	protected ItemManager itemManager;
	
	protected InventoryWindow inventory;
	protected EquipmentWindow equipment;
	protected CraftingUI craftingUI;
	protected Player player;
	protected ChatWindow chatWindow;
	protected QuestManager questManager;
	protected CharacterUI characterUI;
	protected SkillsUI skillsUI;
	protected HPOverlay hpOverlay;
	protected AbilityManager abilityManager;
	
	// Actual code ---v
	
	public World(){
			
			// World-specific classes
			this.player = Handler.get().getPlayer();
			this.inventory = Handler.get().getInventory();
			this.equipment = Handler.get().getEquipment();
			this.chatWindow = Handler.get().getChatWindow();
			this.questManager = Handler.get().getQuestManager();
			this.characterUI = Handler.get().getCharacterUI();
			this.skillsUI = Handler.get().getSkillsUI();
			this.hpOverlay = Handler.get().getHpOverlay();
			this.abilityManager = Handler.get().getAbilityManager();
			
			entityManager = new EntityManager(player);
			itemManager = new ItemManager();
			craftingUI = Handler.get().getCraftingUI();
		
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
		skillsUI.tick();
		hpOverlay.tick();
		abilityManager.tick();
		if(BankUI.isOpen)
			Handler.get().getBankUI().tick();
		if(ShopWindow.isOpen && player.getShopKeeper() != null)
			player.getShopKeeper().getShopWindow().tick();
		
	}
	
	public void render(Graphics g) {
		// Set variables for rendering only the tiles that show on screen
		int xStart = (int) Math.max(0, Handler.get().getGameCamera().getxOffset() / Tiles.TILEWIDTH);
		int xEnd = (int) Math.min(width, (Handler.get().getGameCamera().getxOffset() + Handler.get().getWidth()) / Tiles.TILEWIDTH + 1);
		int yStart = (int) Math.max(0, Handler.get().getGameCamera().getyOffset() / Tiles.TILEHEIGHT);
		int yEnd = (int) Math.min(height, (Handler.get().getGameCamera().getyOffset() + Handler.get().getHeight()) / Tiles.TILEHEIGHT + 1);
		
		// Render the tiles
		
		for (int i = 0; i < layers.length; i++) {
			for(int y = yStart; y < yEnd; y++){
				for(int x = xStart; x < xEnd; x++){
					Tiles t = getTile(i,x,y);
					if(t != Tiles.tiles[736]) {
						t.render(g, (int) (x * Tiles.TILEWIDTH - Handler.get().getGameCamera().getxOffset()), 
								(int) (y * Tiles.TILEHEIGHT - Handler.get().getGameCamera().getyOffset()));
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
		
		hpOverlay.render(g);
		
		// Inventory & Equipment
		equipment.render(g);
		inventory.render(g);
		
		
		// MiniMap
		craftingUI.render(g);
		
		questManager.render(g);
		characterUI.render(g);
		skillsUI.render(g);
		
		abilityManager.render(g);
		
		if(BankUI.isOpen && player.getBankEntity() != null) {
			Handler.get().getBankUI().render(g);
			Text.drawString(g, "Bank of Myrinnia", BankUI.x + (BankUI.width / 2), BankUI.y + 16, true, Color.YELLOW, Assets.font14);
		}
		
	}
	
	public Tiles getTile(int layer, int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tiles.tiles[28];
			
		Tiles t = Tiles.tiles[tiles[layer][x][y]];
		if(t == null)
			return Tiles.tiles[736];
		return t;
	}
	
	protected boolean standingOnTile(Rectangle box) {
		if(box.intersects(player.getCollisionBounds(0, 0))) {
			return true;
		}else {
			return false;
		}
	}
	
	protected void renderNight(Graphics g) {
		float alpha = 0.6f;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		((Graphics2D) g).setComposite(ac);
		g.setColor(night);
		g.fillRect(0, 0, Handler.get().getWidth(), Handler.get().getHeight());
		alpha = 1.0f;
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		((Graphics2D) g).setComposite(ac);
	}

	protected void loadWorld(String path){
		layers = MapLoader.getMapTiles(path);
		tiles = new int[layers.length][width][height];
		
		for (int i = 0; i < layers.length; i++) {
			// Splits worlds files by spaces and puts them all in an array
			layers[i] = layers[i].replace("\n", "").replace("\r", "").trim();
			String[] tokens = layers[i].split(",");
			
			for (int y = 0; y < height; y++){
				for (int x = 0; x < width; x++){
					// Loads in the actual tiles to the tiles[][][]
					tiles[i][x][y] = Utils.parseInt(tokens[(x + y * width)]);
				}
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

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
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

	public String getWorldPath() {
		return worldPath;
	}

	public void setWorldPath(String worldPath) {
		this.worldPath = worldPath;
	}

}
