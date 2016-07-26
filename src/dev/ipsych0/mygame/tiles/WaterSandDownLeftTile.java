package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class WaterSandDownLeftTile extends Tiles {

	public WaterSandDownLeftTile(int id) {
		super(Assets.waterSandDownLeft, id);
	}
	@Override
	public boolean isSolid(){
		return true;
	}

}
