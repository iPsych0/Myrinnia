package dev.ipsych0.myrinnia.abilities;

import java.awt.*;
import java.io.Serializable;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.entities.creatures.Projectile;
import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.gfx.Assets;


public class PoisonDartAbility extends Ability implements Serializable {

    private float velocity = 8.0f;
    private int duration = 5, condiDamage = 5;

    public PoisonDartAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                             double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.poisonDartI, x, y, null);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        // Render the ability animation
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

        Handler.get().playEffect("abilities/ranged_shot.ogg", 0.2f);
        new Projectile.Builder(DamageType.DEX, Assets.poisonDart, caster, targetX, targetY)
                .withVelocity(velocity)
                .withAbility(this)
                .withImpactSound("abilities/ranged_shot_impact.ogg")
                .withImpact((c) -> {
                    c.addCondition(caster, new Condition(Condition.Type.POISON, duration, condiDamage));
                }).build();

        setCasting(false);
    }

    @Override
    public void setCaster(Creature c) {
        this.caster = c;
        this.setActivated(true);
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCondiDamage() {
        return condiDamage;
    }

    public void setCondiDamage(int condiDamage) {
        this.condiDamage = condiDamage;
    }
}
