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
        strength =  0;
        dexterity = 0;
        intelligence = 1;
        vitality = 5;
        defence = 5;
        maxHealth = DEFAULT_HEALTH + vitality * 2;
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
            Handler.get().addTip(new TutorialTip("Aim with with your mouse and left-click to attack."));
        }

        if (name.equalsIgnoreCase("King Azure Crab")) {
            intelligence += 3;
            defence += 5;
            vitality += 5;
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
            Handler.get().getSkill(SkillsList.COMBAT).addExperience(25);
        } else {
            if (firstKill) {
                Handler.get().addTip(new TutorialTip("Right-click when standing on items to pick them up."));
                firstKill = false;
            }
            getDroptableItem();
            Handler.get().getSkill(SkillsList.COMBAT).addExperience(10);
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

        Handler.get().playEffect("abilities/magic_strike.ogg");
        new Projectile.Builder(DamageType.INT, Assets.waterProjectile, this, (int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY()).build();

    }

    @Override
    public void respawn() {
        if (name.equalsIgnoreCase("King Azure Crab")) {
            name = "Crabling";
            width = 32;
            height = 32;
            combatLevel = 2;
            dropTable = "azure_crab.json";
            setCombatLevel();
        }
        Handler.get().getWorld().getEntityManager().addEntity(new AzureCrab(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, direction));
    }

    @Override
    protected void updateDialogue() {

    }
}
