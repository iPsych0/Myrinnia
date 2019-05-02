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
        g.drawImage(Assets.shopWindow, x, y, 160, 16 + (text.length * 16), null);
        int index = 0;
        for(String s : text){
            Text.drawString(g, s, x + 4, y + 16 + (16 * index++), false, Color.YELLOW, Assets.font14);
        }
    }

    public void render(Condition c, Graphics2D g) {
        String[] text = Text.splitIntoLine(c.getType().getDescription(), 20);
        g.drawImage(Assets.shopWindow, x, y, 160, 16 + (text.length * 16), null);
        int index = 0;
        for(String s : text){
            Text.drawString(g, s, x + 4, y + 16 + (16 * index++), false, Color.YELLOW, Assets.font14);
        }
    }
}
