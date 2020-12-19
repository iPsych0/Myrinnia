package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.worlds.World;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class NPCSpawnTile extends StaticEntity {

    private List<Entity> entitiesToSpawn = new ArrayList<>();
    private long timeAllKilled;
    private boolean respawnTimerStarted;
    private Player player;
    private boolean hasSpawned;
    private List<String> names;
    private List<Class<?>> clazzez;
    private List<Rectangle> coords;
    private List<Integer> levels;
    private List<String> animations;
    private String itemsShop;

    public NPCSpawnTile(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = false;
        attackable = false;
        isNpc = false;
        setOverlayDrawn(false);

        coords = new ArrayList<>();
        String[] split;
        levels = new ArrayList<>();
        animations = new ArrayList<>();

        if (jsonFile == null) {
            System.err.println("Please enter [X,Y,W,H] in jsonFile field.");
            return;
        }

        split = jsonFile.split(",");
        if (split.length % 4 != 0) {
            System.err.println("Please enter X,Y,W,H,X,Y,W,H. Coordinates must be 4 digits each.");
            return;
        }
        for (int i = 0; i < split.length; i += 4) {
            coords.add(new Rectangle(
                    Integer.parseInt(split[i]) * 32,
                    Integer.parseInt(split[i + 1]) * 32,
                    Integer.parseInt(split[i + 2]),
                    Integer.parseInt(split[i + 3])
            ));
        }

        // If we entered only 1 anim, but more sets of coordinates, we can assume that we should duplicate the anims
        String[] anims = animation.split(",");
        animations.addAll(Arrays.asList(anims));
        for (int i = animations.size(); i < coords.size(); i++) {
            animations.add(animations.get(0));
        }

        if (itemsShop == null) {
            System.err.println("Please enter the combat levels in the itemsShop field, comma separated: [11,12,13,14].");
            return;
        }

        this.itemsShop = itemsShop;
        String[] levelSplit = itemsShop.split(",");
        for (String lvl : levelSplit) {
            levels.add(Integer.parseInt(lvl));
        }

        // If we entered only 1 level, but more sets of coordinates, we can assume that we should duplicate the levels
        for (int i = levels.size(); i < coords.size(); i++) {
            levels.add(levels.get(0));
        }

        // Get the names & classes
        String[] namesSplit = name.split(",");
        names = new ArrayList<>(Arrays.asList(namesSplit));
        clazzez = getClazzes(namesSplit);
        if (clazzez.isEmpty()) {
            System.err.println("Could not load Entity: " + name + " - in NPCSpawnTile.");
        }

        // If we entered only 1 name, but more sets of coordinates, we can assume that we should duplicate the type of monster
        for (int i = names.size(); i < coords.size(); i++) {
            names.add(names.get(0));
            clazzez.add(clazzez.get(0));
        }

        player = Handler.get().getPlayer();
    }

    @Override
    public Rectangle2D getCollisionBounds(double xOffset, double yOffset) {
        return super.getCollisionBounds(-Handler.get().getWorld().getWidth(), -Handler.get().getWorld().getHeight());
    }

    private void initEnemy(String name, Class<?> c, Rectangle coords, Integer level, String animation, String itemsShop, String direction) throws Exception {
        // Get all constructors
        Constructor[] cstr = c.getDeclaredConstructors();
        Constructor cst = null;

        // Use default constructor if no custom properties
        int constructorArguments = 10;
        if (Creature.class.isAssignableFrom(c)) {
            // For creatures, call
            constructorArguments++;
        }

        for (Constructor t : cstr) {
            if (t.getParameterCount() == constructorArguments) {
                cst = t;
                break;
            }
        }

        if (level == null) {
            level = 1;
        }

        // Invoke the right constructor based on arguments
        Entity e;
        if (constructorArguments == 10) {
            e = (Entity) cst.newInstance(coords.x, coords.y, coords.width, coords.height, name, level, dropTable, jsonFile, animation, itemsShop);
        } else {
            e = (Entity) cst.newInstance(coords.x, coords.y, coords.width, coords.height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        }

        entitiesToSpawn.add(e);

        // Publish a message that the NPC was spawned
        String article;
        name = e.getName();
        if (name.startsWith("A") || name.startsWith("E") || name.startsWith("I") || name.startsWith("O") || name.startsWith("U")) {
            article = "An ";
        } else {
            article = "A ";
        }
        Handler.get().sendMsg(article + name + " appeared!");
    }

    private List<Class<?>> getClazzes(String[] classNames) {
        List<Class<?>> classes = new ArrayList<>();
        for (String className : classNames) {
            String[] packages = {"npcs.", "creatures.", "statics."};
            Class<?> c = null;
            for (int i = 0; i < packages.length; i++) {
                try {
                    c = Class.forName("dev.ipsych0.myrinnia.entities." + packages[i] + className);
                    break;
                } catch (Exception e) {
                    // Only use exception when the class is in none of the 3 packages mentioned above
                    if (i == packages.length - 1) {
                        e.printStackTrace();
                        System.err.println("Could not find Entity '" + className + "' in any package. (World: " + Handler.get().getWorld().getWorldPath() + ")");
                    }
                }
            }
            classes.add(c);
        }

        return classes;
    }

    @Override
    public void tick() {
        long currentTime = System.currentTimeMillis();
        int deadCount = 0;

        if (!hasSpawned && this.getFullBounds(0, 0).contains(player.getCollisionBounds(0, 0))) {
            hasSpawned = true;
            respawnAll();
        }

        if (hasSpawned) {
            Iterator<Entity> entities = entitiesToSpawn.iterator();
            while (entities.hasNext()) {
                Entity e = entities.next();
                // Keep track if all of them have died
                if (!e.isActive()) {
                    deadCount++;
                }
            }
        }

        // When all have died, clear the current managed Entities and re-init them
        if (hasSpawned && !respawnTimerStarted && deadCount == entitiesToSpawn.size()) {
            timeAllKilled = System.currentTimeMillis();
            respawnTimerStarted = true;
        }

        if (respawnTimerStarted) {
            // Assume respawn time of the first Entity provided (use default of 30s anyway)
            if (((currentTime - timeAllKilled) / 1000L) >= entitiesToSpawn.get(0).getRespawnTime()) {
                respawnTimerStarted = false;
                hasSpawned = false;
            }
        }
    }

    private void respawnAll() {
        World w = Handler.get().getWorld();

        entitiesToSpawn.clear();
        try {
            // Init a new instance of all enemies
            for (int i = 0; i < coords.size(); i++) {
                initEnemy(names.get(i), clazzez.get(i), coords.get(i), levels.get(i), animations.get(i), itemsShop, null);
                w.getEntityManager().addRuntimeEntity(entitiesToSpawn.get(i), false);
            }
        } catch (Exception exc) {
            System.err.println("Could not init entity.");
            exc.printStackTrace();
        }
    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {

    }
}
