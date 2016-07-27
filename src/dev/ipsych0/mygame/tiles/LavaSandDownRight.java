package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaSandDownRight extends Tiles {

	public LavaSandDownRight(int id) {
		super(Assets.lavaSandDownRight, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}