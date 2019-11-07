package dev.ipsych0.myrinnia.tutorial;

import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public abstract class TutorialTip {

    private TutorialPopup popup;
    private int offset = 0;
    private boolean slidingDone;
    private int waitTimer = 0;

    public TutorialTip(String tip) {
        String[] lines = Text.splitIntoLine(tip, 26);
        popup = new TutorialPopup(lines);
    }

    public void render(Graphics2D g) {
        if (!slidingDone) {
            if (offset == 200) {
                waitTimer++;
            }
            slidingDone = offset == 200 && waitTimer >= 120;
        }
        popup.render(g, offset);
    }

    public abstract boolean isCompleted();

    public int getOffset() {
        return offset;
    }

    public void increaseOffset() {
        offset += 2;
    }

    public void decreaseOffset() {
        offset -= 2;
    }

    public boolean isSlidingDone() {
        return slidingDone;
    }
}
