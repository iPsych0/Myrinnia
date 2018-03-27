package dev.ipsych0.mygame.shop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.input.KeyManager;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.states.GameState;
import dev.ipsych0.mygame.ui.TextBox;
import dev.ipsych0.mygame.utils.DialogueBox;
import dev.ipsych0.mygame.utils.DialogueButton;
import dev.ipsych0.mygame.utils.Text;

public class ShopWindow implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x, y, width, height;
	private int numCols = 5;
	private int numRows = 6;
	public CopyOnWriteArrayList<ItemSlot> itemSlots;
	public CopyOnWriteArrayList<ItemSlot> invSlots;
	private int alpha = 62;
	private Color selectedColor = new Color(0, 255, 255, alpha);
	private ItemStack selectedShopItem;
	private ItemStack selectedInvItem;
	public static boolean isOpen = false;
	private Handler handler;
	private ItemSlot tradeSlot;
	public static boolean inventoryLoaded = false;
	private Rectangle buyAllButton, sellAllButton, buy1Button, sell1Button, buyXButton, sellXButton, exit;
	private Rectangle windowBounds;
	public static boolean makingChoice = false;
	private DialogueBox dBox;
	private String[] answers = {"Yes", "No"};
	public ItemSlot selectedSlot = null;
	private int dialogueWidth = 300;
	private int dialogueHeight = 150;
	private int restockTimer = 0;
	private int seconds = 60;
	private int[] defaultStock;
	private ArrayList<ItemStack> shopItems;
	public static boolean hasBeenPressed = false;
	
	public ShopWindow(Handler handler, ArrayList<ItemStack> shopItems) {
		this.handler = handler;
		this.shopItems = shopItems;
		x = 240;
		y = 150;
		width = 460;
		height = 313;
		
		// Initialize the shop slots and the inventory slots
		itemSlots = new CopyOnWriteArrayList<ItemSlot>();
		invSlots = new CopyOnWriteArrayList<ItemSlot>();
		
		// Add the shop slots
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(j == (numRows)){
					x += 8;
				}
				
				itemSlots.add(new ItemSlot(x + 17 + (i * (ItemSlot.SLOTSIZE)), y + 48 + (j * ItemSlot.SLOTSIZE), null));
				
				if(j == (numRows)){
					x -= 8;
				}
			}
		}

		// Initialize the default stock of the shop
		defaultStock = new int[shopItems.size()];
		
		// Fill the shop slots with the shop items and set the default stock
		for (int i = 0; i < shopItems.size(); i++) {
			itemSlots.get(i).addItem(shopItems.get(i).getItem(), shopItems.get(i).getAmount());
			defaultStock[i] = shopItems.get(i).getAmount();
		}
		
		// Add the inventory slots
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(j == (numRows)){
					x += 8;
				}
				
				invSlots.add(new ItemSlot(x + (width / 2) + 17 + (i * (ItemSlot.SLOTSIZE)), y + 48 + (j * ItemSlot.SLOTSIZE), null));
				
				if(j == (numRows)){
					x -= 8;
				}
			}
		}
		
		// Initialising all the buttons
		tradeSlot = new ItemSlot(x + (width / 2) - 16, y + (height / 2) + 64, null);
		
		buy1Button = new Rectangle(x + 17, y + (height / 2) + 64, 64, 32);
		sell1Button = new Rectangle(x + (width / 2) + 17, y + (height / 2) + 64, 64, 32);
		
		buyAllButton = new Rectangle(x + 81, y + (height / 2) + 64, 64, 32);
		sellAllButton = new Rectangle(x + (width / 2) + 81, y + (height / 2) + 64, 64, 32);
		
		buyXButton = new Rectangle(x + 145, y + (height / 2) + 64, 64, 32);
		sellXButton = new Rectangle(x + (width / 2) + 145, y + (height / 2) + 64, 64, 32);
		
		exit = new Rectangle(x + width - 35, y + 10, 24, 24);
		
		windowBounds = new Rectangle(x, y, width, height);
		
		// Instance of the DialogueBox
		dBox = new DialogueBox(handler, x + (width / 2) - (dialogueWidth / 2), y + (height / 2) - (dialogueHeight / 2), dialogueWidth, dialogueHeight, answers, "Please confirm your trade.", true);
		
	}
	
	public void tick() {
		
		// Keeps a timer before restocking an item or decrementing an item
		restockTimer++;
		if(restockTimer >= (seconds * 60)) {
			for(int i = 0; i < shopItems.size(); i++) {
				if(itemSlots.get(i).getItemStack() != null) {
					if(itemSlots.get(i).getItemStack().getAmount() < defaultStock[i]) {
						itemSlots.get(i).getItemStack().setAmount(itemSlots.get(i).getItemStack().getAmount() + 1);
					}
					if(itemSlots.get(i).getItemStack().getAmount() > defaultStock[i]) {
						itemSlots.get(i).getItemStack().setAmount(itemSlots.get(i).getItemStack().getAmount() - 1);
					}
				}
			}
			restockTimer = 0;
		}
		
		if(isOpen) {
			
			// Close the inventory and equipment window
			InventoryWindow.isOpen = false;
			EquipmentWindow.isOpen = false;
			
			// Checks if the inventory should be refreshed
			if(!inventoryLoaded) {
				loadInventory();
				inventoryLoaded = true;
			}
		
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			/*
			 * Buy 1 Button onClick
			 */
			if(buy1Button.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice && selectedShopItem != null){
				buyItem();
				hasBeenPressed = false;
				return;
			}
			
			/*
			 * Sell 1 Button onClick
			 */
			if(sell1Button.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice && selectedInvItem != null) {
				sellItem();
				hasBeenPressed = false;
				return;
			}
			
			/*
			 * Buy All Button onClick
			 */
			if(buyAllButton.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice && selectedShopItem != null){
				makingChoice = true;
				DialogueBox.isOpen = true;
				TextBox.isOpen = false;
				dBox.setParam("BuyAll");
				hasBeenPressed = false;
				return;
			}
			
			/*
			 * Sell All Button onClick
			 */
			if(sellAllButton.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice && selectedInvItem != null) {
				makingChoice = true;
				DialogueBox.isOpen = true;
				TextBox.isOpen = false;
				dBox.setParam("SellAll");
				hasBeenPressed = false;
				return;
			}
			
			/*
			 * Buy X Button onClick
			 */
			if(buyXButton.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice && selectedShopItem != null){
				makingChoice = true;
				DialogueBox.isOpen = true;
				TextBox.isOpen = true;
				dBox.setParam("BuyX");
				hasBeenPressed = false;
				return;
			}
			
			/*
			 * Sell X Button onClick
			 */
			if(sellXButton.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice && selectedInvItem != null) {
				makingChoice = true;
				DialogueBox.isOpen = true;
				TextBox.isOpen = true;
				dBox.setParam("SellX");
				hasBeenPressed = false;
				return;
			}
			
			/*
			 * Closing the shop by click/escape/walking away
			 */
			if(exit.contains(mouse) && handler.getMouseManager().isLeftPressed() || handler.getKeyManager().escape || Player.isMoving) {
				isOpen = false;
				inventoryLoaded = false;
				DialogueBox.isOpen = false;
				TextBox.isOpen = false;
				handler.getKeyManager().setTextBoxTyping(false);
				hasBeenPressed = false;
				selectedSlot = null;
				selectedInvItem = null;
				selectedShopItem = null;
				makingChoice = false;
				dBox.setPressedButton(null);
				InventoryWindow.isOpen = true;
				EquipmentWindow.isOpen = true;
				return;
			}
			
			// If the dialoguebox is open and player is making a choice
			if(makingChoice && dBox.getPressedButton() != null) {
				if(!dBox.getTextBox().getCharactersTyped().isEmpty()) {
					// If the user has typed in an amount and confirmed the trade per button, buy the item
					if(dBox.getPressedButton().getButtonParam()[0] == "Yes" && dBox.getPressedButton().getButtonParam()[1] == "BuyX") {
						buyXItem(Integer.parseInt(dBox.getTextBox().getCharactersTyped()));
					}
					// If the user has typed in an amount and confirmed the trade per button, sell the item
					else if(dBox.getPressedButton().getButtonParam()[0] == "Yes" && dBox.getPressedButton().getButtonParam()[1] == "SellX") {
						sellXItem(Integer.parseInt(dBox.getTextBox().getCharactersTyped()));
					}
				}
				// If the user has typed in an amount and confirmed the trade per button, buy the item
				if(dBox.getPressedButton().getButtonParam()[0] == "Yes" && dBox.getPressedButton().getButtonParam()[1] == "BuyAll") {
					buyAllItem();
				}
				// If the user has typed in an amount and confirmed the trade per button, sell the item
				else if(dBox.getPressedButton().getButtonParam()[0] == "Yes" && dBox.getPressedButton().getButtonParam()[1] == "SellAll") {
					sellAllItem();
				}
				
				dBox.setPressedButton(null);
				dBox = new DialogueBox(handler, x + (width / 2) - (dialogueWidth / 2), y + (height / 2) - (dialogueHeight / 2), dialogueWidth, dialogueHeight, answers, "Please confirm your trade.", true);
				DialogueBox.isOpen = false;
				TextBox.isOpen = false;
				handler.getKeyManager().setTextBoxTyping(false);
				makingChoice = false;
				hasBeenPressed = false;
			}
			
			if(TextBox.enterPressed && makingChoice) {
				// If enter is pressed while making choice, this means a positive response ("Yes")
				dBox.setPressedButton(dBox.getButtons().get(0));
				dBox.getPressedButton().getButtonParam()[0] = "Yes";
				dBox.getPressedButton().getButtonParam()[1] = dBox.getParam();
				
				// Buy X item
				if(dBox.getPressedButton().getButtonParam()[0] == "Yes" && dBox.getPressedButton().getButtonParam()[1] == "BuyX") {
					if(!dBox.getTextBox().getCharactersTyped().isEmpty()) {
						buyXItem(Integer.parseInt(dBox.getTextBox().getCharactersTyped()));
					}
				}
				//Sell X item
				else if(dBox.getPressedButton().getButtonParam()[0] == "Yes" && dBox.getPressedButton().getButtonParam()[1] == "SellX") {
					if(!dBox.getTextBox().getCharactersTyped().isEmpty()) {
						sellXItem(Integer.parseInt(dBox.getTextBox().getCharactersTyped()));
					}
				}
				
				dBox.setPressedButton(null);
				dBox = new DialogueBox(handler, x + (width / 2) - (dialogueWidth / 2), y + (height / 2) - (dialogueHeight / 2), dialogueWidth, dialogueHeight, answers, "Please confirm your trade.", true);
				DialogueBox.isOpen = false;
				TextBox.enterPressed = false;
				handler.getKeyManager().setTextBoxTyping(false);
				makingChoice = false;
				hasBeenPressed = false;
			}
			
			for(ItemSlot is : itemSlots) {
				is.tick();
				
				
				Rectangle slot = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				
				// If left-clicked, select an item
				if(slot.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice) {
					if(is.getItemStack() != null) {
						if(selectedShopItem == null) {
							selectedInvItem = null;
							selectedSlot = is;
							selectedShopItem = is.getItemStack();
							tradeSlot.setItemStack(selectedShopItem);
							hasBeenPressed = false;
							return;
						}
						// If we already have this item selected, deselect it
						else if(selectedShopItem == is.getItemStack()) {
							selectedSlot = null;
							selectedInvItem = null;
							selectedShopItem = null;
							tradeSlot.setItemStack(null);
							hasBeenPressed = false;
							return;
						}
						// If clicked on a different item than the currently selected one, select new item
						else if(selectedShopItem != is.getItemStack()) {
							selectedSlot = is;
							selectedInvItem = null;
							selectedShopItem = is.getItemStack();
							tradeSlot.setItemStack(selectedShopItem);
							hasBeenPressed = false;
							return;
						}
						// Otherwise, deselect current selected item
					}else {
						selectedSlot = null;
						selectedInvItem = null;
						selectedShopItem = null;
						tradeSlot.setItemStack(null);
						hasBeenPressed = false;
						return;
					}
				}
			}
				
			for(ItemSlot is : invSlots) {
				is.tick();
				
				Rectangle slot = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				
				if(slot.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice) {
					if(is.getItemStack() != null) {
						// If the price = -1, item cannot be sold
						if(is.getItemStack().getItem().getPrice() == -1) {
							handler.sendMsg("You cannot sell this item.");
							hasBeenPressed = false;
							return;
						}
						// Select an item
						if(selectedInvItem == null) {
							selectedSlot = is;
							selectedShopItem = null;
							selectedInvItem = is.getItemStack();
							tradeSlot.setItemStack(selectedInvItem);
							hasBeenPressed = false;
							return;
						}
						// If we already have this item selected, deselect it
						else if(selectedInvItem == is.getItemStack()) {
							selectedSlot = null;
							selectedShopItem = null;
							selectedInvItem = null;
							tradeSlot.setItemStack(null);
							hasBeenPressed = false;
							return;
						}
						// If clicked on a different item than the currently selected one, select new item
						else if(selectedInvItem != is.getItemStack()) {
							selectedSlot = is;
							selectedShopItem = null;
							selectedInvItem = is.getItemStack();
							tradeSlot.setItemStack(selectedInvItem);
							hasBeenPressed = false;
							return;
						}
						// Otherwise, deselect current selected item
					}else {
						selectedSlot = null;
						selectedShopItem = null;
						selectedInvItem = null;
						tradeSlot.setItemStack(null);
						hasBeenPressed = false;
						return;
					}
				}
				
			}
			
			tradeSlot.tick();
			
			// If player is making a choice, show the dialoguebox
			if(makingChoice)
				dBox.tick();
		
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			g.drawImage(Assets.shopWindow, x, y, width, height, null);
			
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);

			// Buy/sell 1
			if(buy1Button.contains(mouse))
				g.drawImage(Assets.mainMenuButton[0], buy1Button.x, buy1Button.y, buy1Button.width, buy1Button.height, null);
			else
				g.drawImage(Assets.mainMenuButton[1], buy1Button.x, buy1Button.y, buy1Button.width, buy1Button.height, null);
			if(sell1Button.contains(mouse))
				g.drawImage(Assets.mainMenuButton[0], sell1Button.x, sell1Button.y, sell1Button.width, sell1Button.height, null);
			else
				g.drawImage(Assets.mainMenuButton[1], sell1Button.x, sell1Button.y, sell1Button.width, sell1Button.height, null);
			Text.drawString(g, "Buy 1", x + 17 + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Sell 1", x + 17 + (width / 2) + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			
			// Buy/sell ALL
			if(buyAllButton.contains(mouse))
				g.drawImage(Assets.mainMenuButton[0], buyAllButton.x, buyAllButton.y, buyAllButton.width, buyAllButton.height, null);
			else
				g.drawImage(Assets.mainMenuButton[1], buyAllButton.x, buyAllButton.y, buyAllButton.width, buyAllButton.height, null);
			if(sellAllButton.contains(mouse))
				g.drawImage(Assets.mainMenuButton[0], sellAllButton.x, sellAllButton.y, sellAllButton.width, sellAllButton.height, null);
			else
				g.drawImage(Assets.mainMenuButton[1], sellAllButton.x, sellAllButton.y, sellAllButton.width, sellAllButton.height, null);
			Text.drawString(g, "Buy all", x + 81 + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Sell all", x + 81 + (width / 2) + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			
			// Buy/sell X
			if(buyXButton.contains(mouse))
				g.drawImage(Assets.mainMenuButton[0], buyXButton.x, buyXButton.y, buyXButton.width, buyXButton.height, null);
			else
				g.drawImage(Assets.mainMenuButton[1], buyXButton.x, buyXButton.y, buyXButton.width, buyXButton.height, null);
			if(sellXButton.contains(mouse))
				g.drawImage(Assets.mainMenuButton[0], sellXButton.x, sellXButton.y, sellXButton.width, sellXButton.height, null);
			else
				g.drawImage(Assets.mainMenuButton[1], sellXButton.x, sellXButton.y, sellXButton.width, sellXButton.height, null);
			Text.drawString(g, "Buy X", x + 145 + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Sell X", x + 145 + (width / 2) + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			
			// test stuff close button
			if(exit.contains(mouse))
				g.drawImage(Assets.mainMenuButton[0], exit.x, exit.y, exit.width, exit.height, null);
			else
				g.drawImage(Assets.mainMenuButton[1], exit.x, exit.y, exit.width, exit.height, null);
			Text.drawString(g, "X", exit.x + 12, y + 10 + 12, true, Color.YELLOW, GameState.chatFont);
			for(ItemSlot is : itemSlots) {
				
				is.render(g);
				
			}
			
			for(ItemSlot is : invSlots) {
				is.render(g);
				
			}
			
			Text.drawString(g, "Shop stock", x + 81 + 32, y + 36, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Inventory", x + 81 + (width / 2) + 32, y + 36, true, Color.YELLOW, Assets.font14);
			
			if(selectedInvItem != null)
				Text.drawString(g, selectedInvItem.getAmount() + " " + selectedInvItem.getItem().getName() + " will get you: " + selectedInvItem.getItem().getPrice() * selectedInvItem.getAmount() + " coins. (" + selectedInvItem.getItem().getPrice() + " each)", x + (width / 2), y + (height / 2) + 112, true, Color.YELLOW, Assets.font14);
			if(selectedShopItem != null)
				Text.drawString(g, selectedShopItem.getAmount() + " " + selectedShopItem.getItem().getName() + " will cost you: " + selectedShopItem.getItem().getPrice() * selectedShopItem.getAmount() + " coins. (" + selectedShopItem.getItem().getPrice() + " each)", x + (width / 2), y + (height / 2) + 112, true, Color.YELLOW, Assets.font14);
			
			if(selectedSlot != null) {
				g.setColor(selectedColor);
				g.fillRoundRect(selectedSlot.getX(), selectedSlot.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, 16, 16);
			}
			
			if(makingChoice)
				dBox.render(g);
		}
	}
	
	/*
	 * If item reaches 0 quantity that is not in default stock, remove it from the store
	 */
	private void clearNonStockItems() {
		for(int i = shopItems.size(); i < itemSlots.size(); i++) {
			if(itemSlots.get(i).getItemStack() != null && itemSlots.get(i).getItemStack().getAmount() <= 0) {
				itemSlots.get(i).setItemStack(null);
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
	
	/*
	 * Buys 1 item
	 */
	private void buyItem() {
		if(tradeSlot.getItemStack() != null && selectedInvItem == null) {
			if(handler.playerHasItem(Item.coinsItem, (1 * tradeSlot.getItemStack().getItem().getPrice()))) {
				if(!handler.invIsFull(tradeSlot.getItemStack().getItem()) && selectedSlot.getItemStack().getAmount() > 0) {
					handler.removeItem(Item.coinsItem, (1 * tradeSlot.getItemStack().getItem().getPrice()));
					handler.giveItem(tradeSlot.getItemStack().getItem(), 1);
					inventoryLoaded = false;
					
					selectedShopItem.setAmount(selectedShopItem.getAmount() - 1);
					
					if(selectedShopItem.getAmount() <= 0) {
						selectedShopItem = null;
						selectedSlot = null;
						clearNonStockItems();
					}
				}else {
					selectedSlot = null;
					selectedShopItem = null;
				}
				hasBeenPressed = false;
			}else {
				handler.sendMsg("You don't have enough gold to buy " + 1 + " " + tradeSlot.getItemStack().getItem().getName() + "s.");
				hasBeenPressed = false;
			}
		}else {
			hasBeenPressed = false;
		}
	}
	
	/*
	 * Sells 1 item
	 */
	private void sellItem() {
		if(tradeSlot.getItemStack() != null && selectedShopItem == null) {
			if(tradeSlot.getItemStack().getItem().getPrice() == -1) {
				handler.sendMsg("You cannot sell this item.");
				hasBeenPressed = false;
				return;
			}
			if(handler.playerHasItem(tradeSlot.getItemStack().getItem(), 1)) {
				if(findFreeSlot(tradeSlot.getItemStack().getItem()) != -1) {
					handler.removeItem(tradeSlot.getItemStack().getItem(), 1);
					handler.giveItem(Item.coinsItem, (tradeSlot.getItemStack().getItem().getPrice() * 1));
					itemSlots.get(findFreeSlot(tradeSlot.getItemStack().getItem())).addItem(tradeSlot.getItemStack().getItem(), 1);
					inventoryLoaded = false;
					
					if(tradeSlot.getItemStack().getAmount() == 1) {
						selectedInvItem = null;
						selectedSlot = null;
						tradeSlot.setItemStack(null);
					}
				}else {
					handler.sendMsg("You cannot sell any more items to the shop.");
				}
			}
			hasBeenPressed = false;
		}else {
			hasBeenPressed = false;
		}
	}
	
	/*
	 * Buys all items in stock
	 * TODO: Add money check to buy maximum quantity
	 */
	private void buyAllItem() {
		if(tradeSlot.getItemStack() != null && selectedInvItem == null) {
			ArrayList<Integer> slots = getMatchSlots(tradeSlot.getItemStack().getItem());
			int i = 0;
			while(i  < slots.size()) {
				if(handler.playerHasItem(Item.coinsItem, (tradeSlot.getItemStack().getAmount() * tradeSlot.getItemStack().getItem().getPrice()))) {
					if(!handler.invIsFull(tradeSlot.getItemStack().getItem())) {
						handler.removeItem(Item.coinsItem, (tradeSlot.getItemStack().getAmount() * tradeSlot.getItemStack().getItem().getPrice()));
						handler.giveItem(tradeSlot.getItemStack().getItem(), tradeSlot.getItemStack().getAmount());
					}else {
						hasBeenPressed = false;
						break;
					}
				}else {
					handler.sendMsg("You don't have enough gold to buy " + slots.size() + " " + tradeSlot.getItemStack().getItem().getName() + "s.");
					hasBeenPressed = false;
					break;
				}
				i++;
			}
			int matches = 0;
			for(int j = 0; j < itemSlots.size(); j++) {
				if(matches == i)
					break;
				if(itemSlots.get(j).getItemStack() == null)
					continue;
				if(itemSlots.get(j).getItemStack().getItem().getId() == tradeSlot.getItemStack().getItem().getId()) {
					itemSlots.get(j).getItemStack().setAmount(0);
					matches++;
				}
			}
			inventoryLoaded = false;
			tradeSlot.setItemStack(null);
			selectedSlot = null;
			clearNonStockItems();
			if(selectedShopItem != null)
				selectedShopItem = null;
		}else {
			hasBeenPressed = false;
		}
	}
	
	/*
	 * Sells all items from the currently selected slot
	 */
	private void sellAllItem() {
		if(tradeSlot.getItemStack() != null && selectedShopItem == null) {
			if(tradeSlot.getItemStack().getItem().getPrice() == -1) {
				handler.sendMsg("You cannot sell this item.");
				hasBeenPressed = false;
				return;
			}
			while(handler.playerHasItem(tradeSlot.getItemStack().getItem(), tradeSlot.getItemStack().getAmount())) {
				if(findFreeSlot(tradeSlot.getItemStack().getItem()) != -1) {
					handler.removeItem(tradeSlot.getItemStack().getItem(), tradeSlot.getItemStack().getAmount());
					handler.giveItem(Item.coinsItem, (tradeSlot.getItemStack().getItem().getPrice() * tradeSlot.getItemStack().getAmount()));
					itemSlots.get(findFreeSlot(tradeSlot.getItemStack().getItem())).addItem(tradeSlot.getItemStack().getItem(), tradeSlot.getItemStack().getAmount());
					inventoryLoaded = false;
				}else {
					handler.sendMsg("You cannot sell any more items to the shop.");
					break;
				}
			}
			tradeSlot.setItemStack(null);
			selectedSlot = null;
			if(selectedInvItem != null)
				selectedInvItem = null;
			hasBeenPressed = false;
		}else {
			hasBeenPressed = false;
		}
	}
	
	/**
	 * Buys X amount of items from the shop
	 * @param amount - quantity to buy
	 */
	private void buyXItem(int amount) {
		if(tradeSlot.getItemStack() != null && selectedInvItem == null) {
			ArrayList<Integer> slots = getMatchSlots(tradeSlot.getItemStack().getItem());
			int i = slots.size();
			int index = 0;
			
			if(amount > i && !tradeSlot.getItemStack().getItem().isStackable()) {
				amount = i;
			}
			else if(amount > tradeSlot.getItemStack().getAmount() && tradeSlot.getItemStack().getItem().isStackable()) {
				amount = tradeSlot.getItemStack().getAmount();
			}
			while (index < amount) {
				if(handler.playerHasItem(Item.coinsItem, (1 * tradeSlot.getItemStack().getItem().getPrice()))) {
					if(!handler.invIsFull(tradeSlot.getItemStack().getItem())) {
						handler.removeItem(Item.coinsItem, 1 * tradeSlot.getItemStack().getItem().getPrice());
						handler.giveItem(tradeSlot.getItemStack().getItem(), 1);
						
					}else {
						hasBeenPressed = false;
						amount = index;
						break;
					}
				}else {
					handler.sendMsg("You don't have enough gold to buy " + amount + " " + tradeSlot.getItemStack().getItem().getName() + "s.");
					hasBeenPressed = false;
					amount = index;
					break;
				}
				index++;
			}
			
			int matches = 0;
			for(int j = 0; j < itemSlots.size(); j++) {
				if(matches == amount)
					break;
				if(itemSlots.get(j).getItemStack() == null)
					continue;
				if(itemSlots.get(j).getItemStack().getItem().getId() == tradeSlot.getItemStack().getItem().getId()) {
					if(itemSlots.get(j).getItemStack().getItem().isStackable() && itemSlots.get(j).getItemStack().getAmount() - amount > 0) {
						itemSlots.get(j).getItemStack().setAmount(itemSlots.get(j).getItemStack().getAmount() - amount);
						matches++;
					}
					else if(!itemSlots.get(j).getItemStack().getItem().isStackable() && itemSlots.get(j).getItemStack().getAmount() > 0) {
						itemSlots.get(j).getItemStack().setAmount(0);
						matches++;
					}
				}
			}
			
			tradeSlot.setItemStack(null);
			clearNonStockItems();
			inventoryLoaded = false;
			selectedSlot = null;
			if(selectedShopItem != null)
				selectedShopItem = null;
		}else {
			hasBeenPressed = false;
		}
	}
	
	/**
	 * Sells X items
	 * @param amount - quantity to sell
	 */
	public void sellXItem(int amount) {
		if(tradeSlot.getItemStack() != null && selectedShopItem == null) {
			if(tradeSlot.getItemStack().getItem().getPrice() == -1) {
				handler.sendMsg("You cannot sell this item.");
				hasBeenPressed = false;
				return;
			}
			
			if(amount > tradeSlot.getItemStack().getAmount() && tradeSlot.getItemStack().getItem().isStackable()) {
				amount = tradeSlot.getItemStack().getAmount();
			}
			
			int inputAmount = amount;
			
			while(inputAmount != 0) {
				if(handler.playerHasItem(tradeSlot.getItemStack().getItem(), 1)) {
					if(findFreeSlot(tradeSlot.getItemStack().getItem()) != -1) {
						handler.removeItem(tradeSlot.getItemStack().getItem(), 1);
						handler.giveItem(Item.coinsItem, (tradeSlot.getItemStack().getItem().getPrice() * 1));
						itemSlots.get(findFreeSlot(tradeSlot.getItemStack().getItem())).addItem(tradeSlot.getItemStack().getItem(), 1);
					}else {
						handler.sendMsg("You cannot sell any more items to the shop.");
						break;
					}
					
				}else {
					break;
				}
				inputAmount--;
			}
			inventoryLoaded = false;
			tradeSlot.setItemStack(null);
			selectedSlot = null;
			if(selectedInvItem != null)
				selectedInvItem = null;
			hasBeenPressed = false;
		}else {
			hasBeenPressed = false;
		}
	}
	
	/*
	 * Finds a free slot in the shop when selling an item
	 * @returns -1 if no free slot found
	 */
	public int findFreeSlot(Item item) {
		boolean firstFreeSlotFound = false;
		int index = -1;
        for (int i = 0; i < itemSlots.size(); i++) {
        	if(itemSlots.get(i).getItemStack() == null) {
            	if(!firstFreeSlotFound) {
	            	firstFreeSlotFound = true;
	            	index = i;
            	}
            }
        	else if(itemSlots.get(i).getItemStack() != null && !item.isStackable() && itemSlots.get(i).getItemStack().getAmount() == 0 && itemSlots.get(i).getItemStack().getItem().getId() == item.getId()){
        		return i;
        	}
        	else if(itemSlots.get(i).getItemStack() != null && !item.isStackable()) {
        		continue;
        	}
        	else if(itemSlots.get(i).getItemStack() != null && item.isStackable()){
        		if(itemSlots.get(i).getItemStack().getItem().getId() == item.getId()){
            		return i;
        		}
        	}
       }
        if(index != -1)
        	return index;
       System.out.println("No free inventory slot available.");
       return -1;
	}
	
	private ArrayList<Integer> getMatchSlots(Item item) {
		ArrayList<Integer> slots = new ArrayList<Integer>();
		for(int i = 0; i < itemSlots.size(); i++) {
			if(itemSlots.get(i).getItemStack() == null) {
				continue;
			}
			if(itemSlots.get(i).getItemStack().getItem() == item)
				slots.add(i);
		}
		return slots;
	}

	public CopyOnWriteArrayList<ItemSlot> getItemSlots() {
		return itemSlots;
	}

	public void setItemSlots(CopyOnWriteArrayList<ItemSlot> itemSlots) {
		this.itemSlots = itemSlots;
	}

	public CopyOnWriteArrayList<ItemSlot> getInvSlots() {
		return invSlots;
	}

	public void setInvSlots(CopyOnWriteArrayList<ItemSlot> invSlots) {
		this.invSlots = invSlots;
	}

	public Rectangle getWindowBounds() {
		return windowBounds;
	}

	public void setWindowBounds(Rectangle windowBounds) {
		this.windowBounds = windowBounds;
	}

}
