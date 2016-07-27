package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaSandTopMiddle extends Tiles {

	public LavaSandTopMiddle(int id) {
		super(Assets.lavaSandTopMiddle, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}