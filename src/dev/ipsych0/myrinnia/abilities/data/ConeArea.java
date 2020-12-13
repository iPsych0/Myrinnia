package dev.ipsych0.myrinnia.abilities.data;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;

public class ConeArea extends Arc2D.Double {
    private static Color aoeColor = new Color(0, 192, 0, 96);
    private Shape transformedShape;
    private int coneWidth, coneHeight;
    private Creature caster;
    private int xOffset, yOffset;
    private int radius = 90;

    public ConeArea(Creature caster, int coneWidth, int coneHeight, int xOffset, int yOffset, int radius){
        super(0,0, coneWidth, coneHeight, 0, 90, Arc2D.PIE);
        this.coneWidth = coneWidth;
        this.coneHeight = coneHeight;
        this.caster = caster;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.radius = radius;
    }

    public ConeArea(Creature caster, int coneWidth, int coneHeight, int xOffset, int yOffset){
        this(caster, coneWidth, coneHeight, xOffset, yOffset, 90);
    }

    public ConeArea(Creature caster, int coneWidth, int coneHeight){
        this(caster, coneWidth, coneHeight, 0, 0);
    }

    public void tick() {
    }

    public void render(Graphics2D g) {
        double theta = getTheta();

        this.setArcByCenter(caster.getX() + caster.getWidth() / 2 - Handler.get().getGameCamera().getxOffset(),
                caster.getY() + caster.getHeight() / 2 - Handler.get().getGameCamera().getyOffset(),
                radius, -45 + xOffset, -90 + yOffset, Arc2D.PIE);

        g.setColor(aoeColor);

        AffineTransform transform = new AffineTransform();
        AffineTransform old = g.getTransform();
        transform.rotate(Math.toRadians(theta), super.getCenterX(), super.getCenterY());
        transformedShape = transform.createTransformedShape(this);
        g.fill(transformedShape);
        g.setTransform(old);
    }

    public Shape getArea(){
        double theta = getTheta();
        this.setArcByCenter(caster.getX() + caster.getWidth() / 2,
                caster.getY() + caster.getHeight() / 2,
                radius, -45 + xOffset, -90 + yOffset, Arc2D.PIE);
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(theta), super.getCenterX(), super.getCenterY());
        transformedShape = transform.createTransformedShape(this);
        return transformedShape;
    }

    public double getTheta() {
        double angle;
        double casterX = caster.getX() + caster.getWidth() / 2d;
        double casterY = caster.getY() + caster.getHeight() / 2d;

        if (caster.equals(Handler.get().getPlayer())) {
            double targetX = Handler.get().getMouseManager().getMouseX() + Handler.get().getGameCamera().getxOffset();
            double targetY = Handler.get().getMouseManager().getMouseY() + Handler.get().getGameCamera().getyOffset();
            angle = Math.atan2(targetY - casterY, targetX - casterX);
        } else {
            double targetX = Handler.get().getPlayer().getX() - Handler.get().getGameCamera().getxOffset();
            double targetY = Handler.get().getPlayer().getY() - Handler.get().getGameCamera().getyOffset();
            angle = Math.atan2(targetY - casterY, targetX - casterX);
        }

        double theta = Math.toDegrees(angle);

        // Rotate by a quarter, so we always have the wall facing full length
        theta -= 90;

        // Wrap around 360 degrees
        if (theta < 0.0) {
            theta += 360;
        }

        return theta;
    }


}
