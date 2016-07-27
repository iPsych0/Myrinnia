package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class WaterDirtMiddleRight extends Tiles {

	public WaterDirtMiddleRight(int id) {
		super(Assets.waterDirtMiddleRight, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}
