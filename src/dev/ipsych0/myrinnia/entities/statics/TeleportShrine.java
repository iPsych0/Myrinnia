package dev.ipsych0.myrinnia.entities.statics;

import java.awt.Graphics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.npcs.ChatDialogue;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.tiles.Tiles;
import dev.ipsych0.myrinnia.utils.SaveManager;

public class TeleportShrine extends StaticEntity {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8566165980826138340L;
	private String[] firstDialogue = {"Would you like to save your game?"};
	private String[] secondDialogue = {"Save my game. (Overwrites current savegame)", "Don't save."};

	public TeleportShrine(float x, float y) {
		super(x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);

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
		g.drawImage(Assets.teleportShrine2, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
				, width, height, null);
		g.drawImage(Assets.teleportShrine1, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - 32 - Handler.get().getGameCamera().getyOffset())
				, width, height, null);
	}

	@Override
	public void interact() {
		if(this.getSpeakingTurn() == 0){
			speakingTurn++;
			return;
		}
		else if(this.getSpeakingTurn() == 1){
			chatDialogue = new ChatDialogue(firstDialogue);
			speakingTurn++;
		}
		else if(this.getSpeakingTurn() == 2) {
			if(chatDialogue == null) {
				speakingTurn = 1;
				return;
			}
			chatDialogue = new ChatDialogue(secondDialogue);
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
				Handler.get().sendMsg("Game Saved!");
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

	}

	@Override
	public void respawn() {
		
	}

}