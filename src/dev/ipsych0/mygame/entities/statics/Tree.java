package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.tiles.Tiles;

public class Tree extends StaticEntity {
	
	private Item item;
	private Tree tree;

	public Tree(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
		bounds.x = 1;
		bounds.y = 1;
		bounds.width = 32;
		bounds.height = 32;
	}

	@Override
	public void tick() {
		
	}
	
	@Override
	public void die(){
		handler.getWorld().getItemManager().addItem(Item.woodItem.createNew((int) x, (int) y));
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

}