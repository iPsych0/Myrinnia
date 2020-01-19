package dev.ipsych0.myrinnia.cutscenes;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cutscene {

    private List<CutsceneEvent> events;
    private boolean finished;

    public Cutscene(CutsceneEvent... events) {
        this.events = new ArrayList<>(Arrays.asList(events));
    }

    public void tick() {
        if (events.size() > 0) {
            CutsceneEvent currentEvent = events.get(0);
            currentEvent.tick();
            if (currentEvent.isFinished()) {
                events.remove(0);
            }
        } else {
            finished = true;
        }
    }

    public void render(Graphics2D g) {
        if (events.size() > 0) {
            CutsceneEvent currentEvent = events.get(0);
            currentEvent.render(g);
        } else {
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
