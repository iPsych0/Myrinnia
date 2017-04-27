package dev.ipsych0.mygame.items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;

public class Item {
	
	
	// ItemList
	
	public static final int ITEMWIDTH = 24, ITEMHEIGHT = 24, PICKED_UP = -1;
	public static Item[] items = new Item[256];
	public static Item woodItem = new Item(Assets.wood, "Wood", 0, ItemTypes.CRAFTING_MATERIAL);
	public static Item oreItem = new Item(Assets.ore, "Ore", 1, ItemTypes.CRAFTING_MATERIAL);
	public static Item coinsItem = new Item(Assets.coins[0], "Coins", 2, ItemTypes.CURRENCY);
	
	// Class
	
	protected Handler handler;
	protected ItemTypes itemType;
	protected BufferedImage texture;
	protected String name;
	protected final int id;
	protected int x, y;
	protected Rectangle bounds;
	private static int count;
	private InventoryWindow inventoryWindow;
	public static boolean pickUpKeyPressed = false;
	
	
	public Item(BufferedImage texture, String name, int id, ItemTypes itemType){
		this.texture = texture;
		this.name = name;
		this.id = id;
		this.itemType = itemType;
		
		items[id] = this;
		bounds = new Rectangle(0, 0, ITEMWIDTH, ITEMHEIGHT);
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		if(handler == null)
			return;
		render(g, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
	}
	
	public Item createNew(int x, int y){
		Item i = new Item(texture, name, id, itemType);
		i.setPosition(x, y);
		return i;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Rectangle itemPosition(float xOffset, float yOffset){
		//
		bounds.x = 1;
		bounds.y = 1;
		bounds.width = 48;
		bounds.height = 48;
		
		Rectangle ir = new Rectangle();
		int arSize = ITEMWIDTH;
		ir.width = arSize;
		ir.height = arSize;
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	public boolean pickUpItem (Item item, int amount) {
		inventoryWindow = new InventoryWindow(handler, 80, 64);
        int inventoryIndex = InventoryWindow.findFreeSlot(item);
        if (inventoryIndex >= 0) {
            if(item.getName() == name){
            	inventoryWindow.getItemSlots().get(inventoryIndex).addItem(item, amount);
            	System.out.println("Picked up item '" + item.name + "' of type: " + item.itemType.toString());
            	item.setCount(PICKED_UP);
            	return true;
        	}
            System.out.println("Something went wrong picking up this item.");
            return false;
        }
    	System.out.println("Inventory is full!");
    	return false;
    }
	
	
	// Getters & Setters

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		Item.count = count;
	}

	public int getId() {
		return id;
	}

}
