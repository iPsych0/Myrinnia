package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.pathfinding.CombatState;
import dev.ipsych0.myrinnia.skills.SkillsList;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class BurrowingBeetle extends Creature {


    /**
     *
     */
    private static final long serialVersionUID = 917078714756242679L;

    //Attack timer
    private long lastAttackTimer, attackCooldown = 1200, attackTimer = attackCooldown;
    private Animation meleeAnimation;
    private Animation diggingAnimation;
    private Animation originalDown, originalLeft, originalRight, originalUp;
    private Animation poofAnimation;
    private Animation bluntImpact;
    private boolean digging;
    private long digTime = 4L;
    private long timeOfDigging;
    private boolean lastResortDigging;
    private static boolean hasDroppedDynamite;

    public BurrowingBeetle(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        isNpc = false;
        attackable = true;

        diggingAnimation = new Animation(250, Assets.burrowMound);

        originalDown = aDown;
        originalLeft = aLeft;
        originalRight = aRight;
        originalUp = aUp;

        // Creature stats
        strength = 8;
        dexterity = 0;
        intelligence = 0;
        if (level == 6) {
            strength = 10;
            vitality = 35;
            defence = 45;
        } else if (level == 7) {
            strength = 14;
            vitality = 45;
            defence = 50;
        } else {
            vitality = 30;
            defence = 40;
        }
        maxHealth = DEFAULT_HEALTH + vitality * 4;
        health = maxHealth;

        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);

        map = new AStarMap(this, xSpawn - pathFindRadiusX, ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);
    }

    @Override
    public void tick() {
        super.tick();
        if (health <= 0) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        if (digging) {
            if (((currentTime - timeOfDigging) / 1000L) >= digTime) {
                digging = false;

                // Reset to normal animations
                aDown = originalDown;
                aLeft = originalLeft;
                aRight = originalRight;
                aUp = originalUp;
                timeOfDigging = 0;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);

//        // Uncomment to see hitbox
//        double angle = getAngle();
//        Rectangle ar = new Rectangle((int) (32 * Math.cos(angle) + (int) this.getX() - Handler.get().getGameCamera().getxOffset()), (int) (32 * Math.sin(angle) + (int) this.getY() - Handler.get().getGameCamera().getyOffset()), width, height);
//
//        g.setColor(Color.RED);
//        g.drawRect(ar.x, ar.y, ar.width, ar.height);

        if (poofAnimation != null) {
            if (poofAnimation.isTickDone()) {
                poofAnimation = null;
            } else {
                poofAnimation.tick();

                g.drawImage(poofAnimation.getCurrentFrame(), (int) (x - Handler.get().getGameCamera().getxOffset()),
                        (int) (y - 8 - Handler.get().getGameCamera().getyOffset()), (int) (width), (int) (height), null);
            }
        }

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

        if (bluntImpact != null) {
            if (bluntImpact.isTickDone()) {
                bluntImpact = null;
            } else {
                bluntImpact.tick();
                g.drawImage(bluntImpact.getCurrentFrame(), (int) (x + meleeXOffset - Handler.get().getGameCamera().getxOffset()),
                        (int) (y + meleeYOffset - Handler.get().getGameCamera().getyOffset()), width, height, null);
            }
        }
    }

    @Override
    public void die() {
        if (combatLevel == 5) {
            Handler.get().getSkill(SkillsList.COMBAT).addExperience(10);
        } else if (combatLevel == 6) {
            Handler.get().getSkill(SkillsList.COMBAT).addExperience(15);
        } else if (combatLevel == 7) {
            Handler.get().getSkill(SkillsList.COMBAT).addExperience(25);
            if (!hasDroppedDynamite) {
                hasDroppedDynamite = true;
                Handler.get().dropItem(Item.dynamite, 1, (int) x, (int) y);
            }
        }
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

        if (!digging) {
            meleeAnimation = new Animation(32, Assets.meleeBlunt, true);
            bluntImpact = new Animation(32, Assets.meleeBluntImpact, true);

            setMeleeSwing(new Rectangle((int) Handler.get().getPlayer().getX() + 16, (int) Handler.get().getPlayer().getY() + 16, 1, 1));

            Handler.get().playEffect("abilities/impact_blunt.ogg");

            checkMeleeHitboxes();
        }

    }

    @Override
    public void damage(DamageType damageType, Entity dealer) {
        super.damage(damageType, dealer);
        if (health <= 0) {
            return;
        }
        int rnd = Handler.get().getRandomNumber(1, 6);
        if (!lastResortDigging && !digging && health <= maxHealth / 4) {
            poofAnimation = new Animation(150, Assets.airCloud1, true);
            Handler.get().playEffect("abilities/nimble_feet.ogg", 0.1f);
            digging = true;
            lastResortDigging = true;
            aLeft = diggingAnimation;
            aDown = diggingAnimation;
            aRight = diggingAnimation;
            aUp = diggingAnimation;
            timeOfDigging = System.currentTimeMillis();
        } else if (rnd == 1) {
            poofAnimation = new Animation(150, Assets.airCloud1, true);
            Handler.get().playEffect("abilities/nimble_feet.ogg", 0.1f);
            digging = true;
            aLeft = diggingAnimation;
            aDown = diggingAnimation;
            aRight = diggingAnimation;
            aUp = diggingAnimation;
            timeOfDigging = System.currentTimeMillis();
        }
    }

    @Override
    public void damage(DamageType damageType, Entity dealer, Ability ability) {
        super.damage(damageType, dealer, ability);
        if (health <= 0) {
            return;
        }
        int rnd = Handler.get().getRandomNumber(1, 6);
        if (!lastResortDigging && !digging && health <= maxHealth / 4) {
            poofAnimation = new Animation(150, Assets.airCloud1, true);
            Handler.get().playEffect("abilities/nimble_feet.ogg", 0.1f);
            digging = true;
            lastResortDigging = true;
            aLeft = diggingAnimation;
            aDown = diggingAnimation;
            aRight = diggingAnimation;
            aUp = diggingAnimation;
            timeOfDigging = System.currentTimeMillis();
        } else if (rnd == 1) {
            poofAnimation = new Animation(150, Assets.airCloud1, true);
            Handler.get().playEffect("abilities/nimble_feet.ogg", 0.1f);
            digging = true;
            aLeft = diggingAnimation;
            aDown = diggingAnimation;
            aRight = diggingAnimation;
            aUp = diggingAnimation;
            timeOfDigging = System.currentTimeMillis();
        }
    }


    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new BurrowingBeetle(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, direction));
    }

    @Override
    protected void updateDialogue() {

    }

    /**
     * Manages the different combat states of a Creature (IDLE, PATHFINDING, ATTACKING, BACKTRACKING)
     */
    protected void combatStateManager() {

        if (digging) {
            randomWalk();
            return;
        }

        if (damaged) {
            state = CombatState.PATHFINDING;
        }

        Player player = Handler.get().getPlayer();

        // If the player is within the A* map AND moves within the aggro range, state = pathfinding (walk towards goal)
        if (player.getCollisionBounds(0, 0).intersects(getRadius()) && player.getCollisionBounds(0, 0).intersects(map.getMapBounds())) {
            state = CombatState.PATHFINDING;
        }

        if (player.getVerticality() != this.verticality) {
            state = CombatState.IDLE;
        }

        // If the Creature was not following or attacking the player, move around randomly.
        if (state == CombatState.IDLE) {
            randomWalk();
            return;
        }

        // If the player has moved out of the initial aggro box, but is still within the A* map, keep following
        else if (!player.getCollisionBounds(0, 0).intersects(getRadius()) && player.getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.PATHFINDING ||
                !player.getCollisionBounds(0, 0).intersects(getRadius()) && player.getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.ATTACK) {
            state = CombatState.PATHFINDING;
        }

        // If the player has moved out of the aggro box and out of the A* map,
        else if (!player.getCollisionBounds(0, 0).intersects(getRadius()) && !player.getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.PATHFINDING ||
                !player.getCollisionBounds(0, 0).intersects(getRadius()) && !player.getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.ATTACK) {
            state = CombatState.BACKTRACK;
        }


        // If the player is <= X * TileWidth away from the Creature, attack him.
        if (attackable && state != CombatState.BACKTRACK) {
            if (state == CombatState.PATHFINDING && isInAttackRange(player) || state == CombatState.ATTACK && isInAttackRange(player)) {
                lookAtPlayer();
                checkAttacks();
                state = CombatState.ATTACK;
            } else {
                state = CombatState.PATHFINDING;
            }
        }

        // If the Creature was attacking, but the player moved out of the A* map bounds, backtrack to spawn.
        if (state == CombatState.ATTACK && !player.getCollisionBounds(0, 0).intersects(map.getMapBounds())) {
            state = CombatState.BACKTRACK;
        }

        // If the Creature was following the player but he moved out of the A* map, backtrack.
        if (!player.getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.PATHFINDING) {
            state = CombatState.BACKTRACK;
        }

        if (state == CombatState.PATHFINDING || state == CombatState.BACKTRACK) {
            // Control the number of times we check for new path
            pathTimer++;
            if (pathTimer >= TIME_PER_PATH_CHECK) {
                map.init();
                findPath();
                pathTimer = 0;
            }
            followAStar();
        }
    }

    @Override
    public Rectangle2D getFullBounds(double xOffset, double yOffset) {
        if (digging) {
            return new Rectangle(-100, -100, 0, 0);
        } else {
            return super.getCollisionBounds(xOffset, yOffset);
        }
    }

    @Override
    public Rectangle2D getCollisionBounds(double xOffset, double yOffset) {
        if (digging) {
            return new Rectangle(-100, -100, 0, 0);
        } else {
            return super.getCollisionBounds(xOffset, yOffset);
        }
    }
}
