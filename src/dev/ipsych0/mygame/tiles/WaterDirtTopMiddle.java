package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class WaterDirtTopMiddle extends Tiles {

	public WaterDirtTopMiddle(int id) {
		super(Assets.waterDirtTopMiddle, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}
