package dev.ipsych0.myrinnia.shops;

import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemSlot;

import java.awt.*;
import java.io.Serializable;

public class AbilityShopSlot implements Serializable {

    private Ability ability;
    private int x, y;
    private boolean hovering;
    private Rectangle bounds;
    private Color unlockedColor = new Color(106, 105, 105, 96);

    public AbilityShopSlot(Ability ability, int x, int y) {
        this.ability = ability;
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
    }

    public void tick(){

    }

    public void render(Graphics g){
        if(hovering)
            g.drawImage(Assets.genericButton[0], x, y, 32, 32, null);
        else
            g.drawImage(Assets.genericButton[1], x, y, 32, 32, null);
        ability.render(g, x, y);
        if(ability.isUnlocked()){
            g.setColor(unlockedColor);
            g.fillRoundRect(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, 4, 4);
        }
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public boolean isHovering() {
        return hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
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
}


