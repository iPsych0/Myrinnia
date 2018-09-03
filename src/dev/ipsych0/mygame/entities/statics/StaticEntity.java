package dev.ipsych0.mygame.entities.statics;

import dev.ipsych0.mygame.entities.Entity;

public abstract class StaticEntity extends Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StaticEntity(float x, float y, int width, int height) {
		super(x, y, width, height);
		
		staticNpc = true;
		solid = true;
		attackable = false;
	}
	
	@Override
	public String[] getEntityInfo(Entity hoveringEntity) {
		if(attackable) {
			String[] name = new String[2];
			name[0] = hoveringEntity.getClass().getSimpleName();
			name[1] = "Health: " + String.valueOf(health) + "/" + String.valueOf(maxHealth);
			return name;
		}else {
			String[] name = new String[1];
			name[0] = hoveringEntity.getClass().getSimpleName();
			return name;
		}
	}
	
	

}
