package dev.ipsych0.myrinnia.worlds.weather;

public class ColdClimate extends Climate {

    public ColdClimate() {
        weathers.add(new Snow());
        weathers.add(new Rain());
        weathers.add(new Rain(true));
        weathers.add(new Fog());
        weathers.add(new Sunny());

        probabilities.add(0.35);
        probabilities.add(0.2);
        probabilities.add(0.05);
        probabilities.add(0.05);
        probabilities.add(0.35);
    }
}
