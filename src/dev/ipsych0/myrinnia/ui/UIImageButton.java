package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class UIImageButton extends UIObject {

    /**
     *
     */
    private static final long serialVersionUID = -1839735101824151769L;
    private transient BufferedImage[] images;
    private boolean hasHovered;
    public static boolean hasBeenPressed = false;

    public UIImageButton(int x, int y, int width, int height, BufferedImage[] images) {
        super(x, y, width, height);
        this.images = images;
    }

    public UIImageButton(Rectangle bounds, BufferedImage[] images) {
        super(bounds.x, bounds.y, bounds.width, bounds.height);
        this.images = images;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        if (hovering) {
            if(!hasHovered){
                Handler.get().playEffect("ui/ui_button_hover.wav");
                hasHovered = true;
            }
            if(Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed){
                Handler.get().playEffect("ui/ui_button_click.wav");
                hasBeenPressed = false;
            }
            g.drawImage(images[0], x, y, width, height, null);
        }else {
            g.drawImage(images[1], x, y, width, height, null);
            hasHovered = false;
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.images = Assets.genericButton;
    }

}
