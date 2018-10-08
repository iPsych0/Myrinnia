package dev.ipsych0.myrinnia.items;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import dev.ipsych0.myrinnia.gfx.Assets;

public class ItemSlot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6580595372033787037L;

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
		
		g.drawImage(Assets.genericButton[1], x, y, SLOTSIZE, SLOTSIZE, null);
		
		if(itemStack != null){
			if(itemStack.getItem() == Item.coins){
				if(itemStack.getAmount() >= 1  && itemStack.getAmount() < 100){
					itemStack.getItem().setTexture(Assets.coins[0]);
				}
				else if(itemStack.getAmount() >= 100  && itemStack.getAmount() < 1000){
					itemStack.getItem().setTexture(Assets.coins[1]);
				}
				else if(itemStack.getAmount() >= 1000  && itemStack.getAmount() < 10000){
					itemStack.getItem().setTexture(Assets.coins[2]);
				}
				else if(itemStack.getAmount() >= 10000  && itemStack.getAmount() < 100000){
					itemStack.getItem().setTexture(Assets.coins[3]);
				}
			}
			
			g.drawImage(itemStack.getItem().getTexture(), x, y, SLOTSIZE, SLOTSIZE, null);
			
			if(itemStack.getItem().isStackable()) {
				g.setFont(Assets.font14);
				g.setColor(Color.BLACK);
				g.drawString(Integer.toString(itemStack.getAmount()), x+1, y + SLOTSIZE - 20);
				g.setColor(Color.YELLOW);
				g.drawString(Integer.toString(itemStack.getAmount()), x, y + SLOTSIZE - 21);
			}
			else if(!itemStack.getItem().isStackable() && itemStack.getAmount() <= 0) {
				g.setFont(Assets.font14);
				g.setColor(Color.YELLOW);
				g.drawString(Integer.toString(itemStack.getAmount()), x, y + SLOTSIZE - 21);
			}
		}
	}
	
	/*
	 * Adds items to the inventory
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
	
	public void setItemStack(ItemStack item){
		this.itemStack = item;
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