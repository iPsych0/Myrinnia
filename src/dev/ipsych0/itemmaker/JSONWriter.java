package dev.ipsych0.itemmaker;

import com.google.gson.Gson;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.EquipSlot;
import dev.ipsych0.myrinnia.items.ItemRarity;
import dev.ipsych0.myrinnia.items.ItemRequirement;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class JSONWriter {

    static boolean validate(String name, ItemRarity itemRarity,
                                   int price, boolean stackable, EquipSlot equipSlot, int power, int defence, int vitality,
                                   float attackSpeed, float movementSpeed, ItemType[] itemTypes, ItemRequirement... requirements) {
        return !name.isEmpty() && itemRarity != null && price > 0 && equipSlot != null;
    }

    static void write(String name, ItemRarity rarity, int price, boolean stackable, EquipSlot equipSlot, int power, int defence, int vitality, float attackSpeed, float movementSpeed, ItemType[] itemTypes, ItemRequirement[] itemRequirements) {
        // Create the JSON object
        Gson gson = Utils.getGson();
        JSONItem item = new JSONItem(null, name, rarity, equipSlot, power, defence, vitality, attackSpeed, movementSpeed,price,stackable,itemTypes, itemRequirements);
        // Create the JSON String
        String json = gson.toJson(item);

        if(Files.exists(Paths.get("src/dev/ipsych0/myrinnia/items/json/" + name.toLowerCase() + ".json"))){
            System.err.println("File '" + name.toLowerCase() + ".json' already exists.");
            return;
        }
        // Write the JSON file
        try (FileWriter fileWriter = new FileWriter("src/dev/ipsych0/myrinnia/items/json/" + name.toLowerCase() + ".json")) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
