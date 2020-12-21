package dev.ipsych0.myrinnia.worlds.weather.climates;

import dev.ipsych0.myrinnia.worlds.weather.Climate;
import dev.ipsych0.myrinnia.worlds.weather.Drought;
import dev.ipsych0.myrinnia.worlds.weather.SandStorm;
import dev.ipsych0.myrinnia.worlds.weather.Sunny;

public class HotClimate extends Climate {

    public HotClimate() {
        weathers.add(new Drought());
        weathers.add(new SandStorm());
        weathers.add(new Sunny());

        probabilities.add(0.3);
        probabilities.add(0.1);
        probabilities.add(0.6);
    }
}
