package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.states.GameState;

public class EquipmentWindow {
	
	public static boolean isCreated = false;
	public static boolean isOpen = false;
	private int x, y;
	private int width, height;
	private Handler handler;
	private InventoryWindow inventoryWindow;
	private boolean hasBeenPressed = false;
	
	private int numCols = 3;
	private int numRows = 4;
	int alpha = 127;
	Color interfaceColour = new Color(130, 130, 130, alpha);
	
	private static CopyOnWriteArrayList<EquipmentSlot> equipmentSlots;
	private EquipmentStack currentSelectedSlot;
	
	public EquipmentWindow(Handler handler, int x, int y){
		this.x = x;
		this.y = y;
		this.handler = handler;
		width = numCols * (ItemSlot.SLOTSIZE + 10) - 26;
		height = numRows * (ItemSlot.SLOTSIZE + 8);
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
			
			
			// TODO: Hardcoded, add dynamic functions for equipment
			//equipmentSlots.get(0).equipItem(Item.woodItem);
			//equipmentSlots.get(1).equipItem(Item.oreItem);
			
		}
	}
	
	public void tick(){
		if(isOpen) {
			Rectangle temp = new Rectangle(handler.getWorld().getHandler().getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			for(EquipmentSlot es : equipmentSlots) {
				
				es.tick();
				
				Rectangle temp2 = new Rectangle(es.getX(), es.getY(), EquipmentSlot.SLOTSIZE, EquipmentSlot.SLOTSIZE);
				
				// TODO: Zorgen dat als ik bijv. Wood equipped heb, dat hij dat swapped met bijv. Ore en niet vervangt en verdwijnt!!!
				if(temp2.contains(temp) && handler.getMouseManager().isRightPressed() && !hasBeenPressed){
					if(es.getEquipmentStack() != null){
						hasBeenPressed = true;
						inventoryWindow = new InventoryWindow(handler, 658, 112);
						handler.getPlayer().removeEquipmentStats(es.getEquipmentStack().getItem().getEquipSlot());
						inventoryWindow.getItemSlots().get(inventoryWindow.findFreeSlot(es.getEquipmentStack().getItem())).addItem(es.getEquipmentStack().getItem(), 1);
						es.setItem(null);
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
			
			g.drawImage(Assets.equipScreen, x, y, 132, 348, null);
//			g.setColor(interfaceColour);
//			g.fillRect(x - 16, y - 16, width + 32, height);
//			g.setColor(Color.BLACK);
//			g.drawRect(x - 16, y - 16, width + 32, height);
			g.setFont(GameState.myFont);
			g.setColor(Color.YELLOW);
			g.drawString("Equipment", x + 38, y + 24);
			
			for(EquipmentSlot es : equipmentSlots){
				es.render(g);
			}
			
			if(currentSelectedSlot != null){
				g.drawImage(currentSelectedSlot.getItem().getTexture(), handler.getMouseManager().getMouseX(),
						handler.getMouseManager().getMouseY(), null);
			}
			
			g.setColor(Color.YELLOW);
			g.drawString("Power = "+Integer.toString(handler.getPlayer().getPower()), 848, 562);
			g.drawString("Defence = "+Integer.toString(handler.getPlayer().getDefence()), 848, 578);
			g.drawString("Vitality = "+Integer.toString(handler.getPlayer().getVitality()), 848, 594);
			g.drawString("ATK Spd. = "+Float.toString(handler.getPlayer().getAttackSpeed()), 848, 610);
			g.drawString("Mov. Spd. = "+Float.toString(handler.getPlayer().getSpeed()), 848, 626);
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
}
