package dev.ipsych0.myrinnia.gfx;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Animation implements Serializable {


    private static final long serialVersionUID = 6957117142545976181L;
    private int speed, index;
    private long lastTime, timer;
    private transient List<BufferedImage> frames;
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
        this(speed, List.of(frames), tickOnce, reversed);
    }

    public Animation(int speed, BufferedImage[] frames, boolean tickOnce) {
        this(speed, List.of(frames), tickOnce, false);
    }

    public Animation(int speed, BufferedImage[] frames) {
        this(speed, List.of(frames), false, false);
    }

    public Animation(int speed, List<BufferedImage> frames) {
        this(speed, frames, false, false);
    }

    public Animation(int speed, List<BufferedImage> frames, boolean tickOnce) {
        this(speed, frames, tickOnce, false);
    }

    public Animation(int speed, List<BufferedImage> frames, boolean tickOnce, boolean reversed) {
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
                if (index >= frames.size())
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
                if (index >= frames.size()) {
                    index = frames.size() - 1;
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
                if (!reverseStarted) {
                    index++;
                    // Once we've reached the end, decrement backwards to index 0.
                } else {
                    index--;
                }

                timer = 0;
                if (index >= frames.size() && !reverseStarted) {
                    index = frames.size() - 1;
                    reverseStarted = true;
                }
                if (reverseStarted && index == 0) {
                    tickDone = true;
                }
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames.get(index);
    }

    public int getLength() {
        return frames.size();
    }

    public BufferedImage getDefaultFrame() {
        return frames.get(1);
    }

    public BufferedImage getSingleFrame(int index) {
        if (index < 0)
            index = 0;
        if (index >= frames.size())
            index = frames.size() - 1;
        return frames.get(index);
    }

    public void setFrames(BufferedImage[] frames) {
        this.setFrames(List.of(frames));
    }

    public void setFrames(List<BufferedImage> frames) {
        this.frames = frames;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(frames.size()); // how many images are serialized?

        for (BufferedImage eachImage : frames) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ImageIO.write(eachImage, "png", buffer);

            out.writeInt(buffer.size()); // Prepend image with byte count
            buffer.writeTo(out);         // Write image
            buffer.close();
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        int imageCount = in.readInt();
        this.frames = new ArrayList<>();
        for (int i = 0; i < imageCount; i++) {
            int size = in.readInt(); // Read byte count

            byte[] buffer = new byte[size];
            in.readFully(buffer); // Make sure you read all bytes of the image

            InputStream is = new ByteArrayInputStream(buffer);
            this.frames.add(ImageIO.read(is));
            is.close();
        }
    }
}
