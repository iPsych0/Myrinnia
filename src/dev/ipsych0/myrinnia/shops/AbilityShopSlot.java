package dev.ipsych0.myrinnia.shops;

import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Colors;

import java.awt.*;
import java.io.Serializable;

public class AbilityShopSlot extends UIImageButton implements Serializable {

    private Ability ability;
    private int x, y;
    private Rectangle bounds;

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
        ability.renderIcon(g, x, y);
        if (ability.isUnlocked()) {
            g.setColor(Colors.abilityUnlockedColor);
            g.fillRect(x, y, width, height);
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


