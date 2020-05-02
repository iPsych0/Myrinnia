package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.worlds.World;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.*;

public class NPCSpawnTile extends StaticEntity {

    private List<Entity> entitiesToSpawn = new ArrayList<>();
    private Map<Entity, Long> spawnTimes = new HashMap<>();
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

        String[] anims = animation.split(",");
        animations.addAll(Arrays.asList(anims));

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

        if (itemsShop == null) {
            System.err.println("Please enter the combat levels in the itemsShop field, comma separated: [11,12,13,14].");
            return;
        }

        this.itemsShop = itemsShop;
        String[] levelSplit = itemsShop.split(",");
        for (String lvl : levelSplit) {
            levels.add(Integer.parseInt(lvl));
        }

        // If we entered only 1 level, but two sets of coordinats, we can assume that we should duplicate the level
        for (int i = levels.size(); i < coords.size(); i++) {
            levels.add(levels.get(0));
        }

        // Get the names & classes
        String[] namesSplit = name.split(",");
        List<String> names = new ArrayList<>(Arrays.asList(namesSplit));
        clazzez = getClazzes(namesSplit);
        if (clazzez.isEmpty()) {
            System.err.println("Could not load Entity: " + name + " - in NPCSpawnTile.");
        }

        // If we entered only 1 name, but two levels or two sets of coordinats, we can assume that we should duplicate
        // the type of monster
        for (int i = names.size(); i < coords.size(); i++) {
            names.add(names.get(0));
            clazzez.add(clazzez.get(0));
        }

        try {
            for (int i = 0; i < (split.length / 4); i++) {
                initEnemy(names.get(i), clazzez.get(i), coords.get(i), levels.get(i), animation, itemsShop, null);
            }
        } catch (Exception e) {
            System.err.println("Could not init: " + name + " - in NPCSpawnTile.");
        }

        player = Handler.get().getPlayer();

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
        if (!hasSpawned && this.getFullBounds(0, 0).contains(player.getCollisionBounds(0, 0))) {
            hasSpawned = true;

            World w = Handler.get().getWorld();
            Iterator<Entity> entities = entitiesToSpawn.iterator();
            while (entities.hasNext()) {
                Entity e = entities.next();
                // First time add entities to the world plus respawn timers
                if (spawnTimes.size() != entitiesToSpawn.size()) {
                    w.getEntityManager().addRuntimeEntity(e, false);
                    spawnTimes.put(e, System.currentTimeMillis());
                } else {
                    // If the Entity died and it's time to respawn, make it available again if we trigger the tile.
                    if (((currentTime - e.getTimeOfDeath()) / 1000L) >= e.getRespawnTime()) {

                        // First check which index Entity is available for respawn
                        int index = 0;
                        for (int i = 0; i < entitiesToSpawn.size(); i++) {
                            Entity e2 = entitiesToSpawn.get(i);
                            if (e2.equals(e)) {
                                index = i;
                                break;
                            }
                        }

                        try {
                            // Init a new instance of that enemy
                            initEnemy(names.get(index), clazzez.get(index), coords.get(index), levels.get(index), animations.get(index), itemsShop, null);
                            // Add it to the world
                            w.getEntityManager().addRuntimeEntity(entitiesToSpawn.get(entitiesToSpawn.size() - 1), false);
                            spawnTimes.put(e, System.currentTimeMillis());
                        } catch (Exception exc) {
                            System.err.println("Could not init entity: " + names.get(index));
                            exc.printStackTrace();
                        }

                        // Remove the Entity from the current list of actively maintained
                        entities.remove();

                        w.getEntityManager().addRuntimeEntity(e, false);
                        spawnTimes.put(e, System.currentTimeMillis());
                    }
                }
            }
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
