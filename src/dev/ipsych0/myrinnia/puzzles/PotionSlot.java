package dev.ipsych0.myrinnia.puzzles;

import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;

public class PotionSlot {
    private Color potionColor;
    private int value;
    private boolean selected;
    private Rectangle bounds = new Rectangle(0, 0, 64, 64);
    private int x, y;

    public PotionSlot(Color potionColor, int value) {
        this.potionColor = potionColor;
        this.value = value;
    }

    public void tick() {

    }

    public void render(Graphics2D g, int xPos, int yPos) {
        this.x = xPos;
        this.y = yPos;

        // Selected slot rendering
        if (selected) {
            g.drawImage(Assets.celenorPotionCabinetSlotSelected, xPos, yPos, null);
        } else {
            g.drawImage(Assets.celenorPotionCabinetSlot, xPos, yPos, null);
        }

        // Render potion based on color
        if (potionColor == Color.RED) {
            g.drawImage(Assets.celenorPotionRed, xPos, yPos, null);
        } else if (potionColor == Color.YELLOW) {
            g.drawImage(Assets.celenorPotionYellow, xPos, yPos, null);
        } else if (potionColor == Color.GREEN) {
            g.drawImage(Assets.celenorPotionGreen, xPos, yPos, null);
        } else {
            g.drawImage(Assets.celenorPotionBlue, xPos, yPos, null);
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Rectangle getBounds() {
        bounds.setLocation(this.x, this.y);
        return bounds;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
