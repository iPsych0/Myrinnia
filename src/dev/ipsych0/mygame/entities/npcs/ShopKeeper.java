package dev.ipsych0.mygame.entities.npcs;

import java.util.ArrayList;

import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.shop.ShopWindow;

public abstract class ShopKeeper extends Creature {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3802705595380640443L;
	protected ShopWindow shopWindow;

	public ShopKeeper(float x, float y, int width, int height) {
		super(x, y, width, height);
		
		shopWindow = new ShopWindow(new ArrayList<ItemStack>());
		
	}
	
	public ShopWindow getShopWindow() {
		return shopWindow;
	}

	public void setShopWindow(ShopWindow shopWindow) {
		this.shopWindow = shopWindow;
	}

}
