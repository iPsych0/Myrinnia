package dev.ipsych0.myrinnia.character;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class StatTooltip implements Serializable {

    public static final int WIDTH = 224, HEIGHT = 112;

    public StatTooltip() {
    }

    public void render(Graphics2D g, CharacterStats stat, int x, int y) {
        g.drawImage(Assets.uiWindow, x, y, WIDTH, HEIGHT, null);
        Text.drawString(g, stat.toString() + ":", x + 4, y + 20, false, Color.YELLOW, Assets.font20);

        String[] description = Text.splitIntoLine(stat.getDescription(), 32);
        for (int i = 0; i < description.length; i++) {
            Text.drawString(g, description[i], x + 4, y + (i * 16) + 40, false, Color.WHITE, Assets.font14);
        }
    }
}
