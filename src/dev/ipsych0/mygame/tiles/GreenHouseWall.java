package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class GreenHouseWall extends Tiles {

	public GreenHouseWall(int id) {
		super(Assets.greenHouseWall, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}