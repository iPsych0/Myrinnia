package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.states.GameState;

public class InventoryWindow {
	
	public static boolean isOpen = false;
	private boolean hasBeenPressed = false;
	public static boolean isCreated = false;
	
	private int x, y;
	private int width, height;
	private Handler handler;
	
	private int numCols = 12;
	private int numRows = 4;
	int alpha = 127;
	Color interfaceColour = new Color(130, 130, 130, alpha);
	
	private static CopyOnWriteArrayList<ItemSlot> itemSlots;
	private ItemStack currentSelectedSlot;
	
	public InventoryWindow(Handler handler, int x, int y){
		if(isCreated == false){
			this.x = x;
			this.y = y;
			this.handler = handler;
			itemSlots = new CopyOnWriteArrayList<ItemSlot>();
			
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
		
			// Prevents multiple instances of the inventory being created over and over when picking up items
			isCreated = true;
			
		}
	}
	
	
	public void tick() {
		if(isOpen) {
			Rectangle temp = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			for(ItemSlot is : itemSlots) {
				is.tick();
				
				Rectangle temp2 = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				
				if(handler.getMouseManager().isLeftPressed()){
					if(temp2.contains(temp) && !hasBeenPressed) {
						hasBeenPressed = true;
						
						if(currentSelectedSlot == null) {
							if(is.getItemStack() != null) {
								currentSelectedSlot = is.getItemStack();
								
								is.setItem(null);
							} 
						} else {
								if(is.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
								} else {
									if(ItemSlot.stackable == false){
										return;
									}
									is.setItem(currentSelectedSlot);
								}
								
								currentSelectedSlot = null;
						}
					}
				}
			}
			
			if(hasBeenPressed && !handler.getMouseManager().isLeftPressed()) {
				hasBeenPressed = false;
			}
			ItemSlot.stackable = true;
		}
	}
	
	public void render(Graphics g){
		if(isOpen){
			g.setColor(interfaceColour);
			g.fillRect(x - 16, y - 16, width + 32, height + 32);
			g.setColor(Color.BLACK);
			g.drawRect(x - 16, y - 16, width + 32, height + 32);
			
			for(ItemSlot is : itemSlots){
				is.render(g);
			}
			
			if(currentSelectedSlot != null){
				g.drawImage(currentSelectedSlot.getItem().getTexture(), handler.getMouseManager().getMouseX(),
						handler.getMouseManager().getMouseY(), null);
				if(currentSelectedSlot.getAmount() >= 100){
					g.setFont(GameState.myFont);
					g.setColor(Color.GREEN);
					g.drawString(Integer.toString(currentSelectedSlot.getAmount()), handler.getMouseManager().getMouseX() + 16, handler.getMouseManager().getMouseY() + 16);
				}
				else{
					g.setFont(GameState.myFont);
					g.setColor(Color.YELLOW);
					g.drawString(Integer.toString(currentSelectedSlot.getAmount()), handler.getMouseManager().getMouseX() + 16, handler.getMouseManager().getMouseY() + 16);
				}
			}
		}
	}
	
	public static int findFreeSlot(Item item) {
        for (int i = 0; i < itemSlots.size(); i++) {
        	if(itemSlots.get(i).getItemStack() != null){
        		if(itemSlots.get(i).getItemStack().getItem().getName() == item.getName()){
        			System.out.println("We already have this item in our inventory!");
            		return i;
        		}
        	}
            if (itemSlots.get(i).getItemStack() == null) {
            	System.out.println("Free slot found = " + "[" + i + "]");
                return i;
            }
       }
       System.out.println("Something went wrong checking for free slots (or bag is full)");
       return -1;
	}


	public CopyOnWriteArrayList<ItemSlot> getItemSlots() {
		return itemSlots;
	}


	public void setItemSlots(CopyOnWriteArrayList<ItemSlot> itemSlots) {
		InventoryWindow.itemSlots = itemSlots;
	}

}
