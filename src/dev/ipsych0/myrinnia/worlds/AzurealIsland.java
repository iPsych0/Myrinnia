package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Scorpion;
import dev.ipsych0.myrinnia.entities.npcs.AbilityMaster;
import dev.ipsych0.myrinnia.entities.npcs.ShopKeeperNPC;
import dev.ipsych0.myrinnia.entities.npcs.TestNPC;
import dev.ipsych0.myrinnia.entities.statics.*;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.utils.MapLoader;

import java.awt.*;

public class AzurealIsland extends World {

    private static final long serialVersionUID = -3850769561562549459L;

    public AzurealIsland(String path) {
        super();

        this.worldPath = path;

        width = MapLoader.getMapWidth(path);
        height = MapLoader.getMapHeight(path);

        loadWorld(path);

        entityManager.addEntity(new Tree(240, 240));

        entityManager.addEntity(new Rock(182, 182));

//		entityManager.addEntity(new Scorpion(4960, 5700));
        entityManager.addEntity(new Scorpion(4640, 5740));
//		entityManager.addEntity(new Scorpion(4600, 5740));
//		entityManager.addEntity(new Scorpion(4640, 5780));
//		entityManager.addEntity(new Scorpion(4600, 5780));

        entityManager.addEntity(new SavingShrine(5056, 5532));
        entityManager.addEntity(new TestNPC(5056, 5564));
        entityManager.addEntity(new AbilityMaster(150, 150));
        entityManager.addEntity(new ShopKeeperNPC("Lorraine's General Store",150, 182));

        // Southern cliffs
        entityManager.addEntity(new Whirlpool(100, 180));
        entityManager.addEntity(new Whirlpool(4832, 6320));
        entityManager.addEntity(new Whirlpool(5264, 6240));

        entityManager.addEntity(new WaterToBridgePart(3584, 4320));
        entityManager.addEntity(new WaterToBridgePart(3584, 4352));
        entityManager.addEntity(new WaterToBridgePart(3584, 4384));

        entityManager.addEntity(new DirtHole(3360, 3136));

        // World Item Spawns
        itemManager.addItem(Item.regularLogs.createItem(5056, 5596, 5), true);

    }

    @Override
    public void tick() {
        if (Handler.get().getWorld().equals(this)) {
            super.tick();
        }
    }

    @Override
    public void render(Graphics g) {
        if (Handler.get().getWorld().equals(this)) {
            super.render(g);
        }
    }
}
