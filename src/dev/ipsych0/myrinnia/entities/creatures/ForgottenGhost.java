package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.skills.SkillsList;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class ForgottenGhost extends Creature {


    /**
     *
     */
    private static final long serialVersionUID = 917078714756242679L;

    //Attack timer
    private long lastAttackTimer, attackCooldown = 900, attackTimer = attackCooldown;
    private Animation meleeAnimation;
    private Animation bluntImpact;

    public ForgottenGhost(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        isNpc = false;
        attackable = true;

        // Creature stats
        strength = 10;
        dexterity = 0;
        intelligence = 20;
        vitality = 96;
        defence = 48;
        speed = 1.5;

        bounds.x = 2;
        bounds.y = 20;
        bounds.width = 28;
        bounds.height = 36;

        maxHealth = DEFAULT_HEALTH + vitality * 4;
        health = maxHealth;

        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);

        map = new AStarMap(this, xSpawn - pathFindRadiusX, ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);

        if (meleeAnimation != null) {
            if (meleeAnimation.isTickDone()) {
                meleeAnimation = null;
            } else {
                meleeAnimation.tick();

                AffineTransform old = g.getTransform();
                g.rotate(Math.toRadians(meleeDirection), (int) (x + meleeXOffset + width / 2 - Handler.get().getGameCamera().getxOffset()), (int) (y + meleeYOffset + height / 2 - Handler.get().getGameCamera().getyOffset()));
                g.drawImage(meleeAnimation.getCurrentFrame(), (int) (x + meleeXOffset - Handler.get().getGameCamera().getxOffset()),
                        (int) (y + meleeYOffset - Handler.get().getGameCamera().getyOffset()), (int) (width * 1.25f), (int) (height * 1.25f), null);
                g.setTransform(old);
            }
        }

        if (bluntImpact != null) {
            if (bluntImpact.isTickDone()) {
                bluntImpact = null;
            } else {
                bluntImpact.tick();
                g.drawImage(bluntImpact.getCurrentFrame(), (int) (x + meleeXOffset - Handler.get().getGameCamera().getxOffset()),
                        (int) (y + meleeYOffset - Handler.get().getGameCamera().getyOffset()), width, height, null);
            }
        }
    }

    @Override
    public void die() {
        getDroptableItem();
        Handler.get().getSkill(SkillsList.COMBAT).addExperience(30);
    }

    /*
     * Checks the attack timers before the next attack
     */
    protected void checkAttacks() {
        // Attack timers
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown)
            return;

        attackTimer = 0;

        meleeAnimation = new Animation(32, Assets.meleeBlunt, true);
        bluntImpact = new Animation(32, Assets.meleeBluntImpact, true);

        setMeleeSwing(new Rectangle((int) Handler.get().getPlayer().getX() + 16, (int) Handler.get().getPlayer().getY() + 16, 1, 1));

        Handler.get().playEffect("abilities/impact_blunt.ogg");

        checkMeleeHitboxes();

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new ForgottenGhost(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, direction));
    }

    @Override
    protected void updateDialogue() {

    }
}
