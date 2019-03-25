package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.worlds.data.World;

import java.awt.*;

public class SunsetCove extends World {

    public SunsetCove(String path){
        super(path);
    }

    @Override
    public void tick() {
        if (Handler.get().getWorld() == this) {
            super.tick();
        }
    }

    @Override
    public void render(Graphics g) {
        if (Handler.get().getWorld() == this) {
            super.render(g);
        }
    }
}
