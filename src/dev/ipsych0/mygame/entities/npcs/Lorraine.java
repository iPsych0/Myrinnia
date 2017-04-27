package dev.ipsych0.mygame.entities.npcs;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.gfx.Assets;

public class Lorraine extends Creature {

	public Lorraine(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interact() {
		if(speakingTurn == 0){
			handler.getWorld().getChatWindow().sendMessage("Hey, could you help me, please?");
		}
		if(speakingTurn == 1){
			handler.getWorld().getChatWindow().sendMessage("I'm looking for an item");
		}
		if(speakingTurn == 2){
			handler.getWorld().getChatWindow().sendMessage("Do you have an axe for me?");
		}
		if(speakingTurn == 3){
			handler.getWorld().getChatWindow().sendMessage("Yes / No");
		}
	}

}
