package dev.ipsych0.mygame.crafting;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.utils.Text;

public class CraftingSlot implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x, y;
	public static final int SLOTSIZE = 32;
	private ItemStack itemStack;
	public static boolean stackable = true;
	
	public CraftingSlot(int x, int y, ItemStack itemStack) {
		this.x = x;
		this.y = y;
		this.itemStack = itemStack;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
//		g.setColor(Color.DARK_GRAY);
//		g.fillRect(x, y, SLOTSIZE, SLOTSIZE);
//		g.setColor(Color.BLACK);
//		g.drawRect(x, y, SLOTSIZE, SLOTSIZE);
		
		g.drawImage(Assets.mainMenuButton[1], x, y, SLOTSIZE, SLOTSIZE, null);
		
		/*
		 * If there is an item in a slot, render it
		 */
		if(itemStack != null) {
			
			g.drawImage(itemStack.getItem().getTexture(), x, y, SLOTSIZE, SLOTSIZE, null);
			
			if(itemStack.getItem().isStackable()) {
				Text.drawString(g, Integer.toString(itemStack.getAmount()), x, y + SLOTSIZE - 21, false, Color.YELLOW, Assets.font14);
			}
		}
	}
	
	/*
	 * Adds item to the crafting slot
	 * @params: Provide the item and amount to add to the slot (usually by right clicking an ItemStack)
	 */
	public boolean addItem(Item item, int amount) {
		// If the item is stackable
		if(itemStack != null && item.isStackable()) {
			if(item.getId() == itemStack.getItem().getId()) {
				// If a stack already exists and the item is stackable, add to that stack
				this.itemStack.setAmount(this.itemStack.getAmount() + amount);
				return true;
			} else {
				return false;
			}
			
		} else if(!item.isStackable()){
			// If the item isn't stackable
			this.itemStack = new ItemStack(item);
			return true;
		}
		else {
			// Else create a new stack
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

}
