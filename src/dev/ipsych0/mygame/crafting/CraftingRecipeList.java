package dev.ipsych0.mygame.crafting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemStack;

public class CraftingRecipeList implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CraftingRecipe> recipes;
	
	/*
	 * Puts all recipes in the list
	 */
	public CraftingRecipeList() {
		
		recipes = new ArrayList<CraftingRecipe>();
		
		recipes.add(new CraftingRecipe(25, false, new ItemStack(Item.woodItem, 5), new ItemStack(Item.oreItem, 5), new ItemStack(Item.coinsItem, 100)));
		recipes.add(new CraftingRecipe(50, false, new ItemStack(Item.testSword, 1), new ItemStack(Item.oreItem, 2), new ItemStack(Item.woodItem, 5), new ItemStack(Item.purpleSword, 1)));
		recipes.add(new CraftingRecipe(100, false, new ItemStack(Item.coinsItem, 1), new ItemStack(Item.testSword, 1)));
		recipes.add(new CraftingRecipe(5, false, new ItemStack(Item.testSword, 1), new ItemStack(Item.woodItem, 1)));
		
	}

	public List<CraftingRecipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<CraftingRecipe> recipes) {
		this.recipes = recipes;
	}

}
