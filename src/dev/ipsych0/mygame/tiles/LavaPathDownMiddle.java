package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaPathDownMiddle extends Tiles {

	public LavaPathDownMiddle(int id) {
		super(Assets.lavaPathDownMiddle, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}