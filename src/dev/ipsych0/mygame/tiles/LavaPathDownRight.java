package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaPathDownRight extends Tiles {

	public LavaPathDownRight(int id) {
		super(Assets.lavaPathDownRight, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}