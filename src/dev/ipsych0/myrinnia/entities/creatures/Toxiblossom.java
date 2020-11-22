package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.PoisonDartAbility;
import dev.ipsych0.myrinnia.abilities.SepticBlastAbility;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;

public class Toxiblossom extends Creature {


    /**
     *
     */
    private static final long serialVersionUID = 917078714756242679L;

    //Attack timer
    private long lastAttackTimer, attackCooldown = 1200, attackTimer = attackCooldown;
    private SepticBlastAbility septicBlastAbility = Utils.loadAbility("septicblast.json", SepticBlastAbility.class);

    public Toxiblossom(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        isNpc = false;
        attackable = true;

        // Creature stats
        strength = 25;
        dexterity = 31;
        intelligence = 0;
        vitality = 88;
        defence = 58;
        maxHealth = DEFAULT_HEALTH + vitality * 4;
        health = maxHealth;
        attackRange = Tile.TILEWIDTH * 5;

        earthLevel = 6;

        bounds.x = 2;
        bounds.y = 2;
        bounds.width = 28;
        bounds.height = 28;

        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);

        map = new AStarMap(this, xSpawn - pathFindRadiusX, ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);
    }

    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void die() {
        Handler.get().getSkill(SkillsList.COMBAT).addExperience(30);
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

        // If player is in melee range, cast septic blast.
        if (!septicBlastAbility.isOnCooldown() && distanceToEntity(this, Handler.get().getPlayer()) <= Tile.TILEWIDTH * 2) {
            castAbility(septicBlastAbility);
        } else {
            Handler.get().playEffect("abilities/magic_strike.ogg");
            int targetX = (int) Handler.get().getPlayer().getX();
            int targetY = (int) Handler.get().getPlayer().getY();

            new Projectile.Builder(DamageType.DEX, Assets.earthProjectile, this, targetX, targetY)
                    .withImpactSound("abilities/magic_strike_impact.ogg").build();
        }

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Toxiblossom(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, direction));
    }

    @Override
    protected void updateDialogue() {

    }
}
