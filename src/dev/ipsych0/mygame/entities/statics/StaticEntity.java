package dev.ipsych0.mygame.entities.statics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;

public abstract class StaticEntity extends Entity {
	
	public StaticEntity(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		
		staticNpc = true;
		solid = true;
		attackable = false;
	}
	
	@Override
	public String[] getEntityInfo(Entity hoveringEntity) {
		String[] name = new String[1];
		name[0] = hoveringEntity.getClass().getSimpleName();
		return name;
	}
	
	

}
