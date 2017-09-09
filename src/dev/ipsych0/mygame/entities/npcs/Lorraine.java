package dev.ipsych0.mygame.entities.npcs;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.shop.ShopWindow;

public class Lorraine extends Creature {
	
	public static boolean questStarted = false;
	private int speakingTurn;
	private int xSpawn = (int)getX();
	private int ySpawn = (int)getY();
	private ArrayList<ItemStack> shopItems;
	private ShopWindow shopWindow;

	public Lorraine(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		speakingTurn = 0;
		attackable = false;
		isNpc = true;
		
		shopItems = new ArrayList<ItemStack>();
		
		shopItems.add(new ItemStack(Item.woodItem, 5));
		shopItems.add(new ItemStack(Item.oreItem, 10));
		shopItems.add(new ItemStack(Item.testSword, 100));
		
		shopWindow = new ShopWindow(handler, 200, 200, shopItems);
	}

	@Override
	public void tick() {
		if(ShopWindow.isOpen)
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
		if(this.getSpeakingTurn() == 0){
			handler.getPlayer().getChatWindow().sendMessage("Hey, could you help me, please?");
			ShopWindow.isOpen = true;
			this.shopping = true;
			speakingTurn++;
		}
		else if(this.getSpeakingTurn() == 1){
			handler.getPlayer().getChatWindow().sendMessage("I would like you to kill some scorpions, please!");
			speakingTurn++;
		}
		else if(this.getSpeakingTurn() == 2){
			handler.getPlayer().getChatWindow().sendMessage("Kill 5 scorpions and come back!");
			speakingTurn++;
			questStarted = true;
		}
		else if(this.getSpeakingTurn() == 3){
			if(handler.getWorld().getEntityManager().getPlayer().getScorpionKC() < 5){
				handler.getPlayer().getChatWindow().sendMessage("Please come back when you have killed " + (5 - handler.getWorld().getEntityManager().getPlayer().getScorpionKC()) + " more scorpions");
			}
			else{
				handler.getPlayer().getChatWindow().sendMessage("Thanks for killing the 5 scorpions! Here is your reward!");
				speakingTurn++;
			}
		}
		else if(this.getSpeakingTurn() == 4){
			if(!handler.getWorld().getInventory().inventoryIsFull()){
				handler.giveItem(Item.coinsItem, 1000);
				handler.getPlayer().getChatWindow().sendMessage("You received 1000 coins as a reward.");
				speakingTurn++;
			}else{
				handler.getPlayer().getChatWindow().sendMessage("You don't have room for the reward. Free up 1 slot please!");
			}
		}
		else if(this.getSpeakingTurn() == 5){
			handler.getPlayer().getChatWindow().sendMessage("Thanks for helping!");
			speakingTurn++;
		}
		else if(this.getSpeakingTurn() == 6) {
			ShopWindow.isOpen = true;
			this.shopping = true;
			speakingTurn++;
		}
		else if(this.getSpeakingTurn() >= 7 ) {
			if(!ShopWindow.isOpen) {
				this.shopping = false;
				speakingTurn = 5;
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
		if(ShopWindow.isOpen)
			shopWindow.render(g);
	}

}
