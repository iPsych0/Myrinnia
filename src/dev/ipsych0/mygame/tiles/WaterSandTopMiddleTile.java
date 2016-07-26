package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class WaterSandTopMiddleTile extends Tiles {

	public WaterSandTopMiddleTile(int id) {
		super(Assets.waterSandTopMiddle, id);
	}
	@Override
	public boolean isSolid(){
		return true;
	}

}
