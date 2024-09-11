package dev.ipsych0.myrinnia.chatwindow;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIObject;
import dev.ipsych0.myrinnia.utils.Text;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;

@Setter
@Getter
public class TextSlot extends UIObject implements Serializable {

    private static final long serialVersionUID = 6041297662264038958L;
    public static final int WIDTH = 432;
    public static final int HEIGHT = 16;

    private String message;

    public TextSlot(int x, int y, String message) {
        super(x, y, WIDTH, HEIGHT);
        this.x = x;
        this.y = y;
        this.message = message;
        setVisible(false);
        setHoverable(false);
    }

    public void tick() {

    }

    public void render(Graphics2D g) {
        if (message != null) {
            Text.drawString(g, message, x + 6, y + 20, false, Color.YELLOW, Assets.font14);
        }
    }


}
