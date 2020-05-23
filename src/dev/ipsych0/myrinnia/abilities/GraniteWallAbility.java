package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.entities.statics.GraniteWallAbilityObj;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;


public class GraniteWallAbility extends Ability implements Serializable {

    private static Color aoeColor = new Color(0, 192, 0, 96);
    private Rectangle wallBounds;
    private Shape transformedShape;
    private static final int WALL_WIDTH = 96, WALL_HEIGHT = 32;

    public GraniteWallAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                              double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.graniteWallI, x, y, 32, 32,null);
    }

    @Override
    public void setCaster(Creature c) {
        this.caster = c;
        this.setActivated(true);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {

    }

    @Override
    public void renderUnderEntity(Graphics2D g) {
        if (isSelected() && caster.equals(Handler.get().getPlayer())) {
            double theta = getTheta();

            wallBounds = new Rectangle(Handler.get().getMouse().x - WALL_WIDTH / 2, Handler.get().getMouse().y - WALL_HEIGHT / 2, WALL_WIDTH, WALL_HEIGHT);
            g.setColor(aoeColor);

            AffineTransform transform = new AffineTransform();
            AffineTransform old = g.getTransform();
            transform.rotate(Math.toRadians(theta), wallBounds.x + wallBounds.width / 2, wallBounds.y + wallBounds.height / 2);
            transformedShape = transform.createTransformedShape(wallBounds);
            g.fill(transformedShape);
            g.setTransform(old);
        }
    }

    @Override
    public void cast() {
        Player player = Handler.get().getPlayer();


        if (!caster.equals(player)) {

            // First get player pos
            double xPos = player.getX() + player.getWidth() / 2d;
            double yPos = player.getY() + player.getHeight() / 2d;

            double casterX = caster.getX() + caster.getWidth() / 2d;
            double casterY = caster.getY() + caster.getHeight() / 2d;

            // Then summon the wall halfway between us and the player
            wallBounds.x = (int) (xPos - casterX / 2d + casterX);
            wallBounds.y = (int) (yPos - casterY / 2d / 2d + casterY);

        }
        wallBounds.x += Handler.get().getGameCamera().getxOffset();
        wallBounds.y += Handler.get().getGameCamera().getyOffset();

        int earthLevel = caster.getEarthLevel();
        int despawnTimer = 3000 + (earthLevel * 120);

        GraniteWallAbilityObj wall = new GraniteWallAbilityObj(wallBounds.x, wallBounds.y, wallBounds.width, wallBounds.height, "", 1, null, null, null, null);
        wall.setDegrees(getTheta());
        wall.setTransformedShape(transformedShape);
        wall.addDespawnTimer(despawnTimer);
        wall.setCaster(caster);
        Handler.get().getWorld().getEntityManager().addRuntimeEntity(wall, false);

        Handler.get().playEffect("abilities/granite_wall.ogg", 0.1f);

        setCasting(false);
    }

    private double getTheta() {
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

    @Override
    protected void countDown() {
        cooldownTimer++;
        if (cooldownTimer / 60 == cooldownTime) {
            this.setOnCooldown(false);
            this.setActivated(false);
            this.setCasting(false);
            castingTimeTimer = 0;
            cooldownTimer = 0;
            wallBounds = null;
        }
    }

}
