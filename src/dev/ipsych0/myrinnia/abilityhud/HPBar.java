package dev.ipsych0.myrinnia.abilityhud;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.hpoverlay.HPOverlay;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class HPBar implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -6791617940244093129L;
    private int x, y, width, height;

    public HPBar(int x, int y) {
        this.x = x;
        this.y = y - 32;
        this.width = 32;
        this.height = 64;
    }

    public void tick() {

    }

    public void render(Graphics2D g) {
        // HP Bar
        g.drawImage(Assets.invScreen, x, y, width, height, null);
        g.setColor(HPOverlay.hpColorRed);
        g.fillRect(x + 1, y + 1, width - 3, height - 3);
        g.setColor(HPOverlay.hpColorRedOutline);
        g.drawRect(x + 1, y + 1, width - 3, height - 3);

        g.setColor(HPOverlay.hpColorGreen);
        if (Handler.get().getPlayer().getHealth() >= Handler.get().getPlayer().getMaxHealth()) {
            g.fillRect(x + 1, y + 1, width - 3, height - 3);

            g.setColor(HPOverlay.hpColorGreenOutline);
            g.drawRect(x + 1, y + 1, width - 3, height - 3);
        } else {
            double yOffset = (double) Handler.get().getPlayer().getHealth() /
                    (double) Handler.get().getPlayer().getMaxHealth();
            g.fillRect(x + 1, y + 1 + (int) (height * (1 - yOffset)), width - 3, height - 3);

            g.setColor(HPOverlay.hpColorGreenOutline);
            g.drawRect(x + 1, y + 1 + (int) (height * (1 - yOffset)), width - 3, height - 3);
        }
        Text.drawString(g, "HP", x + width / 2, y + height / 2, true, Color.YELLOW, Assets.font14);
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


}
