package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;

public class CraftingUI {
	
	private int x, y, width, height;
	public static boolean isOpen = false;
	private boolean isCreated = false;
	private Handler handler;
	public CopyOnWriteArrayList<CraftingSlot> craftingSlots;
	private int numRows = 2;
	private int numCols = 2;
	
	public CraftingUI(Handler handler, int x, int y) {
		
		this.handler = handler;
		this.x = x;
		this.y = y;
		width = 200;
		height = 200;
		
		if(!isCreated) {
			
			craftingSlots = new CopyOnWriteArrayList<CraftingSlot>();
			
			for(int i = 0; i < numCols; i++) {
				for(int j = 0; j < numRows; j++) {
					craftingSlots.add(new CraftingSlot(x + 16 + (32 * i), y + 16 + (32 * j), null));
				}
			}
			
			isCreated = true;
			
		}
		
	}
	
	public void tick() {
		
		if(isOpen) {
			for(CraftingSlot cs : craftingSlots) {
				cs.tick();
			}
		}
		
	}
	
	public void render(Graphics g) {
		
		if(isOpen) {
		
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
			g.setColor(Color.YELLOW);
			g.fillRect(x, y, width, height);
		
			for(CraftingSlot cs : craftingSlots) {
				cs.render(g);
			}
		}
		
	}
	
	public int findFreeSlot(Item item) {
        for (int i = 0; i < craftingSlots.size(); i++) {
        	if(craftingSlots.get(i).getItemStack() != null){
        		if(craftingSlots.get(i).getItemStack().getItem().getName() == item.getName()){
        			System.out.println("This item is already in the crafting window.");
            		return i;
        		}
        	}
            if (craftingSlots.get(i).getItemStack() == null) {
                return i;
            }
       }
       System.out.println("You can't put any more items in.");
       handler.getPlayer().getChatWindow().sendMessage("You can't put any more items in the crafting window.");
       return -1;
	}

	public CopyOnWriteArrayList<CraftingSlot> getCraftingSlots() {
		return craftingSlots;
	}

	public void setCraftingSlots(CopyOnWriteArrayList<CraftingSlot> craftingSlots) {
		this.craftingSlots = craftingSlots;
	}
	

}
