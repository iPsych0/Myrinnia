package dev.ipsych0.mygame.entities.npcs;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.gfx.Assets;

public class Lorraine extends Creature {

	public Lorraine(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		attackable = false;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.lorraine, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
	}

	@Override
	public void die() {
		
	}
	
	@Override
	public void interact() {
		
	}

}
