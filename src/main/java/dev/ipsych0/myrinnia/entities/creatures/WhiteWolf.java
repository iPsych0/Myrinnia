package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.skills.SkillsList;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class WhiteWolf extends Creature {


    /**
     *
     */
    private static final long serialVersionUID = 917078714756242679L;

    //Attack timer
    private long lastAttackTimer, attackCooldown = 1000, attackTimer = attackCooldown;
    private Animation biteImpact;

    public WhiteWolf(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        isNpc = false;
        attackable = true;

        // Creature stats
        strength = 42;
        dexterity = 0;
        intelligence = 0;
        vitality = 88;
        defence = 15;

        bounds.x = 24;
        bounds.y = 24;
        bounds.width = 24;
        bounds.height = 24;

        if (name.equalsIgnoreCase("Alpha Wolf")) {
            strength = 52;
            vitality = 108;
            defence = 35;
            bounds.x = 32;
            bounds.y = 32;
            bounds.width = 32;
            bounds.height = 40;
        }

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

//        // Uncomment to see hitbox
//        double angle = getAngle();
//        Rectangle ar = new Rectangle((int) (32 * Math.cos(angle) + (int) this.getX() - Handler.get().getGameCamera().getxOffset()), (int) (32 * Math.sin(angle) + (int) this.getY() - Handler.get().getGameCamera().getyOffset()), width, height);
//
//        g.setColor(Color.RED);
//        g.drawRect(ar.x, ar.y, ar.width, ar.height);

        if (biteImpact != null) {
            if (biteImpact.isTickDone()) {
                biteImpact = null;
            } else {
                biteImpact.tick();
                AffineTransform old = g.getTransform();
                g.rotate(Math.toRadians(meleeDirection), (int) (x + meleeXOffset + width / 2 - Handler.get().getGameCamera().getxOffset()), (int) (y + meleeYOffset + height / 2 - Handler.get().getGameCamera().getyOffset()));
                g.drawImage(biteImpact.getCurrentFrame(), (int) (x + meleeXOffset - Handler.get().getGameCamera().getxOffset()),
                        (int) (y + meleeYOffset - Handler.get().getGameCamera().getyOffset()), (int) (width * 0.75), (int) (height * 0.75), null);
                g.setTransform(old);
            }
        }
    }

    @Override
    public void die() {
        int exp = 20;
        if (name.equalsIgnoreCase("Alpha Wolf")) {
            exp = 35;
        }
        Handler.get().getSkill(SkillsList.COMBAT).addExperience(exp);
        getDroptableItem();
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

        biteImpact = new Animation(18, Assets.monsterBite, true, true);

        setMeleeSwing(new Rectangle((int) Handler.get().getPlayer().getX() + 16, (int) Handler.get().getPlayer().getY() + 16, 1, 1));

        Handler.get().playEffect("abilities/wolf_attack.ogg", 0.1f);

        checkMeleeHitboxes();

    }

    @Override
    public void respawn() {
        // When we've killed the alpha wolf, respawn a normal white wolf in its place.
        if (name.equalsIgnoreCase("Alpha Wolf")) {
            name = "White Wolf";
            width = 64;
            height = 64;
            combatLevel = 13;
            strength = 42;
            vitality = 88;
            defence = 15;
            bounds.x = 24;
            bounds.y = 24;
            bounds.width = 24;
            bounds.height = 24;
        }
        Handler.get().getWorld().getEntityManager().addEntity(new WhiteWolf(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, lastFaced));
    }

    @Override
    protected void updateDialogue() {

    }
}
