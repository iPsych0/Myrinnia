package dev.ipsych0.myrinnia.abilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemSlot;

public class EruptionAbility extends Ability {

    /**
     *
     */
    private static final long serialVersionUID = 4028579023728693627L;
    private Color ability = new Color(89, 58, 2, 224);
    private Rectangle hitBox;
    private boolean initDone;
    private int renderTimer = 0;
    private int displayTime = 1 * 60;

    public EruptionAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                           double cooldownTime, double castingTime, double overcastTime, int baseDamage, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, description);

        price = 3;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(Assets.dirtHole, x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
        if (hitBox != null) {
            g.setColor(ability);
            g.fillRect(hitBox.x - (int) Handler.get().getGameCamera().getxOffset(), hitBox.y - (int) Handler.get().getGameCamera().getyOffset(), hitBox.width, hitBox.height);
        }
    }

    @Override
    public void cast() {
        if (!initDone) {
            hitBox = new Rectangle((int) caster.getX() - ItemSlot.SLOTSIZE, (int) caster.getY() - ItemSlot.SLOTSIZE,
                    caster.getWidth() + ItemSlot.SLOTSIZE * 2, caster.getHeight() + ItemSlot.SLOTSIZE * 2);
            initDone = true;

            for (Entity e : Handler.get().getWorld().getEntityManager().getEntities()) {
                if (hitBox.contains(e.getCollisionBounds(0, 0))) {
                    if (!e.isAttackable())
                        continue;
                    if (!e.equals(caster)) {
                        e.damage(caster, e);
                    }
                }
            }
        }
        renderTimer++;
        if (renderTimer == displayTime) {
            hitBox = null;
            this.setCasting(false);
        }
    }

    @Override
    protected void countDown() {
        cooldownTimer++;
        if (cooldownTimer / 60 == cooldownTime) {
            this.setOnCooldown(false);
            this.setActivated(false);
            this.setCasting(false);
            castingTimeTimer = 0;
            initDone = false;
            cooldownTimer = 0;
            renderTimer = 0;
            hitBox = null;
        }
    }

}
