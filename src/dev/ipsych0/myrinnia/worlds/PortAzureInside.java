package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.npcs.BankerNPC;
import dev.ipsych0.myrinnia.worlds.data.World;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;

public class PortAzureInside extends World {

    private static final long serialVersionUID = -3850769561562549459L;

    private Rectangle bottomRightHouseExit;
    private Rectangle bottomLeftHouseExit;
    private Rectangle topRightHouseExit;
    private Rectangle topLeftHouseExit;

    private Rectangle mayorHouseExit;

    private Rectangle playerHouseExit;

    public PortAzureInside(String path) {
        super(path);

        entityManager.addEntity(new BankerNPC(544, 2496));

        bottomLeftHouseExit = new Rectangle(416, 2672, 32, 32);
        bottomRightHouseExit = new Rectangle(1216, 2672, 32, 32);
        topLeftHouseExit = new Rectangle(416, 1840, 32, 32);
        topRightHouseExit = new Rectangle(1216, 1840, 32, 32);

        mayorHouseExit = new Rectangle(1984, 2224, 32, 32);

        playerHouseExit = new Rectangle(2880, 2672, 32, 32);

    }

    @Override
    public void tick() {
        if (Handler.get().getWorld().equals(this)) {
            super.tick();
            if (standingOnTile(bottomRightHouseExit)) {
                Handler.get().goToWorld(Zone.PortAzure, 1312, 2016);
            }
            if (standingOnTile(bottomLeftHouseExit)) {
                Handler.get().goToWorld(Zone.PortAzure, 928, 2016);
            }
            if (standingOnTile(topRightHouseExit)) {
                Handler.get().goToWorld(Zone.PortAzure, 1312, 1568);
            }
            if (standingOnTile(topLeftHouseExit)) {
                Handler.get().goToWorld(Zone.PortAzure, 928, 1568);
            }
            if (standingOnTile(mayorHouseExit)) {
                Handler.get().goToWorld(Zone.PortAzure, 2080, 1848);
            }
            if (standingOnTile(playerHouseExit)) {
                Handler.get().goToWorld(Zone.PortAzure, 2752, 2016);
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
