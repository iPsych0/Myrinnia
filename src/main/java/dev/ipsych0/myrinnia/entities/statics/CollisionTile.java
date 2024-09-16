package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;

import java.awt.*;
import java.util.Map;

public class CollisionTile extends Entity {

    public CollisionTile(double x, double y, int width, int height, Map<String,String> props) {
        super(x, y, width, height, props);
        solid = true;
        attackable = false;
        isNpc = false;
        staticNpc = true;
        respawnTime = 1L;
        overlayDrawn = false;
    }
// dit is een vogelhuis. Daar woont dus een vogel

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new CollisionTile(x, y, width, height, props));
    }

    @Override
    protected void updateDialogue() {

    }
}
