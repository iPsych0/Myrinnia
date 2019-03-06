package dev.ipsych0.myrinnia.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.entities.npcs.Script;
import dev.ipsych0.myrinnia.items.Item;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Type;

public class Utils {

    private static Gson gson;
    public static File abilityJsonDirectory = new File("src/dev/ipsych0/myrinnia/abilities/json/");
    public static File itemJsonDirectory = new File("src/dev/ipsych0/myrinnia/items/json/");
    private static int abilityCounter = 0;

    /**
     * Loads the contents of a file as a String
     *
     * @param path - location of the file
     * @return - Full length String of the file's contents
     */
    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null)
                builder.append(line + "\n");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * Parses Strings to Integers for reading Tiles
     *
     * @param number - String parameter to parse
     * @return - Returns a number if it's valid, otherwise return a default black tile.
     */
    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Couldn't load tile with ID: " + number);
            return 28;
        }
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().setPrettyPrinting().create();
        }
        return gson;
    }

    private static <T> T loadObjectFromJsonFile(String jsonFile, String packageName, final Class<?> clazz) {
        FileInputStream inputStream = null;
        try {
            jsonFile = "src/dev/ipsych0/myrinnia/" + packageName + "/json/" + jsonFile.toLowerCase();
            inputStream = new FileInputStream(new File(jsonFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File '" + jsonFile.toLowerCase() + "' not found.");
            System.exit(1);
        }
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return getGson().fromJson(reader, (Type) clazz);
        } catch (final Exception e) {
            e.printStackTrace();
            System.err.println("Json file could not be loaded.");
            System.exit(1);
        }
        return null;
    }

    private static Class<?> getClassFromString(String jsonFile, String packageName) {
        FileInputStream inputStream = null;
        String name = null;
        try {
            jsonFile = "src/dev/ipsych0/myrinnia/" + packageName + "/json/" + jsonFile.toLowerCase();
            inputStream = new FileInputStream(new File(jsonFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File '" + jsonFile.toLowerCase() + "' not found.");
            System.exit(1);
        }
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("className")) {
                    name = line.substring(16, line.length() - 1).replace(" ", "");
                    break;
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
            System.err.println("Json file could not be loaded.");
            System.exit(1);
        }


        Class<?> clazz = null;
        try {
            clazz = Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Class: " + name + ", is not an existing class.");
            System.exit(1);
        }
        return clazz;
    }

    public static Ability loadAbility(String path) {
        Ability a = loadObjectFromJsonFile(path, "abilities", getClassFromString(path, "abilities"));
        a.setId(abilityCounter++);
        return a;
    }

    public static Item loadItem(String path, BufferedImage sprite) {
        Item i = loadObjectFromJsonFile(path, "items", Item.class);
        Item.items[i.getId()] = i;
        i.setTexture(sprite);
        return i;
    }

    public static Script loadScript(String path) {
        Script s = loadObjectFromJsonFile(path, "entities/npcs", Script.class);
        return s;
    }

}
