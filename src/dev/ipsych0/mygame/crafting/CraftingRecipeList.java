package dev.ipsych0.mygame.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemStack;

public class CraftingRecipeList {
	
	private List<CraftingRecipe> recipes;
	private List<ItemStack> recipeResults;
	
	/*
	 * Recipe 0: Sword
	 * Recipe 1: Coins
	 * Etc.
	 */
	
	
	public CraftingRecipeList() {
		
		recipes = new ArrayList<CraftingRecipe>();
		recipeResults = new ArrayList<ItemStack>();
		
		recipes.add(new CraftingRecipe(0, 25, false, new ItemStack(Item.woodItem, 5), new ItemStack(Item.oreItem, 5)));
		recipes.add(new CraftingRecipe(1, 50, false, new ItemStack(Item.testSword, 1), new ItemStack(Item.oreItem, 2)));
		recipes.add(new CraftingRecipe(2, 100, false, new ItemStack(Item.coinsItem, 1), new ItemStack(Item.testSword, 1)));
		recipes.add(new CraftingRecipe(3, 5, false, new ItemStack(Item.testSword, 1), new ItemStack(Item.woodItem, 1)));
		
	}
	
	

	public List<CraftingRecipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<CraftingRecipe> recipes) {
		this.recipes = recipes;
	}



	public List<ItemStack> getRecipeResults() {
		return recipeResults;
	}



	public void setRecipeResults(List<ItemStack> recipeResults) {
		this.recipeResults = recipeResults;
	}

}
