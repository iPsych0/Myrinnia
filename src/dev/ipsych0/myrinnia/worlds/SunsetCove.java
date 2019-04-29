package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.worlds.data.World;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;

public class SunsetCove extends World {

    private Rectangle portAzureTile;

    public SunsetCove(String path){
        super(path);

        portAzureTile = new Rectangle(1904, 4576, 16, 240);
    }

    @Override
    public void tick() {
        if (Handler.get().getWorld() == this) {
            super.tick();
            if(standingOnTile(portAzureTile)){
                Handler.get().goToWorld(Zone.PortAzure, 32, 320);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (Handler.get().getWorld() == this) {
            super.render(g);
        }
    }
}
