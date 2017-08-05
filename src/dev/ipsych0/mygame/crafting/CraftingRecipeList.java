package dev.ipsych0.mygame.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemStack;

public class CraftingRecipeList {
	
	private List<CraftingRecipe> recipes;
	
	/*
	 * Recipe 0: Sword
	 * Recipe 1: Coins
	 */
	
	
	public CraftingRecipeList() {
		
		recipes = new ArrayList<CraftingRecipe>();
		recipes.add(new CraftingRecipe(0, 25, new ItemStack(Item.woodItem, 5), new ItemStack(Item.oreItem, 5)));
		recipes.add(new CraftingRecipe(1, 50, new ItemStack(Item.testSword, 1), new ItemStack(Item.oreItem, 2)));
		recipes.add(new CraftingRecipe(2, 100, new ItemStack(Item.coinsItem, 100), new ItemStack(Item.testSword, 1)));
		
	}
	
	

	public List<CraftingRecipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<CraftingRecipe> recipes) {
		this.recipes = recipes;
	}

}
