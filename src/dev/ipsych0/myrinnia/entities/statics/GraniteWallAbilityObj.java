package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Timer;
import dev.ipsych0.myrinnia.utils.TimerHandler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.concurrent.TimeUnit;

public class GraniteWallAbilityObj extends StaticEntity {

    private double degrees;
    private Shape transformedShape;

    public GraniteWallAbilityObj(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = true;
        setOverlayDrawn(false);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        AffineTransform transform = new AffineTransform();
        AffineTransform old = g.getTransform();
        transform.rotate(Math.toRadians(degrees), x + width / 2d - Handler.get().getGameCamera().getxOffset(), y + height / 2d - Handler.get().getGameCamera().getyOffset());
        g.setTransform(transform);
        g.drawImage(Assets.rockSlide, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
        g.setTransform(old);
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

    public double getDegrees() {
        return degrees;
    }

    public void setDegrees(double degrees) {
        this.degrees = degrees;
    }

    public Shape getTransformedShape() {
        return transformedShape;
    }

    public void setTransformedShape(Shape transformedShape) {
        this.transformedShape = transformedShape;
    }

    public void addDespawnTimer(int milliseconds){
        TimerHandler.get().addTimer(new Timer(milliseconds, TimeUnit.MILLISECONDS, () -> this.active = false));
    }
}
