package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;

import java.awt.*;

public class CollisionTile extends StaticEntity {

    public CollisionTile(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = true;
        attackable = false;
        isNpc = false;
        respawnTime = 1L;
        overlayDrawn = false;
    }
// dit is een vogelhuis. Daar woont dus een vogel

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new CollisionTile(x, y, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {

    }
}
