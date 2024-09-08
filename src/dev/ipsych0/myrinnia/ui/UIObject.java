package dev.ipsych0.myrinnia.ui;

import java.awt.*;
import java.io.Serializable;


public abstract class UIObject extends Rectangle implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5668099551677326216L;
    boolean hovering = false;
    boolean hoverable = true;
    boolean visible = true;


    protected UIObject(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public abstract void tick();

    public abstract void render(Graphics2D g);

    public boolean isHovering() {
        return hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isHoverable() {
        return hoverable;
    }

    public void setHoverable(boolean hoverable) {
        this.hoverable = hoverable;
    }
}
