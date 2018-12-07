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

        // Create the JSON object
        Gson gson = Utils.getGson();
        String clsName = "dev.ipsych0.myrinnia.abilities." + className;
        JSONAbility ability = new JSONAbility(element, style, name, type, selectable, cooldown, castTime, overcastTime, baseDmg, price, clsName, description);
        name = name.replace(" ", "");


        // Check if ability class already exists, exit
        String fileName = name.substring(0,1).toUpperCase() + name.substring(1).replace(" ", "");
        if (Files.exists(Paths.get("src/dev/ipsych0/myrinnia/abilities/"+fileName+"Ability.java"))) {
            System.err.println("This class/ability already exists! Cause: " + fileName + "Ability.java");
            System.exit(1);
        }

        // Create the JSON String
        String json = gson.toJson(ability);

        // Write the JSON file
        try (FileWriter fileWriter = new FileWriter("src/dev/ipsych0/myrinnia/abilities/json/" + name.toLowerCase() + ".json")) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Create the class from a template
        try (FileWriter fileWriter = new FileWriter("src/dev/ipsych0/myrinnia/abilities/" + fileName + "Ability.java")) {
            for(String s : Files.readAllLines(Paths.get("src/dev/ipsych0/abilitymaker/abilitytemplate.txt"))){
                // Replace the Test class name with the real class name
                if(s.contains("TestAbility")){
                    s = s.replace("TestAbility", fileName+"Ability");
                }
                // Replace the Test image with a temporary, suitable image name
                if(s.contains("TestImage")){
                    s = s.replace("TestImage",name.toLowerCase());
                }
                fileWriter.write(s + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
