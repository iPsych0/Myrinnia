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
	private CraftButton cb;
	private int numRows = 2;
	private int numCols = 2;
	private int craftAmount = 1;
	public static boolean craftButtonPressed = false;
	public static boolean craftResultPressed = false;
	private Rectangle cbBounds;
	private ItemStack currentSelectedSlot;
	private Rectangle crsBounds;
	private RecipeList recipeList;
	
	public CraftingUI(Handler handler, int x, int y) {
		
		this.handler = handler;
		this.x = x;
		this.y = y;
		width = 242;
		height = 320;
		
		if(!isCreated) {
			
			craftingSlots = new CopyOnWriteArrayList<CraftingSlot>();
			
//			for(int i = 0; i < numCols; i++) {
//				for(int j = 0; j < numRows; j++) {
//					craftingSlots.add(new CraftingSlot(x + 16 + (32 * i), y + height - (32 * numCols) - 16 + (32 * j), null));
//				}
//			}
			
			craftingSlots.add(new CraftingSlot(x + 32, y + 50, null));
			craftingSlots.add(new CraftingSlot(x + 80, y + 100, null));
			craftingSlots.add(new CraftingSlot(x + 128, y + 100, null));
			craftingSlots.add(new CraftingSlot(x + 176, y + 50, null));
			
			crs = new CraftResultSlot(x + width / 2 - 16, y + height - 160, null);
			cb = new CraftButton(x + width / 2 - 48, y + height - 112, CraftingSlot.SLOTSIZE * 3, CraftingSlot.SLOTSIZE);
			
			cbBounds = new Rectangle(cb.getBounds());
			crsBounds = new Rectangle(crs.getBounds());
			
			recipeList = new RecipeList();
			
			isCreated = true;
			
		}
		
	}
	
	public void tick() {
		
		if(isOpen) {
			
			crs.tick();
			cb.tick();
			
			Rectangle temp = new Rectangle(handler.getWorld().getHandler().getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			if(handler.getMouseManager().isLeftPressed()) {
				if(cbBounds.contains(temp) && !hasBeenPressed && craftButtonPressed) {
					hasBeenPressed = true;
					
					craftItem(getCraftingSlots().get(0).getItemStack());
					
					craftButtonPressed = false;
					hasBeenPressed = false;
				}
			}
			
			if(handler.getMouseManager().isRightPressed()) {
				if(crsBounds.contains(temp) && !hasBeenPressed && craftResultPressed) {
					if(crs.getItemStack() == null) {
						return;
					}
					hasBeenPressed = true;
					
					handler.giveItem(crs.getItemStack().getItem(), crs.getItemStack().getAmount());
					crs.setItemStack(null);
					
					craftResultPressed = false;
					hasBeenPressed = false;
				}
			}
			
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
		
			g.drawImage(Assets.craftWindow, x, y, width, height, null);
			
//			g.setColor(Color.DARK_GRAY);
//			g.fillRect(x + 8, y + 12, 64, 48);
//			g.fillRect(x + 88, y + 12, 64, 48);
//			g.fillRect(x + 168, y + 12, 64, 48);
//			
//			g.setColor(Color.BLACK);
//			g.drawRect(x + 8, y + 12, 64, 48);
//			g.drawRect(x + 88, y + 12, 64, 48);
//			g.drawRect(x + 168, y + 12, 64, 48);
//			
//			g.setFont(Assets.font14);
//			g.setColor(Color.YELLOW);
			Text.drawString(g, "Crafting", x + width / 2, y + 16, true, Color.YELLOW, Assets.font20);
//			Text.drawString(g, "Smithing", x + 88 + (64 / 2), y + 12 + (48 / 2), true, Color.YELLOW, Assets.font14);
//			Text.drawString(g, "Brewing", x + 168 + (64 / 2), y + 12 + (48 / 2), true, Color.YELLOW, Assets.font14);
			
			
			crs.render(g);
			cb.render(g);
			Text.drawString(g, "Craft " + craftAmount, x + width / 2, y + height - 96, true, Color.YELLOW, Assets.font20);
		
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
	
	public void craftItem(ItemStack a) {
		if(a == null) {
			return;
		}
		if(a.getItem().getId() == Item.woodItem.getId()) {
			if(a.getAmount() >= 5) {
				if(getCraftingSlots().get(0).getItemStack().getAmount() - 5 < 0) {
					handler.sendMsg("You don't have enough " + a.getItem().getName());
					return;
				}
				if(getCraftingSlots().get(0).getItemStack().getAmount() - 5 == 0) {
					getCraftResultSlot().addItem(Item.testSword, 1);
					getCraftingSlots().get(0).setItemStack(null);
					return;
				}else {
					getCraftResultSlot().addItem(Item.testSword, 1);
					getCraftingSlots().get(0).getItemStack().setAmount(a.getAmount() - 5);
				}
			}
		}
	}

	public CopyOnWriteArrayList<CraftingSlot> getCraftingSlots() {
		return craftingSlots;
	}

	public void setCraftingSlots(CopyOnWriteArrayList<CraftingSlot> craftingSlots) {
		this.craftingSlots = craftingSlots;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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

	public CraftResultSlot getCraftResultSlot() {
		return crs;
	}

	public void setCraftResultSlot(CraftResultSlot crs) {
		this.crs = crs;
	}
	

}
