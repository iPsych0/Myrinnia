package dev.ipsych0.mygame.crafting;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemRarity;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.states.GameState;

public class CraftingUI {
	
	private int x, y, width, height;
	public static boolean isOpen = false;
	private boolean isCreated = false;
	private boolean hasBeenPressed = false;
	private boolean itemSelected = false;
	private Handler handler;
	private CopyOnWriteArrayList<CraftingSlot> craftingSlots;
	private CraftResultSlot crs;
	private CraftButton cb;
	private int numRows = 2;
	private int numCols = 2;
	private int craftAmount = 1;
	public static boolean craftButtonPressed = false;
	public static boolean craftResultPressed = false;
	private Rectangle cbBounds;
	private ItemStack currentSelectedSlot;
	private Rectangle crsBounds;
	private CraftingRecipeList craftingRecipeList;
	private ItemStack possibleRecipe = null;
	private String craftableRecipe;
	private BufferedImage craftImg;
	private Rectangle previewImg;
	private boolean hovering = false;
	private Rectangle windowBounds;
	private String[] totalCraftAmount;
	private int[] filledCraftSlots;
	private CraftingRecipe craftRecipe;
	
	public CraftingUI(Handler handler, int x, int y) {
		
		this.handler = handler;
		this.x = x;
		this.y = y;
		width = 242;
		height = 320;
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
			
			craftingRecipeList = new CraftingRecipeList();
			
			previewImg = new Rectangle(x + width + (width / 2) - 36, y + 32, 32, 32);
			
			isCreated = true;
			
		}
		
	}
	
	public void tick() {
		
		if(isOpen) {
			
			crs.tick();
			cb.tick();
			
			Rectangle mouse = new Rectangle(handler.getWorld().getHandler().getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			if(previewImg.contains(mouse)) {
				hovering = true;
			}else {
				hovering = false;
			}
			
			if(handler.getKeyManager().escape) {
				isOpen = false;
			}
			
			// If left-clicked on the "craft" button, craft the item
			if(handler.getMouseManager().isLeftPressed()) {
				if(cbBounds.contains(mouse) && !hasBeenPressed && craftButtonPressed) {
					hasBeenPressed = true;
					
					craftItem();
					
					craftButtonPressed = false;
					hasBeenPressed = false;
				}
			}
			
			// If right-clicked on the crafted item
			if(handler.getMouseManager().isRightPressed()) {
				if(crsBounds.contains(mouse) && !hasBeenPressed && craftResultPressed) {
					// If there is no item in the slot, return
					if(crs.getItemStack() == null) {
						craftResultPressed = false;
						return;
					}
					hasBeenPressed = true;
					
					// If inventory is not full, add item
					if(!handler.invIsFull(crs.getItemStack().getItem())) {
						handler.giveItem(crs.getItemStack().getItem(), crs.getItemStack().getAmount());
						crs.setItemStack(null);
					}
					
					craftResultPressed = false;
					hasBeenPressed = false;
				}
			}
			
			for(CraftingSlot cs : craftingSlots) {
				
				cs.tick();
				
				Rectangle craftSlot = new Rectangle(cs.getX(), cs.getY(), CraftingSlot.SLOTSIZE, CraftingSlot.SLOTSIZE);
				
				// If the player drags an item from a crafting slot
				if(handler.getMouseManager().isDragged()){
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
				
				// If we are dragging an item and let go of the mouse (not dragging anymore)
				if(itemSelected && !handler.getMouseManager().isDragged()) {
					if(craftSlot.contains(mouse)){
						// Add the item to the slot you're hovering over
						if(cs.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
							currentSelectedSlot = null;
							itemSelected = false;
							hasBeenPressed = false;
							findRecipe();
						
						}
					}
				}
				
				// If an item is right-clicked
				if(InventoryWindow.isOpen) {
					if(craftSlot.contains(mouse) && handler.getMouseManager().isRightPressed() && !hasBeenPressed && !handler.getMouseManager().isDragged()){
						hasBeenPressed = true;
						if(cs.getItemStack() != null){
							// If the inventory is full, return
							if(handler.invIsFull(cs.getItemStack().getItem())) {
								hasBeenPressed = false;
								return;
							}else {
								// Otherwise add the item to the inventory
								handler.getWorld().getInventory().getItemSlots().get(handler.getWorld().getInventory().findFreeSlot(cs.getItemStack().getItem())).addItem(cs.getItemStack().getItem(), cs.getItemStack().getAmount());
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

			Text.drawString(g, "Crafting", x + width / 2, y + 20, true, Color.YELLOW, Assets.font20);
			
			
			crs.render(g);
			cb.render(g);
			Text.drawString(g, "Craft " + craftAmount, x + width / 2, y + height - 96, true, Color.YELLOW, Assets.font20);
		
			for(CraftingSlot cs : craftingSlots) {
				
				cs.render(g);
				
				if(currentSelectedSlot != null){
					g.drawImage(currentSelectedSlot.getItem().getTexture(), handler.getMouseManager().getMouseX(),
							handler.getMouseManager().getMouseY(), null);
						g.setFont(Assets.font14);
						g.setColor(Color.YELLOW);
						g.drawString(Integer.toString(currentSelectedSlot.getAmount()), handler.getMouseManager().getMouseX() + 12, handler.getMouseManager().getMouseY() + 16);
				}
			}
			
			if(possibleRecipe != null) {
				craftableRecipe = String.valueOf(possibleRecipe.getAmount());
				
				g.drawImage(Assets.chatwindow, x + width, y, width - 40, height / 2, null);
				
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
						g.setColor(Color.DARK_GRAY);
						g.fillRect(handler.getMouseManager().getMouseX() + 8, handler.getMouseManager().getMouseY(), 112, 128);
						g.setColor(Color.BLACK);
						g.drawRect(handler.getMouseManager().getMouseX() + 8, handler.getMouseManager().getMouseY(), 112, 128);
						
						Text.drawString(g, possibleRecipe.getItem().getName(), handler.getMouseManager().getMouseX() + 12, handler.getMouseManager().getMouseY() + 16, false, Color.YELLOW, Assets.font14);
						Text.drawString(g, possibleRecipe.getItem().getItemRarity().toString(), handler.getMouseManager().getMouseX() + 12, handler.getMouseManager().getMouseY() + 32, false, ItemRarity.getColor(possibleRecipe.getItem()), Assets.font14);
						
						if(possibleRecipe.getItem().getEquipSlot() != 12) {
							Text.drawString(g, "Power: " + Integer.toString(possibleRecipe.getItem().getPower()), handler.getMouseManager().getMouseX() + 12, handler.getMouseManager().getMouseY() + 48, false, Color.YELLOW, Assets.font14);
							Text.drawString(g, "Defence: " + Integer.toString(possibleRecipe.getItem().getDefence()), handler.getMouseManager().getMouseX() + 12, handler.getMouseManager().getMouseY() + 64, false, Color.YELLOW, Assets.font14);
							Text.drawString(g, "Vitality: " + Integer.toString(possibleRecipe.getItem().getVitality()), handler.getMouseManager().getMouseX() + 12, handler.getMouseManager().getMouseY() + 80, false, Color.YELLOW, Assets.font14);
							Text.drawString(g, "ATK Speed: " + Float.toString(possibleRecipe.getItem().getAttackSpeed()), handler.getMouseManager().getMouseX() + 12, handler.getMouseManager().getMouseY() + 96, false, Color.YELLOW, Assets.font14);
							Text.drawString(g, "Mov. SPD: " + Float.toString(possibleRecipe.getItem().getMovementSpeed()), handler.getMouseManager().getMouseX() + 12, handler.getMouseManager().getMouseY() + 112, false, Color.YELLOW, Assets.font14);
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
        			System.out.println("We already have this item in our inventory!");
        			
            		return i;
        		}
        	}
       }
        if(index != -1)
        	return index;
       System.out.println("No free inventory slot available.");
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
		
		// Create an ArrayList of ints to store the Item IDs.
		ArrayList<Integer> sortedCraftSlots = new ArrayList<Integer>();
		
		// Fill the ArrayList with the Item IDs
		for (int i = 0; i < tempCraftSlotList.size(); i++) {
			sortedCraftSlots.add(tempCraftSlotList.get(i).getItem().getId());
		}
		// Sort the IDs numerically in ascending order
		Collections.sort(sortedCraftSlots);
		
		// Create an ArrayList to store Components from Recipes in
		ArrayList<ItemStack> tempCraftRecipeList = new ArrayList<ItemStack>();
		
		// Create an ArrayList to store Item IDs from Components in
		ArrayList<Integer> sortedCraftRecipe = new ArrayList<Integer>();
		
		int matches = 0;
		
		// Iterate over all recipes
		for (int i = 0; i < craftingRecipeList.getRecipes().size(); i++) {
			// Temporarily set tempCraftRecipeList to the current iteration of the recipe
			for (int j = 0; j < craftingRecipeList.getRecipes().get(i).getComponents().size(); j++) {
				tempCraftRecipeList.add(craftingRecipeList.getRecipes().get(i).getComponents().get(j));
			}
			// Iterate over this recipe's components 
			for (int j = 0; j < craftingRecipeList.getRecipes().get(i).getComponents().size(); j++) {
				// Store the recipe component's Item IDs in the ArrayList
				sortedCraftRecipe.add(tempCraftRecipeList.get(j).getItem().getId());
			}
			// Sort the recipe component's Item IDs in ascending order
			Collections.sort(sortedCraftRecipe);
			
			for (int k = 0; k < tempCraftRecipeList.size(); k++) {
				// If user put in X items, skip recipes that are < or > than X
				if(sortedCraftSlots.size() < tempCraftRecipeList.size() || sortedCraftSlots.size() > tempCraftRecipeList.size()) {
					continue;
				}
				System.out.println(sortedCraftSlots.get(k) + " <- CraftID " + sortedCraftRecipe.get(k) + " <- RecipeID en de amounts zijn: " + tempCraftSlotList.get(k).getAmount() + " en " + tempCraftRecipeList.get(k).getAmount());
				// If item matches AND the quantity is equal or higher, add a match
				if(sortedCraftSlots.get(k) == sortedCraftRecipe.get(k) && tempCraftSlotList.get(k).getAmount() >= tempCraftRecipeList.get(k).getAmount()) {
					System.out.println("We hebben een match! " + (k+1) + " keer!");
					matches++;
				}
				else {
					System.out.println("Geen match!");
					// If the item is not the same OR the quantity is not met, set matches back to 0
					matches = 0;
				}
			}
			
			
			// If we have all matching items and we don't have any empty slots, craft the item
			if(matches == sortedCraftSlots.size()) {
				System.out.println("All items match for this recipe: '" + i + "'");
				if(crs.getItemStack() != null && crs.getItemStack().getItem().getId() != getRecipeItem(i).getItem().getId()) {
					handler.sendMsg("Please claim your crafted item before creating a new one.");
					break;
				}
				
				int[] test = new int[craftingRecipeList.getRecipes().get(i).getComponents().size()];
				int index = 0;
				
				for(int j = 0; j < craftingSlots.size(); j++) {
					if(craftingSlots.get(j).getItemStack() == null) {
						continue;
					}else {
						test[index] = j;
						index++;
					}
				}
				
				// Do the subtractions
				for (int j = 0; j < tempCraftSlotList.size(); j++) {
					if(tempCraftSlotList.get(j) == null) {
						continue;
					}
					if(tempCraftSlotList.get(j).getAmount() > tempCraftRecipeList.get(j).getAmount()) {
						tempCraftSlotList.get(j).setAmount(tempCraftSlotList.get(j).getAmount() - tempCraftRecipeList.get(j).getAmount());
						findRecipe();
					}
					else if(tempCraftSlotList.get(j).getAmount() == tempCraftRecipeList.get(j).getAmount()) {
						craftingSlots.get(test[j]).setItemStack(null);
						findRecipe();
					}
				}
				
				// Add an item to the result slot
				makeItem(i);
				handler.getPlayer().addCraftingExperience(getCraftingRecipeList().getRecipes().get(i).getCraftingXP());
				
				// Set matches back to 0 for next craft and stop iterating
				matches = 0;
				break;
			}
			// If there's no match, retry with the next recipe
			sortedCraftRecipe.clear();
			tempCraftRecipeList.clear();
			matches = 0;
		}
		
		// Clear all ArrayLists
		matches = 0;
		tempCraftSlotList.clear();
		tempCraftRecipeList.clear();
		sortedCraftRecipe.clear();
		sortedCraftSlots.clear();
	}
	/*
	 * Create an item
	 * @param: Recipe ID (int), adds the item to the inventory
	 */
	public void makeItem(int recipeID) {
		
		// If the recipe hasn't been made before, make it discovered
		if(!craftRecipe.isDiscovered()) {
			craftRecipe.setDiscovered(true);
			handler.sendMsg("Discovered recipe for: " + possibleRecipe.getItem().getName() + ".");
		}
		
		
		switch(recipeID) {
			case 0:
				if(crs.addItem(Item.testSword, 1));
				break;
			case 1:
				if(crs.addItem(Item.coinsItem, 100));
				break;
			case 2:
				if(crs.addItem(Item.woodItem, 100));
				break;
			case 3:
				if(crs.addItem(Item.purpleSword, 1));
				break;
		}
		
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
			nullSlots = 0;
			tempCraftSlotList.clear();
			return;
		}
		
		// Create an ArrayList of ints to store the Item IDs.
		ArrayList<Integer> sortedCraftSlots = new ArrayList<Integer>();
		
		// Fill the ArrayList with the Item IDs
		for (int i = 0; i < tempCraftSlotList.size(); i++) {
			sortedCraftSlots.add(tempCraftSlotList.get(i).getItem().getId());
		}
		// Sort the IDs numerically in ascending order
		Collections.sort(sortedCraftSlots);
		
		// Create an ArrayList to store Components from Recipes in
		ArrayList<ItemStack> tempCraftRecipeList = new ArrayList<ItemStack>();
		
		// Create an ArrayList to store Item IDs from Components in
		ArrayList<Integer> sortedCraftRecipe = new ArrayList<Integer>();
		
		int matches = 0;
		
		// Iterate over all recipes
		for (int i = 0; i < craftingRecipeList.getRecipes().size(); i++) {
			// Temporarily set tempCraftRecipeList to the current iteration of the recipe
			for (int j = 0; j < craftingRecipeList.getRecipes().get(i).getComponents().size(); j++) {
				tempCraftRecipeList.add(craftingRecipeList.getRecipes().get(i).getComponents().get(j));
			}
			// Iterate over this recipe's components 
			for (int j = 0; j < craftingRecipeList.getRecipes().get(i).getComponents().size(); j++) {
				// Store the recipe component's Item IDs in the ArrayList
				sortedCraftRecipe.add(tempCraftRecipeList.get(j).getItem().getId());
			}
			// Sort the recipe component's Item IDs in ascending order
			Collections.sort(sortedCraftRecipe);
			
			for (int k = 0; k < tempCraftRecipeList.size(); k++) {
				// If user put in X items, skip recipes that are < or > than X
				if(sortedCraftSlots.size() < tempCraftRecipeList.size() || sortedCraftSlots.size() > tempCraftRecipeList.size()) {
					continue;
				}
				// If item matches AND the quantity is equal or higher, add a match
				if(sortedCraftSlots.get(k) == sortedCraftRecipe.get(k) && tempCraftSlotList.get(k).getAmount() >= tempCraftRecipeList.get(k).getAmount()) {
					matches++;
				}
				else {
					// If the item is not the same OR the quantity is not met, set matches back to 0
					matches = 0;
				}
			}
			
			
			// If we have all matching items and we don't have any empty slots, then we have found a recipe
			if(matches == sortedCraftSlots.size()) {
				
				possibleRecipe = getRecipeItem(i);
				craftRecipe = craftingRecipeList.getRecipes().get(i);
				
				totalCraftAmount = new String[craftingRecipeList.getRecipes().get(i).getComponents().size()];
				int temp = 0;
				
				for(int j = 0; j < craftingRecipeList.getRecipes().get(i).getComponents().size(); j++) {
					totalCraftAmount[j] = Integer.toString(tempCraftSlotList.get(j).getAmount()) + " / " + Integer.toString(tempCraftRecipeList.get(j).getAmount());
				}
				
				filledCraftSlots = new int[totalCraftAmount.length];
				for(int j = 0; j < craftingSlots.size(); j++) {
					if(craftingSlots.get(j).getItemStack() == null) {
						continue;
					}else {
						filledCraftSlots[temp] = j;
						temp++;
					}
				}
				
				
				// Set matches back to 0 for next craft and stop iterating
				matches = 0;
				break;
			}
			// If there's no match, retry with the next recipe
			sortedCraftRecipe.clear();
			tempCraftRecipeList.clear();
			matches = 0;
			possibleRecipe = null;
			craftRecipe = null;
			craftImg = null;
		}
		
		// Clear all ArrayLists
		matches = 0;
		tempCraftSlotList.clear();
		tempCraftRecipeList.clear();
		sortedCraftRecipe.clear();
		sortedCraftSlots.clear();
	}
	
	/*
	 * Returns the ItemStack (the possible recipe)
	 * @param: the recipe id
	 */
	public ItemStack getRecipeItem(int recipeID) {
		
		switch(recipeID) {
			case 0:
				craftImg = Assets.testSword;
				return new ItemStack(Item.testSword, 1);
			case 1:
				craftImg = Assets.coins[2];
				return new ItemStack(Item.coinsItem, 100);
			case 2:
				craftImg = Assets.wood;
				return new ItemStack(Item.woodItem, 100);
			case 3:
				craftImg = Assets.purpleSword;
				return new ItemStack(Item.purpleSword, 1);
			default:
				return new ItemStack(null);
		}
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

	public CraftingRecipeList getCraftingRecipeList() {
		return craftingRecipeList;
	}

	public void setCraftingRecipeList(CraftingRecipeList craftingRecipeList) {
		this.craftingRecipeList = craftingRecipeList;
	}

	public Rectangle getWindowBounds() {
		return windowBounds;
	}

	public void setWindowBounds(Rectangle windowBounds) {
		this.windowBounds = windowBounds;
	}
	

}
