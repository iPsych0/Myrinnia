package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.worlds.weather.Climate;
import dev.ipsych0.myrinnia.worlds.weather.climates.TemperateClimate;

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
        addWorld(new World.Builder(Zone.Myrinnia).build()); // DUMMY WORLD, NO FUNCTIONALITY
        addWorld(new World.Builder(Zone.LakeAzure).withClimate(new TemperateClimate()).build());
        addWorld(new World.Builder(Zone.SunriseSands).withClimate(new TemperateClimate()).build());
        addWorld(new World.Builder(Zone.SunsetCove).withClimate(new TemperateClimate()).build());
        addWorld(new World.Builder(Zone.SunshineCoast).withClimate(new TemperateClimate()).build());
        addWorld(new World.Builder(Zone.PortAzureInside).withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.MtAzure1).withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.MtAzure2).withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.MtAzure3).withoutDayNightCycle().withoutDayNightCycle().build());

        addWorld(new World.Builder(Zone.ShamrockHarbour).withClimate(new TemperateClimate()).build());
        addWorld(new World.Builder(Zone.ShamrockTown).withClimate(new TemperateClimate()).withTown().build());
        addWorld(new World.Builder(Zone.ShamrockTownInside).withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.ShamrockMines1).withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.ShamrockMines2).withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.ShamrockMines3).withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.ShamrockMinesBasin).withoutDayNightCycle().withClimate(Climate.CAVE).build());
        addWorld(new World.Builder(Zone.ShamrockCliffs).build());
        addWorld(new World.Builder(Zone.ShamrockWolfDen).withoutDayNightCycle().withClimate(Climate.CAVE).build());

        addWorld(new World.Builder(Zone.MalachiteHills).withClimate(new TemperateClimate()).build());
        addWorld(new World.Builder(Zone.MalachiteHideout).withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.MalachiteFields).withClimate(new TemperateClimate()).build());
        addWorld(new World.Builder(Zone.MalachiteInside).withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.MalachiteSlopes).withClimate(new TemperateClimate()).build());
        addWorld(new World.Builder(Zone.MalachitePass).withClimate(new TemperateClimate()).build());
        addWorld(new World.Builder(Zone.MalachiteOverpass).withClimate(new TemperateClimate()).build());

        addWorld(new World.Builder(Zone.CelenorForestEdge).build());
        addWorld(new World.Builder(Zone.CelenorForestThicket).build());
        addWorld(new World.Builder(Zone.CelenorForestShrine).withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.Celewynn).withTown().build());
        addWorld(new World.Builder(Zone.CelewynnInside).withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.CelenorSeaboard).withClimate(new TemperateClimate()).build());
        addWorld(new World.Builder(Zone.CelenorCaves).withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.AemirRiverflank).withClimate(new TemperateClimate()).build());
        addWorld(new World.Builder(Zone.WardensCabin).withoutDayNightCycle().build());

        addWorld(new World.Builder(Zone.CelenorForestCreek).withoutDayNightCycle().withClimate(Climate.CELENOR_FOREST).build());
        addWorld(new World.Builder(Zone.CelenorForestLandslide).withoutDayNightCycle().withClimate(Climate.CELENOR_FOREST).build());
        addWorld(new World.Builder(Zone.Fyddnymed).withoutDayNightCycle().withClimate(Climate.CELENOR_FOREST).build());
        addWorld(new World.Builder(Zone.FyddnymedCanopy).withoutDayNightCycle().withClimate(Climate.CELENOR_FOREST).build());

        addWorld(new World.Builder(Zone.StozarsDescent).withClimate(new TemperateClimate()).build());
        addWorld(new World.Builder(Zone.StozarsCauseway).withClimate(new TemperateClimate()).build());
        addWorld(new World.Builder(Zone.StozarsQuarry).withClimate(new TemperateClimate()).build());
        addWorld(new World.Builder(Zone.StozarInside).withoutDayNightCycle().build());
        addWorld(new World.Builder(Zone.BonebladeBadlands).withClimate(Climate.SUNNY, 0.9).withClimate(Climate.SANDSTORM, 0.1).build());
        addWorld(new World.Builder(Zone.LakeClayfall).withClimate(Climate.SUNNY, 0.9).withClimate(Climate.SANDSTORM, 0.1).build());
        addWorld(new World.Builder(Zone.RuinsOfTheForgotten).withClimate(Climate.HEAVY_SANDSTORM).build());
        addWorld(new World.Builder(Zone.ForgottenShore).withClimate(Climate.SUNNY).build());
        addWorld(new World.Builder(Zone.RedrockOutpost).withClimate(Climate.SUNNY, 0.9).withClimate(Climate.SANDSTORM, 0.1).build());
        addWorld(new World.Builder(Zone.RedrockInside).withoutDayNightCycle().build());
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
