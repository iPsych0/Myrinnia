package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Buff;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.Resistance;
import dev.ipsych0.myrinnia.entities.droptables.DropTableEntry;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.KeyManager;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.pathfinding.AStarMap;
import dev.ipsych0.myrinnia.pathfinding.CombatState;
import dev.ipsych0.myrinnia.pathfinding.Node;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.*;

public abstract class Creature extends Entity {

    /**
     *
     */
    private static final long serialVersionUID = -2545797921368819194L;

    /*
     * Default Creature variables
     */
    public static final double DEFAULT_SPEED = 1.5;
    public static final double DEFAULT_ATTACKSPEED = 1.0;

    public static final int DEFAULT_CREATURE_WIDTH = 32,
            DEFAULT_CREATURE_HEIGHT = 32;

    private static final int DEFAULT_DAMAGE = 1;
    private static final int DEFAULT_STRENGTH = 0;
    private static final int DEFAULT_DEXTERITY = 0;
    private static final int DEFAULT_INTELLIGENCE = 0;
    private static final int DEFAULT_DEFENCE = 0;
    private static final int DEFAULT_VITALITY = 0;
    protected int baseDamage;
    protected int strength;
    protected int dexterity;
    protected int intelligence;
    protected int defence;
    protected int vitality;
    protected double attackSpeed;
    protected int waterLevel = 1, fireLevel = 1, airLevel = 1, earthLevel = 1;
    protected int combatLevel;
    protected static final double LEVEL_EXPONENT = 0.998;
    protected int attackRange = Tile.TILEWIDTH + 16;
    protected List<Projectile> projectiles = new ArrayList<>();
    protected List<Projectile> toBeAdded = new ArrayList<>();
    protected double meleeDirection, meleeXOffset, meleeYOffset;

    // Walking timer
    private int walkingTimer = 0;

    // Radius variables:
    protected int xSpawn = (int) getX();
    protected int ySpawn = (int) getY();
    int xRadius = 192;
    int yRadius = 192;
    Rectangle radius;

    // A* stuff
    protected boolean aggressive = true;
    protected CombatState state;
    protected List<Node> nodes;
    protected int pathFindRadiusX = 768;
    protected int pathFindRadiusY = 768;
    protected AStarMap map = new AStarMap(this, xSpawn - pathFindRadiusX, ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);
    private int stuckTimerX = 0, stuckTimerY = 0;
    private int lastX = (int) x, lastY = (int) y;
    protected static final int TIMES_PER_SECOND = 4;
    protected static final int TIME_PER_PATH_CHECK = (int) (60d / (double) TIMES_PER_SECOND); // 4 times per second.
    protected int pathTimer = 0;

    protected List<Condition> conditions = new ArrayList<>();
    protected List<Buff> buffs = new ArrayList<>();
    protected List<Resistance> immunities = new ArrayList<>();
    protected Animation aLeft;
    protected Animation aRight;
    protected Animation aUp;
    protected Animation aDown;
    protected Animation aDefault;
    protected double baseDmgExponent = 1.1;
    protected boolean movementAllowed = true;
    protected transient Tile currentTile = Tile.tiles[23780], previousTile = Tile.tiles[23780];
    protected boolean hasSwitchedTile;
    protected transient Map<Tile, Point> postRenderTiles = new HashMap<>();
    protected boolean initialTileSetup;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected Direction direction;
    // Last faced direction
    protected Direction lastFaced;
    protected Direction originalDirection;

    protected double speed;
    protected double xMove;
    protected double yMove;

    protected List<DropTableEntry> dropTableEntries;
    protected int maxDropTableWeight;
    protected int emptyWeight;

    public Creature(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        this.combatLevel = level <= 1 ? 1 : level; // Level 1 is minimum level

        if (animation != null) {
            BufferedImage[][] anims = Assets.getAnimationByTag(animation);
            aDown = new Animation(250, anims[0]);
            aLeft = new Animation(250, anims[1]);
            aRight = new Animation(250, anims[2]);
            aUp = new Animation(250, anims[3]);
            aDefault = aDown;
        }

        if (direction != null) {
            lastFaced = direction;
            this.direction = direction;
            this.originalDirection = direction;
            walker = false;
        }

        if (dropTable != null) {
            dropTableEntries = Utils.loadDropTable(dropTable);
            for (DropTableEntry entry : dropTableEntries) {
                // ItemID -1 for empty drop
                if (entry.getItemId() == -1) {
                    emptyWeight = entry.getWeight();
                } else {
                    maxDropTableWeight += entry.getWeight();
                }
            }
        }

        state = CombatState.IDLE;
        baseDamage = (DEFAULT_DAMAGE);
        strength = (DEFAULT_STRENGTH);
        dexterity = (DEFAULT_DEXTERITY);
        intelligence = (DEFAULT_INTELLIGENCE);
        defence = (DEFAULT_DEFENCE);
        vitality = (DEFAULT_VITALITY);
        speed = (DEFAULT_SPEED);
        attackSpeed = (DEFAULT_ATTACKSPEED);
        maxHealth = DEFAULT_HEALTH + vitality * 4;
        health = maxHealth;
        setCombatLevel();

        xMove = 0;
        yMove = 0;
        drawnOnMap = true;
        radius = new Rectangle((int) x - xRadius, (int) y - yRadius, xRadius * 2, yRadius * 2);
    }

