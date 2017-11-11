package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.worlds.World;

public class DirtHole extends StaticEntity {

	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private int speakingTurn;

	public DirtHole(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		bounds.x = 1;
		bounds.y = 1;
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
		if(this.speakingTurn == 0) {
			handler.sendMsg("You can climb down here. (Interact again)");
			speakingTurn++;
		}
		else if(this.speakingTurn == 1) {
			handler.setWorld(handler.getWorldHandler().getWorlds().get(3));
			handler.getWorld().setHandler(handler);
			handler.getPlayer().setX(6016);
			handler.getPlayer().setY(5312);
		}
	}

	@Override
	public void postRender(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
}
