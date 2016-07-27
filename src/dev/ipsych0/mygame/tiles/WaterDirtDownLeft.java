package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class WaterDirtDownLeft extends Tiles {

	public WaterDirtDownLeft(int id) {
		super(Assets.waterDirtDownLeft, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}
