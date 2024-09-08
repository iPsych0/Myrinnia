package dev.ipsych0.myrinnia.entities.droptables;

import java.io.Serializable;

public class DropTableEntry implements Serializable {

    private int itemId;
    private String itemName;
    private int amount;
    private int weight;

    public DropTableEntry(int itemId, String itemName, int amount, int weight) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.amount = amount;
        this.weight = weight;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
