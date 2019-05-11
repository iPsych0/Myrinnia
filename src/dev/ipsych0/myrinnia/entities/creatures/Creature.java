package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Buff;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.pathfinding.CombatState;
import dev.ipsych0.myrinnia.pathfinding.Node;
import dev.ipsych0.myrinnia.tiles.Tiles;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Creature extends Entity {

    /**
     *
     */
    private static final long serialVersionUID = -2545797921368819194L;

    /*
     * Default Creature variables
     */
    public static final float DEFAULT_SPEED = 1.0f;
    static final float DEFAULT_ATTACKSPEED = 1.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 32,
            DEFAULT_CREATURE_HEIGHT = 32;

    private static final int DEFAULT_DAMAGE = 1;
    private static final int DEFAULT_STRENGTH = 0;
    private static final int DEFAULT_DEXTERITY = 0;
    private static final int DEFAULT_INTELLIGENCE = 0;
    private static final int DEFAULT_DEFENCE = 0;
    private static final int DEFAULT_VITALITY = 0;
    int baseDamage;
    int strength;
    int dexterity;
    int intelligence;
    int defence;
    int vitality;
    float attackSpeed;
    int combatLevel;
    static final double LEVEL_EXPONENT = 0.998;
    int attackRange = Tiles.TILEWIDTH * 2;
    ArrayList<Projectile> projectiles = new ArrayList<>();

    // Walking timer
    private int time = 0;

    // Radius variables:
    int xSpawn = (int) getX();
    int ySpawn = (int) getY();
    int xRadius = 192;
    int yRadius = 192;
    Rectangle radius;

    // A* stuff
    private CombatState state;
    private List<Node> nodes;
    int pathFindRadiusX = 1024;
    int pathFindRadiusY = 1024;
    AStarMap map = new AStarMap(this, xSpawn - pathFindRadiusX, ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2, xSpawn, ySpawn);
    private boolean aStarInitialized;
    private Color pathColour = new Color(44, 255, 12, 127);
    private int stuckTimerX = 0, stuckTimerY = 0;
    private int lastX = (int) x, lastY = (int) y;
    private static final int TIMES_PER_SECOND = 4;
    private int timePerPathCheck = (60 / TIMES_PER_SECOND); // 4 times per second.
    private int pathTimer = 0;
    private List<Condition> conditions = new CopyOnWriteArrayList<>();
    private List<Buff> buffs = new CopyOnWriteArrayList<>();
    Animation aLeft;
    Animation aRight;
    Animation aUp;
    Animation aDown;
    Animation aDefault;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    Direction direction;
    // Last faced direction
    Direction lastFaced;

    float speed;
    float xMove;
    float yMove;

    protected Creature(float x, float y, int width, int height) {
        super(x, y, width, height);
        state = CombatState.IDLE;
        baseDamage = (DEFAULT_DAMAGE);
        strength = (DEFAULT_STRENGTH);
        dexterity = (DEFAULT_DEXTERITY);
        intelligence = (DEFAULT_INTELLIGENCE);
        defence = (DEFAULT_DEFENCE);
        vitality = (DEFAULT_VITALITY);
        speed = (DEFAULT_SPEED);
        attackSpeed = (DEFAULT_ATTACKSPEED);
        maxHealth = (int) (DEFAULT_HEALTH + Math.round(vitality * 1.5));
        health = maxHealth;
        combatLevel = 1;
        xMove = 0;
        yMove = 0;
        drawnOnMap = true;
        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);

    }

    /*
     * The default damage formula (see Entity::getDamage() function)
     * NOTE: USE THIS METHOD WITH @Override IN SPECIFIC ENTITIES TO CREATE PERSONAL DAMAGE FORMULA!
     */
    @Override
    public int getDamage(DamageType damageType, Entity dealer, Entity receiver) {
        return super.getDamage(damageType, dealer, receiver);
    }

    /*
     * Moves on the X or Y axis, keeping in mind the collision detection
     */
    public void move() {
        if (!checkEntityCollisions(xMove, 0f))
            moveX();
        if (!checkEntityCollisions(0f, yMove))
            moveY();
    }

    /*
     * Handles movement on the X-axis
     */
    private void moveX() {
        if (xMove > 0) { // Moving right
            direction = Direction.RIGHT;
            lastFaced = Direction.RIGHT;
            float tx = (x + xMove + bounds.x + bounds.width) / Tiles.TILEWIDTH;

            if (!collisionWithTile((int) tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT, true) &&
                    !collisionWithTile((int) tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT, true)) {
                x += xMove;
            } else {
                x = tx * Tiles.TILEWIDTH - bounds.x - bounds.width - xMove;
            }

        } else if (xMove < 0) { // Moving left
            direction = Direction.LEFT;
            lastFaced = Direction.LEFT;
            float tx = (x + xMove + bounds.x) / Tiles.TILEWIDTH;

            if (!collisionWithTile((int) tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT, true) &&
                    !collisionWithTile((int) tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT, true)) {

                x += xMove;
            } else {
                x = tx * Tiles.TILEWIDTH - bounds.x - xMove;
            }
        } else if (xMove == 0) {
            direction = lastFaced;
        }
    }

    /*
     * Handles movement on the Y-axis
     */
    private void moveY() {
        if (yMove < 0) { // Up
            direction = Direction.UP;
            lastFaced = Direction.UP;
            float ty = (y + yMove + bounds.y) / Tiles.TILEHEIGHT;

            if (!collisionWithTile((int) (x + bounds.x) / Tiles.TILEWIDTH, (int) ty, false) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, (int) ty, false)) {
                y += yMove;
            } else {
                y = ty * Tiles.TILEHEIGHT - bounds.y - yMove;
            }

        } else if (yMove > 0) { // Down
            direction = Direction.DOWN;
            lastFaced = Direction.DOWN;
            float ty = (y + yMove + bounds.y + bounds.height) / Tiles.TILEHEIGHT;

            if (!collisionWithTile((int) (x + bounds.x) / Tiles.TILEWIDTH, (int) ty, false) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, (int) ty, false)) {
                y += yMove;
            } else {
                y = ty * Tiles.TILEHEIGHT - bounds.y - bounds.height - yMove;
            }
        } else if (yMove == 0) {
            direction = lastFaced;
        }
    }

    /*
     * Gets the animation based on last faced direction
     * @returns the animation image
     */
    BufferedImage getAnimationByLastFaced() {
        // Idle animations
        if (xMove == 0 && yMove == 0) {
            if (lastFaced == Direction.LEFT) {
                aDefault = aLeft;
            } else if (lastFaced == Direction.RIGHT) {
                aDefault = aRight;
            } else if (lastFaced == Direction.UP) {
                aDefault = aUp;
            } else if (lastFaced == Direction.DOWN) {
                aDefault = aDown;
            }
            return aDefault.getDefaultFrame();
        }

        if (lastFaced == Direction.LEFT)
            return aLeft.getCurrentFrame();
        if (lastFaced == Direction.RIGHT)
            return aRight.getCurrentFrame();
        if (lastFaced == Direction.UP)
            return aUp.getCurrentFrame();
        if (lastFaced == Direction.DOWN)
            return aDown.getCurrentFrame();

        return aDefault.getCurrentFrame();
    }

    /*
     * Sets the last faced direction, based on last movement
     */
    void setLastFaced() {
        if (lastFaced == null) {
            aDefault = aDown;
        }
        if (lastFaced == Direction.LEFT) {
            aDefault = aLeft;
        } else if (lastFaced == Direction.RIGHT) {
            aDefault = aRight;
        } else if (lastFaced == Direction.DOWN) {
            aDefault = aDown;
        } else if (lastFaced == Direction.UP) {
            aDefault = aUp;
        }
    }

    /*
     * Returns the sprite of the last faced direction
     */
    BufferedImage getLastFacedImg() {
        if (lastFaced == null) {
            return aDown.getDefaultFrame();
        }
        if (lastFaced == Direction.LEFT) {
            return aLeft.getDefaultFrame();
        }
        if (lastFaced == Direction.RIGHT) {
            return aRight.getDefaultFrame();
        }
        if (lastFaced == Direction.DOWN) {
            return aDown.getDefaultFrame();
        }
        if (lastFaced == Direction.UP) {
            return aUp.getDefaultFrame();
        }
        return aDown.getDefaultFrame();
    }

    /*
     * Handles collision detection with Tiles
     */
    boolean collisionWithTile(int x, int y, boolean horizontalDirection) {
        // Debug
        if (Handler.noclipMode && this.equals(Handler.get().getPlayer()))
            return false;

        boolean walkableOnTop = false;
        for (int i = 0; i < Handler.get().getWorld().getLayers().length; i++) {
            Tiles t = Handler.get().getWorld().getTile(i, x, y);
            if (t != null && t.isSolid()) {
                if (horizontalDirection) {
                    walkableOnTop = t.getBounds() != null && !t.getBounds(x, y).intersects(getCollisionBounds(xMove, 0));
                } else {
                    walkableOnTop = t.getBounds() != null && !t.getBounds(x, y).intersects(getCollisionBounds(0, yMove));
                }
            } else {
                if (t != null && t != Tiles.tiles[0])
                    walkableOnTop = true;
            }
        }
        return !walkableOnTop;
    }

    /*
     * Handles collision detection for A*
     */
    public boolean collisionWithTile(int x, int y) {
        boolean walkableOnTop = false;
        for (int i = 0; i < Handler.get().getWorld().getLayers().length; i++) {
            Tiles t = Handler.get().getWorld().getTile(i, x, y);
            if (t != null && t.isSolid()) {
                walkableOnTop = false;
            } else {
                if (t != null && t != Tiles.tiles[0])
                    walkableOnTop = true;
            }
        }
        return !walkableOnTop;
    }

    /**
     * If the Entity is an NPC, this function returns only the name, else it returns name + combat info.
     */
    @Override
    public String[] getEntityInfo(Entity hoveringEntity) {
        if (this.isNpc) {
            String[] name = new String[1];
            name[0] = hoveringEntity.getName();
            return name;
        }
        String[] name = new String[2];
        name[0] = hoveringEntity.getName() + " (level-" + getCombatLevel() + ")";
        name[1] = "HP: " + String.valueOf(health) + "/" + String.valueOf(maxHealth);
        return name;
    }

    /**
     * Draws an Entity's information to an overlay at the top of the screen
     *
     * @param hoveringEntity
     * @param g
     */
    public void drawEntityOverlay(Entity hoveringEntity, Graphics2D g) {
        int yPos = 12;
        g.drawImage(Assets.chatwindow, Handler.get().getWidth() / 2 - 100, 1, 200, 50, null);
        for (int i = 0; i < getEntityInfo(hoveringEntity).length; i++) {
            Text.drawString(g, getEntityInfo(hoveringEntity)[i], Handler.get().getWidth() / 2, yPos + (14 * i), true, Color.YELLOW, Assets.font14);
        }

        for (int i = 0; i < conditions.size(); i++) {
            Rectangle slotPos = new Rectangle(Handler.get().getWidth() / 2 - 100 + (i * ItemSlot.SLOTSIZE), 50, 32, 32);
            conditions.get(i).render(g, slotPos.x, slotPos.y);
            if(slotPos.contains(Handler.get().getMouse())) {
                Handler.get().getAbilityManager().getAbilityHUD().getStatusTooltip().render(conditions.get(i), g);
            }
        }

        int yOffset = 0;
        if (!conditions.isEmpty()) yOffset = 1;
        for (int i = 0; i < buffs.size(); i++) {
            Rectangle slotPos = new Rectangle(Handler.get().getWidth() / 2 - 100 + (i * ItemSlot.SLOTSIZE), 50 + (ItemSlot.SLOTSIZE * yOffset), 32, 32);
            buffs.get(i).render(g, slotPos.x, slotPos.y);
            if(slotPos.contains(Handler.get().getMouse())) {
                Handler.get().getAbilityManager().getAbilityHUD().getStatusTooltip().render(buffs.get(i), g);
            }
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        if (AStarMap.debugMode) {
            g.setColor(Color.BLACK);
            g.drawRect((int) (radius.x - Handler.get().getGameCamera().getxOffset()), (int) (radius.y - Handler.get().getGameCamera().getyOffset()), radius.width, radius.height);

            map.render(g);

            if (nodes != null) {
                for (Node n : nodes) {
                    g.setColor(pathColour);
                    g.fillRect((int) (n.getX() * 32 - Handler.get().getGameCamera().getxOffset()), (int) (n.getY() * 32 - Handler.get().getGameCamera().getyOffset()), 32, 32);
                }
            }
        }
    }

    void tickProjectiles() {
        if(projectiles.size() < 1)
            return;

        Iterator<Projectile> it = projectiles.iterator();
        Collection<Projectile> deleted = new CopyOnWriteArrayList<>();
        while (it.hasNext()) {
            Projectile p = it.next();
            p.tick();
            if (!p.isActive()) {
                deleted.add(p);
            }
            for (Entity e : Handler.get().getWorld().getEntityManager().getEntities()) {
                if (p.getCollisionBounds(0, 0).intersects(e.getCollisionBounds(0, 0)) && p.isActive()) {
                    if (e.equals(Handler.get().getPlayer())) {
                        e.damage(DamageType.DEX, this, e);

                        int dice = Handler.get().getRandomNumber(0, 5);
                        if (dice == 1) {
                            e.addCondition(this, e, new Condition(Condition.Type.POISON, e, 5, 3));
                        }

                        p.setActive(false);
                    }
                    if (!e.isAttackable()) {
                        p.setActive(false);
                    }
                }
            }
        }

        projectiles.removeAll(deleted);
    }

    public void tick() {
        if (aLeft != null && aRight != null && aDown != null && aUp != null && aDefault != null) {
            aDefault.tick();
            aDown.tick();
            aUp.tick();
            aLeft.tick();
            aRight.tick();
        }

        if (!aStarInitialized) {
            map.init();
            aStarInitialized = true;
        }
        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);
        tickProjectiles();
        combatStateManager();
    }

    /*
     * Walks into random directions at given intervals
     */
    private void randomWalk() {
        time++;
        int i = (Handler.get().getRandomNumber(60, 90));
        if (time % i == 0) {
            xMove = 0;
            yMove = 0;

            int direction = Handler.get().getRandomNumber(0, 4);

            if (direction == 0) {
                xMove = 0;
                yMove = 0;
            }

            if (direction == 1) {
                xMove = -speed;
            }
            if (direction == 2) {
                xMove = +speed;
            }
            if (direction == 3) {
                yMove = -speed;
            }
            if (direction == 4) {
                yMove = +speed;
            }
            time = 0;
        }

        if (getX() > (xSpawn + xRadius)) {
            xMove = -speed;
        } else if (getX() < (xSpawn - xRadius)) {
            xMove = +speed;
        } else if (getY() > (ySpawn + yRadius)) {
            yMove = -speed;
        } else if (getY() < (ySpawn - yRadius)) {
            yMove = +speed;
        }
        move();

    }

    private void findPath() {
        if (state == CombatState.BACKTRACK) {
            nodes = map.findPath((int) ((x + 8) / 32) - (xSpawn - pathFindRadiusX) / 32, (int) ((y + 8) / 32) - (ySpawn - pathFindRadiusY) / 32,
                    Math.round(((xSpawn + 8) / 32)) - (xSpawn - pathFindRadiusX) / 32, Math.round(((ySpawn + 8) / 32)) - (ySpawn - pathFindRadiusY) / 32);
        } else {
            int playerX = Math.round(((Handler.get().getPlayer().getX() + 0) / 32)) - (xSpawn - pathFindRadiusX) / 32;
            int playerY = Math.round(((Handler.get().getPlayer().getY() + 4) / 32)) - (ySpawn - pathFindRadiusY) / 32;

            if (playerX == map.getNodes().length || playerY == map.getNodes().length) {
                nodes = map.findPath((int) ((x + 8) / 32) - (xSpawn - pathFindRadiusX) / 32, (int) ((y + 8) / 32) - (ySpawn - pathFindRadiusY) / 32,
                        Math.round(((playerX + 8) / 32)) - (xSpawn - pathFindRadiusX) / 32, Math.round(((playerY + 8) / 32)) - (ySpawn - pathFindRadiusY) / 32);
            } else {
                nodes = map.findPath((int) ((x + 8) / 32) - (xSpawn - pathFindRadiusX) / 32, (int) ((y + 8) / 32) - (ySpawn - pathFindRadiusY) / 32,
                        playerX, playerY);
            }
        }
    }

    /**
     * Manages the different combat states of a Creature (IDLE, PATHFINDING, ATTACKING, BACKTRACKING)
     */
    private void combatStateManager() {

        if (damaged) {
            state = CombatState.PATHFINDING;
        }

        // If the player is within the A* map AND moves within the aggro range, state = pathfinding (walk towards goal)
        if (Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds())) {
            state = CombatState.PATHFINDING;
        }

        // If the Creature was not following or attacking the player, move around randomly.
        if (state == CombatState.IDLE) {
            randomWalk();
            return;
        }

        // If the player has moved out of the initial aggro box, but is still within the A* map, keep following
        else if (!Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.PATHFINDING ||
                !Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.ATTACK) {
            state = CombatState.PATHFINDING;
        }

        // If the player has moved out of the aggro box and out of the A* map,
        else if (!Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && !Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.PATHFINDING ||
                !Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && !Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.ATTACK) {
            state = CombatState.BACKTRACK;
        }

        // If the player is <= X * TileWidth away from the Creature, attack him.
        if (distanceToEntity(((int) this.getX() + this.getWidth() / 2), ((int) this.getY() + +this.getHeight() / 2),
                ((int) Handler.get().getPlayer().getX() + Handler.get().getPlayer().getWidth() / 2),
                ((int) Handler.get().getPlayer().getY() + Handler.get().getPlayer().getHeight() / 2)) <= attackRange &&
                Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) &&
                Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds())) {
            state = CombatState.ATTACK;
            checkAttacks();
        }

        // If the Creature was attacking, but the player moved out of aggro range or out of the A* map bounds, backtrack to spawn.
        if (state == CombatState.ATTACK && !Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) || state == CombatState.ATTACK && !Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds())) {
            state = CombatState.BACKTRACK;
        }

        // If the Creature was following the player but he moved out of the A* map, backtrack.
        if (!Handler.get().getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.PATHFINDING || state == CombatState.BACKTRACK) {
            state = CombatState.BACKTRACK;
        }

        if (state == CombatState.PATHFINDING || state == CombatState.BACKTRACK) {
            // Control the number of times we check for new path
            pathTimer++;
            if (pathTimer >= timePerPathCheck) {
                findPath();
                pathTimer = 0;
            }
            followAStar();
        }
    }

    /**
     * Override this method in the creature's class
     */
    void checkAttacks() {

    }

    /**
     * Movement logic for following each node in the List nodes
     */
    private void followAStar() {
        if (nodes == null) {
            return;
        }
        if (nodes.size() <= 0) {
            return;
        }

        Node next = nodes.get(0);

        // Store the current X & Y pause to check if the entity is stuck
        int currentX = (int) x;
        int currentY = (int) y;

        // If the X pause is the same as last tick, increment stuckTimer
        if (lastX == currentX) {
            stuckTimerX++;
        }
        lastX = (int) this.x;

        // If the Y pause is the same as last tick, increment stuckTimer
        if (lastY == currentY) {
            stuckTimerY++;
        }
        lastY = (int) this.y;

        // If the entity has been stuck for .75 seconds on X-axis, move it on the Y-axis to unstuck it.
        if (stuckTimerX >= 45) {
            yMove = +speed;
            move();
            stuckTimerX = 0;
            return;
        }

        // If the entity has been stuck for .75 seconds on Y-axis, move it on the X-axis to unstuck it.
        if (stuckTimerY >= 45) {
            xMove = +speed;
            move();
            stuckTimerY = 0;
            return;
        }

        if (next.getX() != (int) ((x + 8) / 32)) {
            xMove = (next.getX() < (int) ((x + 8) / 32) ? -speed : speed);
            if (x % 32 == 8) {
                //x -= x % 32;
                if (!nodes.isEmpty())
                    nodes.remove(0);

                stuckTimerX = 0;
                yMove = 0;
            }
        }

        if (next.getY() != (int) ((y + 8) / 32)) {
            yMove = (next.getY() < (int) ((y + 8) / 32) ? -speed : speed);
            if (y % 32 == 8) {
                //y -= y % 32;
                if (!nodes.isEmpty())
                    nodes.remove(0);

                stuckTimerY = 0;
                xMove = 0;
            }
        }

        if (xMove != 0 || yMove != 0) {
            move();
        }
    }

    // GETTERS + SETTERS

    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    private int getCombatLevel() {
        return combatLevel;
    }

    public void setCombatLevel(int combatLevel) {
        this.combatLevel = combatLevel;
    }

    public CombatState getState() {
        return state;
    }

    public void setState(CombatState state) {
        this.state = state;
    }

    private Rectangle getRadius() {
        return radius;
    }

    public void setRadius(Rectangle radius) {
        this.radius = radius;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    public AStarMap getMap() {
        return map;
    }

    public void setMap(AStarMap map) {
        this.map = map;
    }

    public Direction getLastFaced() {
        return lastFaced;
    }

    public void setLastFaced(Direction lastFaced) {
        this.lastFaced = lastFaced;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public void setBuffs(List<Buff> buffs) {
        this.buffs = buffs;
    }

    public int getPathFindRadiusX() {
        return pathFindRadiusX;
    }

    public void setPathFindRadiusX(int pathFindRadiusX) {
        this.pathFindRadiusX = pathFindRadiusX;
    }

    public int getPathFindRadiusY() {
        return pathFindRadiusY;
    }

    public void setPathFindRadiusY(int pathFindRadiusY) {
        this.pathFindRadiusY = pathFindRadiusY;
    }
}
