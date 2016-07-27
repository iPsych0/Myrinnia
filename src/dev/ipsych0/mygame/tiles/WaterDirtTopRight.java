package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class WaterDirtTopRight extends Tiles {

	public WaterDirtTopRight(int id) {
		super(Assets.waterDirtTopRight, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}
