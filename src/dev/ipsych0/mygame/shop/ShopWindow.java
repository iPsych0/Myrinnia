package dev.ipsych0.mygame.shop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.items.InventoryWindow;
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
			handler.sendMsg("Something went wrong. List of items is empty.");
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
			for(int i = 0; i < invSlots.size(); i++) {
				if(handler.getWorld().getInventory().getItemSlots().get(i).getItemStack() == null) {
					invSlots.get(i).setItemStack(null);
					continue;
				}
				invSlots.get(i).setItemStack(handler.getWorld().getInventory().getItemSlots().get(i).getItemStack());
			}
		
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
			
			for(ItemSlot is : itemSlots) {
				
				if(selectedShopItem != null) {
					g.setColor(Color.RED);
					g.fillRect(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				}
				
				is.render(g);
				
			}
			
			for(ItemSlot is : invSlots) {
				is.render(g);
			}
		}
	}

}
