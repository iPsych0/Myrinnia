package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.entities.EntityManager;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.creatures.Scorpion;
import dev.ipsych0.mygame.entities.statics.Rock;
import dev.ipsych0.mygame.entities.statics.Tree;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.ItemManager;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.utils.Utils;

public class UnderWorld {
	
	private Handler handler;
	private int width, height;
	private int[][] tiles;
	private int spawnX, spawnY;
	
	// Entities
	
	private EntityManager entityManager;
	
	// Items
	
	private ItemManager itemManager;
	private InventoryWindow inventory;
	
	public UnderWorld(Handler handler, String path){
		this.handler = handler;
		itemManager = new ItemManager(handler);
		entityManager = new EntityManager(handler, new Player(handler, 100, 100));
		entityManager.addEntity(new Tree(handler, 192, 128));
		entityManager.addEntity(new Tree(handler, 64, 160));
		entityManager.addEntity(new Tree(handler, 64, 192));
		entityManager.addEntity(new Tree(handler, 96, 128));
		entityManager.addEntity(new Tree(handler, 96, 160));
		entityManager.addEntity(new Tree(handler, 96, 192));
		entityManager.addEntity(new Rock(handler, 224, 160));
		entityManager.addEntity(new Rock(handler, 256, 160));
		entityManager.addEntity(new Scorpion(handler, 400, 400));
		entityManager.addEntity(new Scorpion(handler, 420, 420));
		
		loadWorld(path);
		
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
		
		inventory = new InventoryWindow(handler, 80, 64);
	}
	
	public void tick(){
		itemManager.tick();
		entityManager.tick();
		inventory.tick();
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
		
		// Items
		
		itemManager.render(g);
		
		// Entities
		entityManager.render(g);
		
		// Inventory
		inventory.render(g);
	}
	
	
	public Tiles getTile(int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tiles.grassTile;
			
		
		Tiles t = Tiles.tiles[tiles[x][y]];
		if(t == null)
			return Tiles.dirtTile;
		return t;
	}

	private void loadWorld(String path){
		String file = Utils.loadFileAsString(path);
		
		// Splits worlds files by spaces and puts them all in an array
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		tiles = new int[width][height];
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				// Loads in the actual tiles, +4 to skip the first 4 pieces of metadata
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
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
}