package dev.ipsych0.myrinnia.chatwindow;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class ContinueButton extends UIImageButton implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 7648763333442088047L;
    private boolean isPressed = false;
    private static final int WIDTH = 100, HEIGHT = 20;

    public ContinueButton(int x, int y) {
        super(x, y, WIDTH, HEIGHT, Assets.genericButton);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        super.render(g);
        Text.drawString(g, "Continue", x + (width / 2), y + 11, true, Color.YELLOW, Assets.font14);
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

}
