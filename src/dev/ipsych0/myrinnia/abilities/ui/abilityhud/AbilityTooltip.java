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
    public static final int BASE_HEIGHT = 128 + 4, BASE_WIDTH = 256 + 16;

    public AbilityTooltip(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = BASE_WIDTH;
        this.height = BASE_HEIGHT;
    }

    public void tick() {

    }

    public void render(Graphics2D g, Ability ability) {
        render(g, ability, this.x, this.y);
    }

    public void render(Graphics2D g, Ability ability, int x, int y) {
        String[] description = Text.splitIntoLine(ability.getDescription(), 38);
        int heightOffset;
        heightOffset = 16 * (description.length) + 8;

        this.height = BASE_HEIGHT + heightOffset;
        g.drawImage(Assets.uiWindow, x, y, width, height, null);
        ability.renderIcon(g, x + 4, y + 8);
        Text.drawString(g, ability.getName(), x + 40, y + 24, false, Color.YELLOW, Assets.font14);

        int yPosText = 56;
        int yOffset = 16;
        int index = 0;

        String baseDmg = ability.getBaseDamage() == 0 ? "N/A" : String.valueOf(ability.getBaseDamage());
        Text.drawString(g, "Type: " + ability.getAbilityType().getName(), x + 4, y + yPosText + (index++ * yOffset), false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Combat Style: " + ability.getCombatStyle(), x + 4, y + yPosText + (index++ * yOffset), false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Element: " + ability.getElement(), x + 4, y + yPosText + (index++ * yOffset), false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Base dmg: " + baseDmg, x + 4, y + yPosText + (index++ * yOffset), false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Cooldown: " + (int) ability.getCooldownTimer() + "s", x + 4, y + yPosText + (index++ * yOffset), false, Color.YELLOW, Assets.font14);

        for (int i = 0; i < description.length; i++) {
            Text.drawString(g, description[i], x + 4, y + yPosText + (index * yOffset) + (i * 16) + 8, false, Color.WHITE, Assets.font14);
        }
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
