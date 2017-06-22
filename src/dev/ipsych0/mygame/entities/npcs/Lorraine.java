package dev.ipsych0.mygame.entities.npcs;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.creatures.Scorpion;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemSlot;

public class Lorraine extends Creature {
	
	public static boolean questStarted = false;
	private int speakingTurn;
	private int xSpawn = (int)getX();
	private int ySpawn = (int)getY();

	public Lorraine(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		speakingTurn = 0;
		attackable = false;
		isNpc = true;
	}

	@Override
	public void tick() {

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
			handler.getWorld().getChatWindow().sendMessage("Hey, could you help me, please?");
			speakingTurn++;
		}
		else if(this.getSpeakingTurn() == 1){
			handler.getWorld().getChatWindow().sendMessage("I would like you to kill some scorpions, please!");
			speakingTurn++;
		}
		else if(this.getSpeakingTurn() == 2){
			handler.getWorld().getChatWindow().sendMessage("Kill 5 scorpions and come back!");
			speakingTurn++;
			questStarted = true;
		}
		else if(this.getSpeakingTurn() == 3){
			if(handler.getWorld().getEntityManager().getPlayer().getScorpionKC() < 5){
				handler.getWorld().getChatWindow().sendMessage("Please come back when you have killed " + (5 - handler.getWorld().getEntityManager().getPlayer().getScorpionKC()) + " more scorpions");
			}
			else{
				handler.getWorld().getChatWindow().sendMessage("Thanks for killing the 5 scorpions! Here is your reward!");
				speakingTurn++;
			}
		}
		else if(this.getSpeakingTurn() == 4){
			if(!handler.getWorld().getInventory().inventoryIsFull()){
				handler.getWorld().getInventory().getItemSlots().get(handler.getWorld().getInventory().findFreeSlot(Item.coinsItem)).addItem(Item.coinsItem, 1000);
				handler.getWorld().getChatWindow().sendMessage("You received 1000 coins as a reward.");
				speakingTurn++;
			}else{
				handler.getWorld().getChatWindow().sendMessage("You don't have room for the reward. Free up 1 slot please!");
			}
		}
		else if(this.getSpeakingTurn() >= 5){
			handler.getWorld().getChatWindow().sendMessage("Thanks for helping!");
			speakingTurn = 5;
		}
	}
	
	public int getSpeakingTurn() {
		return speakingTurn;
	}

	public void setSpeakingTurn(int speakingTurn) {
		this.speakingTurn = speakingTurn;
	}

}
