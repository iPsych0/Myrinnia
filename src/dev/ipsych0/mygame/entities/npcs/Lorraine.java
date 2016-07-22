package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.ChatWindow;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.states.GameState;

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
		g.drawImage(Assets.lorraine, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
//		g.setColor(Color.red);
//		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
//				(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		
		if(ChatWindow.isTalking){
			// TODO: ADD NPC/PLAYER COORDINATE CHECK BEFORE INTERACTING
				g.setFont(GameState.chatFont);
				g.setColor(Color.YELLOW);
				g.drawString("Lorraine", 230, 269);
				g.drawString("Hello there, welcome to Myrinnia!", 128, 290);
		}
	}

	@Override
	public void die() {
		
	}
	
	@Override
	public void interact() {
		
	}

}