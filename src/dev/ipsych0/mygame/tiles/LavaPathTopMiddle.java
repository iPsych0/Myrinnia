package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaPathTopMiddle extends Tiles {

	public LavaPathTopMiddle(int id) {
		super(Assets.lavaPathTopMiddle, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}