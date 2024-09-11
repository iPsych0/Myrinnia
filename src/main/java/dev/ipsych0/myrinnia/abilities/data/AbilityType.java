package dev.ipsych0.myrinnia.abilities.data;

import lombok.Getter;

@Getter
public enum AbilityType {

    StandardAbility("Ability"), HealingAbility("Healing"), EliteAbility("Elite");

    String name;

    AbilityType(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
