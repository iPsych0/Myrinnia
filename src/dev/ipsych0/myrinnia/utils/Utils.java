package dev.ipsych0.myrinnia.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilities.AbilityManager;
import dev.ipsych0.myrinnia.entities.droptables.DropTableEntry;
import dev.ipsych0.myrinnia.entities.npcs.Script;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.Use;
import dev.ipsych0.myrinnia.shops.Stock;
import dev.ipsych0.myrinnia.states.monologues.Monologue;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    private static Gson gson;
    private static int abilityCounter = 0;

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
            return 0;
        }
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().setPrettyPrinting().create();
        }
        return gson;
    }

    private static <T> T loadObjectFromJsonFile(String jsonFile, String packageName, final Class<?> clazz) {
        InputStream inputStream = null;
        jsonFile = "dev/ipsych0/myrinnia/" + packageName + jsonFile.toLowerCase();
        inputStream = Utils.class.getClassLoader().getResourceAsStream(jsonFile);
        if (inputStream == null) {
            throw new IllegalArgumentException(jsonFile + " could not be found.");
        }
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            T t = getGson().fromJson(reader, (Type) clazz);
            reader.close();
            inputStream.close();
            return t;
        } catch (final Exception e) {
            e.printStackTrace();
            System.err.println("Json file could not be loaded.");
            System.exit(1);
        }
        return null;
    }

    private static Class<?> getClassFromString(String jsonFile, String packageName) {
        InputStream inputStream = null;
        BufferedReader reader = null;
        String name = null;
        jsonFile = "dev/ipsych0/myrinnia/" + packageName + "/json/" + jsonFile.toLowerCase();
        inputStream = Utils.class.getClassLoader().getResourceAsStream(jsonFile);
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("className")) {
                    name = line.substring(16, line.length() - 1).replace(" ", "");
                    break;
                }
            }
            reader.close();
            inputStream.close();
        } catch (Exception e) {
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
        Ability a = loadObjectFromJsonFile(path, "abilities/json/", getClassFromString(path, "abilities"));
        a.setId(abilityCounter++);
        AbilityManager.abilityMap.put(a.getClass(), a);
        return a;
    }

    public static Item loadItem(String path, BufferedImage sprite) {
        Item i = loadObjectFromJsonFile(path, "items/json/", Item.class);
        Item.items[i.getId()] = i;
        i.setTexture(sprite);
        return i;
    }

    public static Item loadItem(String path, BufferedImage sprite, int cooldown, Use use) {
        Item i = loadObjectFromJsonFile(path, "items/json/", Item.class);
        Item.items[i.getId()] = i;
        i.setTexture(sprite);
        i.setUse(use);
        i.setUseCooldown(cooldown);
        return i;
    }

    public static Script loadScript(String path) {
        Script s = loadObjectFromJsonFile(path, "entities/npcs/json/", Script.class);
        s.getDialogues().sort((o1, o2) -> {
            Integer i1 = o1.getId();
            Integer i2 = o2.getId();
            return i1.compareTo(i2);
        });
        return s;
    }

    public static List<Stock> loadStocks(String path) {
        List<Stock> s = new ArrayList<>(Arrays.asList(loadObjectFromJsonFile(path, "shops/json/", Stock[].class)));
        return s;
    }

    public static List<DropTableEntry> loadDropTable(String path) {
        List<DropTableEntry> e = new ArrayList<>(Arrays.asList(loadObjectFromJsonFile(path, "entities/droptables/", DropTableEntry[].class)));
        return e;
    }

    public static Monologue loadMonologue(String path) {
        Monologue m = loadObjectFromJsonFile(path, "states/monologues/", Monologue.class);
        return m;
    }

}
