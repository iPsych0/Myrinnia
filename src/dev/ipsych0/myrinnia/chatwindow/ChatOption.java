package dev.ipsych0.myrinnia.chatwindow;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class ChatOption extends UIImageButton implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -7933908780433920065L;
    private String message;
    private int optionID;
    private boolean isPressed = false;
    private static final int WIDTH = 400, HEIGHT = 20;
    private static final int CONTINUE_BTN_WIDTH = 100;
    private boolean onlyOneLine;

    public ChatOption(int x, int y, int optionID, String message) {
        super(x, y, WIDTH, HEIGHT, Assets.genericButton);
        this.optionID = optionID;
        this.message = message;
    }

    public ChatOption(int x, int y, String message) {
        super(x, y, CONTINUE_BTN_WIDTH, HEIGHT, Assets.genericButton);
        this.message = message;
        this.onlyOneLine = true;
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics g) {
        super.render(g);

        if (onlyOneLine) {
            Text.drawString(g, "Continue", x + (width / 2), y + 11, true, Color.YELLOW, Assets.font14);
        } else {
            Text.drawString(g, message, x + (width / 2), y + 11, true, Color.YELLOW, Assets.font14);
        }
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

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }
}
