package dev.ipsych0.myrinnia.shops;

import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;

import java.awt.*;
import java.io.Serializable;

public class AbilityShopSlot extends UIImageButton implements Serializable {

    private Ability ability;
    private int x, y;
    private Rectangle bounds;
    private Color unlockedColor = new Color(106, 105, 105, 96);

    public AbilityShopSlot(Ability ability, int x, int y) {
        super(x, y, 32, 32, Assets.genericButton);
        this.ability = ability;
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics2D g) {
        super.render(g);
        ability.render(g, x, y);
        if (ability.isUnlocked()) {
            g.setColor(unlockedColor);
            g.fillRoundRect(x, y, width, height, 4, 4);
        }
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}


