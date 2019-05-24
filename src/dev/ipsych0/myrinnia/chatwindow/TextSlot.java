package dev.ipsych0.myrinnia.chatwindow;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

class TextSlot implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 6041297662264038958L;
    public static final int textWidth = 432;
    public static final int textHeight = 16;

    private int x, y;
    private String message;

    public TextSlot(int x, int y, String message) {
        this.x = x;
        this.y = y;
        this.message = message;
    }

    public void tick() {

    }

    public void render(Graphics2D g) {
        if (message != null) {
            Text.drawString(g, message, x + 6, y + 20, false, Color.YELLOW, Assets.font14);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
