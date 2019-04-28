package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.AquaticCultist;
import dev.ipsych0.myrinnia.entities.creatures.Scorpion;
import dev.ipsych0.myrinnia.entities.statics.*;
import dev.ipsych0.myrinnia.worlds.data.World;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;

public class PortAzure extends World {

    private static final long serialVersionUID = -3850769561562549459L;

    private Rectangle sunsetCoveTile;

    public PortAzure(String path) {
        super(path);

        entityManager.addEntity(new Tree(240, 240));
        entityManager.addEntity(new Rock(182, 182));

//		entityManager.addEntity(new Scorpion(4960, 5700));
        entityManager.addEntity(new Scorpion(928, 2336));
//		entityManager.addEntity(new Scorpion(4600, 5740));
//		entityManager.addEntity(new Scorpion(4640, 5780));
//		entityManager.addEntity(new Scorpion(4600, 5780));

        entityManager.addEntity(new SavingShrine(1536, 2560));
        entityManager.addEntity(new AquaticCultist(1832, 2560));
//        entityManager.addEntity(new TestNPC(1440, 2560));
//        entityManager.addEntity(new AbilityMaster(1504, 2336));
//        entityManager.addEntity(new ShopKeeperNPC("Lorraine's General Store",1696, 2592));
//        entityManager.addEntity(new CraftingStation(1504, 2400));

        // Southern beach
        entityManager.addEntity(new FishingSpot(832, 2868));
//        entityManager.addEntity(new Campfire(2080, 2600));

        // World Item Spawns
//        itemManager.addItem(Item.regularLogs.createItem(1536, 2592, 5), true);

        sunsetCoveTile = new Rectangle(0, 160, 16, 400);

    }

    @Override
    public void tick() {
        if (Handler.get().getWorld().equals(this)) {
            super.tick();
            if(standingOnTile(sunsetCoveTile)){
                Handler.get().goToWorld(Zone.SunsetCove, 1856, 4672);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (Handler.get().getWorld().equals(this)) {
            super.render(g);
        }
    }
}
