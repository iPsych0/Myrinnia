package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaPathMiddleRight extends Tiles {

	public LavaPathMiddleRight(int id) {
		super(Assets.lavaPathMiddleRight, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}