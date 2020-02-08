package dev.ipsych0.myrinnia.abilities.data;

public enum AbilityType {

    StandardAbility("Ability"), HealingAbility("Healing"), EliteAbility("Elite");

    String name;

    AbilityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
