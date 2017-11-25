package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.npcs.ChatDialogue;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.worlds.World;

public class Campfire extends StaticEntity {

	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private Animation campfire;
	private int speakingTurn;

	public Campfire(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
		bounds.x = 1;
		bounds.y = 1;
		bounds.width = 32;
		bounds.height = 32;
		isNpc = true;
		attackable = false;
		speakingTurn = 0;
		campfire = new Animation(125, Assets.campfire);
	}

	@Override
	public void tick() {
		campfire.tick();
	}
	
	@Override
	public void die(){
		
		World currentWorld = handler.getWorld();
		
		// Resapwn
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	
		                currentWorld.getEntityManager().addEntity(new Campfire(handler, xSpawn, ySpawn));
		                
		            }
		        }, 
		        10000 
		);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(campfire.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
	}

	@Override
	public void interact() {
		if(this.speakingTurn == 0) {
			chatDialogue = new ChatDialogue(handler, 0, 600, true);
			speakingTurn++;
		}
		else if(this.speakingTurn == 1) {
			chatDialogue = new ChatDialogue(handler, 0, 600, false);
			speakingTurn++;
		}
		else if(this.speakingTurn == 2) {
			chatDialogue = null;
			speakingTurn = 0;
		}
	}

	@Override
	public void postRender(Graphics g) {
		
	}
	
}