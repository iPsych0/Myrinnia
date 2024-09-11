package dev.ipsych0.myrinnia.cutscenes;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Cutscene implements Serializable {

    private static final long serialVersionUID = -5005782572625898350L;
    private List<CutsceneEvent> events;
    private boolean finished;

    public Cutscene(CutsceneEvent... events) {
        this.events = new ArrayList<>(Arrays.asList(events));
    }

    public void tick() {
        if (!events.isEmpty()) {
            CutsceneEvent currentEvent = events.getFirst();
            currentEvent.tick();
            if (currentEvent.isFinished()) {
                events.removeFirst();
            }
        } else {
            finished = true;
        }
    }

    public void render(Graphics2D g) {
        if (!events.isEmpty()) {
            CutsceneEvent currentEvent = events.getFirst();
            currentEvent.render(g);
        } else {
            finished = true;
        }
    }
}
