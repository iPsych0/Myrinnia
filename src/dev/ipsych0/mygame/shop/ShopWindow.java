package dev.ipsych0.mygame.shop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.items.ItemStack;

public class ShopWindow {
	
	public int x, y, width, height;
	private int numCols = 5;
	private int numRows = 6;
	public CopyOnWriteArrayList<ItemSlot> itemSlots;
	public CopyOnWriteArrayList<ItemSlot> invSlots;
	private int alpha = 127;
	private Color interfaceColour = new Color(130, 130, 130, alpha);
	private ItemStack selectedShopItem;
	private ItemStack selectedInvItem;
	public static boolean isOpen = false;
	private Handler handler;
	
	public static boolean hasBeenPressed = false;
	
	public ShopWindow(Handler handler, int x, int y, ArrayList<ItemStack> shopItems) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		width = 460;
		height = 270;
		
		itemSlots = new CopyOnWriteArrayList<ItemSlot>();
		invSlots = new CopyOnWriteArrayList<ItemSlot>();
		
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(j == (numRows)){
					x += 8;
				}
				
				itemSlots.add(new ItemSlot(x + 17 + (i * (ItemSlot.SLOTSIZE)), y + 32 + (j * ItemSlot.SLOTSIZE), null));
				
				if(j == (numRows)){
					x -= 8;
				}
			}
		}
		
		if(shopItems.size() == 0) {
			System.out.println("Shop size = 0");
			return;
		}
		
		for (int i = 0; i < shopItems.size(); i++) {
			itemSlots.get(i).addItem(shopItems.get(i).getItem(), shopItems.get(i).getAmount());
		}
		
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(j == (numRows)){
					x += 8;
				}
				
				invSlots.add(new ItemSlot(x + (width / 2) + 17 + (i * (ItemSlot.SLOTSIZE)), y + 32 + (j * ItemSlot.SLOTSIZE), null));
				
				if(j == (numRows)){
					x -= 8;
				}
			}
		}
		
	}
	
	public void tick() {
		if(isOpen) {
		
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			for(ItemSlot is : itemSlots) {
				is.tick();
				
				Rectangle slot = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				
				if(slot.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
					if(is.getItemStack() != null) {
						if(selectedShopItem == null) {
							selectedShopItem = is.getItemStack();
							hasBeenPressed = false;
							return;
						}
						else if(selectedShopItem == is.getItemStack()) {
							selectedShopItem = null;
							hasBeenPressed = false;
							return;
						}
						else if(selectedShopItem != is.getItemStack()) {
							selectedShopItem = is.getItemStack();
							hasBeenPressed = false;
							return;
						}
					}else {
						selectedShopItem = null;
						hasBeenPressed = false;
						return;
					}
				}
			}
				
			for(ItemSlot is : invSlots) {
				is.tick();
				
				Rectangle slot = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				
				if(slot.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
					if(is.getItemStack() != null) {
						if(selectedInvItem == null) {
							selectedInvItem = is.getItemStack();
							hasBeenPressed = false;
							return;
						}
						else if(selectedInvItem == is.getItemStack()) {
							selectedInvItem = null;
							hasBeenPressed = false;
							return;
						}
						else if(selectedInvItem != is.getItemStack()) {
							selectedInvItem = is.getItemStack();
							hasBeenPressed = false;
							return;
						}
					}else {
						selectedInvItem = null;
						hasBeenPressed = false;
						return;
					}
				}
				
			}
		
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			g.setColor(interfaceColour);
			g.fillRect(x, y, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
			
			// test stuff buy/sell
			g.drawRect(x + 81, y + (height / 2) + 96, 64, 32);
			g.drawRect(x + (width / 2) + 81, y + (height / 2) + 96, 64, 32);
			Text.drawString(g, "Buy", x + 81 + 32, y + (height / 2) + 96 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Sell", x + 81 + (width / 2) + 32, y + (height / 2) + 96 + 16, true, Color.YELLOW, Assets.font14);
			
			// test stuff +1 +10 etc voor buy
			Text.drawString(g, "-10", x + 49 + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "-1", x + 81 + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "+1", x + 113 + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "+10", x + 145 + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			
			// test stuff +1 +10 etc voor sell
			Text.drawString(g, "-10", x + 49 + (width / 2) + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "-1", x + 81 + (width / 2) + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "+1", x + 113 + (width / 2) + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "+10", x + 145 + (width / 2) + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			
			// test stuff close button
			g.setColor(Color.YELLOW);
			g.fillRect(x + width - 26, y + 10, 16, 16);
			g.setColor(Color.BLACK);
			g.drawRect(x + width - 26, y + 10, 16, 16);
			Text.drawString(g, "X", x + width - 26 + 8, y + 10 + 8, true, Color.BLACK, Assets.font14);
			
			for(ItemSlot is : itemSlots) {
				
				if(selectedShopItem != null) {
					g.setColor(Color.YELLOW);
					g.drawImage(selectedShopItem.getItem().getTexture(), x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
					g.drawString(selectedShopItem.getItem().getName(), x, y + 8);
				}
				
				is.render(g);
				
			}
			
			for(ItemSlot is : invSlots) {
				is.render(g);
			}
		}
	}
	
	public int findFreeSlot(Item item) {
        for (int i = 0; i < invSlots.size(); i++) {
        	if(invSlots.get(i).getItemStack() != null){
        		if(invSlots.get(i).getItemStack().getItem().getId() == item.getId()){
            		return i;
        		}
        	}
            if (invSlots.get(i).getItemStack() == null) {
                return i;
            }
       }
       System.out.println("You cannot offer any more items.");
       return -1;
	}

	public CopyOnWriteArrayList<ItemSlot> getItemSlots() {
		return itemSlots;
	}

	public void setItemSlots(CopyOnWriteArrayList<ItemSlot> itemSlots) {
		this.itemSlots = itemSlots;
	}

	public CopyOnWriteArrayList<ItemSlot> getInvSlots() {
		return invSlots;
	}

	public void setInvSlots(CopyOnWriteArrayList<ItemSlot> invSlots) {
		this.invSlots = invSlots;
	}

}
