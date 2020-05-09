package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.abilities.data.AoECircle;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;
import java.util.List;


public class AcidBombAbility extends Ability implements Serializable {

    private Animation animation;
    private boolean initialized;
    private static Color aoeColor = new Color(0, 192, 0, 96);
    private AoECircle circle;
    private static final int AOE_SIZE = 96;

    public AcidBombAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                           double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.acidBombI, x, y, null);
    }

    @Override
    public void setCaster(Creature c) {
        this.caster = c;
        this.setActivated(true);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        if (animation != null) {
            if (animation.isTickDone()) {
                animation = null;
            } else {
                animation.tick();
                g.drawImage(animation.getCurrentFrame(), (int) (circle.getX() - Handler.get().getGameCamera().getxOffset()),
                        (int) (circle.getY() - Handler.get().getGameCamera().getyOffset()), AOE_SIZE, AOE_SIZE, null);
            }
        }
    }

    @Override
    public void renderUnderEntity(Graphics2D g) {
        if (isSelected() && caster.equals(Handler.get().getPlayer())) {
            circle = new AoECircle((float) Handler.get().getMouse().x - AOE_SIZE / 2f, (float) Handler.get().getMouse().y - AOE_SIZE / 2f, AOE_SIZE, AOE_SIZE);
            g.setColor(aoeColor);
            g.fill(circle);
        }
    }

    @Override
    public void cast() {
        Player player = Handler.get().getPlayer();

        if (!initialized) {
            initialized = true;
            if (caster.equals(player)) {
                circle.x += (float) Handler.get().getGameCamera().getxOffset();
                circle.y += (float) Handler.get().getGameCamera().getyOffset();
            } else {
                circle.x = (float) player.getX() + player.getWidth() / 2f - AOE_SIZE / 2f + (float) Handler.get().getGameCamera().getxOffset();
                circle.y = (float) player.getY() + player.getHeight() / 2f - AOE_SIZE / 2f + (float) Handler.get().getGameCamera().getyOffset();
            }
            animation = new Animation(1000 / 8, Assets.acidBomb, true);
        }

        List<Entity> entities = getAllEntitiesInShape(circle);
        if (!entities.isEmpty()) {
            for (Entity e : entities) {
                e.damage(DamageType.INT, caster, this);
                e.addCondition(caster, new Condition(Condition.Type.POISON, 3, 3));
            }
        }

        Handler.get().playEffect("abilities/acid_bomb.ogg", 0.1f);

        setCasting(false);
    }

    @Override
    protected void countDown() {
        cooldownTimer++;
        if (cooldownTimer / 60 == cooldownTime) {
            this.setOnCooldown(false);
            this.setActivated(false);
            this.setCasting(false);
            castingTimeTimer = 0;
            cooldownTimer = 0;
            initialized = false;
            circle = null;
            animation = null;
        }
    }

}
