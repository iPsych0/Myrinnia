package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;

import java.awt.*;
import java.awt.geom.Ellipse2D;

class AoECircle extends Ellipse2D.Float {

    private Rectangle bounds;

    public AoECircle(float x, float y, float w, float h) {
        super(x, y, w, h);
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    @Override
    public Rectangle getBounds(){
        return new Rectangle((int)(x + Handler.get().getGameCamera().getxOffset()), (int)(y + Handler.get().getGameCamera().getyOffset()), (int)width, (int)height);
    }
}
