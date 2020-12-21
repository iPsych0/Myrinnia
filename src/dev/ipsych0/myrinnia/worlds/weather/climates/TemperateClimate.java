package dev.ipsych0.myrinnia.worlds.weather.climates;

import dev.ipsych0.myrinnia.worlds.weather.Climate;
import dev.ipsych0.myrinnia.worlds.weather.Rain;
import dev.ipsych0.myrinnia.worlds.weather.Sunny;

public class TemperateClimate extends Climate {

    public TemperateClimate() {
        weathers.add(new Rain());
        weathers.add(new Rain(true));
        weathers.add(new Sunny());

        probabilities.add(0.15);
        probabilities.add(0.05);
        probabilities.add(0.8);
    }
}
