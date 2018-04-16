package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.entities.npcs.ChatDialogue;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.utils.SaveManager;
import dev.ipsych0.mygame.worlds.World;

public class TeleportShrine extends StaticEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] firstDialogue = {"Would you like to save your game?"};
	private String[] secondDialogue = {"Save my game. (Overwrites current savegame)", "Don't save."};

	public TeleportShrine(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);

		solid = true;
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
		g.drawImage(Assets.teleportShrine1, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - 32 - handler.getGameCamera().getyOffset())
				, width, height, null);
	}

	@Override
	public void interact() {
		if(this.getSpeakingTurn() == 0){
			speakingTurn++;
			return;
		}
		else if(this.getSpeakingTurn() == 1){
			chatDialogue = new ChatDialogue(handler, 0, 600, firstDialogue);
			speakingTurn++;
		}
		else if(this.getSpeakingTurn() == 2) {
			if(chatDialogue == null) {
				speakingTurn = 1;
				return;
			}
			chatDialogue = new ChatDialogue(handler, 0, 600, secondDialogue);
			speakingTurn++;
		}
		else if(this.getSpeakingTurn() == 3){
			if(chatDialogue == null) {
				speakingTurn = 1;
				return;
			}
			if(chatDialogue.getChosenOption().getOptionID() == 0) {
				speakingTurn = 1;
				chatDialogue = null;
	
				// Save the game
				SaveManager.savehandler();
				handler.sendMsg("Game Saved!");
			}else {
				speakingTurn = 1;
				chatDialogue = null;
			}
		}
	}

	public int getSpeakingTurn() {
		return speakingTurn;
	}

	public void setSpeakingTurn(int speakingTurn) {
		this.speakingTurn = speakingTurn;
	}

	@Override
	public void postRender(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}