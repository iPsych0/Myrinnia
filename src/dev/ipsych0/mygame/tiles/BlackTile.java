package dev.ipsych0.mygame.tiles;

import dev.ipsych0.mygame.gfx.Assets;

public class BlackTile extends Tiles {

	public BlackTile(int id) {
		super(Assets.black, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}