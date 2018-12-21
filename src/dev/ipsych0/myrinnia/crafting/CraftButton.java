package dev.ipsych0.myrinnia.crafting;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;

public class CraftButton implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1608461834826405658L;
    private int x, y, width, height;
    private Rectangle bounds;
    private boolean hovering = false;

    public CraftButton(int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        bounds = new Rectangle(x, y, width, height);

    }

    public void tick() {
        if (bounds.contains(Handler.get().getMouseManager().getMouseX(), Handler.get().getMouseManager().getMouseY()))
            hovering = true;
        else
            hovering = false;
    }

    public void render(Graphics g) {
        if (hovering)
            g.drawImage(Assets.genericButton[0], x + 1, y + 1, CraftingSlot.SLOTSIZE * 3, CraftingSlot.SLOTSIZE, null);
        else
            g.drawImage(Assets.genericButton[1], x, y, CraftingSlot.SLOTSIZE * 3, CraftingSlot.SLOTSIZE, null);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
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

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
