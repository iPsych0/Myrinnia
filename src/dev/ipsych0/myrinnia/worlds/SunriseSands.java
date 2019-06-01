package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.worlds.data.World;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;

public class SunriseSands extends World {

    private Rectangle portAzureTile;
    private Rectangle sunshineCoastTile;

    public SunriseSands(String path){
        super(path);

        portAzureTile = new Rectangle(0, 4544, 16, 256);
        sunshineCoastTile = new Rectangle(256, 0, 352, 16);
    }

    @Override
    public void tick() {
        if (Handler.get().getWorld().equals(this)) {
            super.tick();
            if(standingOnTile(portAzureTile)){
                Handler.get().goToWorld(Zone.PortAzure, 3136, 288);
            }
            if(standingOnTile(sunshineCoastTile)){
                Handler.get().goToWorld(Zone.SunshineCoast, 2880, 1536);
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