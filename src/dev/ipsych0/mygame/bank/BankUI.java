package dev.ipsych0.mygame.bank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.utils.Text;

public class BankUI implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int x, y, width, height;
	public static boolean isOpen = false;
	private Handler handler;
	private CopyOnWriteArrayList<ItemSlot> invSlots = new CopyOnWriteArrayList<>();
	private ArrayList<BankTab> tabs = new ArrayList<>();
	private BankTab openedTab;
	private int maxTabs = 10;
	private boolean inventoryLoaded;
	public static boolean hasBeenPressed = false;
	private Rectangle bounds;
	private boolean itemSelected;
	private ItemStack currentSelectedSlot;
	private Color selectedColor = new Color(0, 255, 255, 62);
	private Rectangle exit;
	
	public BankUI(Handler handler) {
		this.handler = handler;
		
		x = 240;
		y = 150;
		width = 460;
		height = 313;
		
		// Add all the tabs
		for(int i = 0; i < maxTabs; i++) {
			tabs.add(new BankTab(handler, x + (width / 2) - ((maxTabs * 32 / 2)) + (i * 32), y + 32, i));
		}
		
		// Add the inventory slots
		for(int i = 0; i < BankTab.ROWS; i++){
			for(int j = 0; j < BankTab.COLS; j++){
				if(j == (BankTab.ROWS)){
					x += 8;
				}
				
				invSlots.add(new ItemSlot(x + (width / 2) + 17 + (i * (ItemSlot.SLOTSIZE)), y + 80 + (j * ItemSlot.SLOTSIZE), null));
				
				if(j == (BankTab.ROWS)){
					x -= 8;
				}
			}
		}
		
		bounds = new Rectangle(x, y, width, height);
		exit = new Rectangle(x + width - 36, y + 12, 24, 24);
		
		// Initially always open the first tab
		openedTab = tabs.get(0);
		
		// Hardcoded items in bank tabs
		tabs.get(0).getBankSlots().get(0).addItem(Item.purpleSword, 1);
		tabs.get(1).getBankSlots().get(0).addItem(Item.testSword, 1);
		
		for(int i = 0; i < tabs.get(3).getBankSlots().size(); i++) {
			tabs.get(3).getBankSlots().get(i).addItem(Item.testAxe, 1);
		}
		
	}
	
	public void tick() {
		if(isOpen) {
			// Checks if the inventory should be refreshed
			if(!inventoryLoaded) {
				loadInventory();
				inventoryLoaded = true;
			}
			
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1 ,1);
			
			if(Player.isMoving || exit.contains(mouse) && handler.getMouseManager().isLeftPressed()) {
				handler.getPlayer().setBankEntity(null);
				isOpen = false;
				hasBeenPressed = false;
				inventoryLoaded = false;
				return;
			}
			
			/*
			 * BankTab mouse interaction
			 */
			for(BankTab tab : tabs) {
				if(tab.getBounds().contains(mouse)){
					tab.setHovering(true);
					if(handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
						tab.setOpen(true);
						openedTab = tab;
						for(BankTab tempTab : tabs) {
							if(!tempTab.equals(tab))
								tempTab.setOpen(false);
						}
						hasBeenPressed = false;
					}
					else if(itemSelected && !handler.getMouseManager().isDragged()) {
						if(tab.getBounds().contains(mouse)){
							// If the itemstack already holds an item
							if(tab.findFreeSlot(currentSelectedSlot.getItem()) != -1) {
								if(currentSelectedSlot.getItem().isStackable()) {
									// And if the item in the slot is stackable
									if(tab.getBankSlots().get(tab.findFreeSlot(currentSelectedSlot.getItem())).addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
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
									// If the item is not stackable, we can add the item
									tab.getBankSlots().get(tab.findFreeSlot(currentSelectedSlot.getItem()))
									   .addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount());
									currentSelectedSlot = null;
									itemSelected = false;
									hasBeenPressed = false;
								}
							}else {
								// 
								hasBeenPressed = false;
								return;
							}
						}
					}
				}else {
					tab.setHovering(false);
				}
			}
			
			if(openedTab != null) {
				/*
				 * Bankslot mouse interaction
				 */
				for(ItemSlot is : openedTab.getBankSlots()) {
					Rectangle slot = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
					if(slot.contains(mouse) && handler.getMouseManager().isRightPressed() && hasBeenPressed) {
						if(is.getItemStack() != null && !handler.invIsFull(is.getItemStack().getItem())) {
							handler.giveItem(is.getItemStack().getItem(), is.getItemStack().getAmount());
							is.setItemStack(null);
							inventoryLoaded = false;
							hasBeenPressed = false;
							return;
						}else {
							hasBeenPressed = false;
							return;
						}
					}
					
					// If an item is dragged
					if(handler.getMouseManager().isDragged()){
						if(slot.contains(mouse) && hasBeenPressed && !itemSelected) {
							hasBeenPressed = false;
							
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
					else if(itemSelected && !handler.getMouseManager().isDragged()) {
						if(slot.contains(mouse)){
							// If the itemstack already holds an item
							if(is.getItemStack() != null) {
								if(currentSelectedSlot.getItem().isStackable()) {
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
				}
				
				/*
				 * Inventory mouse interaction
				 */
				for(ItemSlot is : invSlots) {
					Rectangle slot = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
					if(slot.contains(mouse) && handler.getMouseManager().isRightPressed() && hasBeenPressed) {
						if(is.getItemStack() != null) {
							if(openedTab.findFreeSlot(is.getItemStack().getItem()) != -1) {
								openedTab.getBankSlots().get(openedTab.findFreeSlot(is.getItemStack().getItem()))
										 .addItem(is.getItemStack().getItem(), is.getItemStack().getAmount());
								handler.removeItem(is.getItemStack().getItem(), is.getItemStack().getAmount());
								inventoryLoaded = false;
								hasBeenPressed = false;
								return;
							}else {
								hasBeenPressed = false;
								return;
							}
						}
					}
					
					// If an item is dragged
					if(handler.getMouseManager().isDragged()){
						if(slot.contains(mouse) && hasBeenPressed && !itemSelected) {
							hasBeenPressed = false;
							
							// Stick the item to the mouse
							if(currentSelectedSlot == null) {
								if(is.getItemStack() != null) {
									currentSelectedSlot = is.getItemStack();
									handler.removeItem(is.getItemStack().getItem(), is.getItemStack().getAmount());
									itemSelected = true;
									inventoryLoaded = false;
								}
								else{
									hasBeenPressed = false;
									return;
								}
							}
						}
					}
					
					// If the item is released
					else if(itemSelected && !handler.getMouseManager().isDragged()) {
						if(slot.contains(mouse)){
							// If the itemstack already holds an item
							if(is.getItemStack() != null) {
								if(currentSelectedSlot.getItem().isStackable()) {
									// And if the item in the slot is stackable
									if(is.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
										// Add the item back to the inventory
										currentSelectedSlot = null;
										itemSelected = false;
										hasBeenPressed = false;
										inventoryLoaded = false;
										return;
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
								handler.giveItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount());
								currentSelectedSlot = null;
								itemSelected = false;
								hasBeenPressed = false;
								inventoryLoaded = false;
								return;
							}
						}
					}
				}
			}
		}
	}
	
	/*
	 * Refreshes the inventory in the shopwindow
	 */
	private void loadInventory() {
		for(int i = 0; i < handler.getInventory().getItemSlots().size(); i++) {
			invSlots.get(i).setItemStack(handler.getInventory().getItemSlots().get(i).getItemStack());
		}
	}

	public void render(Graphics g) {
		if(isOpen) {
			g.drawImage(Assets.shopWindow, x, y, width, height, null);
			
			for(ItemSlot is : invSlots) {
				is.render(g);
			}
			
			for(BankTab tab : tabs) {
				tab.render(g);
				if(tab.isOpen()) {
					g.setColor(selectedColor);
					g.fillRoundRect(tab.x, tab.y, tab.width, tab.height, 4, 4);
				}
			}
			
			if(currentSelectedSlot != null) {
				g.drawImage(currentSelectedSlot.getItem().getTexture(), handler.getMouseManager().getMouseX() - 14,
						handler.getMouseManager().getMouseY() - 14, ItemSlot.SLOTSIZE - 4, ItemSlot.SLOTSIZE - 4, null);
				if(currentSelectedSlot.getItem().isStackable())
					Text.drawString(g, Integer.toString(currentSelectedSlot.getAmount()), handler.getMouseManager().getMouseX() - 14, handler.getMouseManager().getMouseY() - 4, false, Color.YELLOW, Assets.font14);
			}
			
			if(exit.contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY()))
				g.drawImage(Assets.genericButton[0], exit.x, exit.y, exit.width, exit.height, null);
			else
				g.drawImage(Assets.genericButton[1], exit.x, exit.y, exit.width, exit.height, null);
			
			Text.drawString(g, "X", exit.x + 12, exit.y + 12, true, Color.YELLOW, Assets.font14);
		}
	}
	
	/*
	 * Finds a free slot in the inventory
	 * @returns: index if found, -1 if inventory is full
	 */
	public int findFreeSlot(Item item) {
		boolean firstFreeSlotFound = false;
		int index = -1;
        for (int i = 0; i < invSlots.size(); i++) {
        	if(invSlots.get(i).getItemStack() == null) {
            	if(!firstFreeSlotFound) {
	            	firstFreeSlotFound = true;
	            	index = i;
            	}
            }
        	else if(invSlots.get(i).getItemStack() != null && !item.isStackable()) {
        		continue;
        	}
        	else if(invSlots.get(i).getItemStack() != null && item.isStackable()){
        		if(invSlots.get(i).getItemStack().getItem().getId() == item.getId()){
            		return i;
        		}
        	}
       }
       if(index != -1)
    	   return index;
       
       handler.sendMsg("Your inventory is full.");
       return -1;
	}

	public BankTab getOpenedTab() {
		return openedTab;
	}

	public void setOpenedTab(BankTab openedTab) {
		this.openedTab = openedTab;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

}
