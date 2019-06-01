package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.worlds.data.World;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;

public class SunshineCoast extends World {

    private Rectangle sunsetCoveTile;
    private Rectangle sunsetCoveHiddenPathTile;

    public SunshineCoast(String path){
        super(path);

        sunsetCoveTile = new Rectangle(192, 1584, 512, 16);
        sunsetCoveHiddenPathTile = new Rectangle(738, 1584, 256, 16);
    }

    @Override
    public void tick() {
        if (Handler.get().getWorld() == this) {
            super.tick();
        }
        if(standingOnTile(sunsetCoveTile)){
            Handler.get().goToWorld(Zone.SunsetCove, 1728, 32);
        }
        if(standingOnTile(sunsetCoveHiddenPathTile)){
            Handler.get().goToWorld(Zone.SunsetCove, 2048, 32);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (Handler.get().getWorld() == this) {
            super.render(g);
        }
    }
}
