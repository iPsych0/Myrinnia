package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.npcs.ChatDialogue;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.worlds.Zone;

public class DirtHole extends StaticEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private String[] firstDialogue = {"Upon closer inspection you find that you can climb down this hole."};
	private String[] secondDialogue = {"Climb down.", "No thanks, I'm fine."};

	public DirtHole(float x, float y) {
		super(x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
		isNpc = true;
		attackable = false;
	}

	@Override
	public void tick() {

	}
	
	@Override
	public void die(){
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.dirtHole, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
				, width, height, null);
	}

	@Override
	public void interact() {
		switch(speakingTurn) {
		
		case 0:
			speakingTurn++;
			return;
		
		case 1:
			chatDialogue = new ChatDialogue(firstDialogue);
			speakingTurn++;
			break;
			
		case 2:
			if(chatDialogue == null) {
				speakingTurn = 1;
				break;
			}
			chatDialogue = new ChatDialogue(secondDialogue);
			speakingTurn++;
			break;
		case 3:
			if(chatDialogue == null) {
				speakingTurn = 1;
				break;
			}
			
			if(chatDialogue.getChosenOption().getOptionID() == 0) {
				chatDialogue = null;
				Handler.get().goToWorld(Zone.IslandUnderground, 3008, 3392);
				break;
			}
			else if(chatDialogue.getChosenOption().getOptionID() == 1) {
				chatDialogue = null;
				speakingTurn = 1;
				break;
			}
		}
	}

	@Override
	public void postRender(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void respawn() {
		Handler.get().getWorld().getEntityManager().addEntity(new DirtHole(xSpawn, ySpawn));
	}
	
}
