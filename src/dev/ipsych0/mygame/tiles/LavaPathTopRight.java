package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaPathTopRight extends Tiles {

	public LavaPathTopRight(int id) {
		super(Assets.lavaPathTopRight, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}