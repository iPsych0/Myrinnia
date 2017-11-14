package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;

public class EquipmentWindow implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static boolean isCreated = false;
	public static boolean isOpen = true;
	private int x, y;
	private int width, height;
	private Handler handler;
	public static boolean hasBeenPressed = false;
	
	private int numCols = 3;
	private int numRows = 4;
	int alpha = 127;
	Color interfaceColour = new Color(130, 130, 130, alpha);
	
	private static CopyOnWriteArrayList<EquipmentSlot> equipmentSlots;
	private ItemStack currentSelectedSlot;
	public static boolean itemSelected;
	private Rectangle windowBounds;
	
	public EquipmentWindow(Handler handler, int x, int y){
		this.x = x;
		this.y = y;
		this.handler = handler;
		width = numCols * (EquipmentSlot.SLOTSIZE + 10) - 26;
		height = numRows * (EquipmentSlot.SLOTSIZE + 8);
		windowBounds = new Rectangle(x, y, width, height);
		
		if(isCreated == false){
			equipmentSlots = new CopyOnWriteArrayList<EquipmentSlot>();
			
			for(int i = 0; i < numCols; i++){
				for(int j = 0; j < numRows; j++){
					if(j == (numRows)){
						x += 8;
					}
					
					equipmentSlots.add(new EquipmentSlot(x + 17 + (i * (EquipmentSlot.SLOTSIZE)), y + 32 + (j * EquipmentSlot.SLOTSIZE), null));
					
					if(j == (numRows)){
						x -= 8;
					}
				}
			}	
		
			// Prevents multiple instances of the equipment window being created over and over when equipping items
			isCreated = true;
			
		}
	}
	
	public void tick(){
		if(isOpen) {
			Rectangle temp = new Rectangle(handler.getWorld().getHandler().getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			for(EquipmentSlot es : equipmentSlots) {
				
				es.tick();
				
				Rectangle temp2 = new Rectangle(es.getX(), es.getY(), EquipmentSlot.SLOTSIZE, EquipmentSlot.SLOTSIZE);
				
				// If mouse is dragged
				if(handler.getMouseManager().isDragged()){
					if(temp2.contains(temp) && !hasBeenPressed && !itemSelected) {
						hasBeenPressed = true;
						
						// Stick the item to the mouse
						if(currentSelectedSlot == null) {
							if(es.getEquipmentStack() != null) {
								currentSelectedSlot = es.getEquipmentStack();
								handler.getPlayer().removeEquipmentStats(currentSelectedSlot.getItem().getEquipSlot());
								System.out.println("Currently holding: " + es.getEquipmentStack().getItem().getName());
								es.setItem(null);
								itemSelected = true;
							}
							else{
								System.out.println("Clicked on an empty item stack");
								hasBeenPressed = false;
								return;
							}
						}
					}
				}
				
				// If right-clicked on an item
				if(temp2.contains(temp) && handler.getMouseManager().isRightPressed() && !hasBeenPressed){
					if(es.getEquipmentStack() != null){
						hasBeenPressed = true;
						// Unequip the item and remove the equipment stats
						handler.getPlayer().removeEquipmentStats(es.getEquipmentStack().getItem().getEquipSlot());
						handler.getWorld().getInventory().getItemSlots().get(handler.getWorld().getInventory().findFreeSlot(es.getEquipmentStack().getItem())).addItem(es.getEquipmentStack().getItem(), es.getEquipmentStack().getAmount());
						es.setItem(null);
						hasBeenPressed = false;
					}
					else{
						hasBeenPressed = false;
						return;
					}
				}
				
				// If dragging an item outside the equipment window
				if(itemSelected && !handler.getMouseManager().isDragged()){
					if(handler.getMouseManager().getMouseX() <= this.x){
						// Drop the item
						handler.getWorld().getItemManager().addItem(currentSelectedSlot.getItem().createNew((int)handler.getWorld().getEntityManager().getPlayer().getX(), (int)handler.getWorld().getEntityManager().getPlayer().getY(), currentSelectedSlot.getAmount()));
						currentSelectedSlot = null;
						hasBeenPressed = false;
						itemSelected = false;
					}
				}
				
				// If releasing a dragged item inside the equipment window
				if(itemSelected && !handler.getMouseManager().isDragged()) {
					if(temp2.contains(temp)){
						if(getEquipmentSlots().get(handler.getWorld().getInventory().checkEquipmentSlot(currentSelectedSlot.getItem())).equipItem((currentSelectedSlot.getItem()))){
							// Add the stats back and put the item back
							handler.getPlayer().addEquipmentStats(currentSelectedSlot.getItem().getEquipSlot());
							currentSelectedSlot = null;
							itemSelected = false;
							hasBeenPressed = false;
						
						}
					}
				}
			}
		}
	}
	
	public void render(Graphics g){
		if(isOpen){
			
			g.drawImage(Assets.equipScreen, x, y, 132, 348, null);
//			g.setColor(interfaceColour);
//			g.fillRect(x - 16, y - 16, width + 32, height);
//			g.setColor(Color.BLACK);
//			g.drawRect(x - 16, y - 16, width + 32, height);
			g.setFont(Assets.font14);
			g.setColor(Color.YELLOW);
			g.drawString("Equipment", x + 38, y + 24);
			
			for(EquipmentSlot es : equipmentSlots){
				es.render(g);
			}
			
			if(currentSelectedSlot != null){
				g.drawImage(currentSelectedSlot.getItem().getTexture(), handler.getMouseManager().getMouseX(),
						handler.getMouseManager().getMouseY(), null);
			}
			
			g.drawImage(Assets.equipStats, 838, 550, 112, 160, null);
			
			g.setColor(Color.YELLOW);
			g.drawString("Stats ", 876, 546);
			g.drawString("Power = "+Integer.toString(handler.getPlayer().getPower()), 844, 572);
			g.drawString("Defence = "+Integer.toString(handler.getPlayer().getDefence()), 844, 588);
			g.drawString("Vitality = "+Integer.toString(handler.getPlayer().getVitality()), 844, 604);
			g.drawString("ATK Spd. = "+Float.toString(handler.getPlayer().getAttackSpeed()), 844, 620);
			g.drawString("Mov. Spd. = "+Float.toString(handler.getPlayer().getSpeed()), 844, 636);
		}
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

	public CopyOnWriteArrayList<EquipmentSlot> getEquipmentSlots() {
		return equipmentSlots;
	}

	public void setEquipmentSlots(CopyOnWriteArrayList<EquipmentSlot> equipmentSlots) {
		EquipmentWindow.equipmentSlots = equipmentSlots;
	}

	public Rectangle getWindowBounds() {
		return windowBounds;
	}

	public void setWindowBounds(Rectangle windowBounds) {
		this.windowBounds = windowBounds;
	}

	public ItemStack getCurrentSelectedSlot() {
		return currentSelectedSlot;
	}

	public void setCurrentSelectedSlot(ItemStack currentSelectedSlot) {
		this.currentSelectedSlot = currentSelectedSlot;
	}
}
