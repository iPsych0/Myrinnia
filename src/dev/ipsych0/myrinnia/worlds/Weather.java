package dev.ipsych0.myrinnia.worlds;

import java.awt.*;

public interface Weather {
    void tick();

    void render(Graphics2D g);
}
