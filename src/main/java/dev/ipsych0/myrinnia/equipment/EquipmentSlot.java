package dev.ipsych0.myrinnia.equipment;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.ItemStack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class EquipmentSlot implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7441289370053980965L;

    public static final int SLOTSIZE = 32;

    private int x, y;
    private ItemStack itemStack;
    public static boolean hasSwapped = false;
    private Rectangle bounds;

    public EquipmentSlot(int x, int y, ItemStack itemStack) {
        this.x = x;
        this.y = y;
        this.itemStack = itemStack;
        this.bounds = new Rectangle(x, y, SLOTSIZE, SLOTSIZE);
    }

    public void tick() {

    }

    public void render(Graphics2D g, BufferedImage image) {
        g.drawImage(Assets.genericButton[0], x, y, SLOTSIZE, SLOTSIZE, null);

        if (itemStack != null) {
            g.drawImage(itemStack.getItem().getTexture(), x, y, SLOTSIZE, SLOTSIZE, null);
        } else {
            g.drawImage(image, x, y, SLOTSIZE, SLOTSIZE, null);
        }

    }

    public boolean equipItem(Item item) {
        if (this.itemStack != null) {
            return item.getEquipSlot() != itemStack.getItem().getEquipSlot();
        } else {
            this.itemStack = new ItemStack(item);
            return true;
        }

    }

    public void setItem(ItemStack item) {
        this.itemStack = item;
    }

    public ItemStack getEquipmentStack() {
        return itemStack;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}

