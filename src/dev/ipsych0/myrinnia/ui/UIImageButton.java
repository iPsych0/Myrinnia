package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.states.State;

import javax.imageio.ImageIO;
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

    public UIImageButton(float x, float y, int width, int height, BufferedImage[] images) {
        super(x, y, width, height);
        this.images = images;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        if (hovering) {
            if(!hasHovered){
                Handler.get().playEffect("ui/ui_button_hover.wav");
                hasHovered = true;
            }
            if(Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && State.hasBeenPressed){
                Handler.get().playEffect("ui/ui_button_click.wav");
                State.hasBeenPressed = false;
            }
            g.drawImage(images[0], (int) x + 1, (int) y + 1, width, height, null);
        }else {
            g.drawImage(images[1], (int) x, (int) y, width, height, null);
            hasHovered = false;
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(images.length); // how many images are serialized?

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        for (BufferedImage eachImage : images) {
            ImageIO.write(eachImage, "png", buffer);

            out.writeInt(buffer.size()); // Prepend image with byte count
            buffer.writeTo(out);         // Write image
        }
        buffer.close();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        int imageCount = in.readInt();
        this.images = new BufferedImage[imageCount];

        int size = in.readInt(); // Read byte count
        byte[] buffer = new byte[size];
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);

        for (int i = 0; i < imageCount; i++) {
            in.readFully(buffer); // Make sure you read all bytes of the image


            this.images[i] = ImageIO.read(is);
        }
        is.close();
    }

}
