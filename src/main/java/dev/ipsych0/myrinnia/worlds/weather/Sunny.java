package dev.ipsych0.myrinnia.worlds.weather;

import java.awt.*;

/**
 * Normal weather, no specific logic
 */
public class Sunny implements Weather {

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public String getWeatherSoundEffect() {
        return null;
    }
}
