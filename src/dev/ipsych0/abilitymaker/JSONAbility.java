package dev.ipsych0.abilitymaker;

import dev.ipsych0.myrinnia.abilities.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.creatures.Creature;

public class JSONAbility {

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
    private String className;

    public JSONAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable, double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String className, String description) {
        this.className = className.replaceAll(" ", "");
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
}
