package dev.ipsych0.mygame.bank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.utils.Text;


public class BankTab implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3186907209395979291L;
	public int x, y, width, height;
	private Rectangle bounds;
	private int tabNumber;
	public static final int ROWS = 6, COLS = 5;
	private CopyOnWriteArrayList<ItemSlot> bankSlots;
	private boolean isOpen = false;
	private boolean hovering = false;
		
	public BankTab(int x, int y, int tabNumber) {
		this.x = x;
		this.y = y;
		this.width = ItemSlot.SLOTSIZE;
		this.height = ItemSlot.SLOTSIZE;
		this.tabNumber = tabNumber;
		
		bankSlots = new CopyOnWriteArrayList<>();
		
		if(tabNumber == 0) {
			isOpen = true;
		}
		
		// Add the bank slots
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				if(j == (ROWS)){
					x += 8;
				}
				
				bankSlots.add(new ItemSlot(BankUI.x + 17 + (i * ItemSlot.SLOTSIZE), BankUI.y + 80 + (j * ItemSlot.SLOTSIZE), null));
				
				if(j == (ROWS)){
					x -= 8;
				}
			}
		}
		
		this.bounds = new Rectangle(x, y, width, height);
	}
	
	public void tick() {
		if(isOpen) {
			
		}
	}
	
	public void render(Graphics g) {
		if(hovering)
			g.drawImage(Assets.genericButton[0], x, y, width, height, null);
		else
			g.drawImage(Assets.genericButton[1], x, y, width, height, null);
		
		if(bankSlots.get(0).getItemStack() != null)
			g.drawImage(bankSlots.get(0).getItemStack().getItem().getTexture(), x, y, width, height, null);
		else
			Text.drawString(g, ""+(tabNumber+1), x + width / 2, y + height / 2, true, Color.YELLOW, Assets.font14);
		
		if(isOpen) {
			for(ItemSlot is : bankSlots) {
				is.render(g);
			}
		}
	}
	
	/*
	 * Finds a free slot in the inventory
	 * @returns: index if found, -1 if inventory is full
	 */
	public int findFreeSlot(Item item) {
		boolean firstFreeSlotFound = false;
		int index = -1;
        for (int i = 0; i < bankSlots.size(); i++) {
        	if(bankSlots.get(i).getItemStack() == null) {
            	if(!firstFreeSlotFound) {
	            	firstFreeSlotFound = true;
	            	index = i;
            	}
            }
        	else if(bankSlots.get(i).getItemStack() != null && !item.isStackable()) {
        		continue;
        	}
        	else if(bankSlots.get(i).getItemStack() != null && item.isStackable()){
        		if(bankSlots.get(i).getItemStack().getItem().getId() == item.getId()){
            		return i;
        		}
        	}
       }
       if(index != -1)
    	   return index;
       
       Handler.get().sendMsg("This bank tab is full.");
       return -1;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public int getTabNumber() {
		return tabNumber;
	}

	public void setTabNumber(int tabNumber) {
		this.tabNumber = tabNumber;
	}

	public CopyOnWriteArrayList<ItemSlot> getBankSlots() {
		return bankSlots;
	}

	public void setBankSlots(CopyOnWriteArrayList<ItemSlot> bankSlots) {
		this.bankSlots = bankSlots;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public boolean isHovering() {
		return hovering;
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}
	
	

}
