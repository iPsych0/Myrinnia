package dev.ipsych0.mygame.abilities;

import java.awt.Graphics;

import dev.ipsych0.mygame.entities.creatures.Creature;

public abstract class Ability {
	
	protected Creature caster;
	protected int cooldownTimer;
	protected int castingTime;
	protected int overcastTime;
	protected String name;
	protected String description;
	protected AbilityType abilityType;
	protected Element element;
	protected boolean onCooldown, casting, inOvercast;
	protected int baseDamage;
	protected boolean activated;
	
	public Ability(Element element, String name, AbilityType abilityType, int cooldownTimer, int castingTime, int overcastTime, int baseDamage, String description) {
		this.element = element;
		this.abilityType = abilityType;
		this.cooldownTimer = cooldownTimer * 60;
		this.castingTime = castingTime * 60;
		this.overcastTime = overcastTime * 60;
		this.name = name;
		this.baseDamage = baseDamage;
		this.description = description;
		
	}
		
	public void tick() {

	}
	
	public abstract void render(Graphics g);
		
	public abstract void cast();
		

	// Getters & Setters
	
	public AbilityType getAbilityType() {
		return abilityType;
	}

	public void setAbilityType(AbilityType abilityType) {
		this.abilityType = abilityType;
	}

	public Creature getCaster() {
		return caster;
	}

	public void setCaster(Creature caster) {
		this.caster = caster;
	}

	public int getCooldownTimer() {
		return cooldownTimer;
	}

	public void setCooldownTimer(int cooldownTimer) {
		this.cooldownTimer = cooldownTimer;
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

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
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
	
	
	
	
	

}
