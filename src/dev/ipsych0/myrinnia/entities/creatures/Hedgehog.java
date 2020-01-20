package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Hedgehog extends Creature {


    /**
     *
     */
    private static final long serialVersionUID = 917078714756242679L;

    //Attack timer
    private long lastAttackTimer, attackCooldown = 1000, attackTimer = attackCooldown;
    private Animation rollingAnimation;
    private boolean rolling;
    private int chargeTime = 60 * 2; // 2 seconds
    private int chargeTimer;
    private int rollChance = 3, diceRolls;
    private double rollSpeed = 4.0;
    private double xVelocity, yVelocity;
    private static final int RADIUS = 256;
    private double angle, rotation;

    public Hedgehog(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        isNpc = false;
        attackable = true;

        // Creature stats
        strength = 15;
        dexterity = 12;
        intelligence = 0;
        vitality = 36;
        defence = 40;
        maxHealth = DEFAULT_HEALTH + vitality * 4;
        health = maxHealth;
        attackRange = Tile.TILEWIDTH * 5;


        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);

        map = new AStarMap(this, xSpawn - pathFindRadiusX, ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);
    }


    public void tick() {
        super.tick();
        if (health <= 0) {
            return;
        }

        if (rolling) {
            // Update position based on direction
            double ty = (y + yVelocity + bounds.y + (bounds.height / 2d)) / Tile.TILEHEIGHT;
            double tx = (x + xVelocity + bounds.x + (bounds.width / 2d)) / Tile.TILEWIDTH;
            if (collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, (int) ty, true) ||
                    collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, (int) ty, true) ||
                    collisionWithTile((int) tx, (int) (y + bounds.y) / Tile.TILEHEIGHT, false) ||
                    collisionWithTile((int) tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT, false)) {
                stopRolling();
                return;
            }

            // If out of range, remove this projectile
            if (x > x + RADIUS || x < x + -RADIUS || y > y + RADIUS || y < y + -RADIUS) {
                stopRolling();
            }

            for (Entity e : Handler.get().getWorld().getEntityManager().getEntities()) {
                if (getCollisionBounds(xVelocity, yVelocity).intersects(e.getCollisionBounds(0, 0))) {
                    if (e.equals(Handler.get().getPlayer())) {
                        e.damage(DamageType.STR, this, e);
                        stopRolling();
                        return;
                    }
                }
            }

            x += xVelocity;
            y += yVelocity;
        }

    }

    private void stopRolling() {
        rolling = false;
        rollingAnimation = null;
    }

    @Override
    public void render(Graphics2D g) {
        if (rollingAnimation != null) {
            rollingAnimation.tick();

            AffineTransform old = g.getTransform();
            g.rotate(Math.toRadians(rotation), (int) (x + width / 2 - Handler.get().getGameCamera().getxOffset()), (int) (y + height / 2 - Handler.get().getGameCamera().getyOffset()));
            g.drawImage(rollingAnimation.getCurrentFrame(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
            g.setTransform(old);

        } else {
            g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                    , width, height, null);
        }
    }

    @Override
    public void die() {
        getDroptableItem();
        Handler.get().getSkill(SkillsList.COMBAT).addExperience(20);
    }

    /*
     * Checks the attack timers before the next attack
     */
    protected void checkAttacks() {
        // Attack timers
        if (rolling) {
            return;
        }

        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown)
            return;

        attackTimer = 0;

        diceRolls++;
        if (diceRolls >= rollChance) {
            rolling = true;
            rollingAnimation = new Animation(25, Assets.hedgeHogRoll);

            // Get the angle towards the player
            angle = Math.atan2(Handler.get().getPlayer().getY() - y, Handler.get().getPlayer().getX() - x);

            xVelocity = rollSpeed * Math.cos(angle);
            yVelocity = rollSpeed * Math.sin(angle);

            // Set the rotation of the projectile in degrees (0 = RIGHT, 270 = UP, 180 = LEFT, 90 = DOWN)
            rotation = Math.toDegrees(angle);
            if (rotation < 0) {
                rotation += 360d;
            }

            diceRolls = 0;
        }

        if (!rolling) {
            Handler.get().playEffect("abilities/ranged_shot.ogg");
            new Projectile.Builder(DamageType.DEX, Assets.regularArrow, this, (int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY()).build();
        }

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Hedgehog(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, direction));
    }

    @Override
    protected void updateDialogue() {

    }

    @Override
    protected void combatStateManager() {
        if (!rolling) {
            super.combatStateManager();
        }
    }
}
