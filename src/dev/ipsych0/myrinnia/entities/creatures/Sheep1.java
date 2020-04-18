package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.utils.Timer;
import dev.ipsych0.myrinnia.utils.TimerHandler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.concurrent.TimeUnit;

public class Sheep1 extends Creature {


    /**
     *
     */
    private static final long serialVersionUID = 917078714756242679L;

    //Attack timer
    private long lastAttackTimer, attackCooldown = 1200, attackTimer = attackCooldown;
    private Animation meleeAnimation;
    private Animation originalDown, originalLeft, originalRight, originalUp;
    private boolean sheared;

    public Sheep1(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        isNpc = true;
        attackable = true;

        originalDown = aDown;
        originalLeft = aLeft;
        originalRight = aRight;
        originalUp = aUp;

        // Creature stats
        strength = 10;
        dexterity = 0;
        intelligence = 0;
        vitality = 25;
        defence = 10;
        speed = 1.0f;

        maxHealth = DEFAULT_HEALTH + vitality * 4;
        health = maxHealth;

        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);

        map = new AStarMap(this, xSpawn - pathFindRadiusX, ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);

        if (meleeAnimation != null) {
            if (meleeAnimation.isTickDone()) {
                meleeAnimation = null;
            } else {
                meleeAnimation.tick();

                AffineTransform old = g.getTransform();
                g.rotate(Math.toRadians(meleeDirection), (int) (x + meleeXOffset + width / 2 - Handler.get().getGameCamera().getxOffset()), (int) (y + meleeYOffset + height / 2 - Handler.get().getGameCamera().getyOffset()));
                g.drawImage(meleeAnimation.getCurrentFrame(), (int) (x + meleeXOffset - Handler.get().getGameCamera().getxOffset()),
                        (int) (y + meleeYOffset - Handler.get().getGameCamera().getyOffset()), (int) (width * 1.25f), (int) (height * 1.25f), null);
                g.setTransform(old);
            }
        }
    }

    @Override
    public void die() {
        if (!sheared) {
            Handler.get().dropItem(Item.wool, 1, (int) x, (int) y);
        }
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

        meleeAnimation = new Animation(48, Assets.regularMelee, true, false);

        setMeleeSwing(new Rectangle((int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY(), 1, 1));

        Handler.get().playEffect("abilities/sword_swing.ogg", -0.05f);

        checkMeleeHitboxes();

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Sheep1(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, direction));
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        if ("hasShears".equals(condition)) {
            return !sheared && Handler.get().playerHasItem(Item.shears, 1);
        }
        System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
        return false;
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 2:
                sheared = true;

                // Change animation to shaved version
                aDown = new Animation(250, Assets.shavedSheep1Down);
                aLeft = new Animation(250, Assets.shavedSheep1Left);
                aRight = new Animation(250, Assets.shavedSheep1Right);
                aUp = new Animation(250, Assets.shavedSheep1Up);

                // Give wool
                Handler.get().giveItem(Item.wool, 1);
                speakingTurn = -1;

                // Set timer to revert to 'unshaved' form after a minute
                TimerHandler.get().addTimer(new Timer(1L, TimeUnit.MINUTES, () -> {
                    this.sheared = false;

                    aDown = originalDown;
                    aLeft = originalLeft;
                    aRight = originalRight;
                    aUp = originalUp;
                }));
                break;
        }
    }
}
