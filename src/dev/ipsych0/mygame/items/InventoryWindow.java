package dev.ipsych0.mygame.items;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.states.GameState;

public class InventoryWindow implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static boolean isOpen = false;
	public static boolean isEquipped = false;
	private boolean hasBeenPressed = false;
	public static boolean isCreated = false;
	private boolean isHovering = false;
	
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
	private ItemStack firstItemSwap;
	private EquipmentStack finalItemSwap;
	private EquipmentStack firstEquipSwap;
	private ItemStack finalEquipSwap;
	private Item swap;
	private int swapAmount;
	private boolean itemSelected;
	private boolean useSelected;
	
	public InventoryWindow(Handler handler, int x, int y){
		this.handler = handler;
		this.x = x;
		this.y = y;
		width = numCols * (ItemSlot.SLOTSIZE + 11) - 29;
		height = numRows * (ItemSlot.SLOTSIZE + 11) - 58;
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
			
		}
	}
	
	
	public void tick() {
		if(isOpen) {
			Rectangle temp = new Rectangle(handler.getWorld().getHandler().getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			for(ItemSlot is : itemSlots) {
				
				is.tick();
				
				Rectangle temp2 = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				
				if(handler.getMouseManager().isDragged()){
					if(temp2.contains(temp) && !hasBeenPressed && !itemSelected) {
						hasBeenPressed = true;
						
						if(currentSelectedSlot == null) {
							if(is.getItemStack() != null) {
								currentSelectedSlot = is.getItemStack();
								System.out.println("Currently holding: " + is.getItemStack().getItem().getName());
								is.setSelected(false);
								is.setItem(null);
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
						if(is.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
							currentSelectedSlot = null;
							itemSelected = false;
							hasBeenPressed = false;
						
						}
					}
				}
				if(itemSelected && !handler.getMouseManager().isDragged()){
					if(handler.getMouseManager().getMouseX() <= this.x && handler.getMouseManager().getMouseY() >= this.y){
						handler.getWorld().getItemManager().addItem(currentSelectedSlot.getItem().createNew((int)handler.getWorld().getEntityManager().getPlayer().getX(), (int)handler.getWorld().getEntityManager().getPlayer().getY(), currentSelectedSlot.getAmount()));
						currentSelectedSlot = null;
						hasBeenPressed = false;
						itemSelected = false;
					}
				}

				if(temp2.contains(temp) && handler.getMouseManager().isRightPressed() && isEquipped && !hasBeenPressed && !handler.getMouseManager().isDragged()){
					if(is.getItemStack() != null){
						if(is.getItemStack().getItem().equipSlot == 12){
							handler.getPlayer().getChatWindow().sendMessage("You cannot equip " + is.getItemStack().getItem().getName());
							isEquipped = false;
							hasBeenPressed = false;
							return;
						}
						if(is.getItemStack().getItem().equipSlot >= 0 && is.getItemStack().getItem().equipSlot <= 11){
							if(handler.getWorld().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).equipItem(is.getItemStack().getItem())){
								handler.getPlayer().addEquipmentStats(is.getItemStack().getItem().getEquipSlot());
								is.setItem(null);
								isEquipped = false;
								hasBeenPressed = false;
								return;
							}
							else{
								//Store the inventory item in a temporary swap slot
								handler.getPlayer().removeEquipmentStats(is.getItemStack().getItem().getEquipSlot());
								firstItemSwap = is.getItemStack();
								swap = firstItemSwap.getItem();
								swapAmount = firstItemSwap.getAmount();
								finalItemSwap = new EquipmentStack(swap);
								
								// Store the equipment item in a temporary swap slot
								firstEquipSwap = handler.getWorld().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).getEquipmentStack();
								swap = firstEquipSwap.getItem();
								swapAmount = 1;
								finalEquipSwap = new ItemStack(swap);
						
								// Set the stacks
								is.setItem(finalEquipSwap);
								handler.getWorld().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).setItem(finalItemSwap);
								
								isEquipped = false;
								hasBeenPressed = false;
								
								handler.getPlayer().addEquipmentStats(finalEquipSwap.getItem().getEquipSlot());
								
								
								// Clearing variables
								firstItemSwap = null;
								swap = null;
								finalItemSwap = null;
								firstEquipSwap = null;
								finalEquipSwap = null;
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
				
				if(CraftingUI.isOpen) {
					if(temp2.contains(temp) && handler.getMouseManager().isLeftPressed() && !hasBeenPressed){
						if(handler.getMouseManager().isDragged()){
							return;
						}
						hasBeenPressed = true;
						if(is.getItemStack() != null){
								handler.getWorld().getCraftingUI().getCraftingSlots().get(handler.getWorld().getCraftingUI().findFreeSlot(is.getItemStack().getItem())).addItem(is.getItemStack().getItem(), is.getItemStack().getAmount());
								is.setItem(null);
								hasBeenPressed = false;
								return;
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
			g.drawImage(Assets.invScreen, x, y, 132, height, null);
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
	
	public int findFreeSlot(Item item) {
        for (int i = 0; i < itemSlots.size(); i++) {
        	if(itemSlots.get(i).getItemStack() != null){
        		if(itemSlots.get(i).getItemStack().getItem().getName() == item.getName()){
        			System.out.println("We already have this item in our inventory!");
            		return i;
        		}
        	}
            if (itemSlots.get(i).getItemStack() == null) {
                return i;
            }
       }
       System.out.println("Something went wrong checking for free slots (or bag is full)");
       handler.getPlayer().getChatWindow().sendMessage("Your inventory is full. Please make some space!");
       return -1;
	}
	
	public void removeItem(Item item, int amount){
		for(int i = 0; i < itemSlots.size(); i++){
			if(itemSlots.get(i).getItemStack() == null){
				continue;
			}
			if(item.getName() == itemSlots.get(i).getItemStack().getItem().getName()){
				if((itemSlots.get(i).getItemStack().getAmount() - amount) <= 0){
					handler.getPlayer().getChatWindow().sendMessage("You don't have enough " + item.getName() + "s");
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
	
	public Item getItemByID(int id) {
		return Item.items[id];
	}


	public CopyOnWriteArrayList<ItemSlot> getItemSlots() {
		return itemSlots;
	}


	public void setItemSlots(CopyOnWriteArrayList<ItemSlot> itemSlots) {
		InventoryWindow.itemSlots = itemSlots;
	}

}
