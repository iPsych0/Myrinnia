package dev.ipsych0.mygame.crafting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		
		recipes.add(new CraftingRecipe(1, 25, true, new ItemStack(Item.woodItem, 5), new ItemStack(Item.oreItem, 5), new ItemStack(Item.coinsItem, 100)));
		recipes.add(new CraftingRecipe(1, 50, false, new ItemStack(Item.testSword, 1), new ItemStack(Item.oreItem, 2), new ItemStack(Item.woodItem, 5), new ItemStack(Item.coinsItem, 1), new ItemStack(Item.purpleSword, 1)));
		recipes.add(new CraftingRecipe(2, 100, false, new ItemStack(Item.coinsItem, 1), new ItemStack(Item.testSword, 1)));
		recipes.add(new CraftingRecipe(3, 5, false, new ItemStack(Item.testSword, 1), new ItemStack(Item.woodItem, 1)));
		
		
		Collections.sort(recipes, new Comparator<CraftingRecipe>() {

			@Override
			public int compare(CraftingRecipe o1, CraftingRecipe o2) {
				Integer a = o1.getRequiredLevel();
				Integer b = o2.getRequiredLevel();
				return a.compareTo(b);
			}
		});
		
	}
	
	public CraftingRecipe getRecipeByItem(Item item) {
		for(int i = 0; i < recipes.size(); i++) {
			if(recipes.get(i).getResult().getItem().getId() == item.getId()) {
				return recipes.get(i);
			}
		}
		return null;
	}

	public List<CraftingRecipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<CraftingRecipe> recipes) {
		this.recipes = recipes;
	}

}
