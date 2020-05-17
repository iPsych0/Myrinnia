package dev.ipsych0.myrinnia.worlds.weather;

import java.util.ArrayList;
import java.util.List;

public class Climate {
    public static final int CAVE = 0;
    public static final int DROUGHT = 1;
    public static final int FOG = 2;
    public static final int INTENSE_FOG = 3;
    public static final int RAIN = 4;
    public static final int RAIN_THUNDER = 5;
    public static final int SANDSTORM = 6;
    public static final int SNOW = 7;
    public static final int SUNNY = 8;
    public static final int SWAMP = 9;

    protected List<Weather> weathers = new ArrayList<>();
    protected List<Double> probabilities = new ArrayList<>();

    /**
     * Constructor for custom climate control
     *
     * @param weatherID
     * @param probability
     */
    public Climate(int weatherID, double probability) {
        this.weathers.add(getWeatherByID(weatherID));
        this.probabilities.add(probability);
    }

    public Climate() {

    }

    public Climate with(int weatherID, double probability) {
        this.weathers.add(getWeatherByID(weatherID));
        this.probabilities.add(probability);
        return this;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public List<Double> getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(List<Double> probabilities) {
        this.probabilities = probabilities;
    }

    private Weather getWeatherByID(int id) {
        switch (id) {
            case CAVE:
                return new Cave();
            case DROUGHT:
                return new Drought();
            case FOG:
                return new Fog();
            case INTENSE_FOG:
                return new Fog(Fog.Intensity.HEAVY);
            case RAIN:
                return new Rain();
            case RAIN_THUNDER:
                return new Rain(true);
            case SANDSTORM:
                return new SandStorm();
            case SNOW:
                return new Snow();
            case SWAMP:
                return new Swamp();
            default:
                return new Sunny();
        }
    }
}
