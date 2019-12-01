package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
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
    private Animation animation;
    private DamageType damageType;
    private Ability ability;
    private Creature hitCreature;
    private double rotation;
    private String impactSound;

    public Projectile(double x, double y, int mouseX, int mouseY, float velocity, String impactSound, DamageType damageType, Ability ability, Animation animation, BufferedImage[] frames) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, null, 1, null, null, null, null, null);

        this.x = x;
        this.y = y;
        this.width = Creature.DEFAULT_CREATURE_WIDTH;
        this.height = Creature.DEFAULT_CREATURE_HEIGHT;
        this.impactSound = impactSound;
        this.damageType = damageType;
        this.ability = ability;

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

        // Set the rotation of the projectile in degrees (0 = RIGHT, 270 = UP, 180 = LEFT, 90 = DOWN)
        rotation = Math.toDegrees(angle);
        if (rotation < 0) {
            rotation += 360d;
        }

        // Default animation settings
        if (animation == null) {
            this.animation = new Animation(125, frames);
        } else {
            this.animation = animation;
        }

        active = true;
    }

    public Projectile(double x, double y, int mouseX, int mouseY, float velocity, String impactSound, DamageType damageType, BufferedImage[] animation) {
        this(x, y, mouseX, mouseY, velocity, impactSound, damageType, null, null, animation);
    }

    public Projectile(double x, double y, int mouseX, int mouseY, float velocity, String impactSound, DamageType damageType, Animation animation) {
        this(x, y, mouseX, mouseY, velocity, impactSound, damageType, null, animation, null);
    }

    public Projectile(double x, double y, int mouseX, int mouseY, float velocity, String impactSound, DamageType damageType, Ability ability, Animation animation) {
        this(x, y, mouseX, mouseY, velocity, impactSound, damageType, ability, animation, null);
    }

    public Projectile(double x, double y, int mouseX, int mouseY, float velocity, String impactSound, DamageType damageType, Ability ability, BufferedImage[] animation) {
        this(x, y, mouseX, mouseY, velocity, impactSound, damageType, ability, null, animation);
    }

    public void tick() {
        if (active) {
            animation.tick();

            double ty = (y + yVelocity + bounds.y + (bounds.height / 2)) / Tile.TILEHEIGHT;
            double tx = (x + xVelocity + bounds.x + (bounds.width / 2)) / Tile.TILEWIDTH;
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
            AffineTransform old = g.getTransform();
            g.rotate(Math.toRadians(rotation), (int) (x + width / 2 - Handler.get().getGameCamera().getxOffset()), (int) (y + height / 2 - Handler.get().getGameCamera().getyOffset()));
            g.drawImage(animation.getCurrentFrame(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
            g.setTransform(old);
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

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public Creature getHitCreature() {
        return hitCreature;
    }

    public void setHitCreature(Creature hitCreature) {
        this.hitCreature = hitCreature;
    }

    public String getImpactSound() {
        return impactSound;
    }

    public void setImpactSound(String impactSound) {
        this.impactSound = impactSound;
    }
}
