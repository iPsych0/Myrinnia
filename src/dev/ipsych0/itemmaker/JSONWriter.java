package dev.ipsych0.itemmaker;

import com.google.gson.Gson;
import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.items.ItemRarity;
import dev.ipsych0.myrinnia.items.ItemRequirement;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.utils.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

class JSONWriter {

    static boolean validate(String name, ItemRarity itemRarity, int price, EquipSlot equipSlot) {
        return !name.isEmpty() && !name.trim().isEmpty() && itemRarity != null && price >= -1 && equipSlot != null;
    }

    static void write(String name, ItemRarity rarity, int price, boolean stackable, EquipSlot equipSlot, int strength, int dexterity, int intelligence, int defence, int vitality, float attackSpeed, float movementSpeed, ItemType[] itemTypes, ItemRequirement[] itemRequirements) {
        // Create the JSON object
        Gson gson = Utils.getGson();

        name = name.toLowerCase().trim();
        JSONItem item = new JSONItem(null, name, rarity, equipSlot, strength, dexterity, intelligence, defence, vitality, attackSpeed, movementSpeed, price, stackable, itemTypes, itemRequirements);
        // Create the JSON String
        String json = gson.toJson(item);

        if (Files.exists(Paths.get("src/dev/ipsych0/myrinnia/items/json/" + name.replaceAll(" ", "_") + ".json"))) {
            System.err.println("File '" + name.replaceAll(" ", "_") + ".json' already exists.");
            return;
        }
        // Write the JSON file
        try (FileWriter fileWriter = new FileWriter("src/dev/ipsych0/myrinnia/items/json/" + item.id + "_" + name.replaceAll(" ", "_") + ".json")) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the code ready to copy-paste insert into the Item class
        try (Writer fileWriter = new BufferedWriter(new FileWriter("src/dev/ipsych0/myrinnia/items/json/0_all_items.txt", true))) {
            fileWriter.write("public static Item " + name.replaceAll(" ", "") + " = Utils.loadItem(\"" + + item.id + "_" + name.replaceAll(" ", "_") + "\", Assets." + name.replaceAll(" ", "") + ");");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
