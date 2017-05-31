package dev.ipsych0.mygame.items;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.states.GameState;

public class InventoryWindow {
	
	public static boolean isOpen = false;
	public static boolean isEquipped = false;
	private boolean hasBeenPressed = false;
	public static boolean isCreated = false;
	private boolean isHovering = false;
	
	private EquipmentWindow equipmentWindow;
	
	private int x, y;
	private int width, height;
	private Handler handler;
	
	private int numCols = 3;
	private int numRows = 9;
	int alpha = 127;
	Color interfaceColour = new Color(130, 130, 130, alpha);
	Color itemHoverColour = new Color(0, 0, 255, 255);
	
	private static CopyOnWriteArrayList<ItemSlot> itemSlots;
	private ItemStack currentSelectedSlot;
	private ItemStack firstItemSwap;
	private EquipmentStack finalItemSwap;
	private EquipmentStack firstEquipSwap;
	private ItemStack finalEquipSwap;
	private Item swap;
	private int swapAmount;
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
			width = numCols * (ItemSlot.SLOTSIZE + 11) - 29;
			height = numRows * (ItemSlot.SLOTSIZE + 11) - 58;
		
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
				if(temp2.contains(temp) && handler.getMouseManager().isRightPressed() && isEquipped && !hasBeenPressed && !handler.getMouseManager().isDragged()){
					if(is.getItemStack() != null){
						equipmentWindow = new EquipmentWindow(handler, 658, 466);
						if(is.getItemStack().getItem().equipSlot == 12){
							handler.getWorld().getChatWindow().sendMessage("You cannot equip " + is.getItemStack().getItem().getName());
							isEquipped = false;
							hasBeenPressed = false;
							return;
						}
						if(is.getItemStack().getItem().equipSlot >= 0 && is.getItemStack().getItem().equipSlot <= 11){
							if(equipmentWindow.getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).equipItem(is.getItemStack().getItem())){
								is.setItem(null);
								isEquipped = false;
								hasBeenPressed = false;
								return;
							}
							else{
								//Store the inventory item in a temporary swap slot
								firstItemSwap = is.getItemStack();
								swap = firstItemSwap.getItem();
								swapAmount = firstItemSwap.getAmount();
								finalItemSwap = new EquipmentStack(swap);
								
								// Store the equipment item in a temporary swap slot
								firstEquipSwap = equipmentWindow.getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).getEquipmentStack();
								swap = firstEquipSwap.getItem();
								swapAmount = 1;
								finalEquipSwap = new ItemStack(swap);
						
								// Set the stacks
								is.setItem(finalEquipSwap);
								equipmentWindow.getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).setItem(finalItemSwap);
								
								// Clearing variables
								firstItemSwap = null;
								swap = null;
								finalItemSwap = null;
								firstEquipSwap = null;
								finalEquipSwap = null;
								
								isEquipped = false;
								hasBeenPressed = false;
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
				
				if(temp2.contains(temp) && handler.getMouseManager().isLeftPressed() && !hasBeenPressed && !is.isSelected){
					hasBeenPressed = true;
					if(is.getItemStack() != null){
						if(is.getItemStack().getItem().itemType == ItemType.CRAFTING_MATERIAL){
							is.setSelected(true);
							hasBeenPressed = false;
							return;
						}
						else{
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
	
	public void render(Graphics g){
		if(isOpen){
			g.setColor(interfaceColour);
			g.fillRect(x - 16, y - 16, width + 32, height - 8);
			g.setColor(Color.BLACK);
			g.drawRect(x - 16, y - 16, width + 32, height - 8);
			g.setFont(GameState.myFont);
			g.setColor(Color.WHITE);
			g.drawString("Inventory", x + 26, y - 2);
			
			Rectangle temp = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			for(ItemSlot is : itemSlots){
				
				Rectangle temp2 = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				
				if(currentSelectedSlot != null){
					g.drawImage(currentSelectedSlot.getItem().getTexture(), handler.getMouseManager().getMouseX(),
							handler.getMouseManager().getMouseY(), null);
						g.setFont(GameState.myFont);
						g.setColor(Color.BLACK);
						g.drawString(Integer.toString(currentSelectedSlot.getAmount()), handler.getMouseManager().getMouseX() + 12, handler.getMouseManager().getMouseY() + 16);
				}
				
				if(temp2.contains(temp) && is.getItemStack() != null){
					g.setColor(interfaceColour);
					g.fillRect(x - 128, y - 16, 112, 130);
					g.setColor(Color.BLACK);
					g.drawRect(x - 128, y - 16, 112, 130);
					
					g.setColor(Color.YELLOW);
					g.drawString(is.getItemStack().getItem().getName(), x - 126, y);
					
					/*
					 * Draw the colour of the item's rarity
					 */
					if(is.getItemStack().getItem().getItemRarity() == ItemRarity.Common){
						g.setColor(Color.WHITE);
					}
					else if(is.getItemStack().getItem().getItemRarity() == ItemRarity.Uncommon){
						g.setColor(Color.BLUE);
					}
					else if(is.getItemStack().getItem().getItemRarity() == ItemRarity.Rare){
						g.setColor(Color.ORANGE);
					}
					else if(is.getItemStack().getItem().getItemRarity() == ItemRarity.Exquisite){
						g.setColor(Color.GREEN);
					}
					else if(is.getItemStack().getItem().getItemRarity() == ItemRarity.Unique){
						g.setColor(Color.MAGENTA);
					}
					g.drawString(is.getItemStack().getItem().getItemRarity().toString(), x - 126, y + 16);
					
					if(is.getItemStack().getItem().getEquipSlot() != 12){
						// Only compare stats if an item is actually equipped
						if(handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack() != null){
							/*
							 * Draw power colour red/green if stats are lower/higher
							 */
					
							if(is.getItemStack().getItem().getPower() > handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getPower()){
								g.setColor(Color.GREEN);
							}
							else if(is.getItemStack().getItem().getPower() < handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getPower()){
								g.setColor(Color.RED);
							}else{
								g.setColor(Color.YELLOW);
							}
							g.drawString("Power: " + is.getItemStack().getItem().getPower(), x - 126, y + 32);
							g.drawString("(" + (is.getItemStack().getItem().getPower() - handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getPower()) + ")", x - 36, y + 32);
							
							/*
							 * Draw defence colour red/green if stats are lower/higher
							 */
							
							if(is.getItemStack().getItem().getDefence() > handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getDefence()){
								g.setColor(Color.GREEN);
							}
							else if(is.getItemStack().getItem().getDefence() < handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getDefence()){
								g.setColor(Color.RED);
							}else{
								g.setColor(Color.YELLOW);
							}
							g.drawString("Defence: " + is.getItemStack().getItem().getDefence(), x - 126, y + 48);
							g.drawString("(" + (is.getItemStack().getItem().getDefence() - handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getDefence()) + ")", x - 36, y + 48);
							
							/*
							 * Draw vitality colour red/green if stats are lower/higher
							 */
							if(is.getItemStack().getItem().getVitality() > handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getVitality()){
								g.setColor(Color.GREEN);
							}
							else if(is.getItemStack().getItem().getVitality() < handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getVitality()){
								g.setColor(Color.RED);
							}else{
								g.setColor(Color.YELLOW);
							}
							g.drawString("Vitality: " + is.getItemStack().getItem().getVitality(), x - 126, y + 64);
							g.drawString("(" + (is.getItemStack().getItem().getVitality() - handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getVitality()) + ")", x - 36, y + 64);
							
							/*
							 * Draw atk speed colour red/green if stats are lower/higher
							 */
							if(is.getItemStack().getItem().getAttackSpeed() > handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getAttackSpeed()){
								g.setColor(Color.GREEN);
							}
							else if(is.getItemStack().getItem().getAttackSpeed() < handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getAttackSpeed()){
								g.setColor(Color.RED);
							}else{
								g.setColor(Color.YELLOW);
							}
							g.drawString("ATK Speed: " + is.getItemStack().getItem().getAttackSpeed(), x - 126, y + 80);
							g.drawString("(" + (is.getItemStack().getItem().getAttackSpeed() - handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getAttackSpeed()) + ")", x - 36, y + 80);
							
							/*
							 * Draw movement speed colour red/green if stats are lower/higher
							 */
							if(is.getItemStack().getItem().getMovementSpeed() > handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getMovementSpeed()){
								g.setColor(Color.GREEN);
							}
							else if(is.getItemStack().getItem().getMovementSpeed() < handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getMovementSpeed()){
								g.setColor(Color.RED);
							}else{
								g.setColor(Color.YELLOW);
							}
							g.drawString("Mov. Speed: " + is.getItemStack().getItem().getMovementSpeed(), x - 126, y + 96);
							g.drawString("(" + (is.getItemStack().getItem().getMovementSpeed() - handler.getWorld().getEntityManager().getPlayer().getEquipment().getEquipmentSlots().get(is.getItemStack().getItem().getEquipSlot()).getEquipmentStack().getItem().getMovementSpeed()) + ")", x - 36, y + 96);
						}else{
							g.setColor(Color.YELLOW);
							g.drawString("Power: " + is.getItemStack().getItem().getPower(), x - 126, y + 32);
							g.drawString("Defence: " + is.getItemStack().getItem().getDefence(), x - 126, y + 48);
							g.drawString("Vitality: " + is.getItemStack().getItem().getVitality(), x - 126, y + 64);
							g.drawString("ATK Speed: " + is.getItemStack().getItem().getAttackSpeed(), x - 126, y + 80);
							g.drawString("Mov. Speed: " + is.getItemStack().getItem().getMovementSpeed(), x - 126, y + 96);
						}
					}
				}
				is.render(g);
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
	
	public void removeItem(Item item, int amount){
		for(int i = 0; i < itemSlots.size(); i++){
			if(itemSlots.get(i).getItemStack() == null){
				continue;
			}
			if(item.getName() == itemSlots.get(i).getItemStack().getItem().getName()){
				if((itemSlots.get(i).getItemStack().getAmount() - amount) <= 0){
					handler.getWorld().getChatWindow().sendMessage("You don't have enough " + item.getName() + "s");
					return;
				}
				else if((itemSlots.get(i).getItemStack().getAmount() - amount) >= 1){
					itemSlots.get(i).getItemStack().setAmount(itemSlots.get(i).getItemStack().getAmount() - amount);
				}
			}
			else{
				continue;
			}
		}
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


	public CopyOnWriteArrayList<ItemSlot> getItemSlots() {
		return itemSlots;
	}


	public void setItemSlots(CopyOnWriteArrayList<ItemSlot> itemSlots) {
		InventoryWindow.itemSlots = itemSlots;
	}

}
