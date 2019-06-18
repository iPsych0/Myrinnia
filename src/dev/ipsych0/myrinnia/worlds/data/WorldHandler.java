package dev.ipsych0.myrinnia.worlds.data;

import dev.ipsych0.myrinnia.worlds.*;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class WorldHandler implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4805050782549865233L;
    private List<World> worlds;
    private Map<Zone, World> worldsMap;

    public WorldHandler(PortAzure portAzure) {
        worlds = new ArrayList<>();
        worldsMap = new EnumMap<>(Zone.class);
        addWorld(portAzure); // Starting world

        initWorlds();
    }

    private void initWorlds() {
        // Add new worlds here
		addWorld(new Myrinnia("/worlds/myrinnia_DUMMY_MAP.tmx")); // DUMMY WORLD, NO FUNCTIONALITY
        addWorld(new LakeAzure("/worlds/lake_azure.tmx"));
        addWorld(new SunriseSands("/worlds/sunrise_sands.tmx"));
        addWorld(new SunsetCove("/worlds/sunset_cove.tmx"));
        addWorld(new SunshineCoast("/worlds/sunshine_coast.tmx"));
        addWorld(new PortAzureInside("/worlds/port_azure_inside.tmx"));
        addWorld(new MtAzure1("/worlds/mt_azure1.tmx"));
        addWorld(new MtAzure2("/worlds/mt_azure2.tmx"));
        addWorld(new MtAzure3("/worlds/mt_azure3.tmx"));

        try {
            worlds.sort(Comparator.comparing(o -> o.getClass().getSimpleName().toLowerCase()));

            List<Zone> zoneEnum = Arrays.asList(Zone.values());
            zoneEnum.sort(Comparator.comparing(o -> o.toString().toLowerCase()));

            if(worlds.size() != zoneEnum.size()){
                throw new Exception();
            }

            for (int i = 0; i < worlds.size(); i++) {
                worldsMap.put(zoneEnum.get(i), worlds.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR: World enum / World class match not found!");
            System.exit(1);
        }

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
