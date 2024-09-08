package dev.ipsych0.myrinnia.items.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemRarity;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class PickupEntry {

    private Item item;
    private int x, y;
    public static final int HEIGHT = 24;
    public static int WIDTH = 64;
    private Rectangle bounds;

    public PickupEntry(Item item, int x, int y) {
        this.item = item;
        this.x = x;
        this.y = y;
        bounds = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void tick() {

    }

    public int getStrWidth(Graphics2D g){
        // Get string width
        Rectangle strBounds = Text.getStringBounds(g, item.getCount() + "x " + item.getName(), Assets.font14);
        return strBounds.width;
    }

    public void render(Graphics2D g) {
        Rectangle mouse = Handler.get().getMouse();
        if (bounds.contains(mouse)) {
            g.drawImage(Assets.uiWindow, x, y, WIDTH + 4, HEIGHT, null);
            g.setColor(Colors.selectedColor);
            g.fillRoundRect(x, y, WIDTH + 4, HEIGHT, 4, 4);
        } else {
            g.drawImage(Assets.uiWindow, x, y, WIDTH + 4, HEIGHT, null);
        }


        Text.drawString(g, item.getCount() + "x " + item.getName(),
                x + 2, y + 17, false,
                ItemRarity.getColor(item), Assets.font14);

        bounds.setBounds(x, y, WIDTH, HEIGHT);
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
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Rectangle getBounds(int xOffset, int yOffset) {
        return new Rectangle(bounds.x + xOffset, bounds.y + yOffset,
                WIDTH + (Math.abs(xOffset) * 2), HEIGHT + (Math.abs(yOffset) * 2));
    }
}
