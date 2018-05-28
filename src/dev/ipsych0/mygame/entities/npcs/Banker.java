package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.bank.BankUI;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.utils.Text;

public class Banker extends Creature{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xSpawn = (int)getX();
	private int ySpawn = (int)getY();
	private String[] firstDialogue = {"Click this button to bank", "This button does nothing"};

	public Banker(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		attackable = false;
		isNpc = true;
		isBank = true;
	}

	@Override
	public void tick() {
		if(BankUI.isOpen)
			handler.getBankUI().tick();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.banker, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
//		g.setColor(Color.red);
//		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
//				(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
	}

	@Override
	public void die() {
		
	}

	@Override
	public void interact() {
		switch(speakingTurn){
		
		case 0:
			speakingTurn++;
			return;
		
		case 1:
			if(!BankUI.isOpen){
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
				BankUI.isOpen = true;
				chatDialogue = null;
				speakingTurn = 1;
				break;
			}
		}
	}

	@Override
	public void postRender(Graphics g) {
		if(BankUI.isOpen) {
			handler.getBankUI().render(g);
			Text.drawString(g, "Bank of Myrinnia", handler.getBankUI().x + (handler.getBankUI().width / 2), handler.getBankUI().y + 16, true, Color.YELLOW, Assets.font14);
		}
	}

	@Override
	public void respawn() {
		handler.getWorld().getEntityManager().addEntity(new Banker(handler, xSpawn, ySpawn));		
	}

}
