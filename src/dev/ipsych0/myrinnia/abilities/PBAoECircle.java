package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;

import java.awt.geom.Ellipse2D;

class PBAoECircle extends Ellipse2D.Float {

    public PBAoECircle(Creature caster, int circleSize) {
        super((float) caster.getX() + caster.getWidth() / 2f - circleSize / 2f, (float) caster.getY() + caster.getHeight() / 2f - circleSize / 2f, circleSize, circleSize);
    }
}
