package dev.ipsych0.myrinnia.cutscenes;

import java.awt.*;

public interface CutsceneEvent {
    void tick();

    void render(Graphics2D g);

    boolean isFinished();
}
