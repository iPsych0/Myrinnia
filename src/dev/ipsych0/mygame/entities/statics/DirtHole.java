package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Scorpion;
import dev.ipsych0.mygame.entities.npcs.ChatDialogue;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.worlds.World;

public class DirtHole extends StaticEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private String[] firstDialogue = {"Upon closer inspection you find that you can climb down this hole."};
	private String[] secondDialogue = {"Climb down.", "No thanks, I'm fine."};

	public DirtHole(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
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
		g.drawImage(Assets.dirtHole, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
	}

	@Override
	public void interact() {
		switch(speakingTurn) {
		
		case 0:
			speakingTurn++;
			return;
		
		case 1:
			chatDialogue = new ChatDialogue(handler, firstDialogue);
			speakingTurn++;
			break;
			
		case 2:
			if(chatDialogue == null) {
				speakingTurn = 1;
				break;
			}
			chatDialogue = new ChatDialogue(handler, secondDialogue);
			speakingTurn++;
			break;
		case 3:
			if(chatDialogue == null) {
				speakingTurn = 1;
				break;
			}
			
			if(chatDialogue.getChosenOption().getOptionID() == 0) {
				chatDialogue = null;
				handler.setWorld(handler.getWorldHandler().getWorlds().get(3));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(3008);
				handler.getPlayer().setY(3392);
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
		handler.getWorld().getEntityManager().addEntity(new DirtHole(handler, xSpawn, ySpawn));
	}
	
}
