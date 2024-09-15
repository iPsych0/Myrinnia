package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.worlds.World;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
public class NPCSpawnTile extends Creature {

    private List<Entity> entitiesToSpawn = new ArrayList<>();
    private long timeAllKilled;
    private boolean respawnTimerStarted;
    private Player player;
    private boolean hasSpawned;
    private List<String> names;
    private List<Class<?>> clazzez;
    private List<Rectangle> coords;
    private List<String> levels;
    private List<String> animations;
    private Rectangle spawnBounds;

    public NPCSpawnTile(double x, double y, int width, int height, Map<String, String> props) {
        super(x, y, width, height, props);
        solid = false;
        attackable = false;
        isNpc = false;
        walker = false;
        setOverlayDrawn(false);

        coords = new ArrayList<>();
        String[] split;
        levels = new ArrayList<>();
        animations = new ArrayList<>();

        if (jsonFile == null) {
            log.error("Please enter [X,Y,W,H] in jsonFile field.");
            return;
        }

        split = jsonFile.split(",");
        if (split.length % 4 != 0) {
            log.error("Please enter X,Y,W,H,X,Y,W,H. Coordinates must be 4 digits each.");
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

        if (animationTag == null) {
            log.error("Please provide the (animation) sprites for the NPC!");
        }

        // If we entered only 1 anim, but more sets of coordinates, we can assume that we should duplicate the anims
        String[] anims = animationTag.split(",");
        animations.addAll(Arrays.asList(anims));
        for (int i = animations.size(); i < coords.size(); i++) {
            animations.add(animations.get(0));
        }

        if (shopItemsFile == null) {
            log.error("Please enter the combat levels in the itemsShop field, comma separated: [11,12,13,14].");
            return;
        }

        levels.addAll(Arrays.asList(shopItemsFile.split(",")));

        // If we entered only 1 level, but more sets of coordinates, we can assume that we should duplicate the levels
        for (int i = levels.size(); i < coords.size(); i++) {
            levels.add(levels.getFirst());
        }

        // Get the names & classes
        String[] namesSplit = name.split(",");
        names = new ArrayList<>(Arrays.asList(namesSplit));
        clazzez = getClazzes(namesSplit);
        if (clazzez.isEmpty()) {
            log.error("Could not load Entity: {} - in NPCSpawnTile.", name);
        }

        // If we entered only 1 name, but more sets of coordinates, we can assume that we should duplicate the type of monster
        for (int i = names.size(); i < coords.size(); i++) {
            names.add(names.getFirst());
            clazzez.add(clazzez.getFirst());
        }

        player = Handler.get().getPlayer();

        spawnBounds = new Rectangle((int) x, (int) y, width, height);
    }

    @Override
    public Rectangle2D getCollisionBounds(double xOffset, double yOffset) {
        return super.getCollisionBounds(-10000, -10000);
    }

    @Override
    public Rectangle2D getFullBounds(double xOffset, double yOffset) {
        return super.getCollisionBounds(-10000, -10000);
    }

    private void initEnemy(String name, Class<?> c, Rectangle coords, String level, String animation, String itemsShop, String direction) throws Exception {
        // Get all constructors
        Map<String, String> props = new HashMap<>();
        props.put("name", name);
        props.put("level", level);
        props.put("animation", animation);
        props.put("dropTable", this.props.get("dropTable"));

        Constructor[] cstr = c.getDeclaredConstructors();
        Constructor cst = null;

        Entity e = null;
        for (Constructor t : cstr) {
            if (t.getParameterCount() == 5) {
                e = (Entity) t.newInstance(coords.x, coords.y, coords.width, coords.height, props);
                break;
            }
        }

        props.putIfAbsent("level", "1");

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
                        log.error("Exception", e);
                        log.error("Could not find Entity '{}' in any package. (World: {})", className, Handler.get().getWorld().getWorldPath());
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

        if (!hasSpawned && spawnBounds.contains(player.getCollisionBounds(0, 0))) {
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
                initEnemy(names.get(i), clazzez.get(i), coords.get(i), levels.get(i), animations.get(i), shopItemsFile, null);
                w.getEntityManager().addRuntimeEntity(entitiesToSpawn.get(i), false);
            }
        } catch (Exception exc) {
            log.error("Could not init entity.", exc);
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
