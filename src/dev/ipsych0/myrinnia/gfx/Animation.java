package dev.ipsych0.myrinnia.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Animation implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 6957117142545976181L;
    private int speed, index;
    private long lastTime, timer;
    private transient BufferedImage[] frames;
    private boolean tickOnce;
    private boolean tickDone;
    private boolean reversed;
    private boolean reverseStarted;

    /**
     * Initialize the Animation
     *
     * @param speed    1000 speed = 1 second. So for 3 frames per second, the speed should be 333, etc.
     * @param frames   Array of images that comprise the full animation.
     * @param tickOnce Flag true/false if the Animation should only be played once, instead of looped.
     * @param reversed
     */
    public Animation(int speed, BufferedImage[] frames, boolean tickOnce, boolean reversed) {
        this.speed = speed;
        this.frames = frames;
        this.tickOnce = tickOnce;
        this.reversed = reversed;

        // Prevent accidental false flag for tickOnce, as a reversed animation only ticks once from front to back
        if (reversed) {
            this.tickOnce = true;
        }

        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public Animation(int speed, BufferedImage[] frames, boolean tickOnce) {
        this(speed, frames, tickOnce, false);
    }

    public Animation(int speed, BufferedImage[] frames) {
        this(speed, frames, false, false);
    }

    public void tick() {
        if (reversed) {
            tickOnceAndReverse();
        } else if (tickOnce) {
            tickOnce();
        } else {
            timer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();

            if (timer > speed) {
                index++;
                timer = 0;
                if (index >= frames.length)
                    index = 0;
            }
        }
    }

    private void tickOnce() {
        if (!tickDone) {
            timer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();

            if (timer > speed) {
                index++;
                timer = 0;
                if (index >= frames.length) {
                    index = 0;
                    tickDone = true;
                }
            }
        }
    }

    private void tickOnceAndReverse() {
        if (!tickDone) {
            timer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();

            if (timer > speed) {
                // If we haven't reached the end, increment the index
                if(!reverseStarted) {
                    index++;
                // Once we've reached the end, decrement backwards to index 0.
                }else{
                    index--;
                }

                timer = 0;
                if (index >= frames.length && !reverseStarted) {
                    index = frames.length-1;
                    reverseStarted = true;
                }
                if(reverseStarted && index == 0){
                    tickDone = true;
                }
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }

    public int getIndex() {
        return index;
    }

    public int getLength() {
        return frames.length;
    }

    public BufferedImage getDefaultFrame() {
        return frames[1];
    }

    public boolean isTickDone() {
        return tickDone;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(frames.length); // how many images are serialized?

        for (BufferedImage eachImage : frames) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ImageIO.write(eachImage, "png", buffer);

            out.writeInt(buffer.size()); // Prepend image with byte count
            buffer.writeTo(out);         // Write image
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        int imageCount = in.readInt();
        this.frames = new BufferedImage[imageCount];
        for (int i = 0; i < imageCount; i++) {
            int size = in.readInt(); // Read byte count

            byte[] buffer = new byte[size];
            in.readFully(buffer); // Make sure you read all bytes of the image

            this.frames[i] = ImageIO.read(new ByteArrayInputStream(buffer));
        }
    }

}
