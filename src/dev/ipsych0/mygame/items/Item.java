package dev.ipsych0.mygame.items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;

public class Item {
	
	// Handler
	
	public static Item[] items = new Item[256];
	public static Item woodItem = new Item(Assets.wood, "Wood", 0);
	public static Item oreItem = new Item(Assets.ore, "Ore", 1);
	private InventoryWindow inventoryWindow;
	public static boolean pickUpKeyPressed = false;
	protected Rectangle bounds;
	
	// Class
	
	public static final int ITEMWIDTH = 24, ITEMHEIGHT = 24, PICKED_UP = -1;
	
	protected Handler handler;
	protected BufferedImage texture;
	protected String name;
	protected final int id;
	protected int x, y;
	private static int count;
	
	public Item(BufferedImage texture, String name, int id){
		this.texture = texture;
		this.name = name;
		this.id = id;
		count = 1;
		
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
		Item i = new Item(texture, name, id);
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
		bounds.width = 24;
		bounds.height = 24;
		
		Rectangle ir = new Rectangle();
		int arSize = ITEMWIDTH;
		ir.width = arSize;
		ir.height = arSize;
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
		
	}
	
	// TODO: pickUp function that is executed when the player and the item overlap. Remove the item from the world,
	// Add it to the player's inventory.
	
	// TODO: find a way to include the arraylist of item slots, to check what item the player is standing on, then
	// add that item to the player's inventory at the given index
	
	public boolean pickUpItem (Item item, int amount) {
		inventoryWindow = new InventoryWindow(handler, 80, 64);
        int inventoryIndex = InventoryWindow.findFreeSlot();
        if (inventoryIndex >= 0) {
            if(item.getName() == name){
            	inventoryWindow.getItemSlots().get(inventoryIndex).addItem(item, amount);
            	System.out.println("itemstack = " + inventoryWindow.getItemSlots().get(inventoryIndex).getItemStack().getItem().getName());
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
