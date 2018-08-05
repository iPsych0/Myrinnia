package dev.ipsych0.mygame.abilities;

import java.awt.Graphics;

import dev.ipsych0.mygame.character.CharacterStats;
import dev.ipsych0.mygame.entities.creatures.Creature;

public abstract class Ability {
	
	protected Creature caster;
	protected int cooldownTime;
	protected int castingTime;
	protected int overcastTime;
	protected String name;
	protected String description;
	protected AbilityType abilityType;
	protected CharacterStats element;
	protected boolean onCooldown, casting, inOvercast;
	protected int castingTimeTimer = 0;
	protected int cooldownTimer = 0;
	protected int baseDamage;
	protected boolean activated;
	protected CastState castState;
	protected boolean channeling;
	
	public Ability(CharacterStats element, String name, AbilityType abilityType, int cooldownTime, int castingTime, int overcastTime, int baseDamage, String description) {
		this.element = element;
		this.abilityType = abilityType;
		this.cooldownTime = cooldownTime;
		this.castingTime = castingTime;
		this.overcastTime = overcastTime;
		this.name = name;
		this.baseDamage = baseDamage;
		this.description = description;
		this.castState = CastState.READY;
	}
	
	public abstract void render(Graphics g, int x, int y);
	
	public abstract void cast();
		
	public void setCaster(Creature c) {
		this.caster = c;
		this.setActivated(true);
		this.setOnCooldown(true);
		if(this.getCastingTime() > 0) {
			this.setChanneling(true);
		}
		System.out.println("Cast: "+this.getName());
	}
	
	public void tick() {
		if(this.castingTime * 60 == castingTimeTimer++) {
			this.setCasting(true);
			this.setChanneling(false);
		}
		
		if(casting) {
			cast();
		}
		
		if(onCooldown) {
			countDown();
		}
	}
	
	protected void countDown() {
		cooldownTimer++;
		if(cooldownTimer / 60 == cooldownTime) {
			this.setOnCooldown(false);
			this.setActivated(false);
			this.setCasting(false);
			castingTimeTimer = 0;
			cooldownTimer = 0;
		}
	}
		

	// Getters & Setters
	
	public int getRemainingCooldown() {
		return cooldownTime - (cooldownTimer/60);
	}
	
	public AbilityType getAbilityType() {
		return abilityType;
	}

	public void setAbilityType(AbilityType abilityType) {
		this.abilityType = abilityType;
	}

	public Creature getCaster() {
		return caster;
	}

	public int getCooldownTimer() {
		return cooldownTime;
	}

	public void setCooldownTimer(int cooldownTimer) {
		this.cooldownTime = cooldownTimer;
	}

	public int getCastingTime() {
		return castingTime;
	}

	public void setCastingTime(int castingTime) {
		this.castingTime = castingTime;
	}

	public int getOvercastTime() {
		return overcastTime;
	}

	public void setOvercastTime(int overcastTime) {
		this.overcastTime = overcastTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isOnCooldown() {
		return onCooldown;
	}

	public void setOnCooldown(boolean onCooldown) {
		this.onCooldown = onCooldown;
	}

	public boolean isCasting() {
		return casting;
	}

	public void setCasting(boolean casting) {
		this.casting = casting;
	}

	public boolean isInOvercast() {
		return inOvercast;
	}

	public void setInOvercast(boolean inOvercast) {
		this.inOvercast = inOvercast;
	}

	public CharacterStats getElement() {
		return element;
	}

	public void setElement(CharacterStats element) {
		this.element = element;
	}

	public int getBaseDamage() {
		return baseDamage;
	}

	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean isActivated) {
		this.activated = isActivated;
	}

	public int getCooldownTime() {
		return cooldownTime;
	}

	public void setCooldownTime(int cooldownTime) {
		this.cooldownTime = cooldownTime;
	}

	public int getCastingTimeTimer() {
		return castingTimeTimer;
	}

	public void setCastingTimeTimer(int castingTimeTimer) {
		this.castingTimeTimer = castingTimeTimer;
	}

	public CastState getCastState() {
		return castState;
	}

	public void setCastState(CastState castState) {
		this.castState = castState;
	}

	public boolean isChanneling() {
		return channeling;
	}

	public void setChanneling(boolean channeling) {
		this.channeling = channeling;
	}
	
	
	
	
	

}
