package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.worlds.data.World;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;

public class SunsetCove extends World {

    private Rectangle portAzureTile;
    private Rectangle sunshineCoastTile;
    private Rectangle sunshineCoastHiddenPathTile;

    public SunsetCove(String path){
        super(path);

        portAzureTile = new Rectangle(1904, 4576, 16, 240);
        sunshineCoastTile = new Rectangle(1472, 0, 544, 16);
        sunshineCoastHiddenPathTile = new Rectangle(2048, 0, 32, 16);
    }

    @Override
    public void tick() {
        if (Handler.get().getWorld() == this) {
            super.tick();
            if(standingOnTile(portAzureTile)){
                Handler.get().goToWorld(Zone.PortAzure, 32, 320);
            }
            if(standingOnTile(sunshineCoastTile)){
                Handler.get().goToWorld(Zone.SunshineCoast, 480, 1536);
            }
            if(standingOnTile(sunshineCoastHiddenPathTile)){
                Handler.get().goToWorld(Zone.SunshineCoast, 768, 1536);
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
