package dev.ipsych0.mygame.crafting;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemStack;

public class CraftingSlot {
	
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
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, SLOTSIZE, SLOTSIZE);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, SLOTSIZE, SLOTSIZE);
		
		/*
		 * If there is an item in a slot, render it
		 */
		if(itemStack != null) {
			g.drawImage(itemStack.getItem().getTexture(), x, y, SLOTSIZE, SLOTSIZE, null);
			g.setColor(Color.YELLOW);
			g.setFont(Assets.font14);
			g.drawString(Integer.toString(itemStack.getAmount()), x, y + SLOTSIZE - 21);
		}
	}
	
	/*
	 * Adds item to the crafting slot
	 * @params: Provide the item and amount to add to the slot (usually by right clicking an ItemStack)
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
		} else {
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

}
