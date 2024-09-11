package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;

@Getter
@Setter
public class DialogueButton extends UIImageButton implements Serializable {

    private static final long serialVersionUID = 4508512327560107696L;
    private String text;
    private Rectangle buttonBounds;
    private String[] buttonParam = new String[2];

    public DialogueButton(int x, int y, int width, int height, String text) {
        super(x, y, width, height, Assets.genericButton);
        this.text = text;

        buttonBounds = new Rectangle(x, y, width, height);
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics2D g) {
        super.render(g);
        Text.drawString(g, text, x + (width / 2), y + (height / 2), true, Color.YELLOW, Assets.font14);
    }

    public void pressedButton(String answer, String param) {
        buttonParam[0] = answer;
        buttonParam[1] = param;
    }

}
