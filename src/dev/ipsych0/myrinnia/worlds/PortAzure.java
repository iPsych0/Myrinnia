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
//        entityManager.addEntity(new FishingSpot(928, 2520));
//        entityManager.addEntity(new FishingSpot(1312, 2584));
//        entityManager.addEntity(new PortAzureSailor(2464, 2526));

//        entityManager.addEntity(new Campfire(2080, 2600));

        // World Item Spawns
//        itemManager.addItem(Item.regularLogs.createItem(1536, 2592, 5), true);

    }

    @Override
    public void tick() {
        if (Handler.get().getWorld().equals(this)) {
            super.tick();
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (Handler.get().getWorld().equals(this)) {
            super.render(g);
        }
    }
}
