package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.crafting.CraftingUI;
import dev.ipsych0.mygame.entities.npcs.ChatDialogue;
import dev.ipsych0.mygame.gfx.Assets;

public class Workbench extends StaticEntity {

	private String[] firstDialogue = {"You may use this workbench to craft items."};
	private String[] secondDialogue = {"Craft items", "Leave"};
	
	public Workbench(Handler handler, float x, float y) {
		super(handler, x, y, 64, 64);
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
		g.drawImage(Assets.workbench, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
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
			chatDialogue = new ChatDialogue(handler, 0, 600, secondDialogue);
			speakingTurn++;
			break;
		case 3:
			if(chatDialogue.getChosenOption().getOptionID() == 0) {
				handler.getCraftingUI().openWindow();
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
