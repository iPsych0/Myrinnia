package dev.ipsych0.myrinnia.abilities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilityhud.AbilitySlot;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.creatures.Creature;

public abstract class Ability implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6105053373876560350L;
	protected Creature caster;
	protected double cooldownTime;
	protected double castingTime;
	protected double overcastTime;
	protected String name;
	protected String description;
	protected AbilityType abilityType;
	protected CharacterStats element;
	protected CharacterStats combatStyle;
	protected boolean onCooldown, casting, inOvercast;
	protected int castingTimeTimer = 0;
	protected int cooldownTimer = 0;
	protected int baseDamage;
	protected boolean activated;
	protected boolean channeling;
	protected boolean selectable;
	protected boolean selected;
	protected boolean unlocked;
	protected int price;
	protected int id;

	public Ability(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable, double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
		this.element = element;
		this.combatStyle = combatStyle;
		this.abilityType = abilityType;
		this.selectable = selectable;
		this.cooldownTime = cooldownTime;
		this.castingTime = castingTime;
		this.overcastTime = overcastTime;
		this.name = name;
		this.baseDamage = baseDamage;
		this.price = price;
		this.description = description;
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
		Rectangle mouse = Handler.get().getMouse();
		if(isSelectable() && isSelected()) {
			if(!Handler.get().getPlayer().hasLeftClickedUI(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
				setSelected(false);
				for(AbilitySlot as : Handler.get().getAbilityManager().getPlayerHUD().getSlottedAbilities()) {
					if(as.getAbility() != null) {
						if(as.getAbility().isChanneling()) {
							this.setActivated(false);
							return;
						}
					}
				}
				if(this.getCastingTime() > 0) {
					this.setChanneling(true);
				}
				this.setOnCooldown(true);
			}
		}else {
			if(this.castingTime * 60 == castingTimeTimer++) {
				this.setCasting(true);
				this.setChanneling(false);
			}
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
	
	public double getRemainingCooldown() {
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

	public double getCooldownTimer() {
		return cooldownTime;
	}

	public void setCooldownTimer(double cooldownTimer) {
		this.cooldownTime = cooldownTimer;
	}

	public double getCastingTime() {
		return castingTime;
	}

	public void setCastingTime(double castingTime) {
		this.castingTime = castingTime;
	}

	public double getOvercastTime() {
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

	public double getCooldownTime() {
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

	public boolean isChanneling() {
		return channeling;
	}

	public void setChanneling(boolean channeling) {
		this.channeling = channeling;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isUnlocked() {
		return unlocked;
	}

	public void setUnlocked(boolean unlocked) {
		this.unlocked = unlocked;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public CharacterStats getCombatStyle() {
		return combatStyle;
	}

	public void setCombatStyle(CharacterStats combatStyle) {
		this.combatStyle = combatStyle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
