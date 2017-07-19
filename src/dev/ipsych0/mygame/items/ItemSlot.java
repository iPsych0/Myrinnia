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
	public boolean isSelected = false;
	public static boolean stackable = true;
	int alpha = 127;
	Color selectedColour = new Color(255, 127, 127, alpha);
	Color interfaceColour = new Color(100, 100, 100, alpha);
	
	public ItemSlot(int x, int y, ItemStack itemStack){
		this.x = x;
		this.y = y;
		this.itemStack = itemStack;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		if(!this.isSelected){
			g.setColor(interfaceColour);
			g.fillRect(x, y, SLOTSIZE, SLOTSIZE);
		}
		if(this.isSelected){
			g.setColor(selectedColour);
			g.fillRect(x, y, SLOTSIZE, SLOTSIZE);
		}
		
		g.drawImage(Assets.invSlot, x, y, SLOTSIZE, SLOTSIZE, null);
		
//		g.setColor(Color.BLACK);
//		g.drawRect(x, y, SLOTSIZE, SLOTSIZE);
		
		
		if(itemStack != null){
			if(itemStack.getItem().getName() == "Coins"){
				if(itemStack.getAmount() >= 1  && itemStack.getAmount() < 100){
					itemStack.getItem().setTexture(Assets.coins[0]);
				}
				if(itemStack.getAmount() >= 100  && itemStack.getAmount() < 1000){
					itemStack.getItem().setTexture(Assets.coins[1]);
				}
				if(itemStack.getAmount() >= 1000  && itemStack.getAmount() < 10000){
					itemStack.getItem().setTexture(Assets.coins[2]);
				}
				if(itemStack.getAmount() >= 10000  && itemStack.getAmount() < 100000){
					itemStack.getItem().setTexture(Assets.coins[3]);
				}
			}
			
			g.drawImage(itemStack.getItem().getTexture(), x + 2, y + 2, SLOTSIZE - 4, SLOTSIZE - 4, null);
			g.setFont(Assets.font14);
			g.setColor(Color.YELLOW);
			g.drawString(Integer.toString(itemStack.getAmount()), x, y + SLOTSIZE - 21);
		}
	}
	
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
					System.out.println("Not the same item?");
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

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	

}
