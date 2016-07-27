package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaSandMiddleLeft extends Tiles {

	public LavaSandMiddleLeft(int id) {
		super(Assets.lavaSandMiddleLeft, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}