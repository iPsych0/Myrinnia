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

    public GlacialShotAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                       double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        g.drawImage(Assets.glacialShotI, x, y, null);
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

        Handler.get().playEffect("abilities/glacial_shot.wav", 0.1f);
        if (Handler.get().getMouseManager().isLeftPressed() || Handler.get().getMouseManager().isDragged()) {

            int targetX = (int) (mouse.getX() + Handler.get().getGameCamera().getxOffset() - 16);
            int targetY = (int) (mouse.getY() + Handler.get().getGameCamera().getyOffset() - 16);

            new Projectile.Builder(DamageType.DEX, animation, caster, targetX, targetY)
                    .withImpactSound("abilities/ice_projectile_impact.wav")
                    .withAbility(this)
                    .withVelocity(7.0f)
                    .withImpact((Serializable & OnImpact) (receiver) ->
                            receiver.addCondition(Handler.get().getPlayer(), receiver, new Condition(Condition.Type.CHILL, receiver, 3))).build();
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
