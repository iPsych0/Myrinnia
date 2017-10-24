package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.worlds.World;

public class WaterToBridgePart extends StaticEntity {
	
	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	public static boolean isFixed = false;
	private String layout;
	private int speakingTurn = 0;
	private BufferedImage texture;
	
	public WaterToBridgePart(Handler handler, float x, float y, String layout) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		this.layout = layout.toLowerCase();
		
		bounds.x = 1;
		bounds.y = 1;
		bounds.width = 32;
		bounds.height = 32;
		isNpc = true;
		attackable = false;
		texture = Assets.waterMiddleMiddle;
	}

	@Override
	public void tick() {
		if(isFixed) {
			die();
		}
	}
	
	@Override
	public void die(){
		this.active = false;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(texture, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
			, width, height, null);
	}

	@Override
	public void interact() {
		
		switch(speakingTurn) {
		
		case 0:
			handler.sendMsg("This bridge looks like it could be fixed...");
			speakingTurn = 1;
			break;
			
		case 1:
			if(!isFixed && layout == "horizontal") {
				if(handler.playerHasItem(Item.woodItem, 5)) {
					handler.removeItem(Item.woodItem, 5);
					bounds.width = 0;
					bounds.height = 0;
					texture = Assets.woodenBridgeHorizontal;
					this.speakingTurn = 1;
					isFixed = true;
					handler.sendMsg("You fixed the bridge!");
					break;
				}else {
					handler.sendMsg("I don't have anything to fix the bridge with...");
					break;
				}
			}
			else if(!isFixed && layout == "vertical") {
				if(handler.playerHasItem(Item.woodItem, 5)) {
					handler.removeItem(Item.woodItem, 5);
					bounds.width = 0;
					bounds.height = 0;
					texture = Assets.woodenBridgeVertical;
					this.speakingTurn = 1;
					isFixed = true;
					handler.sendMsg("You fixed the bridge!");
					break;
				}else {
					handler.sendMsg("I don't have anything to fix the bridge with...");
					break;
				}
			}
		}
		
		
	}

	@Override
	public void postRender(Graphics g) {
		
	}

}