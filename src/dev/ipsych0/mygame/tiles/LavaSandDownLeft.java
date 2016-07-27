package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaSandDownLeft extends Tiles {

	public LavaSandDownLeft(int id) {
		super(Assets.lavaSandDownLeft, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}