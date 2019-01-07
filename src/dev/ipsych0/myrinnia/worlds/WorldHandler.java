package dev.ipsych0.myrinnia.worlds;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class WorldHandler implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4805050782549865233L;
    private ArrayList<World> worlds;
    private HashMap<Zone, World> worldsMap;

    public WorldHandler(Island island) {
        worlds = new ArrayList<World>();
        worldsMap = new HashMap<Zone, World>();
        addWorld(island); // Starting world

        initWorlds();
    }

    private void initWorlds() {
        // Add new worlds here
		addWorld(new TestLand("res/worlds/testmap2.tmx"));
		addWorld(new Myrinnia("res/worlds/island.tmx")); // DUMMY WORLD, NO FUNCTIONALITY
        addWorld(new SwampLand("res/worlds/testmap.tmx"));
        addWorld(new IslandUnderground("res/worlds/island_indoors.tmx"));

        try {
            worlds.sort(Comparator.comparing(o -> o.getClass().getSimpleName().toLowerCase()));

            List<Zone> zoneEnum = Arrays.asList(Zone.values());
            zoneEnum.sort(Comparator.comparing(o -> o.toString().toLowerCase()));

            for (int i = 0; i < worlds.size(); i++) {
                worldsMap.put(zoneEnum.get(i), worlds.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR: World enum / World class match not found!");
        }

    }

    public void tick() {
        Iterator<World> it = worlds.iterator();
        while (it.hasNext()) {
            World w = it.next();
            w.tick();
        }
    }

    public void render(Graphics g) {
        Iterator<World> it = worlds.iterator();
        while (it.hasNext()) {
            World w = it.next();
            w.render(g);
        }
    }

    public void addWorld(World w) {
        worlds.add(w);
    }

    public ArrayList<World> getWorlds() {
        return worlds;
    }

    public void setWorlds(ArrayList<World> worlds) {
        this.worlds = worlds;
    }

    public HashMap<Zone, World> getWorldsMap() {
        return worldsMap;
    }

    public void setWorldsMap(HashMap<Zone, World> worldsMap) {
        this.worldsMap = worldsMap;
    }
}
