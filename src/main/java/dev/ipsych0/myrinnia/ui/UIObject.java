package dev.ipsych0.myrinnia.ui;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;


@Setter
@Getter
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

}
