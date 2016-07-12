package dev.ipsych0.mygame.items;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;

public class Item {
	
	// Handler
	
	public static Item[] items = new Item[256];
	public static Item woodItem = new Item(Assets.wood, "Wood", 0);
	private InventoryWindow inventoryWindow;
	private ArrayList<ItemSlot> itemSlots;
	
	// Class
	
	public static final int ITEMWIDTH = 16, ITEMHEIGHT = 16, PICKED_UP = -1;
	
	protected Handler handler;
	protected BufferedImage texture;
	protected String name;
	protected final int id;
	
	protected int x, y, count;
	
	public Item(BufferedImage texture, String name, int id){
		this.texture = texture;
		this.name = name;
		this.id = id;
		count = 1;
		
		items[id] = this;
		itemSlots = new ArrayList<ItemSlot>();
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
	
	// TODO: pickUp function that is executed when the player and the item overlap. Remove the item from the world,
	// Add it to the player's inventory.
	
	// TODO: find a way to include the arraylist of item slots, to check what item the player is standing on, then
	// add that item to the player's inventory at the given index
	
	public boolean pickUpItem (Item item, int amount) {
		for (int i = 0; i < itemSlots.size(); i++){
			System.out.println("itemSlots contains: " + itemSlots.get(i).getItemStack());
		}
		System.out.println("item = " + item + " and amount = " + amount);
        int inventoryIndex = InventoryWindow.findFreeSlot();
        System.out.println("invIndex = "+inventoryIndex);
        if (inventoryIndex >= 0) {
            if(item.getName() == name){
            	System.out.println("Found an item: " + item.getName() + " (equals " + name + ")");
            	itemSlots.get(inventoryIndex).getItemStack().setItem(item);
            	itemSlots.get(inventoryIndex).getItemStack().setAmount(amount);
            	item.count = PICKED_UP;
            	return true;
        	}
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
		this.count = count;
	}

	public int getId() {
		return id;
	}

}
