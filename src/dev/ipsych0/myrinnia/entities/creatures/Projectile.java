package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilities.data.OnImpact;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Projectile extends Creature implements Serializable {

    public static class Builder implements Serializable {
        private Creature caster;
        private double x, y;
        private int targetX, targetY;
        private Animation animation;
        private float velocity = 6.0f;
        private String impactSound;
        private float impactVolume = 0.1f;
        private boolean piercing;
        private DamageType damageType;
        private Ability ability;
        private OnImpact onImpact;
        private BufferedImage[] frames;

        public Builder(DamageType damageType, Animation animation, Creature caster, int targetX, int targetY) {
            this.damageType = damageType;
            this.animation = animation;
            this.caster = caster;
            this.x = caster.getX();
            this.y = caster.getY();
            this.targetX = targetX;
            this.targetY = targetY;

            switch (damageType) {
                case INT:
                    this.impactSound = "abilities/magic_strike_impact.ogg";
                    break;
                case DEX:
                    this.impactSound = "abilities/ranged_shot_impact.ogg";
                    break;
            }
        }

        public Builder(DamageType damageType, BufferedImage[] frames, Creature caster, int targetX, int targetY) {
            this.damageType = damageType;
            this.frames = frames;
            this.caster = caster;
            this.x = caster.getX();
            this.y = caster.getY();
            this.targetX = targetX;
            this.targetY = targetY;

            switch (damageType) {
                case INT:
                    this.impactSound = "abilities/magic_strike_impact.ogg";
                    break;
                case DEX:
                    this.impactSound = "abilities/ranged_shot_impact.ogg";
                    break;
            }
        }

        public Builder withVelocity(float velocity) {
            this.velocity = velocity;
            return this;
        }

        public Builder withPiercing(boolean piercing) {
            this.piercing = piercing;
            return this;
        }

        public Builder withImpactSound(String sound, float impactVolume) {
            this.impactSound = sound;
            this.impactVolume = impactVolume;
            return this;
        }

        public Builder withImpactSound(String sound) {
            this.impactSound = sound;
            return this;
        }

        public Builder withAbility(Ability ability) {
            this.ability = ability;
            return this;
        }

        public Builder withAnimation(Animation animation) {
            this.animation = animation;
            return this;
        }

        public Builder withFrames(BufferedImage[] frames) {
            this.frames = frames;
            return this;
        }

        public Builder withImpact(OnImpact onImpact) {
            this.onImpact = onImpact;
            return this;
        }

        public Projectile build() {
            Projectile p = new Projectile(caster, x, y, targetX, targetY, velocity, impactSound, impactVolume, damageType, ability, animation, frames, onImpact, piercing);
            p.setCurrentTile(caster.getCurrentTile());
            p.setPreviousTile(caster.getPreviousTile());
            return p;
        }

    }


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
    private Creature caster;
    private Creature hitCreature;
    private double rotation;
    private String impactSound;
    private float impactVolume;
    private OnImpact onImpact;
    private boolean piercing;
    private Set<Creature> hitCreatures = new HashSet<>();

    private Projectile(Creature caster, double x, double y, int targetX, int targetY, float velocity, String impactSound, float impactVolume, DamageType damageType, Ability ability, Animation animation, BufferedImage[] frames, OnImpact onImpact, boolean piercing) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, null, 1, null, null, null, null, null);

        this.caster = caster;
        this.impactSound = impactSound;
        this.impactVolume = impactVolume;
        this.damageType = damageType;
        this.ability = ability;
        this.onImpact = onImpact;
        this.piercing = piercing;

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
        angle = Math.atan2(targetY - y, targetX - x);
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

        caster.getProjectiles().add(this);
    }

    public void tick() {
        if (active) {
            animation.tick();

            double ty = (y + yVelocity + bounds.y + (bounds.height / 2d)) / Tile.TILEHEIGHT;
            double tx = (x + xVelocity + bounds.x + (bounds.width / 2d)) / Tile.TILEWIDTH;
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

    public OnImpact getOnImpact() {
        return onImpact;
    }

    public void setOnImpact(OnImpact onImpact) {
        this.onImpact = onImpact;
    }

    public float getImpactVolume() {
        return impactVolume;
    }

    public void setImpactVolume(float impactVolume) {
        this.impactVolume = impactVolume;
    }

    public boolean isPiercing() {
        return piercing;
    }

    public void setPiercing(boolean piercing) {
        this.piercing = piercing;
    }

    public Set<Creature> getHitCreatures() {
        return hitCreatures;
    }

    public void setHitCreatures(Set<Creature> hitCreatures) {
        this.hitCreatures = hitCreatures;
    }
}
