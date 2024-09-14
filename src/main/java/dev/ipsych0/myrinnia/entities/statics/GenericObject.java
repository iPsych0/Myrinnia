package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.entities.Entity;import dev.ipsych0.myrinnia.Handler;

import java.awt.*;

public class GenericObject extends Entity {

    public GenericObject() {
        
        solid = false;
        attackable = false;
        isNpc = true;
    }

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
        GenericObject respawn = new GenericObject();
        respawn.setX(x);
        respawn.setY(y);
        Handler.get().getWorld().getEntityManager().addEntity(respawn);
    }

    @Override
    protected void updateDialogue() {

    }
}
