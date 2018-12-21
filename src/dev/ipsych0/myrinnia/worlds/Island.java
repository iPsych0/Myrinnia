package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Scorpion;
import dev.ipsych0.myrinnia.entities.npcs.AbilityMaster;
import dev.ipsych0.myrinnia.entities.statics.*;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.utils.MapLoader;

import java.awt.*;

public class Island extends World {

    /**
     *
     */
    private static final long serialVersionUID = -5184904694109102870L;
    private Rectangle house1;
    private Rectangle house2;
    private Rectangle house3;
    private Rectangle beachHouse;
    private Rectangle beachCave;
    private Rectangle stoneHouse1;
    private Rectangle southwestVillageHouse;
    private Rectangle northwestVillageHouse;


    public Island(String path) {
        super();

        this.worldPath = path;

        width = MapLoader.getMapWidth(path);
        height = MapLoader.getMapHeight(path);

        loadWorld(path);

        entityManager.addEntity(new Tree(5216, 5536));

        entityManager.addEntity(new Rock(5280, 5536));

//		entityManager.addEntity(new Scorpion(4960, 5700));
        entityManager.addEntity(new Scorpion(4640, 5740));
//		entityManager.addEntity(new Scorpion(4600, 5740));
//		entityManager.addEntity(new Scorpion(4640, 5780));
//		entityManager.addEntity(new Scorpion(4600, 5780));

        entityManager.addEntity(new TeleportShrine(5056, 5532));
        entityManager.addEntity(new AbilityMaster(5088, 5532));

        // Southern cliffs
        entityManager.addEntity(new Whirlpool(1280, 6320));
        entityManager.addEntity(new Whirlpool(4832, 6320));
        entityManager.addEntity(new Whirlpool(5264, 6240));

        entityManager.addEntity(new WaterToBridgePart(3584, 4320));
        entityManager.addEntity(new WaterToBridgePart(3584, 4352));
        entityManager.addEntity(new WaterToBridgePart(3584, 4384));

        entityManager.addEntity(new DirtHole(3360, 3136));

        house1 = new Rectangle(5056, 5424, 32, 32);
        house2 = new Rectangle(4608, 5360, 32, 32);
        house3 = new Rectangle(4384, 5776, 32, 32);
        beachHouse = new Rectangle(5856, 5808, 32, 32);
        beachCave = new Rectangle(2688, 6120, 32, 32);
        stoneHouse1 = new Rectangle(3712, 5440, 32, 32);
        southwestVillageHouse = new Rectangle(3776, 5784, 32, 32);
        northwestVillageHouse = new Rectangle(4032, 5216, 32, 32);

        // World Item Spawns
        itemManager.addItem(Item.regularLogs.createItem(5056, 5596, 5), true);

    }

    @Override
    public void tick() {
        if (Handler.get().getWorld().equals(this)) {
            super.tick();
            if (standingOnTile(house1)) {
                Handler.get().goToWorld(Zone.IslandUnderground, 6016, 6140);
            }
//			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(house1)){
//				Handler.get().getPlayer().setX(6016);
//				Handler.get().getPlayer().setY(6140);
//				Handler.get().setWorld(Handler.get().getWorldHandler().getWorlds().get(3));
//				Handler.get().getWorld().setHandler(handler);
//			}

            if (standingOnTile(house2)) {
                Handler.get().goToWorld(Zone.IslandUnderground, 4960, 6272);
            }

            if (standingOnTile(house3)) {
                Handler.get().goToWorld(Zone.IslandUnderground, 3904, 6272);
            }

            if (standingOnTile(beachHouse)) {
                Handler.get().goToWorld(Zone.IslandUnderground, 4960, 5552);
            }

            if (standingOnTile(beachCave)) {
                Handler.get().goToWorld(Zone.IslandUnderground, 3744, 5360);
            }

            if (standingOnTile(stoneHouse1)) {
                Handler.get().goToWorld(Zone.IslandUnderground, 6016, 5312);
            }

            if (standingOnTile(southwestVillageHouse)) {
                Handler.get().goToWorld(Zone.IslandUnderground, 4960, 4848);
            }

            if (standingOnTile(northwestVillageHouse)) {
                Handler.get().goToWorld(Zone.IslandUnderground, 3040, 6288);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (Handler.get().getWorld() == this) {
            super.render(g);
//			g.drawRect((int) (house1.x - Handler.get().getGameCamera().getxOffset()), (int) (house1.y - Handler.get().getGameCamera().getyOffset()), 32, 32);
//			g.drawRect((int) (house2.x - Handler.get().getGameCamera().getxOffset()), (int) (house2.y - Handler.get().getGameCamera().getyOffset()), 32, 32);
//			g.drawRect((int) (house3.x - Handler.get().getGameCamera().getxOffset()), (int) (house3.y - Handler.get().getGameCamera().getyOffset()), 32, 32);

        }
    }
}
