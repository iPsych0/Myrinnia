package dev.ipsych0.myrinnia.tutorial;

import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class TutorialTip implements Serializable {

    private static final long serialVersionUID = -4527345307194365770L;
    private TutorialPopup popup;
    private int offset = 0;
    private boolean slidingDone;

    public TutorialTip(String tip) {
        String[] lines = Text.splitIntoLine(tip, 26);
        popup = new TutorialPopup(lines);
    }

    public void render(Graphics2D g) {
        if (!slidingDone) {
            slidingDone = offset == 200;
        }
        popup.render(g, offset);
    }


    public int getOffset() {
        return offset;
    }

    public void increaseOffset() {
        offset += 4;
    }

    public void decreaseOffset() {
        offset -= 4;
    }

    public boolean isSlidingDone() {
        return slidingDone;
    }

    public TutorialPopup getPopup() {
        return popup;
    }
}
