package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaSandTopLeft extends Tiles {

	public LavaSandTopLeft(int id) {
		super(Assets.lavaSandTopLeft, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}