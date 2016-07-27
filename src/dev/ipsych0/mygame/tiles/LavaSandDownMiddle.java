package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaSandDownMiddle extends Tiles {

	public LavaSandDownMiddle(int id) {
		super(Assets.lavaSandDownMiddle, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}