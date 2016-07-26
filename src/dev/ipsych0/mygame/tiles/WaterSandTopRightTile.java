package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class WaterSandTopRightTile extends Tiles {

	public WaterSandTopRightTile(int id) {
		super(Assets.waterSandTopRight, id);
	}
	@Override
	public boolean isSolid(){
		return true;
	}

}
