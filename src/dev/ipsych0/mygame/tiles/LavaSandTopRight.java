package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaSandTopRight extends Tiles {

	public LavaSandTopRight(int id) {
		super(Assets.lavaSandTopRight, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}