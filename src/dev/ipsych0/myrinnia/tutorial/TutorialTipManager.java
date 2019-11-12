package dev.ipsych0.myrinnia.tutorial;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TutorialTipManager {

    private List<TutorialTip> tips;

    public TutorialTipManager() {
        tips = new ArrayList<>();
    }

    public void tick() {
        if (tips.size() > 0) {
            TutorialTip tip = tips.get(0);

            // Slide out when completed
            if (tip.isSlidingDone()) {
                if (tip.getPopup().isOkPressed()) {
                    if (tip.getOffset() < -16) {
                        tips.remove(0);
                    }
                    tip.decreaseOffset();
                    return;
                }
            }

            // Slide in the tip
            if (tip.getOffset() < 200) {
                tip.increaseOffset();
            }
        }
    }

    public void render(Graphics2D g) {
        if (tips.size() > 0) {
            TutorialTip tip = tips.get(0);
            tip.render(g);
        }
    }

    public void addTip(TutorialTip tip) {
        tips.add(tip);
    }

    public TutorialTip getCurrentTip() {
        if (!tips.isEmpty()) {
            return tips.get(0);
        }
        return null;
    }
}
