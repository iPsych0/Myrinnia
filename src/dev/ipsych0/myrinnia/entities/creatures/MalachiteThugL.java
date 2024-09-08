package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class MalachiteThugL extends Creature {


    /**
     *
     */
    private static final long serialVersionUID = 917078714756242679L;

    //Attack timer
    private long lastAttackTimer, attackCooldown = 1200, attackTimer = attackCooldown;
    private Animation meleeAnimation;
    private boolean hasSpawnedFirstAlly, hasSpawnedSecondAlly;
    private int firstTextTimer, secondTextTimer;
    private Ability debilitatingStrike = Utils.loadAbility("debilitatingstrike.json");

    public MalachiteThugL(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        isNpc = false;
        attackable = true;

        // Creature stats
        strength = 15;
        dexterity = 0;
        intelligence = 0;
        vitality = 45;
        defence = 30;
        maxHealth = DEFAULT_HEALTH + vitality * 4;
        health = maxHealth;

        bounds.x = 2;
        bounds.y = 2;
        bounds.width = 28;
        bounds.height = 28;

        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);

        map = new AStarMap(this, xSpawn - pathFindRadiusX, ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);
    }

    @Override
    public void tick() {
        super.tick();

        // If below 75% HP, spawn first ally
        if (health <= (maxHealth * 0.75d) && !hasSpawnedFirstAlly) {
            Handler.get().getWorld().getEntityManager().addRuntimeEntity(new MalachiteThugR(53 * 32, 17 * 32, width, height, "Devon's associate", 3, null, null, "malachiteThug2", null, null));
            hasSpawnedFirstAlly = true;
            // If below 40% health, spawn second ally
        } else if (health <= (maxHealth * 0.40d) && !hasSpawnedSecondAlly) {
            Handler.get().getWorld().getEntityManager().addRuntimeEntity(new MalachiteThugR(48 * 32, 23 * 32, width, height, "Devon's associate", 3, null, null, "malachiteThug2", null, null));
            hasSpawnedSecondAlly = true;
        }
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

        if (hasSpawnedFirstAlly && firstTextTimer <= 120) {
            firstTextTimer++;
            Text.drawString(g, "My associate will deal with you!",
                    (int) (x + width / 2 - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - 16 - Handler.get().getGameCamera().getyOffset()),
                    true, Color.YELLOW, Assets.font14);
        } else if (hasSpawnedSecondAlly && secondTextTimer <= 120) {
            secondTextTimer++;
            Text.drawString(g, "Argh, get her!",
                    (int) (x + width / 2 - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - 16 - Handler.get().getGameCamera().getyOffset()),
                    true, Color.YELLOW, Assets.font14);
        }
    }

    @Override
    public void die() {
        Handler.get().getSkill(SkillsList.COMBAT).addExperience(40);
        Handler.get().dropItem(Item.miningEquipment, 1, (int) x, (int) y);
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

        if (!debilitatingStrike.isOnCooldown()) {
            castAbility(debilitatingStrike);
        } else {
            meleeAnimation = new Animation(48, Assets.regularMelee, true, false);

            setMeleeSwing(new Rectangle((int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY(), 1, 1));

            Handler.get().playEffect("abilities/sword_swing.ogg", -0.05f);

            checkMeleeHitboxes();
        }

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {

    }
}
