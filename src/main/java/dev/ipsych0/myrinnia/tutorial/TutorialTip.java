package dev.ipsych0.myrinnia.tutorial;

import dev.ipsych0.myrinnia.utils.Text;
import lombok.Getter;

import java.awt.*;
import java.io.Serializable;

@Getter
public class TutorialTip implements Serializable {

    private static final long serialVersionUID = -4527345307194365770L;
    private TutorialPopup popup;
    private int offset = 0;
    private boolean slidingDone;
    private String tip;

    public TutorialTip(String tip) {
        this.tip = tip;
        String[] messages = Text.splitIntoLine(tip, 30);
        popup = new TutorialPopup(messages);
    }

    public void render(Graphics2D g) {
        if (!slidingDone) {
            slidingDone = offset == 200;
        }
        popup.render(g, offset);
    }


    public void increaseOffset() {
        offset += 4;
    }

    public void decreaseOffset() {
        offset -= 4;
    }

}
