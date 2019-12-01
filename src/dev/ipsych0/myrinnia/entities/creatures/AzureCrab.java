package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.skills.ui.Bounty;
import dev.ipsych0.myrinnia.skills.ui.BountyManager;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;

public class AzureCrab extends Creature {

    //Attack timer
    private long lastAttackTimer, attackCooldown = 1200, attackTimer = attackCooldown;
    private static boolean firstKill = true;
    private static boolean hasFoughtBefore;

    public AzureCrab(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);

        isNpc = false;
        attackable = true;

        // Creature stats
        strength += 0;
        dexterity += 0;
        intelligence += 2;
        vitality += 5;
        defence += 5;
        maxHealth = (int) (DEFAULT_HEALTH + Math.round(vitality * 1.5));
        health = maxHealth;
        attackRange = Tile.TILEWIDTH * 5;

//        bounds.x = 2;
//        bounds.y = 2;
//        bounds.width = 28;
//        bounds.height = 28;

        // Animations
        aDown = new Animation(333, Assets.blueCrabDown);
        aUp = new Animation(333, Assets.blueCrabUp);
        aLeft = new Animation(333, Assets.blueCrabLeft);
        aRight = new Animation(333, Assets.blueCrabRight);
        aDefault = aDown;

        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);

        map = new AStarMap(this, xSpawn - pathFindRadiusX, ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);

        if (!name.equalsIgnoreCase("King Azure Crab") && !hasFoughtBefore) {
            hasFoughtBefore = true;
            Handler.get().addTip(new TutorialTip("Left-click and aim with your mouse to attack."));
        }

        if (name.equalsIgnoreCase("King Azure Crab")) {
            intelligence += 2;
            vitality += 2;
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    protected void die() {
        Bounty bounty = BountyManager.get().getBountyByZoneAndTask(Zone.PortAzure, "Cut the Crab");
        if (name.equalsIgnoreCase("King Azure Crab") && bounty != null && bounty.isAccepted()) {
            if (firstKill) {
                Handler.get().addTip(new TutorialTip("Right-click when standing on items to pick them up."));
                firstKill = false;
            }
            Handler.get().dropItem(Item.ryansAxe, 1, (int) x, (int) y);
            Handler.get().getSkill(SkillsList.COMBAT).addExperience(50);
        } else {
            if (firstKill) {
                Handler.get().addTip(new TutorialTip("Right-click when standing on items to pick them up."));
                firstKill = false;
            }
            Handler.get().dropItem(Item.coins, Handler.get().getRandomNumber(1, 5), (int) x, (int) y);
            Handler.get().getSkill(SkillsList.COMBAT).addExperience(20);
        }
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

        Handler.get().playEffect("abilities/magic_strike.wav");
        projectiles.add(new Projectile(x, y, (int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY(), 6.0f, "abilities/magic_strike_impact.wav", DamageType.INT, Assets.waterProjectile));

    }

    @Override
    public void respawn() {
        if (!name.equalsIgnoreCase("King Azure Crab")) {
            Handler.get().getWorld().getEntityManager().addEntity(new AzureCrab(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, direction));
        }
    }

    @Override
    protected void updateDialogue() {

    }
}
