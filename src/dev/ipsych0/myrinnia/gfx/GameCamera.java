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
    private double xOffset, yOffset;

    public GameCamera(double xOffset, double yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    private void checkBlankSpace() {
        // Don't correct outside map bounds for small maps
        if (Handler.get().getWorld().getWidth() * Tile.TILEWIDTH < Handler.get().getWidth() ||
                Handler.get().getWorld().getHeight() * Tile.TILEHEIGHT < Handler.get().getHeight()) {
            return;
        }
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

    public boolean isAtAnyBound() {
        return isAtRightBound() || isAtBottomBound() || isAtLeftBound() || isAtTopBound();
    }

    public boolean isAtRightBound() {
        if(Handler.get().getWorld().getWidth() * Tile.TILEWIDTH < Handler.get().getWidth())
            return false;
        return xOffset >= Handler.get().getWorld().getWidth() * Tile.TILEWIDTH - Handler.get().getWidth();
    }

    public boolean isAtLeftBound() {
        return xOffset <= 0;
    }

    public boolean isAtBottomBound() {
        if(Handler.get().getWorld().getHeight() * Tile.TILEHEIGHT < Handler.get().getHeight()){
            return false;
        }
        return yOffset >= Handler.get().getWorld().getHeight() * Tile.TILEHEIGHT - Handler.get().getHeight();
    }

    public boolean isAtTopBound() {
        return yOffset <= 0;
    }

    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - Handler.get().getWidth() / 2f + e.getWidth() / 2f;
        yOffset = e.getY() - Handler.get().getHeight() / 2f + e.getHeight() / 2f;
        checkBlankSpace();
    }

    public void move(double xAmount, double yAmount) {
        xOffset += xAmount;
        yOffset += yAmount;
        checkBlankSpace();
    }

    public double getxOffset() {
        return xOffset;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }

}
