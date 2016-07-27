package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class WaterDirtTopLeft extends Tiles {

	public WaterDirtTopLeft(int id) {
		super(Assets.waterDirtTopLeft, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}
