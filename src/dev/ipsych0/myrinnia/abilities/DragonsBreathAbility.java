package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.abilities.data.ConeArea;
import dev.ipsych0.myrinnia.abilities.data.MeleeDirection;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.List;


public class DragonsBreathAbility extends Ability implements Serializable {

    private Animation animation;
    private ConeArea cone;
    private double rotation, xPos, yPos;

    public DragonsBreathAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                                double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void setCaster(Creature c) {
        this.caster = c;
        this.setActivated(true);
    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.dragonsBreathI, x, y, null);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        if (animation != null && !animation.isTickDone()) {
            animation.tick();
            AffineTransform old = g.getTransform();
            g.rotate(Math.toRadians(rotation), (int) (caster.getX() + xPos + 32 / 2 - Handler.get().getGameCamera().getxOffset()), (int) (caster.getY() + yPos + 32 / 2 - Handler.get().getGameCamera().getyOffset()));
            g.drawImage(animation.getCurrentFrame(),
                    (int) (caster.getX() + caster.getWidth() / 2 - 40 + xPos - Handler.get().getGameCamera().getxOffset()),
                    (int) (caster.getY() + caster.getHeight() / 2 - 80 + yPos - Handler.get().getGameCamera().getyOffset()), null);
            g.setTransform(old);
        } else if (animation != null && animation.isTickDone()) {
            animation = null;
        }
    }

    @Override
    public void renderUnderEntity(Graphics2D g) {
        if (isSelected() && caster.equals(Handler.get().getPlayer())) {
            cone = new ConeArea(caster, 90, 135);
            cone.render(g);
        }
    }

    @Override
    public void cast() {
        if(cone == null)
            cone = new ConeArea(caster, 90, 135);
        setDirection();
        // TODO: Add sound effect
        animation = new Animation(375 / Assets.dragonsBreath.length, Assets.dragonsBreath, true);
        List<Entity> entities = getAllEntitiesInShape(cone.getArea());
        if (!entities.isEmpty()) {
            for (Entity e : entities) {
                e.damage(DamageType.INT, caster, this);
            }
        }
        setCasting(false);
    }

    private void setDirection() {
        Rectangle direction;
        if (caster.equals(Handler.get().getPlayer())) {
            direction = Handler.get().getMouse();
        } else {
            direction = new Rectangle((int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY(), 1, 1);
        }

        MeleeDirection meleeDirection = getMeleeSwing(direction);
        this.rotation = meleeDirection.getRotation();
        this.rotation += 90.0;
        if (rotation > 360.0)
            rotation -= 360;
        this.xPos = meleeDirection.getxPos();
        this.yPos = meleeDirection.getyPos();
    }

    @Override
    void reset() {
        cone = null;
    }

}
