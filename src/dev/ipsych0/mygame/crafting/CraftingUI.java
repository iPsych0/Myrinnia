package dev.ipsych0.mygame.crafting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.character.CharacterUI;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemRarity;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.quests.QuestHelpUI;
import dev.ipsych0.mygame.quests.QuestUI;
import dev.ipsych0.mygame.skills.SkillsList;
import dev.ipsych0.mygame.skills.SkillsOverviewUI;
import dev.ipsych0.mygame.skills.SkillsUI;
import dev.ipsych0.mygame.utils.Text;

public class CraftingUI implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6741379998525736950L;
	private int x, y, width, height;
	public static boolean isOpen = false;
	private boolean isCreated = false;
	private boolean hasBeenPressed = false;
	private boolean itemSelected = false;
	private CopyOnWriteArrayList<CraftingSlot> craftingSlots;
	private CraftResultSlot crs;
	private CraftButton cb;
	private int craftAmount = 1;
	public static boolean craftButtonPressed = false;
	public static boolean craftResultPressed = false;
	private Rectangle cbBounds;
	private ItemStack currentSelectedSlot;
	private Rectangle crsBounds;
	private CraftingManager craftingManager;
	private ItemStack possibleRecipe = null;
	private String craftableRecipe;
	private transient BufferedImage craftImg;
	private Rectangle previewImg;
	private boolean hovering = false;
	private Rectangle windowBounds;
	private String[] totalCraftAmount;
	private int[] filledCraftSlots;
	private CraftingRecipe craftRecipe;
	
	public CraftingUI() {
		this.width = 242;
		this.height = 320;
		this.x = Handler.get().getWidth() / 2 - width / 2;
		this.y = Handler.get().getHeight() / 2 - height / 2;
		
		windowBounds = new Rectangle(x, y, width, height);
		
		// First time it runs: set the window and dimensions/parameters
		if(!isCreated) {
			
			craftingSlots = new CopyOnWriteArrayList<CraftingSlot>();
			
			craftingSlots.add(new CraftingSlot(x + 32, y + 50, null));
			craftingSlots.add(new CraftingSlot(x + 80, y + 100, null));
			craftingSlots.add(new CraftingSlot(x + 128, y + 100, null));
			craftingSlots.add(new CraftingSlot(x + 176, y + 50, null));
			
			crs = new CraftResultSlot(x + width / 2 - 16, y + height - 160, null);
			cb = new CraftButton(x + width / 2 - 48, y + height - 112, CraftingSlot.SLOTSIZE * 3, CraftingSlot.SLOTSIZE);
			
			cbBounds = new Rectangle(cb.getBounds());
			crsBounds = new Rectangle(crs.getBounds());
			
			craftingManager = new CraftingManager();
			
			previewImg = new Rectangle(x + width + (width / 2) - 36, y + 32, 32, 32);
			
			isCreated = true;
			
		}
	}
	
	public void tick() {
		if(Handler.get().getKeyManager().getLastUIKeyPressed() != -1) {
			closeCraftingUI();
			Handler.get().getKeyManager().setLastUIKeyPressed(-1);
		}
		if(isOpen) {
			
			crs.tick();
			cb.tick();
			
			Rectangle mouse = Handler.get().getMouse();
			
			if(previewImg.contains(mouse)) {
				hovering = true;
			}else {
				hovering = false;
			}
			
			// If the window is closed or the player moves, re-add all items back to inventory or drop them if no space.
			if(Handler.get().getKeyManager().escape || Player.isMoving) {
				closeCraftingUI();
			}
			
			// If left-clicked on the "craft" button, craft the item
			if(Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged()) {
				if(cbBounds.contains(mouse) && craftButtonPressed) {
					
					craftItem();
					
					craftButtonPressed = false;
				}
			}
			
			// If right-clicked on the crafted item
			if(Handler.get().getMouseManager().isRightPressed() && !Handler.get().getMouseManager().isDragged()) {
				if(crsBounds.contains(mouse) && craftResultPressed) {
					// If there is no item in the slot, return
					if(crs.getItemStack() == null) {
						craftResultPressed = false;
						return;
					}
					
					int numItems = crs.getItemStack().getAmount();
					// If inventory is not full, add item
					for(int i = 0; i < numItems; i++) {
						if(Handler.get().invIsFull(crs.getItemStack().getItem())) {
							break;
						}else {
							Handler.get().giveItem(crs.getItemStack().getItem(), 1);
							
							if(crs.getItemStack().getAmount() > 1)
								crs.getItemStack().setAmount(crs.getItemStack().getAmount() - 1);
							else
								crs.setItemStack(null);
						}
					}
					
					craftResultPressed = false;
				}
			}
			
			for(CraftingSlot cs : craftingSlots) {
				
				cs.tick();
				
				Rectangle craftSlot = new Rectangle(cs.getX(), cs.getY(), CraftingSlot.SLOTSIZE, CraftingSlot.SLOTSIZE);
				
				// If the player drags an item from a crafting slot
				if(Handler.get().getMouseManager().isDragged()){
					if(craftSlot.contains(mouse) && !hasBeenPressed && !itemSelected) {
						hasBeenPressed = true;
						
						// If we don't have anything selected, pick the item up.
						if(currentSelectedSlot == null) {
							if(cs.getItemStack() != null) {
								currentSelectedSlot = cs.getItemStack();
								cs.setItemStack(null);
								itemSelected = true;
								findRecipe();
							}
							else{
								hasBeenPressed = false;
								return;
							}
						}
					}
				}
				
				// If the item is released
				if(itemSelected && !Handler.get().getMouseManager().isDragged()) {
					if(craftSlot.contains(mouse)){
						// If the itemstack already holds an item
						if(cs.getItemStack() != null) {
							if(currentSelectedSlot.getItem().isStackable()) {
								// And if the item in the slot is stackable
								if(cs.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
									// Add the item back to the inventory
									currentSelectedSlot = null;
									itemSelected = false;
									hasBeenPressed = false;
									findRecipe();
								
								}else {
									// If we cannot add the item to an existing stack
									hasBeenPressed = false;
									return;
								}
							}
							else {
								// If the item is not stackable / we cannot add the item
								hasBeenPressed = false;
							}
						}else {
							// If the item stack == null, we can safely add it.
							cs.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount());
							currentSelectedSlot = null;
							itemSelected = false;
							hasBeenPressed = false;
							findRecipe();
						}
					}
				}
				
				// If an item is right-clicked
				if(InventoryWindow.isOpen) {
					if(craftSlot.contains(mouse) && Handler.get().getMouseManager().isRightPressed() && !hasBeenPressed && !Handler.get().getMouseManager().isDragged()){
						hasBeenPressed = true;
						if(cs.getItemStack() != null){
							// If the inventory is full, return
							if(Handler.get().invIsFull(cs.getItemStack().getItem())) {
								hasBeenPressed = false;
								return;
							}else {
								// Otherwise add the item to the inventory
								Handler.get().getInventory().getItemSlots().get(Handler.get().getInventory().findFreeSlot(cs.getItemStack().getItem())).addItem(cs.getItemStack().getItem(), cs.getItemStack().getAmount());
								cs.setItemStack(null);
								hasBeenPressed = false;
								findRecipe();
								return;
							}
						}
						else{
							hasBeenPressed = false;
							return;
						}
					}
				}
			}
		}
		
	}
	
	public void render(Graphics g) {
		
		if(isOpen) {
		
			g.drawImage(Assets.invScreen, x, y, width, height, null);

			Text.drawString(g, "Crafting", x + width / 2, y + 26, true, Color.YELLOW, Assets.font20);
			
			
			crs.render(g);
			cb.render(g);
			Text.drawString(g, "Craft " + craftAmount, x + width / 2, y + height - 96, true, Color.YELLOW, Assets.font20);
		
			for(CraftingSlot cs : craftingSlots) {
				
				cs.render(g);
				
				if(currentSelectedSlot != null){
					g.drawImage(currentSelectedSlot.getItem().getTexture(), Handler.get().getMouseManager().getMouseX(),
							Handler.get().getMouseManager().getMouseY(), null);
						g.setFont(Assets.font14);
						g.setColor(Color.YELLOW);
						g.drawString(Integer.toString(currentSelectedSlot.getAmount()), Handler.get().getMouseManager().getMouseX() + 12, Handler.get().getMouseManager().getMouseY() + 16);
				}
			}
			
			if(possibleRecipe != null) {
				craftableRecipe = String.valueOf(possibleRecipe.getAmount());
				
				g.drawImage(Assets.shopWindow, x + width, y, width - 40, height / 2, null);
				
			}else {
				g.dispose();
				return;
			}
			
			if(possibleRecipe != null) {
				if(craftImg != null) {
					for(int i = 0; i < totalCraftAmount.length; i++) {
						Text.drawString(g, totalCraftAmount[i], craftingSlots.get(filledCraftSlots[i]).getX() + 16, craftingSlots.get(filledCraftSlots[i]).getY() - 8, true, Color.YELLOW, Assets.font14);
					}
					
					if(craftRecipe.isDiscovered()) {
						g.drawImage(craftImg, x + width + (width / 2) - 36, y + 32, null);
					}else {
						g.drawImage(Assets.undiscovered, x + width + (width / 2) - 36, y + 32, null);
					}
					
					if(hovering && craftRecipe.isDiscovered()) {
						g.drawImage(Assets.shopWindow, Handler.get().getMouseManager().getMouseX() + 8, Handler.get().getMouseManager().getMouseY(), 112, 128, null);
						
						Text.drawString(g, possibleRecipe.getItem().getName(), Handler.get().getMouseManager().getMouseX() + 12, Handler.get().getMouseManager().getMouseY() + 16, false, Color.YELLOW, Assets.font14);
						Text.drawString(g, possibleRecipe.getItem().getItemRarity().toString(), Handler.get().getMouseManager().getMouseX() + 12, Handler.get().getMouseManager().getMouseY() + 32, false, ItemRarity.getColor(possibleRecipe.getItem()), Assets.font14);
						
						if(possibleRecipe.getItem().getEquipSlot() != 12) {
							Text.drawString(g, "Power: " + Integer.toString(possibleRecipe.getItem().getPower()), Handler.get().getMouseManager().getMouseX() + 12, Handler.get().getMouseManager().getMouseY() + 48, false, Color.YELLOW, Assets.font14);
							Text.drawString(g, "Defence: " + Integer.toString(possibleRecipe.getItem().getDefence()), Handler.get().getMouseManager().getMouseX() + 12, Handler.get().getMouseManager().getMouseY() + 64, false, Color.YELLOW, Assets.font14);
							Text.drawString(g, "Vitality: " + Integer.toString(possibleRecipe.getItem().getVitality()), Handler.get().getMouseManager().getMouseX() + 12, Handler.get().getMouseManager().getMouseY() + 80, false, Color.YELLOW, Assets.font14);
							Text.drawString(g, "ATK Speed: " + Float.toString(possibleRecipe.getItem().getAttackSpeed()), Handler.get().getMouseManager().getMouseX() + 12, Handler.get().getMouseManager().getMouseY() + 96, false, Color.YELLOW, Assets.font14);
							Text.drawString(g, "Mov. SPD: " + Float.toString(possibleRecipe.getItem().getMovementSpeed()), Handler.get().getMouseManager().getMouseX() + 12, Handler.get().getMouseManager().getMouseY() + 112, false, Color.YELLOW, Assets.font14);
						}
					}
				}
				Text.drawString(g, "You can craft: ", x + width + (width / 2) - 20, y + 16, true, Color.YELLOW, Assets.font14);
				
				if(craftRecipe.isDiscovered()) {
					Text.drawString(g, craftableRecipe, x + width + (width / 2) - 36, y + 40, false, Color.YELLOW, Assets.font14);
				}
			}
			
		}
		
	}
	
	public void openWindow() {
		CraftingUI.isOpen = true;
		CharacterUI.isOpen = false;
		QuestUI.isOpen = false;
		QuestHelpUI.isOpen = false;
		QuestUI.renderingQuests = false;
		SkillsUI.isOpen = false;
		SkillsOverviewUI.isOpen = false;
	}
	
	private void closeCraftingUI() {
		if(currentSelectedSlot != null) {
			if(!Handler.get().invIsFull(currentSelectedSlot.getItem())) {
				Handler.get().giveItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount());
			}else {
				Handler.get().dropItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount(),
						(int)Handler.get().getPlayer().getX(), (int)Handler.get().getPlayer().getY());
			}
			currentSelectedSlot = null;
			itemSelected = false;
			hasBeenPressed = false;
		}
		if(crs.getItemStack() != null) {
			int numItems = crs.getItemStack().getAmount();
			for(int i = 0; i < numItems; i++) {
				if(!Handler.get().invIsFull(crs.getItemStack().getItem())) {
					Handler.get().giveItem(crs.getItemStack().getItem(), 1);
					if(crs.getItemStack().getAmount() > 1)
						crs.getItemStack().setAmount(crs.getItemStack().getAmount() - 1);
					else
						crs.setItemStack(null);
					findRecipe();
				}else {
					Handler.get().dropItem(crs.getItemStack().getItem(), 1,
							(int)Handler.get().getPlayer().getX(), (int)Handler.get().getPlayer().getY());
					if(crs.getItemStack().getAmount() > 1)
						crs.getItemStack().setAmount(crs.getItemStack().getAmount() - 1);
					else
						crs.setItemStack(null);
					findRecipe();
				}
			}
		}
		
		boolean invFull = false;
		for(CraftingSlot cs : craftingSlots) {
			if(cs.getItemStack() != null) {
				if(!Handler.get().invIsFull(cs.getItemStack().getItem())) {
					Handler.get().giveItem(cs.getItemStack().getItem(), cs.getItemStack().getAmount());
					cs.setItemStack(null);
					findRecipe();
				}else {
					invFull = true;
					Handler.get().dropItem(cs.getItemStack().getItem(), cs.getItemStack().getAmount(), (int)Handler.get().getPlayer().getX(), (int)Handler.get().getPlayer().getY());
					cs.setItemStack(null);
					findRecipe();
					
				}
			}
		}
		if(invFull)
			Handler.get().sendMsg("The remaining items in the crafting slots have been dropped.");
		isOpen = false;
	}
	
	/*
	 * Finds the first free slot in the crafting UI to add an item to
	 * @returns: the index in the List of Slots / -1 if no space
	 */
	public int findFreeSlot(Item item) {
		boolean firstFreeSlotFound = false;
		int index = -1;
        for (int i = 0; i < craftingSlots.size(); i++) {
        	if(craftingSlots.get(i).getItemStack() == null) {	
            	if(!firstFreeSlotFound) {
	            	firstFreeSlotFound = true;
	            	index = i;
            	}
            }
        	else if(craftingSlots.get(i).getItemStack() != null && !item.isStackable()) {
        		continue;
        	}
        	else if(craftingSlots.get(i).getItemStack() != null && item.isStackable()){
        		if(craftingSlots.get(i).getItemStack().getItem().getId() == item.getId()){
            		return i;
        		}
        	}
       }
        if(index != -1)
        	return index;
       return -1;
	}
	
	public void craftItem() {
		
		//Create an ArrayList to store the ItemStacks from the Crafting Slots
		ArrayList<ItemStack> tempCraftSlotList = new ArrayList<ItemStack>();
		
		int nullSlots = 0;
		
		//Fill the ArrayList with the slots (skip empty slots)
		for (int i = 0; i < craftingSlots.size(); i++) {
			if(craftingSlots.get(i).getItemStack() == null) {
				nullSlots++;
				continue;
			}else {
				tempCraftSlotList.add(craftingSlots.get(i).getItemStack());
				
			}
		}
		
		if(nullSlots == craftingSlots.size()) {
			nullSlots = 0;
			tempCraftSlotList.clear();
			return;
		}
		
		Collections.sort(tempCraftSlotList, new Comparator<ItemStack>() {

			@Override
			public int compare(ItemStack o1, ItemStack o2) {
				Integer a = o1.getItem().getId();
				Integer b = o2.getItem().getId();
				return a.compareTo(b);
			}
		});
		
		// Create an ArrayList to store Components from Recipes in
		ArrayList<ItemStack> tempCraftRecipeList = new ArrayList<ItemStack>();
		
		int matches = 0;
		
		// Iterate over all recipes
		for (int i = 0; i < craftingManager.getRecipes().size(); i++) {
			// Temporarily set tempCraftRecipeList to the current iteration of the recipe
			for (int j = 0; j < craftingManager.getRecipes().get(i).getComponents().size(); j++) {
				tempCraftRecipeList.add(craftingManager.getRecipes().get(i).getComponents().get(j));
			}
			
			Collections.sort(tempCraftRecipeList, new Comparator<ItemStack>() {

				@Override
				public int compare(ItemStack o1, ItemStack o2) {
					Integer a = o1.getItem().getId();
					Integer b = o2.getItem().getId();
					return a.compareTo(b);
				}
			});
			
			for (int k = 0; k < tempCraftRecipeList.size(); k++) {
				// If user put in X items, skip recipes that are < or > than X
				if(tempCraftSlotList.size() < tempCraftRecipeList.size() || tempCraftSlotList.size() > tempCraftRecipeList.size()) {
					break;
				}
				// If item matches AND the quantity is equal or higher, add a match
				if(tempCraftRecipeList.get(k).getItem().getId() == tempCraftSlotList.get(k).getItem().getId() && tempCraftSlotList.get(k).getAmount() >= tempCraftRecipeList.get(k).getAmount()) {
					matches++;
				}
				else {
					// If the item is not the same OR the quantity is not met, set matches back to 0
					matches = 0;
				}
			}
			
			
			// If we have all matching items and we don't have any empty slots, craft the item
			if(matches == tempCraftRecipeList.size()) {
				
				// If we haven't discovered the recipe yet
				if(!craftingManager.getRecipes().get(i).isDiscovered()) {
					Handler.get().sendMsg("Explore the world or do quests to unlock recipes!");
					findRecipe();
					break;
				}
				
				// If we don't have the crafting level requirement
				if(Handler.get().getSkillsUI().getSkill(SkillsList.CRAFTING).getLevel() < craftingManager.getRecipes().get(i).getRequiredLevel()) {
					findRecipe();
					Handler.get().sendMsg("You need a crafting level of " + craftingManager.getRecipes().get(i).getRequiredLevel() + " to make this item.");
					break;
				}
				// If we try to craft a new item before claiming the last one, break
				if(crs.getItemStack() != null && crs.getItemStack().getItem().getId() != getRecipeItem(i).getItem().getId()) {
					findRecipe();
					Handler.get().sendMsg("Please claim your crafted item before creating a new one.");
					break;
				}
				
				// Stores the slots that are filled
				int[] test = new int[craftingManager.getRecipes().get(i).getComponents().size()];
				int index = 0;
				
				for(int j = 0; j < craftingSlots.size(); j++) {
					if(craftingSlots.get(j).getItemStack() == null) {
						continue;
					}else {
						test[index] = j;
						index++;
					}
				}
				for(int j = 0; j < craftingSlots.size(); j++) {
					if(craftingSlots.get(j).getItemStack() == null)
						continue;
					
					for(int k = 0; k < craftingManager.getRecipes().get(i).getComponents().size(); k++) {
						if(craftingSlots.get(j).getItemStack() == null)
							continue;
						if(craftingManager.getRecipes().get(i).getComponents().get(k).getItem().getId() == craftingSlots.get(j).getItemStack().getItem().getId()) {
							if(craftingSlots.get(j).getItemStack().getAmount() > craftingManager.getRecipes().get(i).getComponents().get(k).getAmount()) {
								craftingSlots.get(j).getItemStack().setAmount(craftingSlots.get(j).getItemStack().getAmount() - craftingManager.getRecipes().get(i).getComponents().get(k).getAmount());
							}
							else if(craftingSlots.get(j).getItemStack().getAmount() == craftingManager.getRecipes().get(i).getComponents().get(k).getAmount()) {
								craftingSlots.get(test[j]).setItemStack(null);
							}
						}
							
					}
				}
	
				// Add an item to the result slot
				possibleRecipe = getRecipeItem(i);
				craftRecipe = craftingManager.getRecipes().get(i);
				makeItem(i);
				Handler.get().getSkillsUI().getSkill(SkillsList.CRAFTING).addExperience(craftingManager.getRecipes().get(i).getCraftingXP());
				findRecipe();
				
				// Set matches back to 0 for next craft and stop iterating
				matches = 0;
				break;
			}
			// If there's no match, retry with the next recipe
			tempCraftRecipeList.clear();
			matches = 0;
			craftRecipe = null;
			possibleRecipe = null;
			craftImg = null;
		}
		
		// Clear all ArrayLists
		matches = 0;
		tempCraftSlotList.clear();
		tempCraftRecipeList.clear();
	}
	/*
	 * Create an item
	 * @param: Recipe ID (int), adds the item to the inventory
	 */
	public void makeItem(int recipeID) {
		
		// If the recipe hasn't been made before, make it discovered
//		if(craftRecipe == craftingRecipeList.getRecipes().get(recipeID)) {
//			if(!craftingRecipeList.getRecipes().get(recipeID).isDiscovered()) {
//				craftingRecipeList.getRecipes().get(recipeID).setDiscovered(true);
//				Handler.get().sendMsg("Discovered recipe for: " + possibleRecipe.getItem().getName() + ".");
//			}
//		}
		
		crs.addItem(craftingManager.getRecipes().get(recipeID).getResult().getItem(), craftingManager.getRecipes().get(recipeID).getResult().getAmount());
		
	}
	
	/*
	 * Checks if the current items in the craftingslots make up an existing recipe
	 */
	public void findRecipe() {
		
		//Create an ArrayList to store the ItemStacks from the Crafting Slots
		ArrayList<ItemStack> tempCraftSlotList = new ArrayList<ItemStack>();
		
		int nullSlots = 0;
		
		//Fill the ArrayList with the slots (skip empty slots)
		for (int i = 0; i < craftingSlots.size(); i++) {
			if(craftingSlots.get(i).getItemStack() == null) {
				nullSlots++;
				continue;
			}else {
				tempCraftSlotList.add(craftingSlots.get(i).getItemStack());
				
			}
		}
		
		if(nullSlots == craftingSlots.size()) {
			craftRecipe = null;
			possibleRecipe = null;
			craftImg = null;
			return;
		}
		
		Collections.sort(tempCraftSlotList, new Comparator<ItemStack>() {

			@Override
			public int compare(ItemStack o1, ItemStack o2) {
				Integer a = o1.getItem().getId();
				Integer b = o2.getItem().getId();
				return a.compareTo(b);
			}
		});
		
		// Create an ArrayList to store Components from Recipes in
		ArrayList<ItemStack> tempCraftRecipeList = new ArrayList<ItemStack>();
		
		int matches = 0;
		
		// Iterate over all recipes
		for (int i = 0; i < craftingManager.getRecipes().size(); i++) {
			// Temporarily set tempCraftRecipeList to the current iteration of the recipe
			for (int j = 0; j < craftingManager.getRecipes().get(i).getComponents().size(); j++) {
				tempCraftRecipeList.add(craftingManager.getRecipes().get(i).getComponents().get(j));
			}
			
			Collections.sort(tempCraftRecipeList, new Comparator<ItemStack>() {

				@Override
				public int compare(ItemStack o1, ItemStack o2) {
					Integer a = o1.getItem().getId();
					Integer b = o2.getItem().getId();
					return a.compareTo(b);
				}
			});
			
			for (int k = 0; k < tempCraftRecipeList.size(); k++) {
				// If user put in X items, skip recipes that are < or > than X
				if(tempCraftSlotList.size() < tempCraftRecipeList.size() || tempCraftSlotList.size() > tempCraftRecipeList.size()) {
					break;
				}
				// If item matches AND the quantity is equal or higher, add a match
				if(tempCraftRecipeList.get(k).getItem().getId() == tempCraftSlotList.get(k).getItem().getId() && tempCraftSlotList.get(k).getAmount() >= tempCraftRecipeList.get(k).getAmount()) {
					matches++;
				}
				else {
					// If the item is not the same OR the quantity is not met, set matches back to 0
					matches = 0;
				}
			}
			
			
			// If we have all matching items and we don't have any empty slots, then we have found a recipe
			if(matches == tempCraftRecipeList.size()) {
				
				possibleRecipe = getRecipeItem(i);
				craftRecipe = craftingManager.getRecipes().get(i);
				craftImg = craftingManager.getRecipes().get(i).getResult().getItem().getTexture();
				
				totalCraftAmount = new String[craftingManager.getRecipes().get(i).getComponents().size()];
				
				int temp = 0;
				filledCraftSlots = new int[totalCraftAmount.length];
				for(int j = 0; j < craftingSlots.size(); j++) {
					if(craftingSlots.get(j).getItemStack() == null) {
						continue;
					}else {
						filledCraftSlots[temp] = j;
						temp++;
					}
				}
				
				temp = 0;
				for(int j = 0; j < craftingSlots.size(); j++) {
					if(craftingSlots.get(j).getItemStack() == null)
						continue;
					
					for(int k = 0; k < craftingManager.getRecipes().get(i).getComponents().size(); k++) {
						if(craftingManager.getRecipes().get(i).getComponents().get(k).getItem().getId() == craftingSlots.get(j).getItemStack().getItem().getId()) {
							totalCraftAmount[temp] = Integer.toString(craftingSlots.get(j).getItemStack().getAmount()) + " / " + Integer.toString(craftingManager.getRecipes().get(i).getComponents().get(k).getAmount());
							temp++;
						}
							
					}
				}
				
				// Set matches back to 0 for next craft and stop iterating
				matches = 0;
				break;
			}
			// If there's no match, retry with the next recipe
			tempCraftRecipeList.clear();
			matches = 0;
			craftRecipe = null;
			possibleRecipe = null;
			craftImg = null;
		}
		
		// Clear all ArrayLists
		matches = 0;
		tempCraftSlotList.clear();
		tempCraftRecipeList.clear();
	}
	
	/*
	 * Returns the ItemStack (the possible recipe)
	 * @param: the recipe id
	 */
	public ItemStack getRecipeItem(int recipeID) {
		return craftingManager.getRecipes().get(recipeID).getResult();
	}

	public CopyOnWriteArrayList<CraftingSlot> getCraftingSlots() {
		return craftingSlots;
	}

	public void setCraftingSlots(CopyOnWriteArrayList<CraftingSlot> craftingSlots) {
		this.craftingSlots = craftingSlots;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public CraftResultSlot getCraftResultSlot() {
		return crs;
	}

	public void setCraftResultSlot(CraftResultSlot crs) {
		this.crs = crs;
	}

	public CraftingManager getCraftingRecipeList() {
		return craftingManager;
	}

	public void setCraftingRecipeList(CraftingManager craftingManager) {
		this.craftingManager = craftingManager;
	}

	public Rectangle getWindowBounds() {
		return windowBounds;
	}

	public void setWindowBounds(Rectangle windowBounds) {
		this.windowBounds = windowBounds;
	}

}
