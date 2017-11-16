package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.crafting.CraftingUI;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.shop.ShopWindow;

public class InventoryWindow implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static boolean isOpen = true;
	public static boolean isEquipped = false;
	public static boolean hasBeenPressed = false;
	public static boolean isCreated = false;
	
	private int x, y;
	private int width, height;
	private Handler handler;
	
	private int numCols = 3;
	private int numRows = 10;
	private int alpha = 127;
	private Color interfaceColour = new Color(130, 130, 130, alpha);
	
	private static CopyOnWriteArrayList<ItemSlot> itemSlots;
	private ItemStack currentSelectedSlot;
	private ItemStack itemSwap;
	private ItemStack equipSwap;
	public static boolean itemSelected;
	private Rectangle windowBounds;
	
	public InventoryWindow(Handler handler, int x, int y){
		this.handler = handler;
		this.x = x;
		this.y = y;
		width = numCols * (ItemSlot.SLOTSIZE + 11) + 3;
		height = numRows * (ItemSlot.SLOTSIZE + 11) - 58;
		windowBounds = new Rectangle(x, y, width, height);
		if(isCreated == false){
			
			itemSlots = new CopyOnWriteArrayList<ItemSlot>();
			
			for(int i = 0; i < numCols; i++){
				for(int j = 0; j < numRows; j++){
					if(j == (numRows)){
						x += 8;
					}
					
					itemSlots.add(new ItemSlot(x + 17 + (i * (ItemSlot.SLOTSIZE)), y + 32 + (j * ItemSlot.SLOTSIZE), null));
					
					if(j == (numRows)){
						x -= 8;
					}
				}
			}	
		
			// Prevents multiple instances of the inventory being created over and over when picking up items
			isCreated = true;
			
			getItemSlots().get(findFreeSlot(Item.woodItem)).addItem(Item.woodItem, 100);
			getItemSlots().get(findFreeSlot(Item.oreItem)).addItem(Item.oreItem, 100);
			getItemSlots().get(findFreeSlot(Item.testSword)).addItem(Item.testSword, 1);
			getItemSlots().get(findFreeSlot(Item.purpleSword)).addItem(Item.purpleSword, 1);
			
		}
	}
	
	
	public void tick() {
		if(isOpen) {
			Rectangle mouse = new Rectangle(handler.getWorld().getHandler().getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			for(ItemSlot is : itemSlots) {
				
				is.tick();
				
				Rectangle slot = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				
				// If an item is dragged
				if(handler.getMouseManager().isDragged()){
					if(slot.contains(mouse) && !hasBeenPressed && !itemSelected) {
						hasBeenPressed = true;
						
						// Stick the item to the mouse
						if(currentSelectedSlot == null) {
							if(is.getItemStack() != null) {
								currentSelectedSlot = is.getItemStack();
								is.setItemStack(null);
								itemSelected = true;
							}
							else{
								hasBeenPressed = false;
								return;
							}
						}
					}
				}
				
				// If the item is released
				if(itemSelected && !handler.getMouseManager().isDragged()) {
					if(slot.contains(mouse)){
						// If the itemstack already holds an item
						if(is.getItemStack() != null) {
							if(currentSelectedSlot.getItem().isStackable) {
								// And if the item in the slot is stackable
								if(is.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
									// Add the item back to the inventory
									currentSelectedSlot = null;
									itemSelected = false;
									hasBeenPressed = false;
								
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
							is.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount());
							currentSelectedSlot = null;
							itemSelected = false;
							hasBeenPressed = false;
						}
					}
				}
				
				// If the item is dragged outside the inventory
				if(itemSelected && !handler.getMouseManager().isDragged()){
					if(handler.getMouseManager().getMouseX() <= this.x && handler.getMouseManager().getMouseY() >= this.y){
						// Drop the item
						handler.getWorld().getItemManager().addItem(currentSelectedSlot.getItem().createNew((int)handler.getWorld().getEntityManager().getPlayer().getX(), (int)handler.getWorld().getEntityManager().getPlayer().getY(), currentSelectedSlot.getAmount()));
						currentSelectedSlot = null;
						hasBeenPressed = false;
						itemSelected = false;
					}
				}

				// If item is right-clicked
				if(slot.contains(mouse) && handler.getMouseManager().isRightPressed() && isEquipped && !hasBeenPressed && !handler.getMouseManager().isDragged() && !CraftingUI.isOpen && !ShopWindow.isOpen){
					if(is.getItemStack() != null){
						if(is.getItemStack().getItem().equipSlot == 12){
							// If the item's equipmentslot = 12, that means it's unequippable, so return
							handler.sendMsg("You cannot equip " + is.getItemStack().getItem().getName());
							isEquipped = false;
							hasBeenPressed = false;
							return;
						}
						
						// If the item's equipmentslot is a valid slot
						if(is.getItemStack().getItem().equipSlot >= 0 && is.getItemStack().getItem().equipSlot <= 11){
							if(handler.getWorld().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).getEquipmentStack() != null &&
									is.getItemStack().getItem().getId() ==
									handler.getWorld().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).getEquipmentStack().getItem().getId()){
								// If trying to equip the exact same item, return message
								handler.sendMsg("You've already equipped this item!");
								isEquipped = false;
								hasBeenPressed = false;
								return;
							}
							
							// If we have no item equipped in that slot
							if(handler.getWorld().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).equipItem(is.getItemStack().getItem())){
								handler.getPlayer().addEquipmentStats(is.getItemStack().getItem().getEquipSlot());
								// Add equipment stats and subtract 1 from the item in our inventory
								if(is.getItemStack().getAmount() >= 2) {
									is.getItemStack().setAmount(is.getItemStack().getAmount() - 1);
								}else {
									// Or if only 1 item left, set the stack to null
									is.setItemStack(null);
								}
								isEquipped = false;
								hasBeenPressed = false;
								return;
							}
							else{
								
								// Set the swaps
								itemSwap = is.getItemStack();
								equipSwap = handler.getWorld().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).getEquipmentStack();
								
								// Remove the equipment stats
								handler.getPlayer().removeEquipmentStats(is.getItemStack().getItem().getEquipSlot());
								
								// If more than one of the item
								if(is.getItemStack().getAmount() >= 2) {
									// Subtract one from the inventory stack and then swap
									is.getItemStack().setAmount(is.getItemStack().getAmount() - 1);
									handler.giveItem(equipSwap.getItem(), equipSwap.getAmount());
									handler.getWorld().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).setItem(new ItemStack(itemSwap.getItem(), 1));

								}else {
									// Otherwise, swap the items and set the inventory stack to null
									handler.giveItem(equipSwap.getItem(), equipSwap.getAmount());
									handler.getWorld().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).setItem(itemSwap);
									is.setItemStack(null);
								}
								
								// Add the equipment stats after equipping
								handler.getPlayer().addEquipmentStats(itemSwap.getItem().getEquipSlot());
								
								// Set the swaps back to null
								isEquipped = false;
								hasBeenPressed = false;
								itemSwap = null;
								equipSwap = null;
							}
						}
						else{
							isEquipped = false;
							hasBeenPressed = false;
							return;
						}
					}
					else{
						isEquipped = false;
						hasBeenPressed = false;
						return;
					}
				}
				
				// If right-clicked on an item while CraftingUI is open
				if(CraftingUI.isOpen) {
					if(slot.contains(mouse) && handler.getMouseManager().isRightPressed() && isEquipped && !hasBeenPressed && !handler.getMouseManager().isDragged()){

						hasBeenPressed = true;
						if(is.getItemStack() != null){
							if(handler.getWorld().getCraftingUI().findFreeSlot(is.getItemStack().getItem()) == -1) {
								// If all crafting slots are full, return
								hasBeenPressed = false;
								handler.sendMsg("You cannot add more than 4 items to the crafting window.");
								isEquipped = false;
								return;
							} else {
								// Otherwise, remove the stack from the inventory and put it in a crafting slot
								handler.getWorld().getCraftingUI().getCraftingSlots().get(handler.getWorld().getCraftingUI().findFreeSlot(is.getItemStack().getItem())).addItem(is.getItemStack().getItem(), is.getItemStack().getAmount());
								is.setItemStack(null);
								// Update if there is a possible recipe
								handler.getWorld().getCraftingUI().findRecipe();
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
	
	public void render(Graphics g){
		if(isOpen){
			g.drawImage(Assets.invScreen, x, y, width, height, null);
//			g.setColor(interfaceColour);
//			g.fillRect(x - 16, y - 16, width + 32, height - 8);
//			g.setColor(Color.BLACK);
//			g.drawRect(x - 16, y - 16, width + 32, height - 8);
			g.setFont(Assets.font14);
			g.setColor(Color.YELLOW);
			g.drawString("Inventory", x + 42, y + 24);
			
			Rectangle temp = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			for(ItemSlot is : itemSlots){
				
				is.render(g);
				
				Rectangle temp2 = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				
				if(currentSelectedSlot != null){
					g.drawImage(currentSelectedSlot.getItem().getTexture(), handler.getMouseManager().getMouseX(),
							handler.getMouseManager().getMouseY(), null);
						g.setFont(Assets.font14);
						g.setColor(Color.BLACK);
						g.drawString(Integer.toString(currentSelectedSlot.getAmount()), handler.getMouseManager().getMouseX() + 12, handler.getMouseManager().getMouseY() + 16);
				}
				
				// If hovering over an item in the inventory, draw the tooltip
				if(temp2.contains(temp) && is.getItemStack() != null){
					g.setColor(interfaceColour);
					g.fillRect(x - 145, y, 145, 130);
					g.setColor(Color.BLACK);
					g.drawRect(x - 145, y, 145, 130);
					
					g.setColor(Color.YELLOW);
					g.drawString(is.getItemStack().getItem().getName(), x - 142, y + 16);
					
					/*
					 * Draw the colour of the item's rarity
					 */
					g.setColor(ItemRarity.getColor(is.getItemStack().getItem()));
					g.drawString(is.getItemStack().getItem().getItemRarity().toString(), x - 142, y + 32);
					
					if(is.getItemStack().getItem().getEquipSlot() != 12){
						// Only compare stats if an item is actually equipped
						if(handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack() != null){
							/*
							 * Draw power colour red/green if stats are lower/higher
							 */
					
							if(is.getItemStack().getItem().getPower() > handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getPower()){
								g.setColor(Color.GREEN);
							}
							else if(is.getItemStack().getItem().getPower() < handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getPower()){
								g.setColor(Color.RED);
							}else{
								g.setColor(Color.YELLOW);
							}
							g.drawString("Power: " + is.getItemStack().getItem().getPower(), x - 142, y + 48);
							g.drawString("(" + (is.getItemStack().getItem().getPower() - handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getPower()) + ")", x - 32, y + 48);
							
							/*
							 * Draw defence colour red/green if stats are lower/higher
							 */
							
							if(is.getItemStack().getItem().getDefence() > handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getDefence()){
								g.setColor(Color.GREEN);
							}
							else if(is.getItemStack().getItem().getDefence() < handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getDefence()){
								g.setColor(Color.RED);
							}else{
								g.setColor(Color.YELLOW);
							}
							g.drawString("Defence: " + is.getItemStack().getItem().getDefence(), x - 142, y + 64);
							g.drawString("(" + (is.getItemStack().getItem().getDefence() - handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getDefence()) + ")", x - 32, y + 64);
							
							/*
							 * Draw vitality colour red/green if stats are lower/higher
							 */
							if(is.getItemStack().getItem().getVitality() > handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getVitality()){
								g.setColor(Color.GREEN);
							}
							else if(is.getItemStack().getItem().getVitality() < handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getVitality()){
								g.setColor(Color.RED);
							}else{
								g.setColor(Color.YELLOW);
							}
							g.drawString("Vitality: " + is.getItemStack().getItem().getVitality(), x - 142, y + 80);
							g.drawString("(" + (is.getItemStack().getItem().getVitality() - handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getVitality()) + ")", x - 32, y + 80);
							
							/*
							 * Draw atk speed colour red/green if stats are lower/higher
							 */
							if(is.getItemStack().getItem().getAttackSpeed() > handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getAttackSpeed()){
								g.setColor(Color.GREEN);
							}
							else if(is.getItemStack().getItem().getAttackSpeed() < handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getAttackSpeed()){
								g.setColor(Color.RED);
							}else{
								g.setColor(Color.YELLOW);
							}
							g.drawString("ATK Speed: " + is.getItemStack().getItem().getAttackSpeed(), x - 142, y + 96);
							g.drawString("(" + (is.getItemStack().getItem().getAttackSpeed() - handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getAttackSpeed()) + ")", x - 32, y + 96);
							
							/*
							 * Draw movement speed colour red/green if stats are lower/higher
							 */
							if(is.getItemStack().getItem().getMovementSpeed() > handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getMovementSpeed()){
								g.setColor(Color.GREEN);
							}
							else if(is.getItemStack().getItem().getMovementSpeed() < handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getMovementSpeed()){
								g.setColor(Color.RED);
							}else{
								g.setColor(Color.YELLOW);
							}
							g.drawString("Mov. Speed: " + is.getItemStack().getItem().getMovementSpeed(), x - 142, y + 112);
							g.drawString("(" + (is.getItemStack().getItem().getMovementSpeed() - handler.getWorld().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getMovementSpeed()) + ")", x - 32, y + 112);
						}else{
							g.setColor(Color.YELLOW);
							g.drawString("Power: " + is.getItemStack().getItem().getPower(), x - 142, y + 48);
							g.drawString("Defence: " + is.getItemStack().getItem().getDefence(), x - 142, y + 64);
							g.drawString("Vitality: " + is.getItemStack().getItem().getVitality(), x - 142, y + 80);
							g.drawString("ATK Speed: " + is.getItemStack().getItem().getAttackSpeed(), x - 142, y + 96);
							g.drawString("Mov. Speed: " + is.getItemStack().getItem().getMovementSpeed(), x - 142, y + 112);
						}
					}
				}
			}
		}
	}
	
	/*
	 * Finds a free slot in the inventory
	 * @returns: index if found, -1 if inventory is full
	 */
	public int findFreeSlot(Item item) {
		boolean firstFreeSlotFound = false;
		int index = -1;
        for (int i = 0; i < itemSlots.size(); i++) {
        	if(itemSlots.get(i).getItemStack() != null && item.isStackable){
        		if(itemSlots.get(i).getItemStack().getItem().getId() == item.getId()){
        			System.out.println("We already have this item in our inventory!");
            		return i;
        		}
        	}
        	else if(itemSlots.get(i).getItemStack() != null && !item.isStackable) {
        		continue;
        	}
        	else if(itemSlots.get(i).getItemStack() == null) {
            	if(!firstFreeSlotFound) {
	            	firstFreeSlotFound = true;
	            	index = i;
            	}
            }
       }
        if(index != -1)
        	return index;
       System.out.println("No free inventory slot available.");
       return -1;
	}
	
	/*
	 * Checks the inventory for a given item & quantity met
	 * @returns boolean: true if player has item + quantity, false if player doesn't have item or doesn't have enough
	 */
	public boolean playerHasItem(Item item, int amount) {
		boolean found = false;
		for(int i = 0; i < itemSlots.size(); i++) {
			// Skip empty slots
			if(itemSlots.get(i).getItemStack() == null){
				continue;
			}
			
			//If the item is found
			if(item.getId() == itemSlots.get(i).getItemStack().getItem().getId()){
				if((itemSlots.get(i).getItemStack().getAmount() - amount) < 0){
					found = false;
				}
				else if((itemSlots.get(i).getItemStack().getAmount() - amount) >= 0){
					found = true;
				}
			}
			else{
				continue;
			}
		}
		return found;
	}
	
	/*
	 * Checks if the player has the item+quantity and removes it
	 * @returns boolean: true if successful, false if item+quantity requirement not met
	 */
	public boolean removeItem(Item item, int amount){
		boolean hasItem = false;
		if(!playerHasItem(item, amount)) {
			handler.sendMsg("You don't have enough " + item.getName().toLowerCase() + ".");
			return hasItem;
		}
		for(int i = 0; i < itemSlots.size(); i++){
			if(itemSlots.get(i).getItemStack() == null){
				continue;
			}
			if(item.getName() == itemSlots.get(i).getItemStack().getItem().getName()){
				if((itemSlots.get(i).getItemStack().getAmount() - amount) < 0){
					return hasItem;
				}
				if((itemSlots.get(i).getItemStack().getAmount() - amount) == 0){
					itemSlots.get(i).setItemStack(null);
					hasItem = true;
				}
				else if((itemSlots.get(i).getItemStack().getAmount() - amount) >= 1){
					itemSlots.get(i).getItemStack().setAmount(itemSlots.get(i).getItemStack().getAmount() - amount);
					hasItem = true;
				}
			}
			else{
				continue;
			}
		}
		return hasItem;
	}
	
	/*
	 * Iterates over the inventory to see if there are any free slots
	 * @returns boolean: true if full, false if not full
	 */
	public boolean inventoryIsFull(Item item){
		int emptySlots = 0;
		for (int i = 0; i < itemSlots.size(); i++){
			if(itemSlots.get(i).getItemStack() == null){
				emptySlots++;
			}
			if(itemSlots.get(i).getItemStack() != null && itemSlots.get(i).getItemStack().getItem().getId() == item.getId()) {
				return false;
			}
		}
		if(emptySlots == 0) {
			handler.sendMsg("Your inventory is full.");
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * Checks the equipment slot of an item
	 * @returns int: index of the equipment slot to be filled
	 */
	public int checkEquipmentSlot(Item item){
		if(item.equipSlot == 0){
			return 0;
		}
		if(item.equipSlot == 1){
			return 1;
		}
		if(item.equipSlot == 2){
			return 2;
		}
		if(item.equipSlot == 3){
			return 3;
		}
		if(item.equipSlot == 4){
			return 4;
		}
		if(item.equipSlot == 5){
			return 5;
		}
		if(item.equipSlot == 6){
			return 6;
		}
		if(item.equipSlot == 7){
			return 7;
		}
		if(item.equipSlot == 8){
			return 8;
		}
		if(item.equipSlot == 9){
			return 9;
		}
		if(item.equipSlot == 10){
			return 10;
		}
		if(item.equipSlot == 11){
			return 11;
		}
		return -10;
	}
	
	/*
	 * Returns an item based on id
	 * @param: Item ID
	 */
	public Item getItemByID(int id) {
		return Item.items[id];
	}


	public CopyOnWriteArrayList<ItemSlot> getItemSlots() {
		return itemSlots;
	}


	public void setItemSlots(CopyOnWriteArrayList<ItemSlot> itemSlots) {
		InventoryWindow.itemSlots = itemSlots;
	}


	public Rectangle getWindowBounds() {
		return windowBounds;
	}


	public void setWindowBounds(Rectangle windowBounds) {
		this.windowBounds = windowBounds;
	}


	public int getNumCols() {
		return numCols;
	}


	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}


	public int getNumRows() {
		return numRows;
	}


	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}


	public ItemStack getCurrentSelectedSlot() {
		return currentSelectedSlot;
	}


	public void setCurrentSelectedSlot(ItemStack currentSelectedSlot) {
		this.currentSelectedSlot = currentSelectedSlot;
	}

}
