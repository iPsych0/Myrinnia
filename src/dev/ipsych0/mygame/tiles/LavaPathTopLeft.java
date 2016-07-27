package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaPathTopLeft extends Tiles {

	public LavaPathTopLeft(int id) {
		super(Assets.lavaPathTopLeft, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}