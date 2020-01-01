package dev.ipsych0.myrinnia.abilities.ui.abilityhud;

import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class AbilityTooltip implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -8419146815388425820L;
    private int x, y, width, height;

    public AbilityTooltip(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 160;
        this.height = 160;
    }

    public void tick() {

    }

    public void render(Graphics2D g, Ability ability) {
        String[] description = Text.splitIntoLine("Description: " + ability.getDescription(), 22);
        int heightOffset = 0;
        if (description.length > 1) {
            heightOffset = 16 * (description.length - 1);
        }
        g.drawImage(Assets.uiWindow, x, y, width, height + heightOffset, null);
        ability.renderIcon(g, x + 4, y + 16);
        Text.drawString(g, ability.getName(), x + 40, y + 32, false, Color.YELLOW, Assets.font14);

        int yPosText = 64;
        int yOffset = 16;
        int index = 0;

        String baseDmg = ability.getBaseDamage() == 0 ? "N/A" : String.valueOf(ability.getBaseDamage());
        Text.drawString(g, "Type: " + ability.getAbilityType().getName(), x + 4, y + yPosText + (index++ * yOffset), false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Combat Style: " + ability.getCombatStyle(), x + 4, y + yPosText + (index++ * yOffset), false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Element: " + ability.getElement(), x + 4, y + yPosText + (index++ * yOffset), false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Base dmg: " + baseDmg, x + 4, y + yPosText + (index++ * yOffset), false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Cooldown: " + (int) ability.getCooldownTimer() + "s", x + 4, y + yPosText + (index++ * yOffset), false, Color.YELLOW, Assets.font14);

        for (int i = 0; i < description.length; i++) {
            Text.drawString(g, description[i], x + 4, y + yPosText + (index * yOffset) + (i * 16), false, Color.YELLOW, Assets.font14);
        }
    }

}
