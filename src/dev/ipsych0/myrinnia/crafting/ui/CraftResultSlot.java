package dev.ipsych0.myrinnia.crafting.ui;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class CraftResultSlot extends UIImageButton implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 8523315261993088996L;
    private static final int SLOTSIZE = 32;
    private ItemStack itemStack;
    private static boolean stackable = true;
    private Rectangle bounds;

    public CraftResultSlot(int x, int y, ItemStack itemStack) {
        super(x, y, 32, 32, Assets.genericButton);
        this.x = x;
        this.y = y;
        this.itemStack = itemStack;
        bounds = new Rectangle(x, y, SLOTSIZE, SLOTSIZE);
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics2D g) {
        super.render(g);

        if (itemStack != null) {
            g.drawImage(itemStack.getItem().getTexture(), x, y, SLOTSIZE, SLOTSIZE, null);
            Text.drawString(g, Integer.toString(itemStack.getAmount()), x, y + SLOTSIZE - 21, false, Color.YELLOW, Assets.font14);
        }
    }

    /*
     * Adds item to the result slot when the craft button is pressed
     * @returns: true/false if it can be added or not
     */
    public boolean addItem(Item item, int amount) {
        if (itemStack != null && stackable) {
            if (item.getName() == itemStack.getItem().getName()) {
                this.itemStack.setAmount(this.itemStack.getAmount() + amount);
                stackable = true;
                return true;
            } else {
                stackable = false;
                return false;
            }
        } else {
            if (itemStack != null) {
                if (item.getName() != itemStack.getItem().getName()) {
                    stackable = false;
                    return false;
                } else {
                    if (item.getName() == itemStack.getItem().getName()) {
                        this.itemStack.setAmount(this.itemStack.getAmount() + amount);
                        stackable = true;
                        return true;
                    }
                }
            }

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

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

}
