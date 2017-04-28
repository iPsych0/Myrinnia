package dev.ipsych0.mygame.entities.npcs;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;

public class Lorraine extends Creature {
	
	private boolean scorpionsKilled = false;

	public Lorraine(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		attackable = false;
		isNpc = true;
	}

	@Override
	public void tick() {
		if(handler.getWorld().getEntityManager().getPlayer().getScorpionKC() >= 5){
			scorpionsKilled = true;
		}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interact() {
		if(getSpeakingTurn() == 0){
			handler.getWorld().getChatWindow().sendMessage("Hey, could you help me, please?");
		}
		if(getSpeakingTurn() == 1){
			handler.getWorld().getChatWindow().sendMessage("I would like you to kill some scorpions, please!");
		}
		if(getSpeakingTurn() == 2){
			handler.getWorld().getChatWindow().sendMessage("Kill 5 scorpions and come back!");
		}
		if(getSpeakingTurn() == 3){
			if(handler.getWorld().getEntityManager().getPlayer().getScorpionKC() < 5){
				handler.getWorld().getChatWindow().sendMessage("Please come back when you have killed 5 scorpions");
			}
			else{
				handler.getWorld().getChatWindow().sendMessage("Thanks for killing the 5 scorpions! Here is your reward!");
			}
		}
		if(getSpeakingTurn() == 4){
			if(handler.getWorld().getEntityManager().getPlayer().getScorpionKC() < 5){
				speakingTurn -= 1;
			}else{
				handler.getWorld().getInventory().getItemSlots().get(handler.getWorld().getInventory().findFreeSlot(Item.coinsItem)).addItem(Item.coinsItem, 1000);
				handler.getWorld().getChatWindow().sendMessage("You received 1000 coins as a reward.");
			}
		}
		if(getSpeakingTurn() >= 5){
			handler.getWorld().getChatWindow().sendMessage("Thanks for helping!");
			speakingTurn = 5;
		}
	}

}
