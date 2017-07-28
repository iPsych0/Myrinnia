package dev.ipsych0.mygame.items;

import java.util.Map;

public class CraftingRecipe {

	private Map<Item, Integer> recipe;
	
	public CraftingRecipe(Map<Item, Integer> recipe) {
		this.recipe = recipe;
	}

}
