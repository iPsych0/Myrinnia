package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.states.GameState;

public class ItemSlot {
	
	public static final int SLOTSIZE = 32;
	
	private int x, y;
	private ItemStack itemStack;
	public static boolean stackable = true;
	int alpha = 127;
	Color interfaceColour = new Color(100, 100, 100, alpha);
	
	public ItemSlot(int x, int y, ItemStack itemStack){
		this.x = x;
		this.y = y;
		this.itemStack = itemStack;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		g.setColor(interfaceColour);
		g.fillRect(x, y, SLOTSIZE, SLOTSIZE);
		
		g.setColor(Color.BLACK);
		g.drawRect(x, y, SLOTSIZE, SLOTSIZE);
		
		
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
			
			g.drawImage(itemStack.getItem().texture, x, y + 4, SLOTSIZE -4, SLOTSIZE -4, null);
			g.setFont(GameState.myFont);
			g.setColor(Creature.hpColor);
			g.drawString(Integer.toString(itemStack.getAmount()), x, y + SLOTSIZE - 21);
		}
		
	}
	
	public boolean addItem(Item item, int amount) {
		if(itemStack != null && stackable == true) {
			if(item.getName() == itemStack.getItem().getName()) {
				this.itemStack.setAmount(this.itemStack.getAmount() + amount);
				stackable = true;
				System.out.println("Items added to the stack");
				return true;
			} else {
				System.out.println(item.getName() + " cannot be stacked with " + itemStack.getItem().getName());
				stackable = false;
				return false;
			}
		} else {
			if(itemStack != null){
				if(item.getName() != itemStack.getItem().getName()){
					stackable = false;
					System.out.println("Dit item kan niet gestacked worden");
					return false;
				}
				else{
					if(item.getName() == itemStack.getItem().getName()){
						this.itemStack.setAmount(this.itemStack.getAmount() + amount);
						System.out.println("Dit item is stackable");
						stackable = true;
						return true;
					}
				}
			}
			
			this.itemStack = new ItemStack(item, amount);
			System.out.println("Nieuwe stack gemaakt");
			return true;
			}
	}
	
//	public void removeItemFromInventory(Item item){
//		if(itemStack != null){
//			item.getHandler().getWorld().getItemManager().getItems().remove(item);
//		}
//	}
	
	public void setItem(ItemStack item){
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
