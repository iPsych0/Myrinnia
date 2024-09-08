package dev.ipsych0.myrinnia.abilities.ui.abilityhud;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class AbilitySlot extends UIImageButton implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 4376752517769900190L;
    private Ability ability;

    public AbilitySlot(Ability ability, int x, int y) {
        super(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, Assets.genericButton);
        this.ability = ability;
        this.x = x;
        this.y = y;
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics2D g, char slotKey) {
        super.render(g);
        if (ability != null) {
            ability.renderIcon(g, x, y);
            if (!Handler.get().getAbilityManager().getAbilityHUD().compatibleWeaponType(ability, false)) {
                g.setColor(Colors.blockedColor);
                g.fillRect(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
            }
            if (ability.isSelectable() && ability.isSelected()) {
                g.setColor(Colors.selectedColor);
                g.fillRect(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
            } else if (ability.isOnCooldown()) {
                g.setColor(Colors.cooldownColor);
                g.fillRect(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
            }
        }

        g.drawImage(getBorderByType(), x - 3, y - 3, width + 6, height + 6, null);

        if (ability != null) {
            if (ability.getAbilityType() == AbilityType.EliteAbility) {
                g.drawImage(Assets.aEliteSlot, x - 3, y - 3, width + 6, height + 6, null);
            } else if (ability.getAbilityType() == AbilityType.HealingAbility) {
                g.drawImage(Assets.aHealingSlot, x - 3, y - 3, width + 6, height + 6, null);
            }

            if (ability.isOnCooldown()) {
                Text.drawString(g, String.valueOf((int) ability.getRemainingCooldown()), x + 16, y, true, Color.YELLOW, Assets.font14);
            }
        }

        Text.drawString(g, String.valueOf(slotKey), x + ItemSlot.SLOTSIZE - 10, y + ItemSlot.SLOTSIZE - 4, false, Color.YELLOW, Assets.font14);
    }

    private BufferedImage getBorderByType() {
        // If slot has no ability, return empty slot sprite
        if (ability == null) {
            return Assets.aEmptySlot;
        }

        switch (ability.getElement()) {
            case Water:
                return Assets.aWaterSlot;
            case Fire:
                return Assets.aFireSlot;
            case Air:
                return Assets.aAirSlot;
            case Earth:
                return Assets.aEarthSlot;
        }

        // If no matches found, return empty slot sprite
        return Assets.aEmptySlot;
    }

    public void render(Graphics2D g) {
        super.render(g);
        if (ability != null) {
            ability.renderIcon(g, x, y);
            if (!ability.isUnlocked()) {
                g.setColor(Colors.cooldownColor);
                g.fillRect(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
            }
        }
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }


}
