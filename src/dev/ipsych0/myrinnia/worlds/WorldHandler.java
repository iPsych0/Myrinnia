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
        // Add new World.Builders here
        addWorld(new World.Builder(Zone.Myrinnia, "/worlds/myrinnia_DUMMY_MAP.tmx").build()); // DUMMY WORLD, NO FUNCTIONALITY
        addWorld(new World.Builder(Zone.LakeAzure, "/worlds/lake_azure.tmx").build());
        addWorld(new World.Builder(Zone.SunriseSands, "/worlds/sunrise_sands.tmx").build());
        addWorld(new World.Builder(Zone.SunsetCove, "/worlds/sunset_cove.tmx").build());
        addWorld(new World.Builder(Zone.SunshineCoast, "/worlds/sunshine_coast.tmx").build());
        addWorld(new World.Builder(Zone.PortAzureInside, "/worlds/port_azure_inside.tmx").withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.MtAzure1, "/worlds/mt_azure1.tmx").withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.MtAzure2, "/worlds/mt_azure2.tmx").withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.MtAzure3, "/worlds/mt_azure3.tmx").withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.MtAzure3, "/worlds/mt_azure3.tmx").withoutDayNightCycle().build());

        addWorld(new World.Builder(Zone.ShamrockHarbour, "/worlds/shamrock_harbour.tmx").build());
        addWorld(new World.Builder(Zone.ShamrockTown, "/worlds/shamrock_town.tmx").withTown().build());
        addWorld(new World.Builder(Zone.ShamrockTownInside, "/worlds/shamrock_town_inside.tmx").withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.ShamrockMines1, "/worlds/shamrock_mines1.tmx").withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.ShamrockMines2, "/worlds/shamrock_mines2.tmx").withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.ShamrockMines3, "/worlds/shamrock_mines3.tmx").withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.ShamrockMines3, "/worlds/shamrock_mines3.tmx").withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.ShamrockMinesBasin, "/worlds/shamrock_mines4.tmx").withoutDayNightCycle().withWeather(new Cave()).build());
        addWorld(new World.Builder(Zone.ShamrockCliffs, "/worlds/shamrock_cliffs.tmx").build());

        addWorld(new World.Builder(Zone.MalachiteHills, "/worlds/malachite_hills.tmx").build());
        addWorld(new World.Builder(Zone.MalachiteHideout,"/worlds/malachite_hideout.tmx").withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.MalachiteFields, "/worlds/malachite_fields.tmx").build());
        addWorld(new World.Builder(Zone.MalachiteInside, "/worlds/malachite_inside.tmx").withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.MalachiteSlopes, "/worlds/malachite_slopes.tmx").build());
        addWorld(new World.Builder(Zone.MalachitePass, "/worlds/malachite_pass.tmx").build());
        addWorld(new World.Builder(Zone.MalachiteOverpass, "/worlds/malachite_overpass.tmx").build());

        addWorld(new World.Builder(Zone.CelenorForestEdge, "/worlds/celenor_forest_edge.tmx").build());
        addWorld(new World.Builder(Zone.CelenorForestThicket, "/worlds/celenor_forest_thicket.tmx").build());
        addWorld(new World.Builder(Zone.CelenorForestShrine, "/worlds/celenor_shrine.tmx").withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.Celewynn, "/worlds/celewynn.tmx").withTown().build());
        addWorld(new World.Builder(Zone.CelewynnInside, "/worlds/celewynn_inside.tmx").withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.CelenorSeaboard, "/worlds/celenor_seaboard.tmx").build());
        addWorld(new World.Builder(Zone.CelenorCaves, "/worlds/celenor_caves.tmx").withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.AemirRiverflank, "/worlds/aemir_riverflank.tmx").build());
        addWorld(new World.Builder(Zone.WardensCabin, "/worlds/wardens_cabin.tmx").withoutDayNightCycle().build());
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
