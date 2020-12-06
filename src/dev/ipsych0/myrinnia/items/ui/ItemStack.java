package dev.ipsych0.myrinnia.items.ui;

import dev.ipsych0.myrinnia.items.Item;

import java.io.Serializable;

public class ItemStack implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6216487918948558086L;
    private int amount;
    private Item item;

    public ItemStack(Item item) {
        this.item = item;
        this.amount = 1;
    }

    public ItemStack(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    /**
     * Helper function to construct ItemStacks easier
     * @param item The item to create -> static access via Item.myItem
     * @param amount The quantity in the stack
     * @return A constructed wrapper of the item + amount is returned
     */
    public static ItemStack of(Item item, int amount) {
        return new ItemStack(item, amount);
    }

    public static ItemStack of(Item item) {
        return of(item, 1);
    }

    public int getAmount() {
        return amount;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
