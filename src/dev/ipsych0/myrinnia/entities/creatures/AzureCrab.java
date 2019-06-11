package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;

public class AzureCrab extends Creature {

    //Attack timer
    private long lastAttackTimer, attackCooldown = 600, attackTimer = attackCooldown;

    public AzureCrab(float x, float y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        isNpc = false;
        attackable = true;

        // Creature stats
        strength = 0;
        dexterity = 0;
        intelligence = 5;
        vitality = 5;
        defence = 5;
        speed = DEFAULT_SPEED + 0.5f;
        attackSpeed = DEFAULT_ATTACKSPEED;
        maxHealth = (int) (DEFAULT_HEALTH + Math.round(vitality * 1.5));
        health = maxHealth;
        combatLevel = 2;

        double exponent = 1.1;
        for (int i = 1; i < combatLevel; i++) {
            baseDamage = (int) Math.ceil((baseDamage * exponent) + 1);
            exponent *= LEVEL_EXPONENT;
        }
        attackRange = Tile.TILEWIDTH * 6;

        bounds.x = 2;
        bounds.y = 2;
        bounds.width = 28;
        bounds.height = 28;

        // Animations
        aDown = new Animation(333, Assets.blueCrabDown);
        aUp = new Animation(333, Assets.blueCrabUp);
        aLeft = new Animation(333, Assets.blueCrabLeft);
        aRight = new Animation(333, Assets.blueCrabRight);
        aDefault = aDown;

        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);

        map = new AStarMap(this, xSpawn - pathFindRadiusX, ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2, xSpawn, ySpawn);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    protected void die() {

    }

    /*
     * Checks the attack timers before the next attack
     */
    public void checkAttacks() {
        // Attack timers
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown)
            return;

//		// Set attack-box
//		Rectangle cb = getCollisionBounds(0,0);
//		Rectangle ar = new Rectangle();
//		int arSize = Creature.DEFAULT_CREATURE_HEIGHT;
//		ar.width = arSize;
//		ar.height = arSize;

        attackTimer = 0;

        Handler.get().playEffect("abilities/fireball.wav");
        projectiles.add(new Projectile(x, y, (int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY(), 9.0f, Assets.waterProjectile));

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new AzureCrab(xSpawn, ySpawn));
    }

    @Override
    protected void updateDialogue() {

    }
}
