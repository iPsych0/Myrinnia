package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.AquaticCultist;
import dev.ipsych0.myrinnia.entities.creatures.Scorpion;
import dev.ipsych0.myrinnia.entities.npcs.AbilityMaster;
import dev.ipsych0.myrinnia.entities.npcs.PortAzureSailor;
import dev.ipsych0.myrinnia.entities.npcs.ShopKeeperNPC;
import dev.ipsych0.myrinnia.entities.npcs.TestNPC;
import dev.ipsych0.myrinnia.entities.statics.*;
import dev.ipsych0.myrinnia.utils.MapLoader;
import dev.ipsych0.myrinnia.worlds.data.World;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PortAzure extends World {

    private static final long serialVersionUID = -3850769561562549459L;

    private Rectangle sunsetCoveTile;
    private Rectangle sunriseSandsTile;
    private Rectangle lakeAzureTile;

    private Rectangle bottomRightHouseTile;
    private Rectangle bottomLeftHouseTile;
    private Rectangle topRightHouseTile;
    private Rectangle topLeftHouseTile;
    private Rectangle mayorHouseTile;

    private Rectangle playerHouseTile;

    public PortAzure(String path) {
        super(path);

        entityManager.addEntity(new Tree(240, 240));
        entityManager.addEntity(new Rock(182, 182));

        entityManager.addEntity(new Scorpion(928, 800));

//        entityManager.addEntity(new SavingShrine(2304, 1888));
        entityManager.addEntity(new AquaticCultist(2336, 1600));
//        entityManager.addEntity(new TestNPC(1440, 2560));
//        entityManager.addEntity(new AbilityMaster(1504, 2336));
//        entityManager.addEntity(new CraftingStation(1504, 2400));

        // Southern beach
        entityManager.addEntity(new FishingSpot(928, 2520));
        entityManager.addEntity(new FishingSpot(1312, 2584));
//        entityManager.addEntity(new PortAzureSailor(2464, 2526));

//        entityManager.addEntity(new Campfire(2080, 2600));

        // World Item Spawns
//        itemManager.addItem(Item.regularLogs.createItem(1536, 2592, 5), true);

        // Zone transition tiles

        sunsetCoveTile = new Rectangle(0, 160, 16, 400);
        sunriseSandsTile = new Rectangle(3184, 192, 16, 224);
        lakeAzureTile = new Rectangle(1472, 0, 320, 16);

        bottomRightHouseTile = new Rectangle(1312, 1984, 32, 32);
        bottomLeftHouseTile = new Rectangle(928, 1984, 32, 32);
        topRightHouseTile = new Rectangle(1312, 1536, 32, 32);
        topLeftHouseTile = new Rectangle(928, 1536, 32, 32);

        mayorHouseTile = new Rectangle(2080, 1816, 32, 32);

        playerHouseTile = new Rectangle(2752, 1984, 32, 32);

    }

    @Override
    public void tick() {
        if (Handler.get().getWorld().equals(this)) {
            super.tick();
            if(standingOnTile(sunsetCoveTile)){
                Handler.get().goToWorld(Zone.SunsetCove, 2176, 4736);
            }
            if(standingOnTile(bottomRightHouseTile)){
                Handler.get().goToWorld(Zone.PortAzureInside, 1216, 2640);
            }
            if(standingOnTile(bottomLeftHouseTile)){
                Handler.get().goToWorld(Zone.PortAzureInside, 416, 2640);
            }
            if(standingOnTile(topRightHouseTile)){
                Handler.get().goToWorld(Zone.PortAzureInside, 1216, 1808);
            }
            if(standingOnTile(topLeftHouseTile)){
                Handler.get().goToWorld(Zone.PortAzureInside, 416, 1808, "General Store");
            }
            if(standingOnTile(mayorHouseTile)){
                Handler.get().goToWorld(Zone.PortAzureInside, 1984, 2180, "Town Hall");
            }
            if(standingOnTile(playerHouseTile)){
                Handler.get().goToWorld(Zone.PortAzureInside, 2880, 2624);
            }
            if(standingOnTile(sunriseSandsTile)){
                Handler.get().goToWorld(Zone.SunriseSands, 32, 4672);
            }
            if(standingOnTile(lakeAzureTile)){
                Handler.get().goToWorld(Zone.LakeAzure, 1584, 4736);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (Handler.get().getWorld().equals(this)) {
            super.render(g);
        }
    }
}
