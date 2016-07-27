package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class WaterDirtMiddleLeft extends Tiles {

	public WaterDirtMiddleLeft(int id) {
		super(Assets.waterDirtMiddleLeft, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}
