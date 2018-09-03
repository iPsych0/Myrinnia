package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.crafting.CraftingUI;
import dev.ipsych0.mygame.entities.npcs.ChatDialogue;
import dev.ipsych0.mygame.gfx.Assets;

public class Workbench extends StaticEntity {

	private String[] firstDialogue = {"You may use this workbench to craft items."};
	private String[] secondDialogue = {"Craft items", "Leave"};
	
	public Workbench(float x, float y) {
		super(x, y, 64, 64);
		bounds.y = 16;
		bounds.height -= 12;
		
		attackable = false;
		isNpc = true;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.workbench, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
		
	}

	@Override
	public void postRender(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interact() {
		switch(speakingTurn){
		
		case 0:
			speakingTurn++;
			return;
		
		case 1:
			if(!CraftingUI.isOpen){
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
			chatDialogue = new ChatDialogue(secondDialogue);
			speakingTurn++;
			break;
		case 3:
			if(chatDialogue.getChosenOption().getOptionID() == 0) {
				Handler.get().getCraftingUI().openWindow();
				chatDialogue = null;
				speakingTurn = 1;
				break;
			}else if(chatDialogue.getChosenOption().getOptionID() == 1) {
				chatDialogue = null;
				speakingTurn = 1;
				break;
			}
		}
			
		
	}

	@Override
	public void respawn() {
		// TODO Auto-generated method stub
		
	}
}
