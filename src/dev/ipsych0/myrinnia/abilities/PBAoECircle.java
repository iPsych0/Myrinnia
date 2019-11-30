package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;

import java.awt.geom.Ellipse2D;

class PBAoECircle extends Ellipse2D.Float {

    public PBAoECircle(int circleSize) {
        super(Handler.get().getPlayer().getX() + Handler.get().getPlayer().getWidth() / 2f - circleSize / 2f, Handler.get().getPlayer().getY() + Handler.get().getPlayer().getHeight() / 2f - circleSize / 2f, circleSize, circleSize);
    }
}
