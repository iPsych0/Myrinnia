package dev.ipsych0.myrinnia.recap;

import dev.ipsych0.myrinnia.gfx.ScreenShot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class RecapEvent implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6372863712369781038L;
    private transient BufferedImage img;
    private String description;

    public RecapEvent(String description) {
        this.img = ScreenShot.take();
        this.description = description;
    }

    public void tick() {

    }

    public void render(Graphics2D g) {
        g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
    }

    public BufferedImage getImg() {
        return img;
    }

    public String getDescription() {
        return description;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ImageIO.write(this.img, "png", buffer);

        out.writeInt(buffer.size()); // Prepend image with byte count
        buffer.writeTo(out);         // Write image
        buffer.close();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        int size = in.readInt(); // Read byte count
        byte[] buffer = new byte[size];
        in.readFully(buffer); // Make sure you read all bytes of the image

        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        this.img = ImageIO.read(is);
        is.close();
    }

}
