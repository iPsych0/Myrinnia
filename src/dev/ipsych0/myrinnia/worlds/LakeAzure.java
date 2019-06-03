package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.worlds.data.World;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;

public class LakeAzure extends World {

    private Rectangle portAzureTile;
    private Rectangle sunriseSandsTile;

    public LakeAzure(String path){
        super(path);

        portAzureTile = new Rectangle(1376, 4784, 448, 16);
        sunriseSandsTile = new Rectangle(3184, 2496, 16, 160);
    }

    @Override
    public void tick() {
        if (Handler.get().getWorld() == this) {
            super.tick();
            if(standingOnTile(portAzureTile)){
                Handler.get().goToWorld(Zone.PortAzure, 1632, 32);
            }
            if(standingOnTile(sunriseSandsTile)){
                Handler.get().goToWorld(Zone.SunriseSands, 32, 2240);
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
