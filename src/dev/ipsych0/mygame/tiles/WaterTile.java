package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class WaterTile extends Tiles {

	public WaterTile(int id) {
		super(Assets.water, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}
