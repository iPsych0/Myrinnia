package dev.ipsych0.myrinnia.abilities.ui.abilityoverview;

import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;

@Setter
@Getter
public class AbilityOverviewUIButton extends UIImageButton implements Serializable {

    private static final long serialVersionUID = -4740203689067214388L;
    private CharacterStats stat;

    public AbilityOverviewUIButton(int x, int y, CharacterStats characterStats) {
        super(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, Assets.genericButton);
        this.stat = characterStats;
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics2D g) {
        super.render(g);
        g.drawImage(stat.getIcon(), x, y, width, height, null);
    }

}
