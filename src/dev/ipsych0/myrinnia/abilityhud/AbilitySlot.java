package dev.ipsych0.myrinnia.abilityhud;

import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class AbilitySlot extends UIImageButton implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 4376752517769900190L;
    private Ability ability;
    private int x, y;
    private Color cooldownColor = new Color(24, 24, 24, 192);
    private Color selectedColor = new Color(64, 64, 64, 192);
    private Rectangle bounds;

    public AbilitySlot(Ability ability, int x, int y) {
        super(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, Assets.genericButton);
        this.ability = ability;
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics2D g, int slotNum) {
        super.render(g);
        g.drawImage(Assets.genericButton[1], x, y, width, height, null);
        if (ability != null) {
            ability.render(g, x, y);
            if (ability.isSelectable() && ability.isSelected()) {
                g.setColor(selectedColor);
                g.fillRect(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
            }
            if (ability.isOnCooldown()) {
                g.setColor(cooldownColor);
                g.fillRect(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
                Text.drawString(g, String.valueOf((int) ability.getRemainingCooldown()), x + 16, y, true, Color.YELLOW, Assets.font14);

            }
        }
        Text.drawString(g, Integer.toString(slotNum), x + ItemSlot.SLOTSIZE - 10, y + ItemSlot.SLOTSIZE - 4, false, Color.YELLOW, Assets.font14);
    }

    public void render(Graphics2D g) {
        super.render(g);
        if (ability != null) {
            ability.render(g, x, y);
            if (!ability.isUnlocked()) {
                g.setColor(cooldownColor);
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

    public Rectangle getBounds() {
        return bounds;
    }


}
