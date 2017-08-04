package dev.ipsych0.mygame.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeList {
	
	private List<CraftingRecipe> recipes;
	
	/*
	 * Recipe 0: Sword
	 * Recipe 1: Coins
	 */
	
	
	public RecipeList() {
		
		recipes = new ArrayList<CraftingRecipe>();
		recipes.add(new CraftingRecipe(0, new ItemStack(Item.woodItem, 5), new ItemStack(Item.oreItem, 5)));
		recipes.add(new CraftingRecipe(1, new ItemStack(Item.testSword, 1), new ItemStack(Item.oreItem, 2)));
		recipes.add(new CraftingRecipe(2, new ItemStack(Item.coinsItem, 100), new ItemStack(Item.testSword, 1)));
		
	}
	
	

	public List<CraftingRecipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<CraftingRecipe> recipes) {
		this.recipes = recipes;
	}

}
