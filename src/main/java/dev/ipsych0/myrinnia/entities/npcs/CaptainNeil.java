package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;

import java.awt.*;
import java.util.Map;

public class CaptainNeil extends Creature {

    public CaptainNeil(float x, float y, int width, int height, Map<String, String> props) {
        super(x, y, width, height, props);
        solid = true;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void die() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected void updateDialogue() {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {

    }
}
