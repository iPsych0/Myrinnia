package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;

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
	private RecipeList recipeList;
	
	public CraftingUI(Handler handler, int x, int y) {
		
		this.handler = handler;
		this.x = x;
		this.y = y;
		width = 242;
		height = 320;
		
		if(!isCreated) {
			
			craftingSlots = new CopyOnWriteArrayList<CraftingSlot>();
			
//			for(int i = 0; i < numCols; i++) {
//				for(int j = 0; j < numRows; j++) {
//					craftingSlots.add(new CraftingSlot(x + 16 + (32 * i), y + height - (32 * numCols) - 16 + (32 * j), null));
//				}
//			}
			
			craftingSlots.add(new CraftingSlot(x + 32, y + 50, null));
			craftingSlots.add(new CraftingSlot(x + 80, y + 100, null));
			craftingSlots.add(new CraftingSlot(x + 128, y + 100, null));
			craftingSlots.add(new CraftingSlot(x + 176, y + 50, null));
			
			crs = new CraftResultSlot(x + width / 2 - 16, y + height - 160, null);
			cb = new CraftButton(x + width / 2 - 48, y + height - 112, CraftingSlot.SLOTSIZE * 3, CraftingSlot.SLOTSIZE);
			
			cbBounds = new Rectangle(cb.getBounds());
			crsBounds = new Rectangle(crs.getBounds());
			
			recipeList = new RecipeList();
			
			isCreated = true;
			
		}
		
	}
	
	public void tick() {
		
		if(isOpen) {
			
			crs.tick();
			cb.tick();
			
			Rectangle temp = new Rectangle(handler.getWorld().getHandler().getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			if(handler.getMouseManager().isLeftPressed()) {
				if(cbBounds.contains(temp) && !hasBeenPressed && craftButtonPressed) {
					hasBeenPressed = true;
					
					craftItem();
					
					craftButtonPressed = false;
					hasBeenPressed = false;
				}
			}
			
			if(handler.getMouseManager().isRightPressed()) {
				if(crsBounds.contains(temp) && !hasBeenPressed && craftResultPressed) {
					if(crs.getItemStack() == null) {
						return;
					}
					hasBeenPressed = true;
					
					handler.giveItem(crs.getItemStack().getItem(), crs.getItemStack().getAmount());
					crs.setItemStack(null);
					
					craftResultPressed = false;
					hasBeenPressed = false;
				}
			}
			
			for(CraftingSlot cs : craftingSlots) {
				
				cs.tick();
				
				Rectangle temp2 = new Rectangle(cs.getX(), cs.getY(), CraftingSlot.SLOTSIZE, CraftingSlot.SLOTSIZE);
				
				if(handler.getMouseManager().isDragged()){
					if(temp2.contains(temp) && !hasBeenPressed && !itemSelected) {
						hasBeenPressed = true;
						
						if(currentSelectedSlot == null) {
							if(cs.getItemStack() != null) {
								currentSelectedSlot = cs.getItemStack();
								System.out.println("Currently holding: " + cs.getItemStack().getItem().getName());
								cs.setItemStack(null);
								itemSelected = true;
							}
							else{
								System.out.println("Dragging from an empty item stack");
								hasBeenPressed = false;
								return;
							}
						}
					}
				}
				
				if(itemSelected && !handler.getMouseManager().isDragged()) {
					if(temp2.contains(temp)){
						if(cs.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
							currentSelectedSlot = null;
							itemSelected = false;
							hasBeenPressed = false;
						
						}
					}
				}
				
				if(InventoryWindow.isOpen) {
					if(temp2.contains(temp) && handler.getMouseManager().isRightPressed() && !hasBeenPressed && !handler.getMouseManager().isDragged()){
						hasBeenPressed = true;
						if(cs.getItemStack() != null){
							if(handler.getWorld().getInventory().findFreeSlot(cs.getItemStack().getItem()) == -1) {
								hasBeenPressed = false;
								return;
							}else {
								handler.getWorld().getInventory().getItemSlots().get(handler.getWorld().getInventory().findFreeSlot(cs.getItemStack().getItem())).addItem(cs.getItemStack().getItem(), cs.getItemStack().getAmount());
								cs.setItemStack(null);
								hasBeenPressed = false;
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
		
			g.drawImage(Assets.craftWindow, x, y, width, height, null);
			
//			g.setColor(Color.DARK_GRAY);
//			g.fillRect(x + 8, y + 12, 64, 48);
//			g.fillRect(x + 88, y + 12, 64, 48);
//			g.fillRect(x + 168, y + 12, 64, 48);
//			
//			g.setColor(Color.BLACK);
//			g.drawRect(x + 8, y + 12, 64, 48);
//			g.drawRect(x + 88, y + 12, 64, 48);
//			g.drawRect(x + 168, y + 12, 64, 48);
//			
//			g.setFont(Assets.font14);
//			g.setColor(Color.YELLOW);
			Text.drawString(g, "Crafting", x + width / 2, y + 16, true, Color.YELLOW, Assets.font20);
//			Text.drawString(g, "Smithing", x + 88 + (64 / 2), y + 12 + (48 / 2), true, Color.YELLOW, Assets.font14);
//			Text.drawString(g, "Brewing", x + 168 + (64 / 2), y + 12 + (48 / 2), true, Color.YELLOW, Assets.font14);
			
			
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
		}
		
	}
	
	public int findFreeSlot(Item item) {
        for (int i = 0; i < craftingSlots.size(); i++) {
        	if(craftingSlots.get(i).getItemStack() != null){
        		if(craftingSlots.get(i).getItemStack().getItem().getName() == item.getName()){
        			System.out.println("This item is already in the crafting window.");
            		return i;
        		}
        	}
            if (craftingSlots.get(i).getItemStack() == null) {
                return i;
            }
       }
       System.out.println("You can't put any more items in.");
       handler.getPlayer().getChatWindow().sendMessage("You can't put any more items in the crafting window.");
       return -1;
	}
	
	public void craftItem() {
		
		//Create an ArrayList to store the ItemStacks from the Crafting Slots
		ArrayList<ItemStack> tempCraftSlotList = new ArrayList<ItemStack>();
		
		//Fill the ArrayList with the slots (skip empty slots)
		for (int i = 0; i < craftingSlots.size(); i++) {
			if(craftingSlots.get(i).getItemStack() == null) {
				continue;
			}else {
				tempCraftSlotList.add(craftingSlots.get(i).getItemStack());
				
			}
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
		for (int i = 0; i < recipeList.getRecipes().size(); i++) {
			// Temporarily set tempCraftRecipeList to the current iteration of the recipe
			tempCraftRecipeList = recipeList.getRecipes().get(i).getComponents();
			// Iterate over this recipe's components 
			for (int j = 0; j < recipeList.getRecipes().get(i).getComponents().size(); j++) {
				// Store the recipe component's Item IDs in the ArrayList
				sortedCraftRecipe.add(tempCraftRecipeList.get(j).getItem().getId());
			}
			// Sort the recipe component's Item IDs in ascending order
			Collections.sort(sortedCraftRecipe);
			
			for (int k = 0; k < tempCraftRecipeList.size(); k++) {
				// If user put in X items, skip all recipes that are < or > than X
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
			
			// If we have all matching items and we don't have any empty slots, craft the item
			if(matches == sortedCraftSlots.size() && sortedCraftSlots.size() != 0) {
				System.out.println("All items match for this recipe: '" + i + "'");
				
				for (int j = 0; j < tempCraftSlotList.size(); j++) {
					if(tempCraftSlotList.get(j) == null) {
						continue;
					}
					if(tempCraftSlotList.get(j).getAmount() > tempCraftRecipeList.get(j).getAmount()) {
						tempCraftSlotList.get(j).setAmount(tempCraftSlotList.get(j).getAmount() - tempCraftRecipeList.get(j).getAmount());
					}
					if(tempCraftSlotList.get(j).getAmount() == tempCraftRecipeList.get(j).getAmount()) {
						craftingSlots.get(j).setItemStack(null);
					}
				}
				
				// Add an item to the result slot
				makeItem(i);		
				
				// Set matches back to 0 for next craft and stop iterating
				matches = 0;
				break;
			}
			// If there's no match, retry with the next recipe
			sortedCraftRecipe.clear();
			matches = 0;
		}
		
		// Clear all ArrayLists
		tempCraftSlotList.clear();
		tempCraftRecipeList.clear();
		sortedCraftRecipe.clear();
		sortedCraftSlots.clear();
	}
	
	public void makeItem(int id) {
		switch(id) {
			case 0:
				crs.addItem(Item.testSword, 1);
				break;
			case 1:
				crs.addItem(Item.coinsItem, 100);
				break;
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
	

}
