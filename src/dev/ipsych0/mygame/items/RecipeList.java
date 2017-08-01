package dev.ipsych0.mygame.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeList {
	
	private List<CraftingRecipe> recipes;
	
	// Recipe 1
	// Recipe 2 etc
	// (Lijst van ALLE mogelijke recipes)
	
	
	public RecipeList() {
		
		recipes = new ArrayList<CraftingRecipe>();
		recipes.add(new CraftingRecipe(new ItemStack(Item.woodItem, 5), new ItemStack(Item.oreItem, 5), new ItemStack(Item.coinsItem, 5)));
		recipes.add(new CraftingRecipe(new ItemStack(Item.woodItem, 5), new ItemStack(Item.oreItem, 5), new ItemStack(Item.testSword, 1)));
		
	}
	
	

	public List<CraftingRecipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<CraftingRecipe> recipes) {
		this.recipes = recipes;
	}

}
