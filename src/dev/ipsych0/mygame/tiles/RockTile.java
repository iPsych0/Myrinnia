package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class RockTile extends Tiles {

	public RockTile(int id) {
		super(Assets.stone, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}
	
}
