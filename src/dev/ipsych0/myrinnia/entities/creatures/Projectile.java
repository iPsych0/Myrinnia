package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
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
    private Animation animation;
    private DamageType damageType;
    private Ability ability;
    private Creature hitCreature;

    public Projectile(float x, float y, int mouseX, int mouseY, float velocity, DamageType damageType, Ability ability, Animation animation, BufferedImage[] frames) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, null, 1, null, null, null, null, null);

        this.x = x;
        this.y = y;
        this.width = Creature.DEFAULT_CREATURE_WIDTH;
        this.height = Creature.DEFAULT_CREATURE_HEIGHT;
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

        // Default animation settings
        if (animation == null) {
            this.animation = new Animation(125, frames);
        } else {
            this.animation = animation;
        }

        active = true;
    }

    public Projectile(float x, float y, int mouseX, int mouseY, float velocity, DamageType damageType, BufferedImage[] animation) {
        this(x, y, mouseX, mouseY, velocity, damageType, null, null, animation);
    }

    public Projectile(float x, float y, int mouseX, int mouseY, float velocity, DamageType damageType, Animation animation) {
        this(x, y, mouseX, mouseY, velocity, damageType, null, animation, null);
    }

    public Projectile(float x, float y, int mouseX, int mouseY, float velocity, DamageType damageType, Ability ability, Animation animation) {
        this(x, y, mouseX, mouseY, velocity, damageType, ability, animation, null);
    }

    public Projectile(float x, float y, int mouseX, int mouseY, float velocity, DamageType damageType, Ability ability, BufferedImage[] animation) {
        this(x, y, mouseX, mouseY, velocity, damageType, ability, null, animation);
    }

    public void tick() {
        if (active) {
            animation.tick();

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
            g.drawImage(animation.getCurrentFrame(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
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
}
