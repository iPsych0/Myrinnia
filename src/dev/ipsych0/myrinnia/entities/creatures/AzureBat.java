package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;

import java.awt.*;

public class AzureBat extends Creature {

    //Attack timer
    private long lastAttackTimer, attackCooldown = 1200, attackTimer = attackCooldown;
    private static boolean tipShown = false;

    public AzureBat(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);

        isNpc = false;
        attackable = true;

        // Creature stats
        strength = 0;
        dexterity = 0;
        intelligence = 7;
        vitality = 16;
        defence = 8;
        maxHealth = DEFAULT_HEALTH + vitality * 4;
        health = maxHealth;
        attackRange = Tile.TILEWIDTH * 5;

        bounds.x = 2;
        bounds.y = 2;
        bounds.width = 28;
        bounds.height = 28;

        // Animations
        aDown = new Animation(333, Assets.azureBatDown);
        aUp = new Animation(333, Assets.azureBatUp);
        aLeft = new Animation(333, Assets.azureBatLeft);
        aRight = new Animation(333, Assets.azureBatRight);
        aDefault = aDown;

        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);

        map = new AStarMap(this, xSpawn - pathFindRadiusX, ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);

        if (!tipShown) {
            tipShown = true;
            Handler.get().addTip(new TutorialTip("You can lock in an enemy's or object's hover info by right-clicking on it."));
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
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

        Handler.get().playEffect("abilities/magic_strike.ogg");
        new Projectile.Builder(DamageType.INT, Assets.waterProjectile, this, (int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY()).build();

    }

    @Override
    protected void die() {
        Handler.get().getSkill(SkillsList.COMBAT).addExperience(10);
        getDroptableItem();
    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new AzureBat(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, direction));
    }

    @Override
    protected void updateDialogue() {

    }
}
