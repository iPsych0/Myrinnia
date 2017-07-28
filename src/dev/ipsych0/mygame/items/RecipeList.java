package dev.ipsych0.mygame.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeList {
	
	private List<CraftingRecipe> recipes;
	private Map<Item, Integer> recipe;
	
	public RecipeList() {
		
		recipes = new ArrayList<CraftingRecipe>();
		
	}
	
	

	public List<CraftingRecipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<CraftingRecipe> recipes) {
		this.recipes = recipes;
	}

}
