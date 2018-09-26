package dev.ipsych0.mygame.crafting;

import java.io.Serializable;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.skills.SkillCategory;

public class CraftingRecipe implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3659085845474939235L;
	private ItemStack item1, item2, item3, item4;
	private ItemStack result;
	private ArrayList<ItemStack> components;
	private int craftingXP;
	private int requiredLevel;
	private boolean discovered;
	private SkillCategory category;
	
	/*
	 * Use this constructor for a recipe with 4 components
	 */
	public CraftingRecipe(int requiredLevel, int craftingXP, boolean discovered, SkillCategory category, ItemStack item1, ItemStack item2, ItemStack item3, ItemStack item4, ItemStack result) {
		this.item1 = item1;
		this.item2 = item2;
		this.item3 = item3;
		this.item4 = item4;
		this.result = result;
		this.requiredLevel = requiredLevel;
		this.category = category;
		
		components = new ArrayList<ItemStack>();
		
		components.add(item1);
		components.add(item2);
		components.add(item3);
		components.add(item4);

		
		this.craftingXP = craftingXP;
		this.discovered = discovered;
	}

	/*
	 * Use this constructor for a recipe with 3 components
	 */
	public CraftingRecipe(int requiredLevel, int craftingXP, boolean discovered, SkillCategory category, ItemStack item1, ItemStack item2, ItemStack item3, ItemStack result) {
		this.item1 = item1;
		this.item2 = item2;
		this.item3 = item3;
		this.result = result;
		this.requiredLevel = requiredLevel;
		this.category = category;
		
		components = new ArrayList<ItemStack>();
		
		components.add(item1);
		components.add(item2);
		components.add(item3);
		
		this.craftingXP = craftingXP;
		this.discovered = discovered;
	}

	/*
	 * Use this constructor for a recipe with 2 components
	 */
	public CraftingRecipe(int requiredLevel, int craftingXP, boolean discovered, SkillCategory category, ItemStack item1, ItemStack item2, ItemStack result) {
		this.item1 = item1;
		this.item2 = item2;
		this.result = result;
		this.requiredLevel = requiredLevel;
		this.category = category;
		
		components = new ArrayList<ItemStack>();
		
		components.add(item1);
		components.add(item2);
		
		this.craftingXP = craftingXP;
		this.discovered = discovered;
	}
	
	/*
	 * Use this constructor for a recipe with 1 components
	 */
	public CraftingRecipe(int requiredLevel, int craftingXP, boolean discovered, SkillCategory category, ItemStack item1, ItemStack result) {
		this.item1 = item1;
		this.result = result;
		this.requiredLevel = requiredLevel;
		this.category = category;

		components = new ArrayList<ItemStack>();
		
		components.add(item1);
		
		this.craftingXP = craftingXP;
		this.discovered = discovered;
	}

	public ArrayList<ItemStack> getComponents() {
		return components;
	}

	public void setComponents(ArrayList<ItemStack> components) {
		this.components = components;
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

	public void setDiscovered(Handler handler, boolean discovered) {
		handler.sendMsg("Discovered recipe for: " + this.getResult().getItem().getName() + ".");
		this.discovered = discovered;
	}

	public ItemStack getResult() {
		return result;
	}

	public void setResult(ItemStack result) {
		this.result = result;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}

	public void setRequiredLevel(int requiredLevel) {
		this.requiredLevel = requiredLevel;
	}
	
	@Override 
	public String toString() {
		String s = "You need: ";
		for(int i = 0; i < this.components.size(); i++) {
			s += components.get(i).getAmount() + "x ";
			if(i == components.size()-1) {
				s += components.get(i).getItem().getName();
			}else {
				s += components.get(i).getItem().getName() + ", ";
			}
		}
		return s;
	}

	public SkillCategory getCategory() {
		return category;
	}

	public void setCategory(SkillCategory category) {
		this.category = category;
	}

}
