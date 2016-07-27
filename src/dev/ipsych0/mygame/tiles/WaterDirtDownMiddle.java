package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class WaterDirtDownMiddle extends Tiles {

	public WaterDirtDownMiddle(int id) {
		super(Assets.waterDirtDownMiddle, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}
