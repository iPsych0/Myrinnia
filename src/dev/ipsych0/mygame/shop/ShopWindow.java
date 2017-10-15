package dev.ipsych0.mygame.shop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;
import dev.ipsych0.mygame.input.KeyManager;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.ui.TextBox;
import dev.ipsych0.mygame.utils.DialogueBox;
import dev.ipsych0.mygame.utils.DialogueButton;

public class ShopWindow {
	
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
		
		itemSlots = new CopyOnWriteArrayList<ItemSlot>();
		invSlots = new CopyOnWriteArrayList<ItemSlot>();
		
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

		
		defaultStock = new int[shopItems.size()];
		
		for (int i = 0; i < shopItems.size(); i++) {
			itemSlots.get(i).addItem(shopItems.get(i).getItem(), shopItems.get(i).getAmount());
			defaultStock[i] = shopItems.get(i).getAmount();
		}
		
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
		
		tradeSlot = new ItemSlot(x + (width / 2) - 16, y + (height / 2) + 64, null);
		
		buy1Button = new Rectangle(x + 17, y + (height / 2) + 64, 64, 32);
		sell1Button = new Rectangle(x + (width / 2) + 17, y + (height / 2) + 64, 64, 32);
		
		buyAllButton = new Rectangle(x + 81, y + (height / 2) + 64, 64, 32);
		sellAllButton = new Rectangle(x + (width / 2) + 81, y + (height / 2) + 64, 64, 32);
		
		buyXButton = new Rectangle(x + 145, y + (height / 2) + 64, 64, 32);
		sellXButton = new Rectangle(x + (width / 2) + 145, y + (height / 2) + 64, 64, 32);
		
		exit = new Rectangle(x + width - 26, y + 10, 16, 16);
		
		windowBounds = new Rectangle(x, y, width, height);
		
