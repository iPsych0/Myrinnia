package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.npcs.ChatDialogue;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.worlds.World;

public class DirtHole extends StaticEntity {

	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private int speakingTurn;
	private String[] firstDialogue = {"Upon closer inspection you find that you can climb down this hole."};
	private String[] secondDialogue = {"Climb down.", "No thanks, I'm fine."};

	public DirtHole(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 32;
		bounds.height = 32;
		isNpc = true;
		attackable = false;
		speakingTurn = 0;
	}

	@Override
	public void tick() {

	}
	
	@Override
	public void die(){
		
		World currentWorld = handler.getWorld();
		
		// Resapwn
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	
		                currentWorld.getEntityManager().addEntity(new DirtHole(handler, xSpawn, ySpawn));
		                
		            }
		        }, 
		        10000 
		);
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
			chatDialogue = new ChatDialogue(handler, 0, 600, firstDialogue);
			speakingTurn++;
			break;
			
		case 1:
			if(chatDialogue == null) {
				speakingTurn = 0;
				break;
			}
			chatDialogue = new ChatDialogue(handler, 0, 600, secondDialogue);
			speakingTurn++;
			break;
		case 2:
			if(chatDialogue == null) {
				speakingTurn = 0;
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
				speakingTurn = 0;
				break;
			}
		}
	}

	@Override
	public void postRender(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
}
