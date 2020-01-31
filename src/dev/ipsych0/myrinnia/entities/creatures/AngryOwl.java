package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;

public class AngryOwl extends Creature {


    /**
     *
     */
    private static final long serialVersionUID = 917078714756242679L;

    //Attack timer
    private long lastAttackTimer, attackCooldown = 1200, attackTimer = attackCooldown;

    public AngryOwl(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        isNpc = false;
        attackable = true;

        // Creature stats
        strength =  0;
        dexterity = 0;
        intelligence = 5;
        vitality = 8;
        defence = 5;
        maxHealth = DEFAULT_HEALTH + vitality * 4;
        health = maxHealth;
        attackRange = Tile.TILEWIDTH * 5;

        bounds.x = 2;
        bounds.y = 2;
        bounds.width = 28;
        bounds.height = 28;

        // Animations
        aDown = new Animation(666, Assets.angryOwl);
        aUp = new Animation(666, Assets.angryOwl);
        aLeft = new Animation(666, Assets.angryOwl);
        aRight = new Animation(666, Assets.angryOwl);
        aDefault = aDown;

        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);

        map = new AStarMap(this, xSpawn - pathFindRadiusX, ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);

        // Draw HP above head
//		Text.drawString(g, Integer.toString(getHealth()) + "/" + maxHealth, (int) (x - Handler.get().getGameCamera().getxOffset() - 6),
//				(int) (y - Handler.get().getGameCamera().getyOffset() - 8), false, Color.YELLOW, Creature.hpFont);


    }

    @Override
    public void die() {
        Handler.get().getSkill(SkillsList.COMBAT).addExperience(10);
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

//		// Set attack-box
//		Rectangle cb = getCollisionBounds(0,0);
//		Rectangle ar = new Rectangle();
//		int arSize = Creature.DEFAULT_CREATURE_HEIGHT;
//		ar.width = arSize;
//		ar.height = arSize;

        attackTimer = 0;

        Handler.get().playEffect("abilities/magic_strike.ogg");
        new Projectile.Builder(DamageType.INT, Assets.earthProjectile, this, (int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY()).build();

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new AngryOwl(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, direction));
    }

    @Override
    protected void updateDialogue() {

    }
}
