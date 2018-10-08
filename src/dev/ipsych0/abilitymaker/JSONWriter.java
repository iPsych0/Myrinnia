package dev.ipsych0.abilitymaker;

import com.google.gson.Gson;
import dev.ipsych0.myrinnia.abilities.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.utils.Utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONWriter {

    public static boolean validate(CharacterStats element, CharacterStats style, String name, AbilityType type, boolean selectable, double cooldown, double castTime, double overcastTime, int baseDmg, int price, String className, String description) {
        return !name.isEmpty() && cooldown >= 0 && castTime >= 0 && overcastTime >= 0 && baseDmg >= 0 && price >= 0 && !description.isEmpty() && className.length() > 7;
    }

    public static void write(CharacterStats element, CharacterStats style, String name, AbilityType type, boolean selectable, double cooldown, double castTime, double overcastTime, int baseDmg, int price, String className, String description) {
        Gson gson = Utils.getGson();
        String clsName = "dev.ipsych0.myrinnia.abilities."+className;
        JSONAbility ability = new JSONAbility(element, style, name, type, selectable,cooldown, castTime, overcastTime, baseDmg, price, clsName, description);
        String json = gson.toJson(ability);
        Path path = Paths.get("src/dev/ipsych0/myrinnia/abilities/json/" + name.toLowerCase() + ".json");

        if(Files.exists(path)){
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try(FileWriter fileWriter = new FileWriter("src/dev/ipsych0/myrinnia/abilities/json/" + name.toLowerCase() + ".json")){
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
