package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.npcs.ChatDialogue;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.utils.SaveManager;

public class TeleportShrine2 extends StaticEntity {
	
	private int speakingTurn = 0;
	private String[] firstDialogue = {"Would you like to save your game?"};
	private String[] secondDialogue = {"Save my game. (Overwrites current savegame)", "Don't save."};

	public TeleportShrine2(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		bounds.x = 1;
		bounds.y = 1;
		bounds.width = 32;
		bounds.height = 32;
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
			chatDialogue = new ChatDialogue(handler, 0, 600, firstDialogue);
			speakingTurn++;
		}
		else if(this.getSpeakingTurn() == 1) {
			if(chatDialogue == null) {
				speakingTurn = 0;
				return;
			}
			chatDialogue = new ChatDialogue(handler, 0, 600, secondDialogue);
			speakingTurn++;
		}
		else if(this.getSpeakingTurn() == 2){
			if(chatDialogue == null) {
				speakingTurn = 0;
				return;
			}
			if(chatDialogue.getChosenOption().getOptionID() == 0) {
				// Clear previous saved data in order to write the new data
				SaveManager.clearInventoryItems();
				SaveManager.clearEquipmentItems();
				SaveManager.clearSaveData();
				
				// Add the player data
				SaveManager.addSaveData(Integer.toString(handler.getWorld().getEntityManager().getPlayer().getAttackExperience()));
				SaveManager.addSaveData(Float.toString(handler.getWorld().getEntityManager().getPlayer().getX()));
				SaveManager.addSaveData(Float.toString(handler.getWorld().getEntityManager().getPlayer().getY()));
				SaveManager.addSaveData(Integer.toString(handler.getWorld().getEntityManager().getPlayer().getHealth()));
				
				// Save the world!
				SaveManager.addSaveData(Integer.toString(handler.getWorld().getWorldID()));
				
				// Add the inventory objects to an arraylist
				for(int i = 0; i < handler.getWorld().getInventory().getItemSlots().size(); i++){
					if(handler.getWorld().getInventory().getItemSlots().get(i).getItemStack() == null){
						continue;
					}
					SaveManager.addInventoryItems(handler.getWorld().getInventory().getItemSlots().get(i).getItemStack());
				}
				
				// Add equipment objects to an arraylist
				for(int i = 0; i < handler.getWorld().getEquipment().getEquipmentSlots().size(); i++){
					if(handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack() == null){
						continue;
					}
					SaveManager.addEquipmentItems((handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack()));
				}
				
				// Finally, save the game
				SaveManager.saveInventory();
				SaveManager.saveEquipment();
				SaveManager.saveGame();
				handler.sendMsg("Game Saved!");
				speakingTurn = 0;
				chatDialogue = null;
			}else {
				speakingTurn = 0;
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