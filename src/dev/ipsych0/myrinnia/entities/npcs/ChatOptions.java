package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class ChatOptions extends UIImageButton implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -7933908780433920065L;
    private String message;
    private int optionID;
    private static final int WIDTH = 400, HEIGHT = 20;

    public ChatOptions(int x, int y, int optionID, String message) {
        super(x, y, WIDTH, HEIGHT, Assets.genericButton);
        this.optionID = optionID;
        this.message = message;
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics g) {
        super.render(g);
        Text.drawString(g, message, x + (width / 2), y + 11, true, Color.YELLOW, Assets.font14);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getOptionID() {
        return optionID;
    }

    public void setOptionID(int optionID) {
        this.optionID = optionID;
    }

}
