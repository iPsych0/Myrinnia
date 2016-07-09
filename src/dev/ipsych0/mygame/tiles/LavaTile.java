package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class LavaTile extends Tiles {

	public LavaTile(int id) {
		super(Assets.lava, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}