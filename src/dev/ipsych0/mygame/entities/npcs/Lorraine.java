package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.shop.ShopWindow;
import dev.ipsych0.mygame.utils.Text;

public class Lorraine extends ShopKeeper {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static boolean questStarted = false;
	private int xSpawn = (int)getX();
	private int ySpawn = (int)getY();
	private ArrayList<ItemStack> shopItems;
	private String[] firstDialogue = {"Click this button to trade", "This button does nothing"};

	public Lorraine(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		attackable = false;
		isNpc = true;
		isShop = true;
		
		shopItems = new ArrayList<ItemStack>();
		
		shopItems.add(new ItemStack(Item.woodItem, 5));
		shopItems.add(new ItemStack(Item.oreItem, 10));
		shopItems.add(new ItemStack(Item.testSword, 1));
		
		shopWindow = new ShopWindow(handler, shopItems);
	}

	@Override
	public void tick() {
		if(ShopWindow.isOpen && handler.getPlayer().getShopKeeper() == this)
			shopWindow.tick();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.lorraine, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
//		g.setColor(Color.red);
//		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
//				(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
	}

	@Override
	public void die() {
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                handler.getWorld().getEntityManager().addEntity(new Lorraine(handler, xSpawn, ySpawn));
		                
		            }
		        }, 
		        5000 
		);
	}

	@Override
	public void interact() {
		switch(speakingTurn){
		
		case 0:
			speakingTurn++;
			return;
		
		case 1:
			if(!ShopWindow.isOpen){
				chatDialogue = new ChatDialogue(handler, 0, 600, firstDialogue);
				speakingTurn++;
				break;
			}else {
				speakingTurn = 1;
				break;
			}
		case 2:
			if(chatDialogue == null) {
				speakingTurn = 1;
				break;
			}
			if(chatDialogue.getChosenOption().getOptionID() == 0) {
				ShopWindow.isOpen = true;
				this.shopping = true;
				chatDialogue = null;
				speakingTurn = 1;
				break;
			}
		}
	}
	
	public int getSpeakingTurn() {
		return speakingTurn;
	}

	public void setSpeakingTurn(int speakingTurn) {
		this.speakingTurn = speakingTurn;
	}

	@Override
	public void postRender(Graphics g) {
		if(ShopWindow.isOpen && handler.getPlayer().getShopKeeper() == this) {
			shopWindow.render(g);
			Text.drawString(g, "Lorraine's General Store", shopWindow.x + (shopWindow.width / 2), shopWindow.y + 16, true, Color.YELLOW, Assets.font14);
		}
	}

}
