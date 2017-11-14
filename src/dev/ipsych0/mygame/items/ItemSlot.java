package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.states.GameState;

public class ItemSlot implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int SLOTSIZE = 32;
	
	private int x, y;
	private ItemStack itemStack;
	public static boolean stackable = true;
	
	public ItemSlot(int x, int y, ItemStack itemStack){
		this.x = x;
		this.y = y;
		this.itemStack = itemStack;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		
		g.drawImage(Assets.invSlot, x, y, SLOTSIZE, SLOTSIZE, null);
		
		if(itemStack != null){
			if(itemStack.getItem() == Item.coinsItem){
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
			
			g.drawImage(itemStack.getItem().getTexture(), x + 2, y + 2, SLOTSIZE - 4, SLOTSIZE - 4, null);
			g.setFont(Assets.font14);
			g.setColor(Color.YELLOW);
			g.drawString(Integer.toString(itemStack.getAmount()), x, y + SLOTSIZE - 21);
		}
	}
	
	/*
	 * Adds items to the inventory
	 */
	public boolean addItem(Item item, int amount) {
		if(itemStack != null && stackable == true) {
			if(item.getId() == itemStack.getItem().getId()) {
				// If a stack already exists, add to that stack
				this.itemStack.setAmount(this.itemStack.getAmount() + amount);
				stackable = true;
				return true;
			} else {
				stackable = false;
				return false;
			}
		} else {
			if(itemStack != null){
				if(item.getId() != itemStack.getItem().getId()){
					stackable = false;
					return false;
				}
				else{
					if(item.getId() == itemStack.getItem().getId()){
						this.itemStack.setAmount(this.itemStack.getAmount() + amount);
						stackable = true;
						return true;
					}
				}
			}
			
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
