package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.gfx.Assets;

public class EquipmentSlot {
	
	public static final int SLOTSIZE = 32;
	
	private int x, y;
	private EquipmentStack equipmentStack;
	private EquipmentWindow equipmentWindow;
	public boolean stackable = false;
	public static boolean hasSwapped = false;
	int alpha = 127;
	Color interfaceColour = new Color(100, 100, 100, alpha);
	
	public EquipmentSlot(int x, int y, EquipmentStack equipmentStack){
		this.x = x;
		this.y = y;
		this.equipmentStack = equipmentStack;
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
		
		if(equipmentStack != null){
			g.drawImage(equipmentStack.getItem().texture, x, y, SLOTSIZE, SLOTSIZE, null);
		}
		
	}
	
	public boolean equipItem(Item item) {
		if(this.equipmentStack != null){
			if(item.equipSlot == equipmentStack.getItem().equipSlot){
				return false;
			}
		}else{
			this.equipmentStack = new EquipmentStack(item);
			return true;
		}
		return false;
	}
	
	public void setItem(EquipmentStack item){
		this.equipmentStack = item;
	}

	public EquipmentStack getEquipmentStack() {
		return equipmentStack;
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

