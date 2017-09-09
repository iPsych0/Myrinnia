package dev.ipsych0.mygame.shop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.items.ItemStack;

public class ShopWindow {
	
	public int x, y, width, height;
	private int numCols = 5;
	private int numRows = 6;
	public CopyOnWriteArrayList<ItemSlot> itemSlots;
	private Handler handler;
	private int alpha = 127;
	private Color interfaceColour = new Color(130, 130, 130, alpha);
	private ItemStack selectedItem;
	
	public static boolean hasBeenPressed = false;
	
	public ShopWindow(Handler handler, int x, int y) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		width = 360;
		height = 270;
		
		itemSlots = new CopyOnWriteArrayList<ItemSlot>();
		
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
		
		itemSlots.get(0).addItem(Item.woodItem, 5);
		itemSlots.get(1).addItem(Item.oreItem, 5);
		itemSlots.get(2).addItem(Item.testSword, 5);
		itemSlots.get(3).addItem(Item.purpleSword, 5);
		
	}
	
	public void tick() {
		
		Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
		for(ItemSlot is : itemSlots) {
			is.tick();
			
			Rectangle slot = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
			
			if(slot.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
				if(is.getItemStack() != null) {
					if(selectedItem == null) {
						selectedItem = is.getItemStack();
						hasBeenPressed = false;
						return;
					}
					else if(selectedItem == is.getItemStack()) {
						selectedItem = null;
						hasBeenPressed = false;
						return;
					}
					else if(selectedItem != is.getItemStack()) {
						selectedItem = is.getItemStack();
						hasBeenPressed = false;
						return;
					}
				}else {
					selectedItem = null;
					hasBeenPressed = false;
					return;
				}
			}
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(interfaceColour);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		
		for(ItemSlot is : itemSlots) {
			
			if(selectedItem != null) {
				g.setColor(Color.RED);
				g.fillRect(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
			}
			
			is.render(g);
			
		}
		
	}

}
