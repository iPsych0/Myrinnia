package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.npcs.BankerNPC;
import dev.ipsych0.myrinnia.entities.npcs.PortAzureMayor;
import dev.ipsych0.myrinnia.entities.npcs.ShopKeeperNPC;
import dev.ipsych0.myrinnia.worlds.data.World;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;

public class PortAzureInside extends World {

    private static final long serialVersionUID = -3850769561562549459L;

    public PortAzureInside(String path) {
        super(path);

        entityManager.addEntity(new ShopKeeperNPC("Lorraine's General Store",416, 1600));

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
