package dev.ipsych0.abilitymaker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilities.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.utils.Utils;

import java.io.*;

public class JSONWriter {

    public static boolean validate(CharacterStats element, CharacterStats style, String name, AbilityType type, boolean selectable, double cooldown, double castTime, double overcastTime, int baseDmg, String description) {
        return !name.isEmpty() && cooldown >= 0 && castTime >= 0 && overcastTime >= 0 && baseDmg >= 0 && !description.isEmpty();
    }

    public static void write(CharacterStats element, CharacterStats style, String name, AbilityType type, boolean selectable, double cooldown, double castTime, double overcastTime, int baseDmg, String description) {
        Gson gson = Utils.getGson();
        JSONAbility ability = new JSONAbility(element, style, name, type, selectable,cooldown, castTime, overcastTime, baseDmg, description);
        String json = gson.toJson(ability);

        try(FileWriter fileWriter = new FileWriter(name + ".json")){
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
