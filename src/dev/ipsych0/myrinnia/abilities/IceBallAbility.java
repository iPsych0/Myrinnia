package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.entities.creatures.Projectile;
import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;


public class IceBallAbility extends Ability implements Serializable {

    public IceBallAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                       double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {

    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.iceBallI, x, y, null);
    }

    @Override
    public void cast() {

        Player player = Handler.get().getPlayer();
        Rectangle direction;
        if (caster.equals(player)) {
            direction = Handler.get().getMouse();
            if (player.hasLeftClickedUI(direction))
                return;

            // Change attacking animation depending on which weapon type
            player.setWeaponAnimations(EquipSlot.Mainhand.getSlotId());
        } else {
            direction = new Rectangle((int) player.getX(), (int) player.getY(), 1, 1);
        }

        int targetX, targetY;
        if (caster.equals(player)) {
            targetX = (int) (direction.getX() + Handler.get().getGameCamera().getxOffset() - 16);
            targetY = (int) (direction.getY() + Handler.get().getGameCamera().getyOffset() - 16);
            setSelected(false);
        } else {
            targetX = (int) (direction.getX());
            targetY = (int) (direction.getY());
        }

        Handler.get().playEffect("abilities/ice_ball.ogg", 0.1f);
        new Projectile.Builder(DamageType.INT, Assets.iceBall1, caster, targetX, targetY)
                .withVelocity(7.0f)
                .withAbility(this)
                .withImpactSound("abilities/ice_projectile_impact.ogg")
                .withImpact((c) -> {
                    c.addCondition(caster, c, new Condition(Condition.Type.CHILL, c, 3));
                }).build();

        setCasting(false);
    }

    @Override
    public void setCaster(Creature c) {
        this.caster = c;
        this.setActivated(true);
    }

    @Override
    public void countDown() {
        cooldownTimer++;
        if (cooldownTimer / 60 == cooldownTime) {
            this.setOnCooldown(false);
            this.setActivated(false);
            this.setCasting(false);
            castingTimeTimer = 0;
            cooldownTimer = 0;
        }
    }

}
