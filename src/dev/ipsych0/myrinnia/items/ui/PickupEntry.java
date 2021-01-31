package dev.ipsych0.myrinnia.items.ui;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemRarity;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class PickupEntry {

    private Item item;
    private int x, y;
    private int width, height;
    private Rectangle bounds;

    public PickupEntry(Item item, int x, int y) {
        this.item = item;
        this.x = x;
        this.y = y;
        width = 64;
        height = ItemSlot.SLOTSIZE / 2;
        bounds = new Rectangle(x, y, width, height);
    }

    public void tick() {

    }

    public void render(Graphics2D g) {
        // Get string width
        Rectangle strBounds = Text.getStringBounds(g, item.getCount() + "x " + item.getName(), Assets.font14);
        width = strBounds.width;
        g.drawRect(x, y, width + 8, ItemSlot.SLOTSIZE / 2);
        Text.drawString(g, item.getCount() + "x " + item.getName(),
                x + 2, y + 14, false,
                ItemRarity.getColor(item), Assets.font14);

        bounds.setBounds(x, y, width, height);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
