package dev.ipsych0.mygame.items;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.states.GameState;

public class InventoryWindow {
	
	public static boolean isOpen = false;
	private boolean hasBeenPressed = false;
	public static boolean isCreated = false;
	
	private EquipmentWindow equipmentWindow;
	
	private int x, y;
	private int width, height;
	private Handler handler;
	
	private int numCols = 3;
	private int numRows = 10;
	int alpha = 127;
	Color interfaceColour = new Color(130, 130, 130, alpha);
	Color itemHoverColour = new Color(0, 0, 255, 255);
	
	private static CopyOnWriteArrayList<ItemSlot> itemSlots;
	private ItemStack currentSelectedSlot;
	private boolean itemSelected;
	private boolean useSelected;
	
	public InventoryWindow(Handler handler, int x, int y){
		if(isCreated == false){
			this.x = x;
			this.y = y;
			this.handler = handler;
			itemSlots = new CopyOnWriteArrayList<ItemSlot>();
			
			for(int i = 0; i < numCols; i++){
				for(int j = 0; j < numRows; j++){
					if(j == (numRows)){
						x += 8;
					}
					
					itemSlots.add(new ItemSlot(x + (i * (ItemSlot.SLOTSIZE)), y + (j * ItemSlot.SLOTSIZE), null));
					
					if(j == (numRows)){
						x -= 8;
					}
				}
			}	
			width = numCols * (ItemSlot.SLOTSIZE + 10) - 29;
			height = numRows * (ItemSlot.SLOTSIZE + 10) - 58;
		
			// Prevents multiple instances of the inventory being created over and over when picking up items
			isCreated = true;
			
		}
	}
	
	
	public void tick() {
		if(isOpen) {
			Rectangle temp = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			for(ItemSlot is : itemSlots) {
				
				is.tick();
				
				Rectangle temp2 = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				
				if(handler.getMouseManager().isDragged()){
					if(temp2.contains(temp) && !hasBeenPressed) {
						hasBeenPressed = true;
						
						if(currentSelectedSlot == null) {
							if(is.getItemStack() != null) {
								currentSelectedSlot = is.getItemStack();
								is.setItem(null);
								itemSelected = true;
							}
							else{
								hasBeenPressed = false;
								return;
							}
						}
					}
					// Stacking werkt nog niet met unieke items, alleen met zelfde items
				}
				
				if(itemSelected && !handler.getMouseManager().isDragged()) {
					if(temp2.contains(temp)){
						if(is.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
							currentSelectedSlot = null;
							itemSelected = false;
							hasBeenPressed = false;
						}
						else{
							return;
						}
					}
				}
				// TODO: Zorgen dat als ik bijv. Wood equipped heb, dat hij dat swapped met bijv. Ore en niet vervangt en verdwijnt!!!
				if(temp2.contains(temp) && handler.getMouseManager().isRightPressed() && !hasBeenPressed && !handler.getMouseManager().isDragged()){
					hasBeenPressed = true;
					if(is.getItemStack() != null){
						equipmentWindow = new EquipmentWindow(handler, 658, 466);
						if(is.getItemStack().getItem().equipSlot == 10){
							handler.getWorld().getChatWindow().sendMessage("You cannot equip " + is.getItemStack().getItem().getName());
							hasBeenPressed = false;
							return;
						}
						equipmentWindow.getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).equipItem(is.getItemStack().getItem());
						is.setItem(null);
						hasBeenPressed = false;
					}
					else{
						hasBeenPressed = false;
						return;
					}
				}
				if(temp2.contains(temp) && handler.getMouseManager().isLeftPressed() && !hasBeenPressed && !is.isSelected){
					hasBeenPressed = true;
					if(is.getItemStack() != null){
						if(is.getItemStack().getItem().itemType == ItemTypes.CRAFTING_MATERIAL){
							is.setSelected(true);
							hasBeenPressed = false;
							return;
						}
						hasBeenPressed = false;
					}
					else{
						hasBeenPressed = false;
						return;
					}
				}
			}
		}
	}
	
	public void render(Graphics g){
		if(isOpen){
			g.setColor(interfaceColour);
			g.fillRect(x - 16, y - 16, width + 32, height - 8);
			g.setColor(Color.BLACK);
			g.drawRect(x - 16, y - 16, width + 32, height - 8);
			g.setFont(GameState.myFont);
			g.setColor(Color.WHITE);
			g.drawString("Inventory", x + 26, y - 2);
			
			
			
			for(ItemSlot is : itemSlots){
				is.render(g);
			}
			
			
			if(currentSelectedSlot != null){
				g.drawImage(currentSelectedSlot.getItem().getTexture(), handler.getMouseManager().getMouseX(),
						handler.getMouseManager().getMouseY(), null);
					g.setFont(GameState.myFont);
					g.setColor(Color.YELLOW);
					g.drawString(Integer.toString(currentSelectedSlot.getAmount()), handler.getMouseManager().getMouseX() + 16, handler.getMouseManager().getMouseY() + 16);
			}
		}
	}
	
	public int findFreeSlot(Item item) {
        for (int i = 0; i < itemSlots.size(); i++) {
        	if(itemSlots.get(i).getItemStack() != null){
        		if(itemSlots.get(i).getItemStack().getItem().getName() == item.getName()){
        			System.out.println("We already have this item in our inventory!");
            		return i;
        		}
        	}
            if (itemSlots.get(i).getItemStack() == null) {
            	System.out.println("Free slot found = " + "[" + i + "]");
                return i;
            }
       }
       System.out.println("Something went wrong checking for free slots (or bag is full)");
       return -1;
	}
	
	public boolean inventoryIsFull(){
		int emptySlots = 0;
		for (int i = 0; i < itemSlots.size(); i++){
			if(itemSlots.get(i).getItemStack() == null){
				emptySlots++;
			}
		}
		if(emptySlots == 0)
			return true;
		else
			return false;
	}
	
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
		return -10;
	}


	public CopyOnWriteArrayList<ItemSlot> getItemSlots() {
		return itemSlots;
	}


	public void setItemSlots(CopyOnWriteArrayList<ItemSlot> itemSlots) {
		InventoryWindow.itemSlots = itemSlots;
	}

}
