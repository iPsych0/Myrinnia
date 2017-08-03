package dev.ipsych0.mygame.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CraftingRecipe {

	private ItemStack item1, item2, item3, item4;
	private ArrayList<ItemStack> components;
	private int recipeID;
	
	public CraftingRecipe(int recipeID, ItemStack item1, ItemStack item2, ItemStack item3, ItemStack item4) {
		this.item1 = item1;
		this.item2 = item2;
		this.item3 = item3;
		this.item4 = item4;
		
		components = new ArrayList<ItemStack>();
		
		components.add(item1);
		components.add(item2);
		components.add(item3);
		components.add(item4);
		
		this.recipeID = recipeID;
	}

	public CraftingRecipe(int recipeID, ItemStack item1, ItemStack item2, ItemStack item3) {
		this.item1 = item1;
		this.item2 = item2;
		this.item3 = item3;
		
		components = new ArrayList<ItemStack>();
		
		components.add(item1);
		components.add(item2);
		components.add(item3);
		
		this.recipeID = recipeID;
	}

	public CraftingRecipe(int recipeID, ItemStack item1, ItemStack item2) {
		this.item1 = item1;
		this.item2 = item2;
		
		components = new ArrayList<ItemStack>();
		
		components.add(item1);
		components.add(item2);
		
		this.recipeID = recipeID;
	}
	
	public CraftingRecipe(int recipeID, ItemStack item1) {
		this.item1 = item1;

		components = new ArrayList<ItemStack>();
		
		components.add(item1);
		
		this.recipeID = recipeID;
	}

	public ArrayList<ItemStack> getComponents() {
		return components;
	}

	public void setComponents(ArrayList<ItemStack> components) {
		this.components = components;
	}

	public int getRecipeID() {
		return recipeID;
	}

	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}

}
