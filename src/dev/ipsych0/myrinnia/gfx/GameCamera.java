package dev.ipsych0.myrinnia.gfx;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.io.Serializable;

public class GameCamera implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 6890827040135434870L;
    private float xOffset, yOffset;

    public GameCamera(float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    private void checkBlankSpace() {
        if (xOffset < 0) {
            xOffset = 0;
        } else if (xOffset > Handler.get().getWorld().getWidth() * Tile.TILEWIDTH - Handler.get().getWidth()) {
            xOffset = Handler.get().getWorld().getWidth() * Tile.TILEWIDTH - Handler.get().getWidth();
        }

        if (yOffset < 0) {
            yOffset = 0;
        } else if (yOffset > Handler.get().getWorld().getHeight() * Tile.TILEHEIGHT - Handler.get().getHeight()) {
            yOffset = Handler.get().getWorld().getHeight() * Tile.TILEHEIGHT - Handler.get().getHeight();
        }
    }

    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - Handler.get().getWidth() / 2f + e.getWidth() / 2f;
        yOffset = e.getY() - Handler.get().getHeight() / 2f + e.getHeight() / 2f;
        checkBlankSpace();
    }

    public void move(float xAmount, float yAmount) {
        xOffset += xAmount;
        yOffset += yAmount;
        checkBlankSpace();
    }

    public synchronized float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public synchronized float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

}
