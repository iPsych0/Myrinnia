package dev.ipsych0.myrinnia.ui;

import java.awt.*;
import java.io.Serializable;


public abstract class UIObject extends Rectangle implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5668099551677326216L;
    boolean hovering = false;

    UIObject(int x, int y, int width, int height) {
        super(x,y,width,height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void tick();

    public abstract void render(Graphics2D g);

    public boolean isHovering() {
        return hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

}
