package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Timer;
import dev.ipsych0.myrinnia.utils.TimerHandler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GraniteWallAbilityObj extends StaticEntity {

    private double degrees;
    private Shape transformedShape;
    private Animation animation;
    private Creature caster;

    public GraniteWallAbilityObj(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = true;
        hasPolyBounds = true;
        setOverlayDrawn(false);
        this.animation = new Animation(64, Assets.graniteWall, true);
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
        if (!animation.isTickDone()) {
            animation.tick();
            if (degrees >= 91 && degrees <= 270) {
                transform.translate(0, -animation.getCurrentFrame().getHeight(null));
                g.rotate(Math.toRadians(180), x + width / 2d - Handler.get().getGameCamera().getxOffset(), (y + height / 2d - Handler.get().getGameCamera().getyOffset()));
                g.drawImage(animation.getCurrentFrame(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
                g.rotate(Math.toRadians(180), x + width / 2d - Handler.get().getGameCamera().getxOffset(), (y + height / 2d - Handler.get().getGameCamera().getyOffset()));
            } else {
                g.drawImage(animation.getCurrentFrame(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
            }
        } else {
            if (degrees >= 91 && degrees <= 270) {
                transform.translate(0, -animation.getSingleFrame(animation.getLength()).getHeight(null));
                g.rotate(Math.toRadians(180), x + width / 2d - Handler.get().getGameCamera().getxOffset(), (y + height / 2d - Handler.get().getGameCamera().getyOffset()));
                g.drawImage(animation.getSingleFrame(animation.getLength()), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
                g.rotate(Math.toRadians(180), x + width / 2d - Handler.get().getGameCamera().getxOffset(), (y + height / 2d - Handler.get().getGameCamera().getyOffset()));
            } else {
                g.drawImage(animation.getSingleFrame(animation.getLength()), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
            }
        }
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
        Path2D.Double points = (Path2D.Double) transformedShape;
        PathIterator it = points.getPathIterator(null);
        List<Integer> xPoints = new ArrayList<>();
        List<Integer> yPoints = new ArrayList<>();
        double[] coords = new double[2];
        while (!it.isDone()) {
            // Get the current 2 coordinates
            it.currentSegment(coords);
            xPoints.add((int) coords[0] + (int) Handler.get().getGameCamera().getxOffset());
            yPoints.add((int) coords[1] + (int) Handler.get().getGameCamera().getyOffset());
            it.next();
        }
        int[] xArr = xPoints.stream().mapToInt(i -> i).toArray();
        int[] yArr = yPoints.stream().mapToInt(i -> i).toArray();
        polyBounds = new Polygon(xArr, yArr, xArr.length);
    }

    public void addDespawnTimer(int milliseconds) {
        TimerHandler.get().addTimer(new Timer(milliseconds, TimeUnit.MILLISECONDS, () -> this.active = false));
    }

    public Creature getCaster() {
        return caster;
    }

    public void setCaster(Creature caster) {
        this.caster = caster;
    }
}
