package dev.ipsych0.mygame.entities.npcs;

import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.entities.EntityManager;

public class NPCText {
	
	private String line1;
	private String line2;
	private Entity entity;
	private EntityManager entityManager;
	
	public NPCText(String line1, String line2){
		this.line1 = line1;
		this.line2 = line2;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	

}