    protected void setCombatLevel() {
        for (int i = 1; i < combatLevel; i++) {
            baseDamage = (int) Math.ceil((baseDamage * baseDmgExponent) + 1);
            baseDmgExponent *= LEVEL_EXPONENT;
            health = maxHealth;
        }
    }

    protected void getDroptableItem() {
        if (maxDropTableWeight == 0) {
            // The drop table must be empty
            Handler.get().sendMsg(getName() + " has an empty drop table.");
            System.err.println("Drop table for " + getName() + " is empty in " + Handler.get().getWorld().getZone().getName() + ". Please check 'dropTable' property in Tiled.");
            return;
        }

        // Roll number between 1 and sum of all weights
        int roll = Handler.get().getRandomNumber(1, maxDropTableWeight + emptyWeight);
        int index = 0;
        for (DropTableEntry entry : dropTableEntries) {
            index += entry.getWeight();
            if (roll <= index && roll > (index - entry.getWeight())) {
                // Don't drop anything if we rolled the empty table
                if (entry.getItemId() == -1)
                    return;

                Handler.get().dropItem(Item.items[entry.getItemId()], entry.getAmount(), (int) x, (int) y);
                return;
            }
        }
        // The drop table must be empty
        Handler.get().sendMsg("Drop table for " + getName() + " is empty.");
    }

    /*
     * Moves on the X or Y axis, keeping in mind the collision detection
     */
    public void move() {
        if (!initialTileSetup) {
            setupInitialPermission((int) x / 32, (int) y / 32);
            initialTileSetup = true;
        }
        if (!checkEntityCollisions(xMove, 0))
            moveX();
        if (!checkEntityCollisions(0, yMove))
            moveY();

    }

