package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;

public class CraftingUI {
	
	private int x, y, width, height;
	public static boolean isOpen = false;
	private boolean isCreated = false;
	private boolean hasBeenPressed = false;
	private boolean itemSelected = false;
	private Handler handler;
	private CopyOnWriteArrayList<CraftingSlot> craftingSlots;
	private CraftResultSlot crs;
	private int numRows = 2;
	private int numCols = 2;
	
	private ItemStack currentSelectedSlot;
	
	public CraftingUI(Handler handler, int x, int y) {
		
		this.handler = handler;
		this.x = x;
		this.y = y;
		width = 240;
		height = 240;
		
		if(!isCreated) {
			
			craftingSlots = new CopyOnWriteArrayList<CraftingSlot>();
			
//			for(int i = 0; i < numCols; i++) {
//				for(int j = 0; j < numRows; j++) {
//					craftingSlots.add(new CraftingSlot(x + 16 + (32 * i), y + height - (32 * numCols) - 16 + (32 * j), null));
//				}
//			}
			
			craftingSlots.add(new CraftingSlot(x + 32, y + 88, null));
			craftingSlots.add(new CraftingSlot(x + 80, y + 136, null));
			craftingSlots.add(new CraftingSlot(x + 128, y + 136, null));
			craftingSlots.add(new CraftingSlot(x + 176, y + 88, null));
			
			crs = new CraftResultSlot(x + width / 2 - 16, y + height - 48, null);
			
			isCreated = true;
			
		}
		
	}
	
	public void tick() {
		
		crs.tick();
		
		Rectangle temp = new Rectangle(handler.getWorld().getHandler().getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
		
		if(isOpen) {
			for(CraftingSlot cs : craftingSlots) {
				
				cs.tick();
				
				Rectangle temp2 = new Rectangle(cs.getX(), cs.getY(), CraftingSlot.SLOTSIZE, CraftingSlot.SLOTSIZE);
				
				if(handler.getMouseManager().isDragged()){
					if(temp2.contains(temp) && !hasBeenPressed && !itemSelected) {
						hasBeenPressed = true;
						
						if(currentSelectedSlot == null) {
							if(cs.getItemStack() != null) {
								currentSelectedSlot = cs.getItemStack();
								System.out.println("Currently holding: " + cs.getItemStack().getItem().getName());
								cs.setItemStack(null);
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
						if(cs.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
							currentSelectedSlot = null;
							itemSelected = false;
							hasBeenPressed = false;
						
						}
					}
				}
				
				if(InventoryWindow.isOpen) {
					if(temp2.contains(temp) && handler.getMouseManager().isRightPressed() && !hasBeenPressed && !handler.getMouseManager().isDragged()){
						hasBeenPressed = true;
						if(cs.getItemStack() != null){
							if(handler.getWorld().getInventory().findFreeSlot(cs.getItemStack().getItem()) == -1) {
								hasBeenPressed = false;
								return;
							}else {
								handler.getWorld().getInventory().getItemSlots().get(handler.getWorld().getInventory().findFreeSlot(cs.getItemStack().getItem())).addItem(cs.getItemStack().getItem(), cs.getItemStack().getAmount());
								cs.setItemStack(null);
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
		
	}
	
	public void render(Graphics g) {
		
		if(isOpen) {
		
			g.setColor(Color.GRAY);
			g.fillRect(x, y, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x + 8, y + 12, 64, 48);
			g.fillRect(x + 88, y + 12, 64, 48);
			g.fillRect(x + 168, y + 12, 64, 48);
			
			g.setColor(Color.BLACK);
			g.drawRect(x + 8, y + 12, 64, 48);
			g.drawRect(x + 88, y + 12, 64, 48);
			g.drawRect(x + 168, y + 12, 64, 48);
			
			g.setFont(Assets.font14);
			g.setColor(Color.YELLOW);
			Text.drawString(g, "Crafting", x + 8 + (64 / 2), y + 12 + (48 / 2), true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Smithing", x + 88 + (64 / 2), y + 12 + (48 / 2), true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Brewing", x + 168 + (64 / 2), y + 12 + (48 / 2), true, Color.YELLOW, Assets.font14);
			
			
			crs.render(g);
		
			for(CraftingSlot cs : craftingSlots) {
				
				cs.render(g);
				
				if(currentSelectedSlot != null){
					g.drawImage(currentSelectedSlot.getItem().getTexture(), handler.getMouseManager().getMouseX(),
							handler.getMouseManager().getMouseY(), null);
						g.setFont(Assets.font14);
						g.setColor(Color.YELLOW);
						g.drawString(Integer.toString(currentSelectedSlot.getAmount()), handler.getMouseManager().getMouseX() + 12, handler.getMouseManager().getMouseY() + 16);
				}
			}
		}
		
	}
	
	public int findFreeSlot(Item item) {
        for (int i = 0; i < craftingSlots.size(); i++) {
        	if(craftingSlots.get(i).getItemStack() != null){
        		if(craftingSlots.get(i).getItemStack().getItem().getName() == item.getName()){
        			System.out.println("This item is already in the crafting window.");
            		return i;
        		}
        	}
            if (craftingSlots.get(i).getItemStack() == null) {
                return i;
            }
       }
       System.out.println("You can't put any more items in.");
       handler.getPlayer().getChatWindow().sendMessage("You can't put any more items in the crafting window.");
       return -1;
	}

	public CopyOnWriteArrayList<CraftingSlot> getCraftingSlots() {
		return craftingSlots;
	}

	public void setCraftingSlots(CopyOnWriteArrayList<CraftingSlot> craftingSlots) {
		this.craftingSlots = craftingSlots;
	}
	

}
