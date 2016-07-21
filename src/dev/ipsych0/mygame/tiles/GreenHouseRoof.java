package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class GreenHouseRoof extends Tiles {

	public GreenHouseRoof(int id) {
		super(Assets.greenHouseRoof, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}
