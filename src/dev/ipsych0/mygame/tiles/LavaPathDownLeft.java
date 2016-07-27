package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaPathDownLeft extends Tiles {

	public LavaPathDownLeft(int id) {
		super(Assets.lavaPathDownLeft, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}