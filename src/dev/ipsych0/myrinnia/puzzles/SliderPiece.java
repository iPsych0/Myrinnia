package dev.ipsych0.myrinnia.puzzles;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class SliderPiece implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2140201307399035730L;
    private int xPos, yPos;
    private int id;
    private boolean blank = false;
    private BufferedImage texture;
    private int windowX, windowY;

    public SliderPiece(int xPos, int yPos, int id) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.blank = false;
        this.id = id;
        texture = Assets.puzzlePieces[xPos][yPos];
    }

    public SliderPiece(int xPos, int yPos, boolean isBlank, int id) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.blank = isBlank;
        this.id = id;
        texture = Assets.puzzlePieces[xPos][yPos];
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(texture, windowX + (32 * xPos), windowY + (32 * yPos), 32, 32, null);
        g.setColor(Color.BLACK);
        g.drawRect(windowX + (32 * xPos), windowY + (32 * yPos), 32, 32);
        if (!blank)
            Text.drawString(g, String.valueOf(id), windowX + (32 * xPos) + 16, windowY + (32 * yPos) + 16, true, Color.YELLOW, Assets.font14);


    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public Rectangle getBounds() {
        return new Rectangle(windowX + (32 * xPos), windowY + (32 * yPos), 32, 32);
    }

    public boolean isBlank() {
        return blank;
    }

    public void setBlank(boolean blank) {
        this.blank = blank;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWindowX() {
        return windowX;
    }

    public int getWindowY() {
        return windowY;
    }

    public void setWindowX(int windowX) {
        this.windowX = windowX;
    }

    public void setWindowY(int windowY) {
        this.windowY = windowY;
    }

}