    /*
     * Handles movement on the X-axis
     */
    private void moveX() {
        if (xMove > 0) { // Moving right
            direction = Direction.RIGHT;
            lastFaced = Direction.RIGHT;
            double tx = (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;

            if (!collisionWithTile((int) tx, (int) (y + bounds.y) / Tile.TILEHEIGHT, true) &&
                    !collisionWithTile((int) tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT, true)) {
                x += xMove;
            } else {
                x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - xMove;
            }

        } else if (xMove < 0) { // Moving left
            direction = Direction.LEFT;
            lastFaced = Direction.LEFT;
            double tx = (x + xMove + bounds.x) / Tile.TILEWIDTH;

            if (tx >= 0 && !collisionWithTile((int) tx, (int) (y + bounds.y) / Tile.TILEHEIGHT, true) &&
                    !collisionWithTile((int) tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT, true)) {

                x += xMove;
            } else {
                x = tx * Tile.TILEWIDTH - bounds.x - xMove;
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
            double ty = (y + yMove + bounds.y) / Tile.TILEHEIGHT;

            if (ty >= 0 && !collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, (int) ty, false) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, (int) ty, false)) {
                y += yMove;
            } else {
                y = ty * Tile.TILEHEIGHT - bounds.y - yMove;
            }

        } else if (yMove > 0) { // Down
            direction = Direction.DOWN;
            lastFaced = Direction.DOWN;
            double ty = (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;

            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, (int) ty, false) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, (int) ty, false)) {
                y += yMove;
            } else {
                y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - yMove;
            }
        } else if (yMove == 0) {
            direction = lastFaced;
        }
    }

    /*
     * Gets the animation based on last faced direction
     * @returns the animation image
     */
    protected BufferedImage getAnimationByLastFaced() {
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
    protected void setLastFaced() {
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
    protected BufferedImage getLastFacedImg() {
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

    private boolean isOutsideMap(int x, int y) {
        int width = Handler.get().getWorld().getWidth();
        int height = Handler.get().getWorld().getHeight();
        return x < 0 || y < 0 || x >= width || y >= height;
    }

    private void setupInitialPermission(int x, int y) {
        if (Handler.get().getWorld().hasPermissionsLayer()) {
            currentTile = Handler.get().getWorld().getTile(Handler.get().getWorld().getLayers().length - 1, x, y);
            if (currentTile != Tile.tiles[0]) {
                if (currentTile.getPermission().equalsIgnoreCase("C")) {
                    verticality = 0;
                } else if (currentTile.getPermission().equalsIgnoreCase("10")) {
                    verticality = 1;
                } else if (currentTile.getPermission().equalsIgnoreCase("14")) {
                    verticality = 2;
                }
            } else {
                currentTile = Tile.tiles[23780];
            }

            previousTile = currentTile;
        }
    }

    private void checkPermissionTiles(int x, int y) {
        if (Handler.get().getWorld().hasPermissionsLayer()) {
            Tile oldTile = currentTile;
            currentTile = Handler.get().getWorld().getTile(Handler.get().getWorld().getLayers().length - 1, x, y);
            if (currentTile != Tile.tiles[0]) {
                if (currentTile != oldTile) {
                    hasSwitchedTile = true;
                }
                if (hasSwitchedTile) {
                    if (currentTile != null) {
                        hasSwitchedTile = false;
                        previousTile = oldTile;
                        if (currentTile.getPermission().equalsIgnoreCase("0") && previousTile.getPermission().equalsIgnoreCase("C") ||
                                currentTile.getPermission().equalsIgnoreCase("10") && previousTile.getPermission().equalsIgnoreCase("0")) {
                            verticality = 1;
                        }
                        if (currentTile.getPermission().equalsIgnoreCase("0") && previousTile.getPermission().equalsIgnoreCase("10") ||
                                currentTile.getPermission().equalsIgnoreCase("C") && previousTile.getPermission().equalsIgnoreCase("0")) {
                            verticality = 0;
                        }
                    }

                    if (this.equals(Handler.get().getPlayer())) {
                        if (previousTile != null) {
                            System.out.println("Previous: " + previousTile.getPermission());
                        }
                        if (currentTile != null) {
                            System.out.println("Current: " + currentTile.getPermission());
                        }
                        System.out.println("Verticality: " + verticality);
                        System.out.println("--------------");
                    }
                }
            } else {
                currentTile = Tile.tiles[23780];
                previousTile = currentTile;
            }
        } else {
            currentTile = Tile.tiles[23780];
            previousTile = currentTile;
        }
    }

    private boolean isAllowedToMove(int topLayer, int x, int y) {
        Tile target = Handler.get().getWorld().getTile(topLayer, x, y);
        if (target != Tile.tiles[0]) {
            if (target.getPermission() != null && previousTile != null && currentTile != null) {
                return hasRightPrivileges(previousTile, currentTile, target, topLayer, x, y);
            }
        }
        return true;
    }

    private boolean hasRightPrivileges(Tile previousTile, Tile currentTile, Tile target, int topLayer, int x, int y) {
        switch (target.getPermission()) {
            case "C":
                postRenderTiles.clear();
                return currentTile.getPermission().equalsIgnoreCase("C") || currentTile.getPermission().equalsIgnoreCase("0") || currentTile.getPermission().equalsIgnoreCase("3C") && previousTile.getPermission().equalsIgnoreCase("C");
            case "0":
                return true;
            case "8":
                return currentTile.getPermission().equalsIgnoreCase("8") || currentTile.getPermission().equalsIgnoreCase("3C") || currentTile.getPermission().equalsIgnoreCase("0");
            case "10":
                return currentTile.getPermission().equalsIgnoreCase("10") || currentTile.getPermission().equalsIgnoreCase("3C") && verticality == 1 || currentTile.getPermission().equalsIgnoreCase("0");
            case "14":
                return currentTile.getPermission().equalsIgnoreCase("14") || currentTile.getPermission().equalsIgnoreCase("3C") && verticality == 2 || currentTile.getPermission().equalsIgnoreCase("0");
            case "18":
                return currentTile.getPermission().equalsIgnoreCase("18") || currentTile.getPermission().equalsIgnoreCase("3C") && verticality == 3 || currentTile.getPermission().equalsIgnoreCase("0");
            case "22":
                return currentTile.getPermission().equalsIgnoreCase("22") || currentTile.getPermission().equalsIgnoreCase("3C") && verticality == 4 || currentTile.getPermission().equalsIgnoreCase("0");
            case "3C":
                if (verticality == 0) {
                    Tile postRender;
                    for (int i = topLayer - 1; i >= 0; i--) {
                        postRender = Handler.get().getWorld().getTile(i, x, y);
                        if (postRender != Tile.tiles[0]) {
                            postRenderTiles.put(postRender, new Point(x, y));
                            break;
                        }
                    }
                }
                return true;
            default:
                return true;

        }
    }

    /*
     * Handles collision detection with Tiles
     */
    public boolean collisionWithTile(int x, int y, boolean horizontalDirection) {
        // Debug
        if (Handler.noclipMode && this.equals(Handler.get().getPlayer()))
            return false;

        if (isOutsideMap(x, y)) {
            return true;
        }

        int topLayer;
        if (Handler.get().getWorld().hasPermissionsLayer()) {
            topLayer = Handler.get().getWorld().getLayers().length - 1;
            boolean allowed = isAllowedToMove(topLayer, x, y);
            if (!allowed) {
                return true;
            }
        } else {
            topLayer = Handler.get().getWorld().getLayers().length;
        }

        if (Handler.get().getWorld().hasShadowsLayer()) {
            if (Handler.get().getWorld().hasPermissionsLayer()) {
                topLayer = Handler.get().getWorld().getLayers().length - 2;
            } else {
                topLayer = Handler.get().getWorld().getLayers().length - 1;
            }
        }

        checkPermissionTiles(x, y);

        // Special exclusion for 3C tiles when walking underneath (allow all movement)
        if (currentTile != null && currentTile.getPermission() != null && currentTile.getPermission().equalsIgnoreCase("3C")) {
            if (verticality == 0) {
                return false;
            }
        }

        boolean walkableOnTop = false;
        boolean solidTileUnderPostRendered = false;
        boolean hasPostRenderedTile = false;
        for (int i = 0; i < topLayer; i++) {
            Tile t = Handler.get().getWorld().getTile(i, x, y);
            if (t != null && t.isSolid()) {
                if (horizontalDirection) {
                    walkableOnTop = t.getPolyBounds() != null && !t.getPolyBounds(x, y).intersects(getCollisionBounds(xMove, 0));
                } else {
                    walkableOnTop = t.getPolyBounds() != null && !t.getPolyBounds(x, y).intersects(getCollisionBounds(0, yMove));
                }
                if (!walkableOnTop) {
                    solidTileUnderPostRendered = true;
                }
            } else {
                if (t != null && t != Tile.tiles[0]) {
                    walkableOnTop = true;
                    if (t.isPostRendered()) {
                        hasPostRenderedTile = true;
                    }

                }
            }
        }

        if (hasPostRenderedTile && solidTileUnderPostRendered) {
            return true;
        }

        return !walkableOnTop;
    }

    /*
     * Handles collision detection for A*, which does not take polygon bounds into consideration
     */
    public boolean collisionWithTile(int x, int y) {
        if (isOutsideMap(x, y)) {
            return true;
        }

        boolean walkableOnTop = false;
        boolean solidTileUnderPostRendered = false;
        boolean hasPostRenderedTile = false;

        int topLayer;
        if (Handler.get().getWorld().hasPermissionsLayer()) {
            topLayer = Handler.get().getWorld().getLayers().length - 1;
            boolean allowed = isAllowedToMove(topLayer, x, y);
            if (!allowed) {
                return true;
            }
        } else {
            topLayer = Handler.get().getWorld().getLayers().length;
        }

        if (Handler.get().getWorld().hasShadowsLayer()) {
            if (Handler.get().getWorld().hasPermissionsLayer()) {
                topLayer = Handler.get().getWorld().getLayers().length - 2;
            } else {
                topLayer = Handler.get().getWorld().getLayers().length - 1;
            }
        }
//
//        checkPermissionTiles(x, y);

        // Special exclusion for 3C tiles when walking underneath (allow all movement)
        if (currentTile != null && currentTile.getPermission() != null && currentTile.getPermission().equalsIgnoreCase("3C")) {
            if (verticality == 0) {
                return false;
            }
        }

        for (int i = 0; i < topLayer; i++) {
            Tile t = Handler.get().getWorld().getTile(i, x, y);
            if (t != null && t.isSolid()) {
                walkableOnTop = false;
                solidTileUnderPostRendered = true;
            } else {
                if (t != null && t != Tile.tiles[0]) {
                    walkableOnTop = true;
                    if (t.isPostRendered()) {
                        hasPostRenderedTile = true;
                    }

                }
            }
        }
        if (hasPostRenderedTile && solidTileUnderPostRendered) {
            return true;
        }

        return !walkableOnTop;
    }


    /**
     * If the Entity is an NPC, this function returns only the name, else it returns name + combat info.
     */
    @Override
    public String[] getEntityInfo(Entity hoveringEntity) {
        if (isNpc()) {
            String[] name = new String[2];
            if (attackable) {
                name[0] = hoveringEntity.getName() + " (level-" + getCombatLevel() + ")";
            } else {
                name[0] = hoveringEntity.getName();
            }

            if (isAggroed()) {
                name[1] = "HP: " + (int) health + "/" + maxHealth;
            } else {
                String interactKey = KeyManager.interactKey == 0x20 ? "Space" : KeyEvent.getKeyText(KeyManager.interactKey);
                name[1] = "Press '" + interactKey + "' to interact";
            }
            return name;
        }
        String[] name = new String[2];
        name[0] = hoveringEntity.getName() + " (level-" + getCombatLevel() + ")";
        name[1] = "HP: " + (int) health + "/" + maxHealth;
        return name;
    }

    /**
     * Draws an Entity's information to an overlay at the top of the screen
     *
     * @param hoveringEntity
     * @param g
     */
    public void drawEntityOverlay(Entity hoveringEntity, Graphics2D g) {
        super.drawEntityOverlay(hoveringEntity, g);

        for (int i = 0; i < conditions.size(); i++) {
            Rectangle slotPos = new Rectangle(Handler.get().getWidth() / 2 - 100 + (i * ItemSlot.SLOTSIZE), 50, 32, 32);
            conditions.get(i).render(g, slotPos.x, slotPos.y);
            if (slotPos.contains(Handler.get().getMouse())) {
                Handler.get().getAbilityManager().getAbilityHUD().getStatusTooltip().render(conditions.get(i), g);
            }
        }

        int yOffset = 0;
        if (!conditions.isEmpty()) yOffset = 1;
        int resistanceCount = 0;
        // First draw Immunities
        for (int i = 0; i < immunities.size(); i++) {
            resistanceCount += ItemSlot.SLOTSIZE;
            Rectangle slotPos = new Rectangle(Handler.get().getWidth() / 2 - 100 + (i * ItemSlot.SLOTSIZE), 50 + (ItemSlot.SLOTSIZE * yOffset), 32, 32);
            immunities.get(i).render(g, slotPos.x, slotPos.y);
            if (slotPos.contains(Handler.get().getMouse())) {
                Handler.get().getAbilityManager().getAbilityHUD().getStatusTooltip().render(immunities.get(i), g);
            }
        }
        // Then draw buffs in the same row, but after the immunities
        for (int i = 0; i < buffs.size(); i++) {
            Rectangle slotPos = new Rectangle(resistanceCount + Handler.get().getWidth() / 2 - 100 + (i * ItemSlot.SLOTSIZE), 50 + (ItemSlot.SLOTSIZE * yOffset), 32, 32);
            buffs.get(i).render(g, slotPos.x, slotPos.y);
            if (slotPos.contains(Handler.get().getMouse())) {
                Handler.get().getAbilityManager().getAbilityHUD().getStatusTooltip().render(buffs.get(i), g);
            }
        }
    }

    @Override
    public void interact() {
        super.interact();
        lookAtPlayer();
    }

    public void lookAtPlayer() {
        Player player = Handler.get().getPlayer();
        // If more than half a tile left or right of this NPC, look that direction
        if (player.getX() - this.x < -Tile.TILEWIDTH / 2d) {
            lastFaced = Direction.LEFT;
        } else if (player.getX() - this.x > Tile.TILEWIDTH / 2d) {
            lastFaced = Direction.RIGHT;
        }

        // If more than half a tile up or down of this NPC, look that direction
        if (player.getY() - this.y < -Tile.TILEWIDTH / 2d) {
            lastFaced = Direction.UP;
        } else if (player.getY() - this.y > Tile.TILEWIDTH / 2d) {
            lastFaced = Direction.DOWN;
        }

        direction = lastFaced;
    }

    @Override
    public void postRender(Graphics2D g) {
        if (Handler.debugAStar) {
            g.setColor(Color.BLACK);
            g.drawRect((int) (radius.x - Handler.get().getGameCamera().getxOffset()), (int) (radius.y - Handler.get().getGameCamera().getyOffset()), radius.width, radius.height);

            map.render(g);

            if (nodes != null) {
                for (Node n : nodes) {
                    g.setColor(Colors.pathFindingColor);
                    g.fillRect((int) (n.getX() * 32 - Handler.get().getGameCamera().getxOffset()), (int) (n.getY() * 32 - Handler.get().getGameCamera().getyOffset()), 32, 32);
                }
            }
        }
    }

    public void addRuntimeProjectile(Projectile p) {
        toBeAdded.add(p);
    }

    protected void tickProjectiles() {
        if (projectiles.size() < 1) {
            if (toBeAdded.size() < 1) {
                return;
            }
        }

        if (projectiles.addAll(toBeAdded)) {
            toBeAdded.clear();
        }

        Iterator<Projectile> it = projectiles.iterator();
        Collection<Projectile> deleted = new ArrayList<>();
        Player player = Handler.get().getPlayer();
        while (it.hasNext()) {
            Projectile p = it.next();
            p.tick();
            if (!p.isActive()) {
                deleted.add(p);
            }

            for (Entity e : Handler.get().getWorld().getEntityManager().getEntities()) {
                if (p.verticality == player.verticality && p.getCollisionBounds(0, 0).intersects(e.getCollisionBounds(0, 0)) && p.isActive()) {
                    if (e.equals(this))
                        continue;
                    if (!e.equals(player)) {
                        if (!p.isPiercing()) {
                            p.setActive(false);
                            return;
                        }
                        continue;
                    }

                    if (!p.getHitCreatures().contains(player)) {
                        // If damageType is null, then we don't deal damage
                        if (p.getDamageType() != null) {
                            if (p.getAbility() != null) {
                                player.damage(p.getDamageType(), this, p.getAbility());
                            } else {
                                player.damage(p.getDamageType(), this);
                            }
                        }

                        if (p.getImpactSound() != null) {
                            Handler.get().playEffect(p.getImpactSound(), p.getImpactVolume());
                        }
                    }


                    p.setHitCreature(player);

                    if (!p.isPiercing()) {
                        p.setActive(false);
                    }

                    // Apply special effect if has one
                    if (p.getOnImpact() != null) {
                        if (!p.getHitCreatures().contains(player)) {
                            p.getOnImpact().impact(player);
                        }
                    }

                    p.getHitCreatures().add(player);
                }
            }
        }

        projectiles.removeAll(deleted);
    }

    public void tickAnimation() {
        if (chatDialogue == null && aLeft != null && aRight != null && aDown != null && aUp != null && aDefault != null) {
            aDefault.tick();
            aDown.tick();
            aUp.tick();
            aLeft.tick();
            aRight.tick();
        }
    }

    public void tick() {
        tickAnimation();

        if (inCombat) {
            combatTimer++;
        }

        if (combatTimer >= 300) {
            inCombat = false;
            combatTimer = 0;
        }

        // If we're interacting (chatdialogue = null), don't do movement logic
        if (attackable && chatDialogue == null) {
            radius.setLocation((int) x - xRadius, (int) y - yRadius);
            tickProjectiles();
            combatStateManager();
        } else if (walker && chatDialogue == null) {
            randomWalk();
        }
    }

    /*
     * Walks into random directions at given intervals
     */
    protected void randomWalk() {
        walkingTimer++;
        int i = (Handler.get().getRandomNumber(60, 90));
        if (walkingTimer % i == 0) {
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
            walkingTimer = 0;
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

    public void findPath() {
        if (state == CombatState.BACKTRACK) {
            nodes = map.findPath((int) ((x + width / 4) / 32) - (xSpawn - pathFindRadiusX) / 32, (int) ((y + height / 4) / 32) - (ySpawn - pathFindRadiusY) / 32,
                    ((xSpawn + width / 4) / 32) - (xSpawn - pathFindRadiusX) / 32, ((ySpawn + height / 4) / 32) - (ySpawn - pathFindRadiusY) / 32);
        } else {
            int playerX = Math.round((((int) Handler.get().getPlayer().getX() + 4) / 32)) - (xSpawn - pathFindRadiusX) / 32;
            int playerY = Math.round((((int) Handler.get().getPlayer().getY() + 4) / 32)) - (ySpawn - pathFindRadiusY) / 32;

            if (playerX == map.getNodes().length || playerY == map.getNodes().length) {
                nodes = map.findPath((int) ((x + width / 4) / 32) - (xSpawn - pathFindRadiusX) / 32, (int) ((y + height / 4) / 32) - (ySpawn - pathFindRadiusY) / 32,
                        ((playerX + width / 4) / 32) - (xSpawn - pathFindRadiusX) / 32, ((playerY + height / 4) / 32) - (ySpawn - pathFindRadiusY) / 32);
            } else {
                nodes = map.findPath((int) ((x + width / 4) / 32) - (xSpawn - pathFindRadiusX) / 32, (int) ((y + height / 4) / 32) - (ySpawn - pathFindRadiusY) / 32,
                        playerX, playerY);
            }
        }
    }

    /**
     * Manages the different combat states of a Creature (IDLE, PATHFINDING, ATTACKING, BACKTRACKING)
     */
    protected void combatStateManager() {

        // For non-aggressive monsters, stay idle walk until attacked
        if (!aggressive) {
            if (!isAggroed()) {
                randomWalk();
                return;
            }
        }

        if (damaged) {
            state = CombatState.PATHFINDING;
        }

        Player player = Handler.get().getPlayer();

        // If the player is within the A* map AND moves within the aggro range, state = pathfinding (walk towards goal)
        if (player.getCollisionBounds(0, 0).intersects(getRadius()) && player.getCollisionBounds(0, 0).intersects(map.getMapBounds())) {
            state = CombatState.PATHFINDING;
        }

        if (player.verticality != this.verticality) {
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

    protected boolean isInAttackRange(Player player) {
        if (distanceToEntity(((int) this.getX() + this.getWidth() / 2), ((int) this.getY() + this.getHeight() / 2),
                ((int) player.getX() + player.getWidth() / 2),
                ((int) player.getY() + player.getHeight() / 2)) <= attackRange &&
                player.getCollisionBounds(0, 0).intersects(getRadius()) &&
                player.getCollisionBounds(0, 0).intersects(map.getMapBounds())) {

            return hasLineOfSight(this.x + this.width / 2d, this.y + this.height / 2d, player.x + 16, player.y + 16);
        }

        return false;
    }

    protected boolean hasLineOfSight(double x1, double y1, double x2, double y2) {
        double m = ((double) (y2 - y1)) / ((double) (x2 - x1));//slope
        double b = y1 - (m * ((double) x1));//vertical shift

        //Takes care of the domain we will loop between.
        //min and max will be assigned minX and maxX if the line is not vertical.
        //minY and maxY are assigned to min and max otherwise.
        int minX = (int) Math.min(x1, x2);//minimum x value we should consider
        int maxX = (int) Math.max(x1, x2);//maximum x value we should consider
        int minY = (int) Math.min(y1, y2);//minimum y value we should consider
        int maxY = (int) Math.max(y1, y2);//maximum y value we should consider
        int min = 0;
        int max = 0;
        boolean plugX = true; //if true, the line is not vertical.
        List<Point> points = new ArrayList<>(); //Store all points here

        if (x1 == x2) {//plug the y value instead the x, this is a vertical line.
            plugX = false;
            min = minY;
            max = maxY;
        } else {//dont change and plug x values.
            min = minX;
            max = maxX;
        }

        for (int i = min; i <= max; i++) {
            int obtained = 0;
            if (plugX) {//not a vertical line
                obtained = (int) Math.round((m * i + b));
//                System.out.println("x = " + i + "  ,  y = " + obtained);
                points.add(new Point(i, obtained));
                //Uncomment to see the full blue line.
//                g.drawLine(i, obtained, i, obtained);
            } else {//vertical line
                obtained = (int) Math.round((double) (i - b) / (double) m);
//                System.out.println("x = " + x1 + "  ,  y = " + i);
//                g.drawLine(x1, i, x1, i);//Uncomment to see the full blue line.
                points.add(new Point((int) x1, i));
            }
        }

        for (Point point : points) {
            double xPos = point.getX();
            double yPos = point.getY();
            if (collisionWithTile((int) (xPos / 32d), (int) (yPos / 32d))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Override this method in the creature's class
     */
    protected void checkAttacks() {

    }

    /**
     * Movement logic for following each node in the List nodes
     */
    public void followAStar() {
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

        if (next.getX() == (int) ((x + width / 4) / 32)) {
//            System.out.println("X == equal");
        } else if (next.getX() != (int) ((x + width / 4) / 32)) {
            xMove = (next.getX() < (int) ((x + width / 4) / 32) ? -speed : speed);
            if ((int) x % 32 == width / 4) {
                //x -= x % 32;
                if (!nodes.isEmpty())
                    nodes.remove(0);

                stuckTimerX = 0;
                yMove = 0;
            }
        }

        if (next.getY() == (int) ((y + height / 4) / 32)) {
//            System.out.println("Y == equal");
        } else if (next.getY() != (int) ((y + height / 4) / 32)) {
            yMove = (next.getY() < (int) ((y + height / 4) / 32) ? -speed : speed);
            if ((int) y % 32 == height / 4) {
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

    protected void checkMeleeHitboxes() {
        if (width > 32 && height > 32) {
            checkMeleeHitboxes(40, 44);
        } else {
            checkMeleeHitboxes(40, 40);
        }
    }


    protected void checkMeleeHitboxes(int boxWidth, int boxHeight) {
        double angle = getAngle();
        Rectangle ar;
        if (width > 32 && height > 32) {
            ar = new Rectangle((int) ((width - width / 2) * Math.cos(angle) + (int) this.x + width / 4), (int) ((height - height / 2) * Math.sin(angle) + (int) this.y + height / 2), boxWidth, boxHeight);
        } else {
            ar = new Rectangle((int) (32 * Math.cos(angle) + (int) this.x), (int) (32 * Math.sin(angle) + (int) this.y), boxWidth, boxHeight);
        }

        if (this.equals(Handler.get().getPlayer())) {
            for (Entity e : Handler.get().getWorld().getEntityManager().getEntities()) {
                if (e.equals(Handler.get().getPlayer()))
                    continue;
                if (!e.isAttackable())
                    continue;
                if (e.getVerticality() == Handler.get().getPlayer().getVerticality() && e.getCollisionBounds(0, 0).intersects(ar)) {
                    e.damage(DamageType.STR, this);
                }
            }
        } else {
            Player player = Handler.get().getPlayer();
            if (player.getVerticality() == this.getVerticality() && player.getFullBounds(0, 0).intersects(ar)) {
                player.damage(DamageType.STR, this);
            }
        }
    }

    protected void setMeleeSwing(Rectangle direction) {
        // The angle and speed of the projectile
        double angle;
        if (this.equals(Handler.get().getPlayer())) {
            angle = Math.atan2((direction.getY() + Handler.get().getGameCamera().getyOffset() - 16) - this.getY(), (direction.getX() + Handler.get().getGameCamera().getxOffset() - 16) - this.getX());
        } else {
            angle = Math.atan2((direction.getY() - 16) - this.getY(), (direction.getX() - 16) - this.getX());
        }
        // Set the rotation of the projectile in degrees (0 = RIGHT, 270 = UP, 180 = LEFT, 90 = DOWN)
        meleeDirection = Math.toDegrees(angle);
        if (meleeDirection < 0) {
            meleeDirection += 360d;
        }

        double xOffset = 1.0f * Math.cos(angle);
        double yOffset = 1.0f * Math.sin(angle);


        // meleeXOffset change RIGHT
        if (meleeDirection >= 270 || meleeDirection < 90) {
            meleeXOffset = (20d + (32d * (width / 32d - 1))) * xOffset;
            // meleeXOffset change LEFT
        } else if (meleeDirection >= 90 || meleeDirection < 270) {
            meleeXOffset = (20d + (32d * (width / 32d - 1))) * xOffset;
        }

        // meleeXOffset change UP
        if (meleeDirection >= 180 || meleeDirection <= 360) {
            meleeYOffset = (20d + (32d * (height / 32d - 1))) * yOffset;
            // meleeXOffset change DOWN
        } else if (meleeDirection >= 0 || meleeDirection < 180) {
            meleeYOffset = (20d + (32d * (height / 32d - 1))) * yOffset;
        }
    }

    protected double getRotation() {
        double angle = getAngle();
        // Set the rotation of the projectile in degrees (0 = RIGHT, 270 = UP, 180 = LEFT, 90 = DOWN)
        double rotation = Math.toDegrees(angle);
        if (rotation < 0) {
            rotation += 360d;
        }

        return rotation;
    }

    protected double getAngle() {
        double angle;
        if (this.equals(Handler.get().getPlayer())) {
            Rectangle direction = Handler.get().getMouse();
            angle = Math.atan2((direction.getY() + Handler.get().getGameCamera().getyOffset() - 16) - this.getY(), (direction.getX() + Handler.get().getGameCamera().getxOffset() - 16) - this.getX());
        } else {
            Rectangle direction = new Rectangle((int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY(), 1, 1);
            angle = Math.atan2((direction.getY() - 16) - this.getY(), (direction.getX() - 16) - this.getX());
        }
        return angle;
    }

    /*
     * Regenerates health
     */
    public void regenHealth() {
        if (health == maxHealth) {
            return;
        }

        double percentageHealthPerSec = 0.1; // Regen 10% health per second
        double frames = 60.0;
        double regen = (double) maxHealth * percentageHealthPerSec / frames;

        // If current health is higher than your max health value, degenerate health
        if (health > maxHealth) {

            if (health - regen > maxHealth) {
                health -= regen;
            } else {
                health = maxHealth;
            }
        }

        // If current health is lower than your max health value, regenerate health
        if (health < maxHealth) {

            if (health + regen < maxHealth) {
                health += regen;
            } else {
                health = maxHealth;
            }

        }
    }

    public void castAbility(Ability ability) {
        if (ability.isOnCooldown() || ability.isChanneling()) {
            return;
        }
        if (ability.isSelectable()) {
            ability.setSelected(true);
        }
        if (!Handler.get().getAbilityManager().getActiveAbilities().contains(ability)) {
            Handler.get().getAbilityManager().getActiveAbilities().add(ability);
            ability.setCaster(this);
        }
    }

    public void clearConditions() {
        for (Condition c : conditions) {
            c.setActive(false);
        }
    }

    public void clearBuffs() {
        for (Buff b : buffs) {
            b.setActive(false);
        }
    }

    public boolean isAggroed() {
        return damaged || inCombat || state != CombatState.IDLE;
    }

    // GETTERS + SETTERS

    public double getxMove() {
        return xMove;
    }

    public void setxMove(double xMove) {
        this.xMove = xMove;
    }

    public double getyMove() {
        return yMove;
    }

    public void setyMove(double yMove) {
        this.yMove = yMove;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        if (speed < 0.0)
            speed = 0.0;
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

        // Change max HP as well
        int previousMaxHP = maxHealth;
        maxHealth = DEFAULT_HEALTH + vitality * 4;
        if (health >= previousMaxHP) {
            health = maxHealth;
        }
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;

    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public int getCombatLevel() {
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

    protected Rectangle getRadius() {
        return radius;
    }

    public void setRadius(Rectangle radius) {
        this.radius = radius;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(List<Projectile> projectiles) {
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
        this.direction = lastFaced;
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

    public List<Resistance> getImmunities() {
        return immunities;
    }

    public void setImmunities(List<Resistance> immunities) {
        this.immunities = immunities;
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

    public int getxSpawn() {
        return xSpawn;
    }

    public int getySpawn() {
        return ySpawn;
    }

    public int getLevelByElement(CharacterStats element) {
        switch (element) {
            case Water:
                return waterLevel;
            case Air:
                return airLevel;
            case Fire:
                return fireLevel;
            case Earth:
                return earthLevel;
            default:
                throw new IllegalArgumentException("Enter an element, not a combat style.");
        }
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public int getFireLevel() {
        return fireLevel;
    }

    public void setFireLevel(int fireLevel) {
        this.fireLevel = fireLevel;
    }

    public int getAirLevel() {
        return airLevel;
    }

    public void setAirLevel(int airLevel) {
        this.airLevel = airLevel;
    }

    public int getEarthLevel() {
        return earthLevel;
    }

    public void setEarthLevel(int earthLevel) {
        this.earthLevel = earthLevel;
    }

    public boolean isMovementAllowed() {
        return movementAllowed;
    }

    public void setMovementAllowed(boolean movementAllowed) {
        if (this instanceof Player) {
            Player.isMoving = false;
        }
        this.movementAllowed = movementAllowed;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public Tile getPreviousTile() {
        return previousTile;
    }

    public void setPreviousTile(Tile previousTile) {
        this.previousTile = previousTile;
    }

    public Map<Tile, Point> getPostRenderTiles() {
        return postRenderTiles;
    }

    public void setPostRenderTiles(Map<Tile, Point> postRenderTiles) {
        this.postRenderTiles = postRenderTiles;
    }

    public Direction getOriginalDirection() {
        return originalDirection;
    }

    public void setOriginalDirection(Direction originalDirection) {
        this.originalDirection = originalDirection;
    }

    private void writeObject(ObjectOutputStream stream)
            throws IOException {
        this.postRenderTiles = new HashMap<>();
        this.initialTileSetup = false;
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream serialized) throws ClassNotFoundException, IOException {
        serialized.defaultReadObject();
        this.postRenderTiles = new HashMap<>();
        this.initialTileSetup = false;
    }
}
