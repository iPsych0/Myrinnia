package dev.ipsych0.mygame.entities.npcs;

import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.shop.ShopWindow;

public abstract class ShopKeeper extends Creature {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ShopWindow shopWindow;

	public ShopKeeper(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		
		shopWindow = new ShopWindow(handler, new ArrayList<ItemStack>());
		
	}
	
	public ShopWindow getShopWindow() {
		return shopWindow;
	}

	public void setShopWindow(ShopWindow shopWindow) {
		this.shopWindow = shopWindow;
	}

}
