package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;

public class AquaticCultist extends Creature {

    //Attack timer
    private long lastAttackTimer, attackCooldown = 600, attackTimer = attackCooldown;

    public AquaticCultist(float x, float y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        isNpc = false;
        attackable = true;

        // Animations
        aDown = new Animation(250, Assets.aquatic_cultist_down);
        aUp = new Animation(250, Assets.aquatic_cultist_up);
        aLeft = new Animation(250, Assets.aquatic_cultist_left);
        aRight = new Animation(250, Assets.aquatic_cultist_right);
        aDefault = aDown;

        // Creature stats
        strength = 0;
        dexterity = 0;
        intelligence = 10;
        vitality = 10;
        defence = 10;
        speed = DEFAULT_SPEED + 0.5f;
        attackSpeed = DEFAULT_ATTACKSPEED;
        maxHealth = (int) (180 + Math.round(vitality * 1.5));
        health = maxHealth;
        combatLevel = 5;

        double exponent = 1.1;
        for (int i = 1; i < combatLevel; i++) {
            baseDamage = (int) Math.ceil((baseDamage * exponent) + 1);
            exponent *= LEVEL_EXPONENT;
        }
        attackRange = Tile.TILEWIDTH * 6;

        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 32;
        bounds.height = 32;

        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);

        map = new AStarMap(this, xSpawn - pathFindRadiusX, ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2, xSpawn, ySpawn);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - 16 - Handler.get().getGameCamera().getyOffset()), null);
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
        Handler.get().getWorld().getEntityManager().addEntity(new AquaticCultist(xSpawn, ySpawn));
    }

    @Override
    protected void updateDialogue() {

    }

    @Override
    public String getName() {
        return "Aquatic Cultist";
    }
}
