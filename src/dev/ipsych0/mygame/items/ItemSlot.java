package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;

public class ItemSlot {
	
	public static final int SLOTSIZE = 32;
	
	private int x, y;
	private ItemStack itemStack;
	private InventoryWindow inventoryWindow;
	public static boolean stackable = true;
	
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
	
	public boolean addItem(Item item, int amount) {
		if(itemStack != null && stackable == true) {
			if(item.getName() == itemStack.getItem().getName()) {
				System.out.println("We already had this item! Stacked " + item.getName() + " with the current stack of: " + itemStack.getItem().getName());
				this.itemStack.setAmount(this.itemStack.getAmount() + amount);
				return true;
			} else {
				System.out.println(item.getName() + " cannot be stacked with " + itemStack.getItem().getName());
				stackable = false;
				return false;
			}
		} else {
			System.out.println("itemname = " + item.getName());
			this.itemStack = new ItemStack(item, amount);
			return true;
		}
	}
	
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
