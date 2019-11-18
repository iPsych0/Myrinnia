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
    private long lastAttackTimer, attackCooldown = 600, attackTimer = attackCooldown;

    public AngryOwl(float x, float y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        isNpc = false;
        attackable = true;

        // Creature stats
        strength = 0;
        dexterity = 0;
        intelligence = 5;
        vitality = 5;
        defence = 5;
        speed = DEFAULT_SPEED + 1.0f;
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
        // Drop table stuff
        int randomNumber = Handler.get().getRandomNumber(1, 50);
        System.out.println("Rolled " + randomNumber + " on the RNG dice.");

        if (randomNumber <= 10) {
            Handler.get().dropItem(Item.regularLogs, 5, (int) x, (int) y);
        } else if (randomNumber >= 11 && randomNumber <= 50) {
            Handler.get().dropItem(Item.regularOre, 10, (int) x, (int) y);
            Handler.get().dropItem(Item.purpleSword, 1, (int) x, (int) y);
        }
        Handler.get().dropItem(Item.coins, 50, (int) x, (int) y);
        Handler.get().dropItem(Item.testAxe, 1, (int) x, (int) y);
        Handler.get().dropItem(Item.testPickaxe, 1, (int) x, (int) y);

//		FOR INSTA NEXT LEVEL: Handler.get().getSkill(SkillsList.COMBAT).getNextLevelXp()
        Handler.get().getSkill(SkillsList.COMBAT).addExperience(25);
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

        Handler.get().playEffect("abilities/fireball.wav");
        projectiles.add(new Projectile(x, y, (int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY(), 9.0f, Assets.waterProjectile));

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new AngryOwl(xSpawn, ySpawn));
    }

    @Override
    protected void updateDialogue() {

    }

    @Override
    public String getName() {
        return "Angry Owl";
    }
}
