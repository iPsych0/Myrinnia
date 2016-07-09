package dev.ipsych0.mygame.items;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;

public class Item {
	
	// Handler
	
	public static Item[] items = new Item[256];
	public static Item woodItem = new Item(Assets.wood, "Wood", 0);
	
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
	
	public boolean pickUp (Item item, int amount) {
        int inventoryIndex = findFreeSlot();
        if (inventoryIndex >= 0) {
            itemSlots.get(inventoryIndex).addItem(item, amount);
            return true;
        }else{
        	return false;
        }
	}
    
	// Finds a free slot in the player's inventory
	// TODO: Find a way to include the array of inventory slots to check for free inventory slots to add the item to.
	
    private int findFreeSlot() {
        for (int i=0;i<itemSlots.size();i++) {
             if (itemSlots.get(i) == null) {
                  return i;
             }
        }
        return -1;
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
