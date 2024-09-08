package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;

public class GoblinoShaman extends Creature {


    /**
     *
     */
    private static final long serialVersionUID = 917078714756242679L;

    //Attack timer
    private long lastAttackTimer, attackCooldown = 750, attackTimer = attackCooldown;

    public GoblinoShaman(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        isNpc = false;
        attackable = true;

        // Creature stats
        strength = 0;
        dexterity = 0;
        intelligence = 28;
        vitality = 90;
        defence = 25;
        maxHealth = DEFAULT_HEALTH + vitality * 4;
        health = maxHealth;
        attackRange = Tile.TILEWIDTH * 6;
        speed = 1.75;

        bounds.x = 2;
        bounds.y = 20;
        bounds.width = 28;
        bounds.height = 36;

        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);

        map = new AStarMap(this, xSpawn - pathFindRadiusX, ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void die() {
        Handler.get().getSkill(SkillsList.COMBAT).addExperience(30);
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

        Handler.get().playEffect("abilities/magic_strike.ogg");
        new Projectile.Builder(DamageType.INT, Assets.regularMagic, this, (int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY())
                .withVelocity(8.0f)
                .build();

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new GoblinoShaman(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, direction));
    }

    @Override
    protected void updateDialogue() {

    }
}
