package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.states.GameState;

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
	
	public static void says(Graphics g, String npcName, String npcText1){
		if(ChatWindow.talkButtonPressed){
			if(talking)
			// TODO: ADD NPC/PLAYER COORDINATE CHECK BEFORE INTERACTING
				g.setFont(GameState.chatFont);
				g.setColor(ChatWindow.chatColour);
				g.drawString(npcName, 230, 269);
				g.drawString(npcText1, 98, 290);
		}
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

}
