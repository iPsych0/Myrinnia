package dev.ipsych0.myrinnia.tutorial;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class TutorialPopup {

    private String[] lines;
    private Rectangle bounds = new Rectangle(-192, 144, 208, 128);

    public TutorialPopup(String[] lines) {
        this.lines = lines;

    }

    public void render(Graphics2D g, int offset) {
        g.drawImage(Assets.uiWindow, bounds.x + offset, bounds.y, bounds.width, bounds.height, null);
        Text.drawString(g, "Tip:", bounds.x + bounds.width / 2 + offset, bounds.y + 10, true, Color.YELLOW, Assets.font20);
        for (int i = 0; i < lines.length; i++) {
            Text.drawString(g, lines[i], bounds.x + bounds.width / 2 + offset, bounds.y + 32 + (i * 16), true, Color.YELLOW, Assets.font14);
        }
    }
}
