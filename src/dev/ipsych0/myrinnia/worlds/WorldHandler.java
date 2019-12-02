package dev.ipsych0.myrinnia.worlds;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.*;

public class WorldHandler implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4805050782549865233L;
    private List<World> worlds;
    private Map<Zone, World> worldsMap;

    public WorldHandler(World portAzure) {
        worlds = new ArrayList<>();
        worldsMap = new EnumMap<>(Zone.class);
        addWorld(portAzure); // Starting world

        initWorlds();
    }

    private void initWorlds() {
        // Add new worlds here
        addWorld(new World(Zone.Myrinnia, "/worlds/myrinnia_DUMMY_MAP.tmx")); // DUMMY WORLD, NO FUNCTIONALITY
        addWorld(new World(Zone.LakeAzure, "/worlds/lake_azure.tmx"));
        addWorld(new World(Zone.SunriseSands, "/worlds/sunrise_sands.tmx"));
        addWorld(new World(Zone.SunsetCove, "/worlds/sunset_cove.tmx"));
        addWorld(new World(Zone.SunshineCoast, "/worlds/sunshine_coast.tmx"));
        addWorld(new World(Zone.PortAzureInside, false, "/worlds/port_azure_inside.tmx"));
        addWorld(new World(Zone.MtAzure1, false, "/worlds/mt_azure1.tmx"));
        addWorld(new World(Zone.MtAzure2, false, "/worlds/mt_azure2.tmx"));
        addWorld(new World(Zone.MtAzure3, false, "/worlds/mt_azure3.tmx"));
        addWorld(new World(Zone.MtAzure3, false, "/worlds/mt_azure3.tmx"));
        addWorld(new World(Zone.ShamrockHarbour, "/worlds/shamrock_harbour.tmx"));
        addWorld(new World(Zone.ShamrockTown, "/worlds/shamrock_town.tmx"));
    }

    public void tick() {
        Iterator<World> it = worlds.iterator();
        while (it.hasNext()) {
            World w = it.next();
            w.tick();
        }
    }

    public void render(Graphics2D g) {
        Iterator<World> it = worlds.iterator();
        while (it.hasNext()) {
            World w = it.next();
            w.render(g);
        }
    }

    private void addWorld(World w) {
        worlds.add(w);
        worldsMap.put(w.getZone(), w);
    }

    public List<World> getWorlds() {
        return worlds;
    }

    public void setWorlds(ArrayList<World> worlds) {
        this.worlds = worlds;
    }

    public Map<Zone, World> getWorldsMap() {
        return worldsMap;
    }

    public void setWorldsMap(Map<Zone, World> worldsMap) {
        this.worldsMap = worldsMap;
    }
}
