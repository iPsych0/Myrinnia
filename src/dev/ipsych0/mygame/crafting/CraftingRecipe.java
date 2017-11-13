package dev.ipsych0.mygame.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.ipsych0.mygame.items.ItemStack;

public class CraftingRecipe {

	private ItemStack item1, item2, item3, item4;
	private ArrayList<ItemStack> components;
	private int recipeID;
	private int craftingXP;
	private boolean discovered;
	
	/*
	 * Use this constructor for a recipe with 4 components
	 */
	public CraftingRecipe(int recipeID, int craftingXP, boolean discovered, ItemStack item1, ItemStack item2, ItemStack item3, ItemStack item4) {
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
		this.craftingXP = craftingXP;
		this.discovered = discovered;
	}

	/*
	 * Use this constructor for a recipe with 3 components
	 */
	public CraftingRecipe(int recipeID, int craftingXP, boolean discovered, ItemStack item1, ItemStack item2, ItemStack item3) {
		this.item1 = item1;
		this.item2 = item2;
		this.item3 = item3;
		
		components = new ArrayList<ItemStack>();
		
		components.add(item1);
		components.add(item2);
		components.add(item3);
		
		this.recipeID = recipeID;
		this.craftingXP = craftingXP;
		this.discovered = discovered;
	}

	/*
	 * Use this constructor for a recipe with 2 components
	 */
	public CraftingRecipe(int recipeID, int craftingXP, boolean discovered, ItemStack item1, ItemStack item2) {
		this.item1 = item1;
		this.item2 = item2;
		
		components = new ArrayList<ItemStack>();
		
		components.add(item1);
		components.add(item2);
		
		this.recipeID = recipeID;
		this.craftingXP = craftingXP;
		this.discovered = discovered;
	}
	
	/*
	 * Use this constructor for a recipe with 1 components
	 */
	public CraftingRecipe(int recipeID, int craftingXP, boolean discovered, ItemStack item1) {
		this.item1 = item1;

		components = new ArrayList<ItemStack>();
		
		components.add(item1);
		
		this.recipeID = recipeID;
		this.craftingXP = craftingXP;
		this.discovered = discovered;
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

	public int getCraftingXP() {
		return craftingXP;
	}

	public void setCraftingXP(int craftingXP) {
		this.craftingXP = craftingXP;
	}

	public boolean isDiscovered() {
		return discovered;
	}

	public void setDiscovered(boolean discovered) {
		this.discovered = discovered;
	}

}
