package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemSlot;

import java.awt.*;

public class EruptionAbility extends Ability {

    /**
     *
     */
    private static final long serialVersionUID = 4028579023728693627L;
    private Color ability;
    private Rectangle hitBox;
    private boolean initDone;
    private int renderTimer;
    private int displayTime;
    private Animation animation;

    public EruptionAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                           double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);

    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(Assets.dirtHole, x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
        if (animation != null) {
            g.drawImage(animation.getCurrentFrame(),
                    (int) (hitBox.x - Handler.get().getGameCamera().getxOffset()),
                    (int) (hitBox.y - Handler.get().getGameCamera().getyOffset()),
                    96, 96, null);
        }
    }

    @Override
    public void cast() {
        if (!initDone) {
            displayTime = 1 * 60;
            hitBox = new Rectangle((int) caster.getX() - ItemSlot.SLOTSIZE, (int) caster.getY() - ItemSlot.SLOTSIZE,
                    caster.getWidth() + ItemSlot.SLOTSIZE * 2, caster.getHeight() + ItemSlot.SLOTSIZE * 2);
            initDone = true;

            Handler.get().playEffect("eruption.wav");

            animation = new Animation(1000 / Assets.eruption1.length, Assets.eruption1, true);

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

        animation.tick();

        if (renderTimer == displayTime) {
            hitBox = null;
            animation = null;
            setCasting(false);
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
