package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class WaterDirtDownRight extends Tiles {

	public WaterDirtDownRight(int id) {
		super(Assets.waterDirtDownRight, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}
