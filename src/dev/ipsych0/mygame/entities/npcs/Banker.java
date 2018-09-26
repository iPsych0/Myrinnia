package dev.ipsych0.mygame.entities.npcs;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.bank.BankUI;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.gfx.Assets;

public class Banker extends Creature{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6734284480542153325L;
	private int xSpawn = (int)getX();
	private int ySpawn = (int)getY();
	private String[] firstDialogue = {"Please show me my bank.", "Never mind."};

	public Banker(float x, float y) {
		super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		attackable = false;
		isNpc = true;
		isBank = true;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.banker, (int) (x - Handler.get().getGameCamera().getxOffset()),
				(int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
//		g.setColor(Color.red);
//		g.fillRect((int) (x + bounds.x - Handler.get().getGameCamera().getxOffset()),
//				(int) (y + bounds.y - Handler.get().getGameCamera().getyOffset()), bounds.width, bounds.height);
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
				chatDialogue = new ChatDialogue(firstDialogue);
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
		
	}

	@Override
	public void respawn() {
		Handler.get().getWorld().getEntityManager().addEntity(new Banker(xSpawn, ySpawn));		
	}

}
