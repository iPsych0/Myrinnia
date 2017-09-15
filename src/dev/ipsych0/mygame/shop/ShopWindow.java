package dev.ipsych0.mygame.shop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.utils.DialogueBox;

public class ShopWindow {
	
	public int x, y, width, height;
	private int numCols = 5;
	private int numRows = 6;
	public CopyOnWriteArrayList<ItemSlot> itemSlots;
	public CopyOnWriteArrayList<ItemSlot> invSlots;
	private int alpha = 127;
	private Color interfaceColour = new Color(130, 130, 130, alpha);
	private ItemStack selectedShopItem;
	private ItemStack selectedInvItem;
	public static boolean isOpen = false;
	private Handler handler;
	private ItemSlot tradeSlot;
	public static boolean inventoryLoaded = false;
	private Rectangle buyButton, sellButton, exit;
	private Rectangle windowBounds;
	public static boolean makingChoice = false;
	private DialogueBox dBox;
	private String[] answers = {"Yes", "No", "Test", "Test 2"};
	
	public static boolean hasBeenPressed = false;
	
	public ShopWindow(Handler handler, int x, int y, ArrayList<ItemStack> shopItems) {
		this.handler = handler;
		this.x = x;
		this.y = y;
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
		
		if(shopItems.size() == 0) {
			System.out.println("Shop size = 0");
			return;
		}
		
		for (int i = 0; i < shopItems.size(); i++) {
			itemSlots.get(i).addItem(shopItems.get(i).getItem(), shopItems.get(i).getAmount());
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
		
		buyButton = new Rectangle(x + 81, y + (height / 2) + 96, 64, 32);
		sellButton = new Rectangle(x + (width / 2) + 81, y + (height / 2) + 96, 64, 32);
		exit = new Rectangle(x + width - 26, y + 10, 16, 16);
		
		windowBounds = new Rectangle(x, y, width, height);
		
		int dialogueWidth = 300;
		int dialogueHeight = 150;
		dBox = new DialogueBox(handler, x + (width / 2) - (dialogueWidth / 2), y + (height / 2) - (dialogueHeight / 2), dialogueWidth, dialogueHeight, answers);
		
	}
	
	public void tick() {
		if(isOpen) {
			
			InventoryWindow.isOpen = false;
			EquipmentWindow.isOpen = false;
			
			if(!inventoryLoaded) {
				loadInventory();
				inventoryLoaded = true;
			}
		
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			if(buyButton.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice && selectedShopItem != null){
				makingChoice = true;
				DialogueBox.isOpen = true;
				dBox.setParam("Buy");
				hasBeenPressed = false;
				return;
			}
			
			if(sellButton.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice && selectedInvItem != null) {
				makingChoice = true;
				DialogueBox.isOpen = true;
				dBox.setParam("Sell");
				hasBeenPressed = false;
				return;
			}
			
			if(exit.contains(mouse) && handler.getMouseManager().isLeftPressed()) {
				isOpen = false;
				inventoryLoaded = false;
				DialogueBox.isOpen = false;
				hasBeenPressed = false;
				return;
			}
			
			for(ItemSlot is : itemSlots) {
				is.tick();
				
				Rectangle slot = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				
				if(slot.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice) {
					if(is.getItemStack() != null) {
						if(selectedShopItem == null) {
							selectedInvItem = null;
							selectedShopItem = is.getItemStack();
							tradeSlot.setItemStack(selectedShopItem);
							hasBeenPressed = false;
							return;
						}
						else if(selectedShopItem == is.getItemStack()) {
							selectedInvItem = null;
							selectedShopItem = null;
							tradeSlot.setItemStack(null);
							hasBeenPressed = false;
							return;
						}
						else if(selectedShopItem != is.getItemStack()) {
							selectedInvItem = null;
							selectedShopItem = is.getItemStack();
							tradeSlot.setItemStack(selectedShopItem);
							hasBeenPressed = false;
							return;
						}
					}else {
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
							selectedShopItem = null;
							selectedInvItem = is.getItemStack();
							tradeSlot.setItemStack(selectedInvItem);
							hasBeenPressed = false;
							return;
						}
						else if(selectedInvItem == is.getItemStack()) {
							selectedShopItem = null;
							selectedInvItem = null;
							tradeSlot.setItemStack(null);
							hasBeenPressed = false;
							return;
						}
						else if(selectedInvItem != is.getItemStack()) {
							selectedShopItem = null;
							selectedInvItem = is.getItemStack();
							tradeSlot.setItemStack(selectedInvItem);
							hasBeenPressed = false;
							return;
						}
					}else {
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
			g.drawRect(x + 17, y + (height / 2) + 64, 64, 32);
			g.drawRect(x + (width / 2) + 17, y + (height / 2) + 64, 64, 32);
			Text.drawString(g, "Buy 1", x + 17 + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Sell 1", x + 17 + (width / 2) + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			
			// Buy/sell ALL
			g.setColor(Color.BLACK);
			g.drawRect(x + 81, y + (height / 2) + 64, 64, 32);
			g.drawRect(x + (width / 2) + 81, y + (height / 2) + 64, 64, 32);
			Text.drawString(g, "Buy all", x + 81 + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Sell all", x + 81 + (width / 2) + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
			
			// Buy/sell X
			g.setColor(Color.BLACK);
			g.drawRect(x + 145, y + (height / 2) + 64, 64, 32);
			g.drawRect(x + (width / 2) + 145, y + (height / 2) + 64, 64, 32);
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
			
			tradeSlot.render(g);
			
			if(selectedInvItem != null)
				Text.drawString(g, selectedInvItem.getAmount() + " " + selectedInvItem.getItem().getName() + " will get you: " + selectedInvItem.getItem().getPrice() * selectedInvItem.getAmount() + " coins.", x + (width / 2) - 8, y + (height / 2) + 104, true, Color.YELLOW, Assets.font14);
			if(selectedShopItem != null)
				Text.drawString(g, selectedShopItem.getAmount() + " " + selectedShopItem.getItem().getName() + " will cost you: " + selectedShopItem.getItem().getPrice() * selectedShopItem.getAmount() + " coins.", x + (width / 2) - 8, y + (height / 2) + 104, true, Color.YELLOW, Assets.font14);
		
			if(makingChoice)
				dBox.render(g);
		}
	}
	
	private void loadInventory() {
		for(int i = 0; i < handler.getWorld().getInventory().getItemSlots().size(); i++) {
			invSlots.get(i).setItemStack(handler.getWorld().getInventory().getItemSlots().get(i).getItemStack());
		}
	}
	
	public void buyItem() {
		if(tradeSlot.getItemStack() != null && selectedInvItem == null) {
			if(handler.playerHasItem(Item.coinsItem, (tradeSlot.getItemStack().getAmount() * tradeSlot.getItemStack().getItem().getPrice()))) {
				if(!handler.invIsFull(tradeSlot.getItemStack().getItem())) {
					handler.removeItem(Item.coinsItem, (tradeSlot.getItemStack().getAmount() * tradeSlot.getItemStack().getItem().getPrice()));
					handler.giveItem(tradeSlot.getItemStack().getItem(), tradeSlot.getItemStack().getAmount());
					tradeSlot.setItemStack(null);
					inventoryLoaded = false;
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
	
	public void sellItem() {
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
					tradeSlot.setItemStack(null);
					inventoryLoaded = false;
					if(selectedInvItem != null)
						selectedInvItem = null;
				}
			}
			hasBeenPressed = false;
		}else {
			hasBeenPressed = false;
			return;
		}
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
