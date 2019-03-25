package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.npcs.ShopKeeperNPC;
import dev.ipsych0.myrinnia.entities.statics.Rock;
import dev.ipsych0.myrinnia.entities.statics.SavingShrine;
import dev.ipsych0.myrinnia.entities.statics.Tree;
import dev.ipsych0.myrinnia.entities.statics.FishingSpot;
import dev.ipsych0.myrinnia.utils.MapLoader;

import java.awt.*;

public class TestLand extends World {

    /**
     *
     */
    private static final long serialVersionUID = 71527867632258853L;
    private Rectangle swampLandTile;
    private Rectangle islandTile;

    public TestLand(String path) {
        super(path);

        entityManager.addEntity(new ShopKeeperNPC("General Store", 732, 440));

        entityManager.addEntity(new Tree(360, 128));
        entityManager.addEntity(new Tree(328, 128));
        entityManager.addEntity(new Tree(296, 192));
        entityManager.addEntity(new Tree(296, 160));

        entityManager.addEntity(new Rock(448, 576));

        entityManager.addEntity(new SavingShrine(200, 200));

        entityManager.addEntity(new FishingSpot(112, 928));

        swampLandTile = new Rectangle(0, 70, 32, 350);
        islandTile = new Rectangle(1568, 70, 32, 350);
    }

    @Override
    public void tick() {
        if (Handler.get().getWorld() == this) {
            super.tick();

            if (standingOnTile(swampLandTile)) {
                Handler.get().goToWorld(Zone.SwampLand, 1490, 1305);
            }

            if (standingOnTile(islandTile)) {
                Handler.get().goToWorld(Zone.Island, 800, 750);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (Handler.get().getWorld() == this) {
            super.render(g);

            g.drawRect((int) (swampLandTile.x - Handler.get().getGameCamera().getxOffset()), (int) (swampLandTile.y - Handler.get().getGameCamera().getyOffset()), 32, 350);
            g.drawRect((int) (islandTile.x - Handler.get().getGameCamera().getxOffset()), (int) (islandTile.y - Handler.get().getGameCamera().getyOffset()), 32, 350);
        }
    }
}
