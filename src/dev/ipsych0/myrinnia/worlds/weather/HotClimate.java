package dev.ipsych0.myrinnia.worlds.weather;

public class HotClimate extends Climate {

    public HotClimate() {
        weathers.add(new Drought());
        weathers.add(new SandStorm());
        weathers.add(new Sunny());

        probabilities.add(0.65);
        probabilities.add(0.15);
        probabilities.add(0.2);
    }
}
