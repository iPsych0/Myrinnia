package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.npcs.BankerNPC;
import dev.ipsych0.myrinnia.entities.npcs.ShopKeeperNPC;
import dev.ipsych0.myrinnia.entities.statics.Campfire;
import dev.ipsych0.myrinnia.entities.statics.CraftingStation;
import dev.ipsych0.myrinnia.utils.MapLoader;

import java.awt.*;

public class IslandUnderground extends World {

    /**
     *
     */
    private static final long serialVersionUID = -4712823915790438486L;
    private Rectangle house1Exit;
    private Rectangle house2Exit;
    private Rectangle house3Exit;
    private Rectangle beachHouse1Exit;
    private Rectangle beachCaveExit;
    private Rectangle stoneHouse1Exit;
    private Rectangle southwestVillageHouseExit;
    private Rectangle northwestVillageHouseExit;

    public IslandUnderground(String path) {
        super();

        this.worldPath = path;

        width = MapLoader.getMapWidth(path);
        height = MapLoader.getMapHeight(path);

        loadWorld(path);

        entityManager.addEntity(new ShopKeeperNPC("General Store", 3904, 6080));
        entityManager.addEntity(new Campfire(4960, 5438));
        entityManager.addEntity(new Campfire(6016, 5828));
        entityManager.addEntity(new Campfire(6016, 4900));
        entityManager.addEntity(new CraftingStation(6176, 5216));
        entityManager.addEntity(new BankerNPC(6016, 5056));

        house1Exit = new Rectangle(6016, 6192, 32, 32);
        house2Exit = new Rectangle(4960, 6320, 32, 32);
        house3Exit = new Rectangle(3904, 6320, 32, 32);
        beachHouse1Exit = new Rectangle(4960, 5584, 32, 32);
        beachCaveExit = new Rectangle(3728, 5392, 64, 32);
        stoneHouse1Exit = new Rectangle(6016, 5356, 32, 32);
        southwestVillageHouseExit = new Rectangle(4960, 4880, 32, 32);
        northwestVillageHouseExit = new Rectangle(3040, 6318, 32, 32);
    }

    @Override
    public void tick() {
        if (Handler.get().getWorld() == this) {
            super.tick();
            if (standingOnTile(house1Exit)) {
                Handler.get().goToWorld(Zone.Island, 5056, 5440);
            }

            if (standingOnTile(house2Exit)) {
                Handler.get().goToWorld(Zone.Island, 4608, 5400);
            }

            if (standingOnTile(house3Exit)) {
                Handler.get().goToWorld(Zone.Island, 4384, 5800);
            }

            if (standingOnTile(beachHouse1Exit)) {
                Handler.get().goToWorld(Zone.Island, 5856, 5824);
            }

            if (standingOnTile(beachCaveExit)) {
                Handler.get().goToWorld(Zone.Island, 2688, 6136);
            }

            if (standingOnTile(stoneHouse1Exit)) {
                Handler.get().goToWorld(Zone.Island, 3712, 5456);
            }

            if (standingOnTile(southwestVillageHouseExit)) {
                Handler.get().goToWorld(Zone.Island, 3776, 5822);
            }

            if (standingOnTile(northwestVillageHouseExit)) {
                Handler.get().goToWorld(Zone.Island, 4032, 5252);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (Handler.get().getWorld() == this) {
            super.render(g);

//			g.drawRect((int) (exit1.x - Handler.get().getGameCamera().getxOffset()), (int) (exit1.y - Handler.get().getGameCamera().getyOffset()), 32, 32);
//			g.drawRect((int) (exit2.x - Handler.get().getGameCamera().getxOffset()), (int) (exit2.y - Handler.get().getGameCamera().getyOffset()), 32, 32);
//			g.drawRect((int) (exit3.x - Handler.get().getGameCamera().getxOffset()), (int) (exit3.y - Handler.get().getGameCamera().getyOffset()), 32, 32);
        }
    }
}
