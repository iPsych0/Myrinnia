package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import dev.ipsych0.mygame.gfx.Assets;

public class EquipmentSlot implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final int SLOTSIZE = 32;
	
	private int x, y;
	private ItemStack itemStack;
	private EquipmentWindow equipmentWindow;
	public boolean stackable = false;
	public static boolean hasSwapped = false;
	int alpha = 127;
	Color interfaceColour = new Color(100, 100, 100, alpha);
	
	public EquipmentSlot(int x, int y, ItemStack itemStack){
		this.x = x;
		this.y = y;
		this.itemStack = itemStack;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		
		g.drawImage(Assets.equipSlot, x, y, SLOTSIZE, SLOTSIZE, null);
//		g.setColor(interfaceColour);
//		g.fillRect(x, y, SLOTSIZE, SLOTSIZE);
//		
//		g.setColor(Color.BLACK);
//		g.drawRect(x, y, SLOTSIZE, SLOTSIZE);
		
		if(itemStack != null){
			g.drawImage(itemStack.getItem().getTexture(), x, y, SLOTSIZE, SLOTSIZE, null);
		}
		
	}
	
	public boolean equipItem(Item item) {
		if(this.itemStack != null){
			if(item.equipSlot == itemStack.getItem().equipSlot){
				return false;
			}else {
				return true;
			}
		}else{
			this.itemStack = new ItemStack(item);
			return true;
		}

	}
	
	public void setItem(ItemStack item){
		this.itemStack = item;
	}

	public ItemStack getEquipmentStack() {
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

