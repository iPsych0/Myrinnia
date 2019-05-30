package dev.ipsych0.myrinnia.crafting.ui;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class CraftingSlot extends UIImageButton implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 4758228481988705528L;
    private int x, y;
    public static final int SLOTSIZE = 32;
    private ItemStack itemStack;
    public static boolean stackable = true;

    public CraftingSlot(int x, int y, ItemStack itemStack) {
        super(x, y, 32, 32, Assets.genericButton);
        this.x = x;
        this.y = y;
        this.itemStack = itemStack;
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics2D g) {
        super.render(g);
        if (itemStack != null) {
            g.drawImage(itemStack.getItem().getTexture(), x, y, SLOTSIZE, SLOTSIZE, null);
            if (itemStack.getItem().isStackable()) {
                Text.drawString(g, Integer.toString(itemStack.getAmount()), x, y + SLOTSIZE - 21, false, Color.YELLOW, Assets.font14);
            }
        }
    }

    /*
     * Adds item to the crafting slot
     * @params: Provide the item and amount to add to the slot (usually by right clicking an ItemStack)
     */
    public boolean addItem(Item item, int amount) {
        // If the item is stackable
        if (itemStack != null && item.isStackable()) {
            if (item.getId() == itemStack.getItem().getId()) {
                // If a stack already exists and the item is stackable, add to that stack
                this.itemStack.setAmount(this.itemStack.getAmount() + amount);
                return true;
            } else {
                return false;
            }

        } else if (!item.isStackable()) {
            // If the item isn't stackable
            this.itemStack = new ItemStack(item);
            return true;
        } else {
            // Else create a new stack
            this.itemStack = new ItemStack(item, amount);
            return true;
        }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

}
