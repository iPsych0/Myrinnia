package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.entities.Entity;import dev.ipsych0.myrinnia.Handler;

import java.awt.*;

public class CollisionTile extends Entity {

    public CollisionTile() {
        
        solid = true;
        attackable = false;
        isNpc = false;
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
    }

    @Override
    protected void updateDialogue() {

    }
}
