package dev.ipsych0.myrinnia.shops;

import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Colors;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;

@Getter
@Setter
public class AbilityShopSlot extends UIImageButton implements Serializable {

    private Ability ability;
    private Rectangle bounds;

    public AbilityShopSlot(Ability ability, int x, int y) {
        super(x, y, 32, 32, Assets.genericButton);
        this.ability = ability;
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
}


