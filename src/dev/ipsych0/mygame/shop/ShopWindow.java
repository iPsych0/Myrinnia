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
	private boolean inventoryLoaded = false;
	private Rectangle buyButton, sellButton, exit;
	private Rectangle windowBounds;
	
	public static boolean hasBeenPressed = false;
	
	public ShopWindow(Handler handler, int x, int y, ArrayList<ItemStack> shopItems) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		width = 460;
		height = 345;
		
		itemSlots = new CopyOnWriteArrayList<ItemSlot>();
		invSlots = new CopyOnWriteArrayList<ItemSlot>();
		
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(j == (numRows)){
					x += 8;
				}
				
				itemSlots.add(new ItemSlot(x + 17 + (i * (ItemSlot.SLOTSIZE)), y + 32 + (j * ItemSlot.SLOTSIZE), null));
				
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
				
				invSlots.add(new ItemSlot(x + (width / 2) + 17 + (i * (ItemSlot.SLOTSIZE)), y + 32 + (j * ItemSlot.SLOTSIZE), null));
				
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
			
			if(buyButton.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed){
				buyItem();
			}
			
			if(sellButton.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
				sellItem();
			}
			
			if(exit.contains(mouse) && handler.getMouseManager().isLeftPressed()) {
				isOpen = false;
			}
			
			for(ItemSlot is : itemSlots) {
				is.tick();
				
				Rectangle slot = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				
				if(slot.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
					if(is.getItemStack() != null) {
						if(selectedShopItem == null) {
							selectedShopItem = is.getItemStack();
							tradeSlot.setItemStack(selectedShopItem);
							hasBeenPressed = false;
							return;
						}
						else if(selectedShopItem == is.getItemStack()) {
							selectedShopItem = null;
							tradeSlot.setItemStack(null);
							hasBeenPressed = false;
							return;
						}
						else if(selectedShopItem != is.getItemStack()) {
							selectedShopItem = is.getItemStack();
							tradeSlot.setItemStack(selectedShopItem);
							hasBeenPressed = false;
							return;
						}
					}else {
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
				
				if(slot.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
					if(is.getItemStack() != null) {
						if(is.getItemStack().getItem().getPrice() == -1) {
							handler.sendMsg("You cannot sell this item.");
							hasBeenPressed = false;
							return;
						}
						if(selectedInvItem == null) {
							selectedInvItem = is.getItemStack();
							tradeSlot.setItemStack(selectedInvItem);
							hasBeenPressed = false;
							return;
						}
						else if(selectedInvItem == is.getItemStack()) {
							selectedInvItem = null;
							tradeSlot.setItemStack(null);
							hasBeenPressed = false;
							return;
						}
						else if(selectedInvItem != is.getItemStack()) {
							selectedInvItem = is.getItemStack();
							tradeSlot.setItemStack(selectedInvItem);
							hasBeenPressed = false;
							return;
						}
					}else {
						selectedInvItem = null;
						tradeSlot.setItemStack(null);
						hasBeenPressed = false;
						return;
					}
				}
				
			}
			
			tradeSlot.tick();
		
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			g.setColor(interfaceColour);
			g.fillRect(x, y, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
			
			// test stuff buy/sell
			g.drawRect(x + 81, y + (height / 2) + 96, 64, 32);
			g.drawRect(x + (width / 2) + 81, y + (height / 2) + 96, 64, 32);
			Text.drawString(g, "Buy", x + 81 + 32, y + (height / 2) + 96 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Sell", x + 81 + (width / 2) + 32, y + (height / 2) + 96 + 16, true, Color.YELLOW, Assets.font14);
			
			// test stuff +1 +10 etc voor buy
			Text.drawString(g, "-10", x + 49 + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "-1", x + 81 + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "+1", x + 113 + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "+10", x + 145 + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			
			// test stuff +1 +10 etc voor sell
			Text.drawString(g, "-10", x + 49 + (width / 2) + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "-1", x + 81 + (width / 2) + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "+1", x + 113 + (width / 2) + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "+10", x + 145 + (width / 2) + 16, y + (height / 2) + 56 + 16, true, Color.YELLOW, Assets.font14);
			
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
			
			tradeSlot.render(g);
			
			if(selectedInvItem != null)
				Text.drawString(g, selectedInvItem.getAmount() + " " + selectedInvItem.getItem().getName() + " will get you: " + selectedInvItem.getItem().getPrice() * selectedInvItem.getAmount() + " coins.", x + (width / 2) - 8, y + (height / 2) + 104, true, Color.YELLOW, Assets.font14);
		}
	}
	
	private void loadInventory() {
		for(int i = 0; i < handler.getWorld().getInventory().getItemSlots().size(); i++) {
			invSlots.get(i).setItemStack(handler.getWorld().getInventory().getItemSlots().get(i).getItemStack());
		}
	}
	
	private void buyItem() {
		if(tradeSlot.getItemStack() != null) {
			if(handler.playerHasItem(Item.coinsItem, (tradeSlot.getItemStack().getAmount() * tradeSlot.getItemStack().getItem().getPrice()))) {
				if(!handler.invIsFull()) {
					handler.removeItem(Item.coinsItem, (tradeSlot.getItemStack().getAmount() * tradeSlot.getItemStack().getItem().getPrice()));
					handler.giveItem(tradeSlot.getItemStack().getItem(), tradeSlot.getItemStack().getAmount());
					tradeSlot.setItemStack(null);
					inventoryLoaded = false;
					if(selectedShopItem != null)
						selectedShopItem = null;
				}
				hasBeenPressed = false;
			}
		}else {
			hasBeenPressed = false;
			return;
		}
	}
	
	private void sellItem() {
		if(tradeSlot.getItemStack() != null) {
			if(tradeSlot.getItemStack().getItem().getPrice() == -1) {
				handler.sendMsg("You cannot sell this item.");
				hasBeenPressed = false;
				return;
			}
			if(!handler.invIsFull()) {
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