		dBox = new DialogueBox(handler, x + (width / 2) - (dialogueWidth / 2), y + (height / 2) - (dialogueHeight / 2), dialogueWidth, dialogueHeight, answers, "Please confirm your trade.", true);
		
	}
	
	public void tick() {
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
			
			
			InventoryWindow.isOpen = false;
			EquipmentWindow.isOpen = false;
			
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
			if(exit.contains(mouse) && handler.getMouseManager().isLeftPressed() || handler.getKeyManager().escape || !handler.getPlayer().playerIsNearNpc()) {
				isOpen = false;
				inventoryLoaded = false;
				DialogueBox.isOpen = false;
				TextBox.isOpen = false;
				KeyManager.typingFocus = false;
				hasBeenPressed = false;
				selectedSlot = null;
				selectedInvItem = null;
				selectedShopItem = null;
				makingChoice = false;
				dBox.setPressedButton(null);
				return;
			}
			
			if(makingChoice && dBox.getPressedButton() != null) {
				if(!dBox.getTextBox().getCharactersTyped().isEmpty()) {
					if(dBox.getPressedButton().getButtonParam()[0] == "Yes" && dBox.getPressedButton().getButtonParam()[1] == "BuyX") {
						buyXItem(Integer.parseInt(dBox.getTextBox().getCharactersTyped()));
					}
					else if(dBox.getPressedButton().getButtonParam()[0] == "Yes" && dBox.getPressedButton().getButtonParam()[1] == "SellX") {
						sellXItem(Integer.parseInt(dBox.getTextBox().getCharactersTyped()));
					}
				}
				
				if(dBox.getPressedButton().getButtonParam()[0] == "Yes" && dBox.getPressedButton().getButtonParam()[1] == "BuyAll") {
					buyAllItem();
				}
				else if(dBox.getPressedButton().getButtonParam()[0] == "Yes" && dBox.getPressedButton().getButtonParam()[1] == "SellAll") {
					sellAllItem();
				}
				
				dBox.setPressedButton(null);
				dBox = new DialogueBox(handler, x + (width / 2) - (dialogueWidth / 2), y + (height / 2) - (dialogueHeight / 2), dialogueWidth, dialogueHeight, answers, "Please confirm your trade.", true);
				DialogueBox.isOpen = false;
				TextBox.isOpen = false;
				KeyManager.typingFocus = false;
				makingChoice = false;
				hasBeenPressed = false;
			}
			
			if(TextBox.enterPressed && makingChoice) {
				
				dBox.setPressedButton(dBox.getButtons().get(0));
				dBox.getPressedButton().getButtonParam()[0] = "Yes";
				dBox.getPressedButton().getButtonParam()[1] = dBox.getParam();
				
				if(dBox.getPressedButton().getButtonParam()[0] == "Yes" && dBox.getPressedButton().getButtonParam()[1] == "BuyX") {
					if(!dBox.getTextBox().getCharactersTyped().isEmpty()) {
						buyXItem(Integer.parseInt(dBox.getTextBox().getCharactersTyped()));
					}
					}
				else if(dBox.getPressedButton().getButtonParam()[0] == "Yes" && dBox.getPressedButton().getButtonParam()[1] == "SellX") {
					if(!dBox.getTextBox().getCharactersTyped().isEmpty()) {
						sellXItem(Integer.parseInt(dBox.getTextBox().getCharactersTyped()));
					}
				}
				
				dBox.setPressedButton(null);
				dBox = new DialogueBox(handler, x + (width / 2) - (dialogueWidth / 2), y + (height / 2) - (dialogueHeight / 2), dialogueWidth, dialogueHeight, answers, "Please confirm your trade.", true);
				DialogueBox.isOpen = false;
				TextBox.enterPressed = false;
				KeyManager.typingFocus = false;
				makingChoice = false;
				hasBeenPressed = false;
			}
			
			for(ItemSlot is : itemSlots) {
				is.tick();
				
				
				Rectangle slot = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				
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
						else if(selectedShopItem == is.getItemStack()) {
							selectedSlot = null;
							selectedInvItem = null;
							selectedShopItem = null;
							tradeSlot.setItemStack(null);
							hasBeenPressed = false;
							return;
						}
						else if(selectedShopItem != is.getItemStack()) {
							selectedSlot = is;
							selectedInvItem = null;
							selectedShopItem = is.getItemStack();
							tradeSlot.setItemStack(selectedShopItem);
							hasBeenPressed = false;
							return;
						}
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
						if(is.getItemStack().getItem().getPrice() == -1) {
							handler.sendMsg("You cannot sell this item.");
							hasBeenPressed = false;
							return;
						}
						if(selectedInvItem == null) {
							selectedSlot = is;
							selectedShopItem = null;
							selectedInvItem = is.getItemStack();
							tradeSlot.setItemStack(selectedInvItem);
							hasBeenPressed = false;
							return;
						}
						else if(selectedInvItem == is.getItemStack()) {
							selectedSlot = null;
							selectedShopItem = null;
							selectedInvItem = null;
							tradeSlot.setItemStack(null);
							hasBeenPressed = false;
							return;
						}
						else if(selectedInvItem != is.getItemStack()) {
							selectedSlot = is;
							selectedShopItem = null;
							selectedInvItem = is.getItemStack();
							tradeSlot.setItemStack(selectedInvItem);
							hasBeenPressed = false;
							return;
						}
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
			
			if(makingChoice)
				dBox.tick();
		
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			g.drawImage(Assets.shopWindow, x, y, width, height, null);
			
			g.setColor(Color.BLACK);
			// Buy/sell 1
			g.drawRect(buy1Button.x, buy1Button.y, buy1Button.width, buy1Button.height);
			g.drawRect(sell1Button.x, sell1Button.y, sell1Button.width, sell1Button.height);
			Text.drawString(g, "Buy 1", x + 17 + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Sell 1", x + 17 + (width / 2) + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			
			// Buy/sell ALL
			g.setColor(Color.BLACK);
			g.drawRect(buyAllButton.x, buyAllButton.y, buyAllButton.width, buyAllButton.height);
			g.drawRect(sellAllButton.x, sellAllButton.y, sellAllButton.width, sellAllButton.height);
			Text.drawString(g, "Buy all", x + 81 + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Sell all", x + 81 + (width / 2) + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			
			// Buy/sell X
			g.setColor(Color.BLACK);
			g.drawRect(buyXButton.x, buyXButton.y, buyXButton.width, buyXButton.height);
			g.drawRect(sellXButton.x, sellXButton.y, sellXButton.width, sellXButton.height);
			Text.drawString(g, "Buy X", x + 145 + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Sell X", x + 145 + (width / 2) + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			
			// test stuff close button
			g.setColor(Color.YELLOW);
			g.fillRect(x + width - 26, y + 10, 16, 16);
			g.setColor(Color.BLACK);
			g.drawRect(x + width - 26, y + 10, 16, 16);
			Text.drawString(g, "X", x + width - 26 + 8, y + 10 + 8, true, Color.BLACK, Assets.font14);
			
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
	
	private void clearNonStockItems() {
		for(int i = shopItems.size(); i < itemSlots.size(); i++) {
			if(itemSlots.get(i).getItemStack() != null && itemSlots.get(i).getItemStack().getAmount() <= 0) {
				itemSlots.get(i).setItemStack(null);
			}
		}
	}
	
	private void loadInventory() {
		for(int i = 0; i < handler.getWorld().getInventory().getItemSlots().size(); i++) {
			invSlots.get(i).setItemStack(handler.getWorld().getInventory().getItemSlots().get(i).getItemStack());
		}
	}
	
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
	
	public void sellItem() {
		if(tradeSlot.getItemStack() != null && selectedShopItem == null) {
			if(tradeSlot.getItemStack().getItem().getPrice() == -1) {
				handler.sendMsg("You cannot sell this item.");
				hasBeenPressed = false;
				return;
			}
			if(!handler.invIsFull(tradeSlot.getItemStack().getItem())) {
				if(handler.playerHasItem(tradeSlot.getItemStack().getItem(), 1)) {
					handler.removeItem(tradeSlot.getItemStack().getItem(), 1);
					handler.giveItem(Item.coinsItem, (tradeSlot.getItemStack().getItem().getPrice() * 1));
					itemSlots.get(findFreeSlot(tradeSlot.getItemStack().getItem())).addItem(tradeSlot.getItemStack().getItem(), 1);
					inventoryLoaded = false;
					
					if(tradeSlot.getItemStack().getAmount() == 1) {
						selectedInvItem = null;
						selectedSlot = null;
						tradeSlot.setItemStack(null);
					}
				}
			}
			hasBeenPressed = false;
		}else {
			hasBeenPressed = false;
		}
	}
	
	public void buyAllItem() {
		if(tradeSlot.getItemStack() != null && selectedInvItem == null) {
			if(handler.playerHasItem(Item.coinsItem, (tradeSlot.getItemStack().getAmount() * tradeSlot.getItemStack().getItem().getPrice()))) {
				if(!handler.invIsFull(tradeSlot.getItemStack().getItem())) {
					handler.removeItem(Item.coinsItem, (tradeSlot.getItemStack().getAmount() * tradeSlot.getItemStack().getItem().getPrice()));
					handler.giveItem(tradeSlot.getItemStack().getItem(), tradeSlot.getItemStack().getAmount());
					inventoryLoaded = false;
					
					tradeSlot.setItemStack(null);
					selectedSlot = null;
					selectedShopItem.setAmount(0);
					clearNonStockItems();
					if(selectedShopItem != null)
						selectedShopItem = null;
				}
				hasBeenPressed = false;
			}else {
				handler.sendMsg("You don't have enough gold to buy " + tradeSlot.getItemStack().getAmount() + " " + tradeSlot.getItemStack().getItem().getName() + "s.");
				hasBeenPressed = false;
			}
		}else {
			hasBeenPressed = false;
		}
	}
	
	public void sellAllItem() {
		if(tradeSlot.getItemStack() != null && selectedShopItem == null) {
			if(tradeSlot.getItemStack().getItem().getPrice() == -1) {
				handler.sendMsg("You cannot sell this item.");
				hasBeenPressed = false;
				return;
			}
			if(!handler.invIsFull(tradeSlot.getItemStack().getItem())) {
				if(handler.playerHasItem(tradeSlot.getItemStack().getItem(), tradeSlot.getItemStack().getAmount())) {
					handler.removeItem(tradeSlot.getItemStack().getItem(), tradeSlot.getItemStack().getAmount());
					handler.giveItem(Item.coinsItem, (tradeSlot.getItemStack().getItem().getPrice() * tradeSlot.getItemStack().getAmount()));
					itemSlots.get(findFreeSlot(tradeSlot.getItemStack().getItem())).addItem(tradeSlot.getItemStack().getItem(), tradeSlot.getItemStack().getAmount());
					tradeSlot.setItemStack(null);
					inventoryLoaded = false;
					selectedSlot = null;
					if(selectedInvItem != null)
						selectedInvItem = null;
				}
			}
			hasBeenPressed = false;
		}else {
			hasBeenPressed = false;
		}
	}
	
	public void buyXItem(int amount) {
		if(tradeSlot.getItemStack() != null && selectedInvItem == null) {
			if(amount > tradeSlot.getItemStack().getAmount()) {
				amount = tradeSlot.getItemStack().getAmount();
			}
			if(handler.playerHasItem(Item.coinsItem, (amount * tradeSlot.getItemStack().getItem().getPrice()))) {
				if(!handler.invIsFull(tradeSlot.getItemStack().getItem())) {
					handler.removeItem(Item.coinsItem, amount * tradeSlot.getItemStack().getItem().getPrice());
					handler.giveItem(tradeSlot.getItemStack().getItem(), amount);
					tradeSlot.setItemStack(null);
					
					if(selectedShopItem.getAmount() - amount >= 0) {
						selectedShopItem.setAmount(selectedShopItem.getAmount() - amount);
						clearNonStockItems();
					}else {
						selectedSlot.setItemStack(null);
					}
					
					inventoryLoaded = false;
					selectedSlot = null;
					if(selectedShopItem != null)
						selectedShopItem = null;
				}
				hasBeenPressed = false;
			}else {
				handler.sendMsg("You don't have enough gold to buy " + amount + " " + tradeSlot.getItemStack().getItem().getName() + "s.");
				hasBeenPressed = false;
			}
		}else {
			hasBeenPressed = false;
		}
	}
	
	public void sellXItem(int amount) {
		if(tradeSlot.getItemStack() != null && selectedShopItem == null) {
			if(tradeSlot.getItemStack().getItem().getPrice() == -1) {
				handler.sendMsg("You cannot sell this item.");
				hasBeenPressed = false;
				return;
			}
			if(!handler.invIsFull(tradeSlot.getItemStack().getItem())) {
				if(amount > tradeSlot.getItemStack().getAmount()) {
					amount = tradeSlot.getItemStack().getAmount();
				}
				if(handler.playerHasItem(tradeSlot.getItemStack().getItem(), amount)) {
					handler.removeItem(tradeSlot.getItemStack().getItem(), amount);
					handler.giveItem(Item.coinsItem, (tradeSlot.getItemStack().getItem().getPrice() * amount));
					itemSlots.get(findFreeSlot(tradeSlot.getItemStack().getItem())).addItem(tradeSlot.getItemStack().getItem(), amount);
					tradeSlot.setItemStack(null);
					inventoryLoaded = false;
					selectedSlot = null;
					if(selectedInvItem != null)
						selectedInvItem = null;
				}
			}
			hasBeenPressed = false;
		}else {
			hasBeenPressed = false;
		}
	}
	
	public int findFreeSlot(Item item) {
		boolean firstFreeSlotFound = false;
		int index = -1;
        for (int i = 0; i < itemSlots.size(); i++) {
        	if(itemSlots.get(i).getItemStack() != null){
        		if(itemSlots.get(i).getItemStack().getItem().getName() == item.getName()){
        			System.out.println("Added the item to the shop stock");
            		return i;
        		}
        	}
            if(itemSlots.get(i).getItemStack() == null) {
            	if(!firstFreeSlotFound) {
	            	firstFreeSlotFound = true;
	            	index = i;
            	}
            }
       }
        if(index != -1)
        	return index;
       System.out.println("The shop has no more room.");
       return -1;
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
