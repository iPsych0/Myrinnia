package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.npcs.ShopKeeperNPC;
import dev.ipsych0.myrinnia.entities.statics.Rock;
import dev.ipsych0.myrinnia.entities.statics.SavingShrine;
import dev.ipsych0.myrinnia.entities.statics.Tree;
import dev.ipsych0.myrinnia.entities.statics.FishingSpot;
import dev.ipsych0.myrinnia.utils.MapLoader;

import java.awt.*;

public class SwampLand extends World {

    /**
     *
     */
    private static final long serialVersionUID = 3169176858936135534L;
    private Rectangle testLandTile;

    public SwampLand(String path) {
        super(path);

        entityManager.addEntity(new ShopKeeperNPC("General Store", 732, 640));

        entityManager.addEntity(new Tree(160, 128));
        entityManager.addEntity(new Tree(128, 128));
        entityManager.addEntity(new Tree(96, 192));
        entityManager.addEntity(new Tree(96, 160));

        entityManager.addEntity(new Rock(448, 576));

//		entityManager.addEntity(new Scorpion(handler, 160, 400));
//		entityManager.addEntity(new Scorpion(handler, 128, 800));
//		entityManager.addEntity(new Scorpion(handler, 128, 888));
//		entityManager.addEntity(new Scorpion(handler, 128, 944));
//		entityManager.addEntity(new Scorpion(handler, 190, 944));
//		entityManager.addEntity(new Scorpion(handler, 190, 888));
//		entityManager.addEntity(new Scorpion(handler, 190, 800));

        entityManager.addEntity(new SavingShrine(200, 200));

        entityManager.addEntity(new FishingSpot(672, 432));

        testLandTile = new Rectangle(1568, 1300, 32, 200);

    }

    @Override
    public void tick() {
        if (Handler.get().getWorld() == this) {
            super.tick();
            if (standingOnTile(testLandTile)) {
                Handler.get().goToWorld(Zone.TestLand, 60, 164);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (Handler.get().getWorld() == this) {
            super.render(g);

            g.drawRect((int) (testLandTile.x - Handler.get().getGameCamera().getxOffset()), (int) (testLandTile.y - Handler.get().getGameCamera().getyOffset()), 32, 168);
        }
    }
}
