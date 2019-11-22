package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class StatusTooltip implements Serializable {


    private static final long serialVersionUID = -5708178237486962575L;
    private int x, y;

    public StatusTooltip(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tick() {

    }

    public void render(Buff b, Graphics2D g) {
        String[] text = Text.splitIntoLine(b.getDescription(), 20);
        g.drawImage(Assets.uiWindow, x, y, 176, 48 + (text.length * 16), null);
        b.render(g, x + 4, y + 4);
        Text.drawString(g, b.toString(), x + 40, y + 22, false, Color.YELLOW, Assets.font14);
        int index = 0;
        for (String s : text) {
            Text.drawString(g, s, x + 4, y + 48 + (16 * index++), false, Color.YELLOW, Assets.font14);
        }
    }

    public void render(Condition c, Graphics2D g) {
        String[] text = Text.splitIntoLine(c.getType().getDescription(), 20);
        g.drawImage(Assets.uiWindow, x, y, 176, 48 + (text.length * 16), null);
        c.render(g, x + 4, y + 4);
        String condition = c.getType().toString();
        Text.drawString(g, condition.substring(0, 1).toUpperCase() + condition.substring(1).toLowerCase(), x + 40, y + 22, false, Color.YELLOW, Assets.font14);
        int index = 0;
        for (String s : text) {
            Text.drawString(g, s, x + 4, y + 48 + (16 * index++), false, Color.YELLOW, Assets.font14);
        }
    }
}
