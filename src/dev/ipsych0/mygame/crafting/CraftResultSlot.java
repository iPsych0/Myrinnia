package dev.ipsych0.mygame.crafting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.utils.Text;

public class CraftResultSlot implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8523315261993088996L;
	private int x, y;
	public static final int SLOTSIZE = 32;
	private ItemStack itemStack;
	public static boolean stackable = true;
	private Rectangle bounds;
	
	public CraftResultSlot(int x, int y, ItemStack itemStack) {
		this.x = x;
		this.y = y;
		this.itemStack = itemStack;
		bounds = new Rectangle(x, y, SLOTSIZE, SLOTSIZE);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		g.drawImage(Assets.genericButton[1], x, y, SLOTSIZE, SLOTSIZE, null);
		
		if(itemStack != null) {
			g.drawImage(itemStack.getItem().getTexture(), x, y, SLOTSIZE, SLOTSIZE, null);
			Text.drawString(g, Integer.toString(itemStack.getAmount()), x, y + SLOTSIZE - 21, false, Color.YELLOW, Assets.font14);
		}
	}
	
	/*
	 * Adds item to the result slot when the craft button is pressed
	 * @returns: true/false if it can be added or not
	 */
	public boolean addItem(Item item, int amount) {
		if(itemStack != null && stackable == true) {
			if(item.getName() == itemStack.getItem().getName()) {
				this.itemStack.setAmount(this.itemStack.getAmount() + amount);
				stackable = true;
				return true;
			} else {
				stackable = false;
				return false;
			}
		}
		else {
			if(itemStack != null){
				if(item.getName() != itemStack.getItem().getName()){
					stackable = false;
					return false;
				}
				else{
					if(item.getName() == itemStack.getItem().getName()){
						this.itemStack.setAmount(this.itemStack.getAmount() + amount);
						stackable = true;
						return true;
					}
				}
			}
			
			this.itemStack = new ItemStack(item, amount);
			return true;
			}
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
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

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

}
