package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaPathMiddleLeft extends Tiles {

	public LavaPathMiddleLeft(int id) {
		super(Assets.lavaPathMiddleLeft, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}