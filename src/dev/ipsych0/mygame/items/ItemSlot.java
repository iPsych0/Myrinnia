package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;

public class ItemSlot {
	
	public static final int SLOTSIZE = 32;
	
	private int x, y;
	private ItemStack itemStack;
	
	public ItemSlot(int x, int y, ItemStack itemStack){
		this.x = x;
		this.y = y;
		this.itemStack = itemStack;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		g.setColor(Color.GRAY);
		g.fillRect(x, y, SLOTSIZE, SLOTSIZE);
		
		g.setColor(Color.BLACK);
		g.drawRect(x, y, SLOTSIZE, SLOTSIZE);
		
		if(itemStack != null){
			g.drawImage(itemStack.getItem().texture, x, y, SLOTSIZE, SLOTSIZE, null);
			g.drawString(Integer.toString(itemStack.getAmount()), x + SLOTSIZE - 16, y + SLOTSIZE - 16);
		}
		
	}
	
	public void setItem(ItemStack item){
		this.itemStack = item;
	}
	
	public boolean addItem(Item item, int amount){
		if(itemStack != null){
			if(item.getName() == itemStack.getItem().getName()){
				this.itemStack.setAmount(this.itemStack.getAmount() + amount);
				return true;
			} else{
				return false;
			}
		} else{
			this.itemStack = new ItemStack(item, amount);
			return true;
		}
	}

	public ItemStack getItemStack() {
		return itemStack;
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
	

}
