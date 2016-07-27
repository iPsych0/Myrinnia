package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaSandMiddleRight extends Tiles {

	public LavaSandMiddleRight(int id) {
		super(Assets.lavaSandMiddleRight, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}