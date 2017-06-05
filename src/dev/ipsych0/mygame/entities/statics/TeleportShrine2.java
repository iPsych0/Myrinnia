package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.utils.SaveManager;

public class TeleportShrine2 extends StaticEntity {
	
	private int speakingTurn;

	public TeleportShrine2(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		bounds.x = 1;
		bounds.y = 1;
		bounds.width = 32;
		bounds.height = 32;
		speakingTurn = 0;
		attackable = false;
		isNpc = true;
	}

	@Override
	public void tick() {
		
	}
	
	@Override
	public void die(){

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.teleportShrine2, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
	}

	@Override
	public void interact() {
		if(this.getSpeakingTurn() == 0){
			handler.getWorld().getChatWindow().sendMessage("You interact with the shrine. Press Space to save your game.");
			speakingTurn++;
		}
		else if(this.getSpeakingTurn() == 1){
			// SAVE GAME HERE?
			SaveManager.clearSaveData();
			SaveManager.addSaveData(Integer.toString(handler.getWorld().getEntityManager().getPlayer().getAttackExperience()));
			SaveManager.addSaveData(Float.toString(handler.getWorld().getEntityManager().getPlayer().getX()));
			SaveManager.addSaveData(Float.toString(handler.getWorld().getEntityManager().getPlayer().getY()));
			SaveManager.addSaveData(Integer.toString(handler.getWorld().getEntityManager().getPlayer().getHealth()));
			SaveManager.saveGame();
			handler.getWorld().getChatWindow().sendMessage("Game Saved!");
			speakingTurn = 0;
		}
	}

	public int getSpeakingTurn() {
		return speakingTurn;
	}

	public void setSpeakingTurn(int speakingTurn) {
		this.speakingTurn = speakingTurn;
	}

}