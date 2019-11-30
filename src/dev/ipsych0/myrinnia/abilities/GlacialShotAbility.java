package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.entities.creatures.Projectile;
import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;


public class GlacialShotAbility extends Ability implements Serializable {

    private Animation animation;
    private boolean initialized;
    private Projectile projectile;

    public GlacialShotAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                       double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        g.drawImage(Assets.chillIcon, x, y, null);
    }

    @Override
    public void cast() {
        if (!initialized) {
            animation = new Animation(600 / Assets.glacialShot1.length, Assets.glacialShot1, true, false);
            initialized = true;
        }

        Handler.get().getMouseManager().setLeftPressed(true);

        Player player = Handler.get().getPlayer();
        Rectangle mouse = Handler.get().getMouse();

        if (player.hasLeftClickedUI(mouse))
            return;

        // Change attacking animation depending on which weapon type
        player.setWeaponAnimations(EquipSlot.Mainhand.getSlotId());

        Handler.get().playEffect("abilities/fireball.wav");
        if (Handler.get().getMouseManager().isLeftPressed() || Handler.get().getMouseManager().isDragged()) {
            projectile = new Projectile(player.getX(), player.getY(),
                    (int) (mouse.getX() + Handler.get().getGameCamera().getxOffset() - 16),
                    (int) (mouse.getY() + Handler.get().getGameCamera().getyOffset() - 16),
                    7.0f, DamageType.INT, this, animation);
            player.getProjectiles().add(projectile);
        }
        Handler.get().getMouseManager().setLeftPressed(false);
        setCasting(false);
    }

    @Override
    public void setCaster(Creature c) {
        this.caster = c;
        this.setActivated(true);
    }

    @Override
    public void countDown() {
        if (projectile != null && !projectile.isActive()) {
            if (projectile.getHitCreature() != null) {
                Creature c = projectile.getHitCreature();
                c.addCondition(Handler.get().getPlayer(), c, new Condition(Condition.Type.CHILL, c, 3));
                projectile = null;
            }
        }

        cooldownTimer++;
        if (cooldownTimer / 60 == cooldownTime) {
            this.setOnCooldown(false);
            this.setActivated(false);
            this.setCasting(false);
            castingTimeTimer = 0;
            cooldownTimer = 0;
            initialized = false;
        }
    }

}
