package dev.ipsych0.mygame.shop;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemSlot;

public class ShopWindow {
	
	public int x, y, width, height;
	private int numCols = 5;
	private int numRows = 6;
	public CopyOnWriteArrayList<ItemSlot> itemSlots;
	private Handler handler;
	private int alpha = 127;
	private Color interfaceColour = new Color(130, 130, 130, alpha);
	
	public ShopWindow(Handler handler, int x, int y) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		width = 360;
		height = 270;
		
		itemSlots = new CopyOnWriteArrayList<ItemSlot>();
		
		for(int i = 0; i < numCols; i++){
			for(int j = 0; j < numRows; j++){
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
		
	}
	
	public void tick() {
		
		for(ItemSlot is : itemSlots) {
			is.tick();
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(interfaceColour);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		for(ItemSlot is : itemSlots) {
			is.render(g);
		}
		
	}

}
