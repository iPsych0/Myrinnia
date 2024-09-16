package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;

import java.awt.*;
import java.util.Map;

public class GenericObject extends StaticEntity {

    public GenericObject(double x, double y, int width, int height, Map<String,String> props) {
        super(x, y, width, height, props);
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
        Handler.get().getWorld().getEntityManager().addEntity(new GenericObject(x, y, width, height, props));
    }

    @Override
    protected void updateDialogue() {

    }
}
