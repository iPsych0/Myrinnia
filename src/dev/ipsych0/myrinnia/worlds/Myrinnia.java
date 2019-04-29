package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.worlds.data.World;

import java.awt.*;

/**
 * DUMMY MODEL CLASS FOR QUEST OVERVIEW CATEGORY, NO FUNCTIONALITY
 */
public class Myrinnia extends World {

    public Myrinnia(String path){
        super(path);
    }

    @Override
    public void tick() {
        if (Handler.get().getWorld() == this) {
            super.tick();
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (Handler.get().getWorld() == this) {
            super.render(g);
        }
    }
}
