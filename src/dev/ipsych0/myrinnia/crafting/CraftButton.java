package dev.ipsych0.myrinnia.crafting;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;

import java.awt.*;
import java.io.Serializable;

public class CraftButton extends UIImageButton implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1608461834826405658L;
    private Rectangle bounds;

    public CraftButton(int x, int y, int width, int height) {
        super(x, y, width, height, Assets.genericButton);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        bounds = new Rectangle(x, y, width, height);

    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics g) {
        super.render(g);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

}
