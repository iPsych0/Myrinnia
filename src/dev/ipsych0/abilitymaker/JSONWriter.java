package dev.ipsych0.abilitymaker;

import dev.ipsych0.myrinnia.abilities.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;

public class JSONWriter {

    public static boolean validate(CharacterStats element, CharacterStats style, String name, AbilityType type, boolean selectable, double cooldown, double castTime, double overcastTime, int baseDmg, String description) {
        return !name.isEmpty() && cooldown >= 0 && castTime >= 0 && overcastTime >= 0 && baseDmg >= 0 && !description.isEmpty();
    }

    public static void write(CharacterStats element, CharacterStats style, String name, AbilityType type, boolean selectable, double cooldown, double castTime, double overcastTime, int baseDmg, String description) {

    }
}
