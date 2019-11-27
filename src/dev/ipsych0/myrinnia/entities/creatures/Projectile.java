package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Projectile extends Creature implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 2796906732989163136L;
    private double xVelocity, yVelocity;
    private int maxX, maxY, minX, minY;
    private double angle;
    private static final int MAX_RADIUS = 320;
    private Animation projectile;

    public Projectile(float x, float y, int mouseX, int mouseY, float velocity, BufferedImage[] animation) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, null, 1, null, null, null, null, null);

        this.x = x;
        this.y = y;
        this.width = Creature.DEFAULT_CREATURE_WIDTH;
        this.height = Creature.DEFAULT_CREATURE_HEIGHT;

        bounds = new Rectangle((int) x, (int) y, width, height);
        bounds.x = 10;
        bounds.y = 14;
        bounds.width = 10;
        bounds.height = 10;

        collision = new Rectangle(bounds);

        // Max distance the projectile can travel
        maxX = (int) (x + MAX_RADIUS);
        maxY = (int) (y + MAX_RADIUS);
        minX = (int) (x - MAX_RADIUS);
        minY = (int) (y - MAX_RADIUS);

        // The angle and speed of the projectile
        angle = Math.atan2(mouseY - y, mouseX - x);
        xVelocity = velocity * Math.cos(angle);
        yVelocity = velocity * Math.sin(angle);

        projectile = new Animation(125, animation);

        active = true;
    }

    public void tick() {
        if (active) {
            projectile.tick();

            float ty = (y + (float) yVelocity + bounds.y + (bounds.height / 2)) / Tile.TILEHEIGHT;
            float tx = (x + (float) xVelocity + bounds.x + (bounds.width / 2)) / Tile.TILEWIDTH;
            if (collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, (int) ty, true) ||
                    collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, (int) ty, true) ||
                    collisionWithTile((int) tx, (int) (y + bounds.y) / Tile.TILEHEIGHT, false) ||
                    collisionWithTile((int) tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT, false)) {
                active = false;
                return;
            }

            x += xVelocity;
            y += yVelocity;

            // If out of range, remove this projectile
            if (x > maxX || x < minX || y > maxY || y < minY) {
                active = false;
            }
        }
    }

    public void render(Graphics2D g) {
        if (active) {
            g.drawImage(projectile.getCurrentFrame(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
        }
    }

    @Override
    public void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {

    }

    @Override
    public String getName() {
        return null;
    }

}
