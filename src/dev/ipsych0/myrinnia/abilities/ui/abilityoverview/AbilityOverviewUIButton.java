package dev.ipsych0.myrinnia.abilities.ui.abilityoverview;

import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class AbilityOverviewUIButton extends UIImageButton implements Serializable {

    private static final long serialVersionUID = -4740203689067214388L;
    private CharacterStats stat;

    public AbilityOverviewUIButton(int x, int y, CharacterStats characterStats) {
        super(x, y, ItemSlot.SLOTSIZE * 2, ItemSlot.SLOTSIZE, Assets.genericButton);
        this.stat = characterStats;
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics2D g) {
        super.render(g);
        Text.drawString(g, stat.toString(), x + width / 2, y + height / 2, true, Color.YELLOW, Assets.font14);
    }

    public void renderBackground(Graphics2D g) {
        if (stat == CharacterStats.Fire || stat == CharacterStats.Air || stat == CharacterStats.Water || stat == CharacterStats.Earth) {
            g.drawImage(Assets.uiWindow, x, y, width, height, null);
        }
    }

    public CharacterStats getStat() {
        return stat;
    }

    public void setStat(CharacterStats stat) {
        this.stat = stat;
    }
}
