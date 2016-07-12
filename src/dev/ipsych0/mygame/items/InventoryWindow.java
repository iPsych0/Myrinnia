package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;

public class InventoryWindow {
	
	public static boolean isOpen = false;
	private boolean hasBeenPressed = false;
	
	private int x, y;
	private int width, height;
	private Handler handler;
	
	private int numCols = 12;
	private int numRows = 4;
	
	private static ArrayList<ItemSlot> itemSlots;
	private ItemStack currentSelectedSlot;
	private Item items;
	
	public InventoryWindow(Handler handler, int x, int y){
		this.x = x;
		this.y = y;
		this.handler = handler;
		itemSlots = new ArrayList<ItemSlot>();
		
		for(int i = 0; i < numCols; i++){
			for(int j = 0; j < numRows; j++){
				if(j == (numRows - 1)){
					y += 32;
				}
				
				itemSlots.add(new ItemSlot(x + (i * (ItemSlot.SLOTSIZE)), y + (j * ItemSlot.SLOTSIZE), null));
				
				if(j == (numRows - 1)){
					y -= 32;
				}
			}
		}	
		width = numCols * (ItemSlot.SLOTSIZE + 10);
		height = numRows * (ItemSlot.SLOTSIZE + 10) + 8;
	
		// TODO: Remove this
		//itemSlots.get(findFreeSlot()).addItem(Item.woodItem, 10);
		
		for (int k = 0; k < itemSlots.size(); k++){
			System.out.println("itemSlots contain: " + itemSlots.get(k).getItemStack() );
		}
		
	}
	
	public boolean pickUpItem (Item item, int amount) {
        int inventoryIndex = InventoryWindow.findFreeSlot();
        System.out.println("invIndex = "+inventoryIndex);
        if (inventoryIndex >= 0) {
            if(item.getName() == items.getName()){
            	System.out.println("Found an item: " + item.getName());
            	itemSlots.get(inventoryIndex).addItem(item, amount);
            	item.count = -1;
            	return true;
        	}
            return false;
        }
    	System.out.println("Inventory is full!");
    	return false;
    }
	
	
	public void tick(){
		if(isOpen){
			Rectangle temp = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			for(ItemSlot is : itemSlots){
				is.tick();
				
				Rectangle temp2 = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				
				if(handler.getMouseManager().isLeftPressed()){
					
					if(temp2.contains(temp) && !hasBeenPressed){
						hasBeenPressed = true;
						if(is.getItemStack() != null){
							if(currentSelectedSlot == null){
								currentSelectedSlot = is.getItemStack();
								
								is.setItem(null);
							} 
						} else{
								if(is.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())){	
								} else {
									is.setItem(currentSelectedSlot);
								}
								
								currentSelectedSlot = null;
							}
						}
					}
				}
			}
			if(hasBeenPressed && !handler.getMouseManager().isLeftPressed()){
				hasBeenPressed = false;
			}
		}
	
	public void render(Graphics g){
		if(isOpen){
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x - 16, y - 16, width + 32, height + 32);
			g.setColor(Color.BLACK);
			g.drawRect(x - 16, y - 16, width + 32, height + 32);
			
			for(ItemSlot is : itemSlots){
				is.render(g);
			}
			
			if(currentSelectedSlot != null){
				g.drawImage(currentSelectedSlot.getItem().getTexture(), handler.getMouseManager().getMouseX(),
						handler.getMouseManager().getMouseY(), null);
				g.drawString(Integer.toString(currentSelectedSlot.getAmount()), handler.getMouseManager().getMouseX() + 16, handler.getMouseManager().getMouseY() + 16);
			}
		}
	}
	
	public static int findFreeSlot() {
        for (int i = 0; i < itemSlots.size(); i++) {
             if (itemSlots.get(i).getItemStack() == null) {
            	 System.out.println("Free slot found = " + "[" + i + "]");
                 return i;
             }
        }
        System.out.println("Something went wrong checking for free slots (or bag is full)");
        return -1;
   }


	public ArrayList<ItemSlot> getItemSlots() {
		return itemSlots;
	}


	public void setItemSlots(ArrayList<ItemSlot> itemSlots) {
		this.itemSlots = itemSlots;
	}

}
