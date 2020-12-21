package dev.ipsych0.myrinnia.worlds.weather;

import java.util.ArrayList;
import java.util.List;

public class Climate {
    public static final int CAVE = 0;
    public static final int DROUGHT = 1;
    public static final int FOG = 2;
    public static final int HEAVY_FOG = 3;
    public static final int RAIN = 4;
    public static final int RAIN_THUNDER = 5;
    public static final int SANDSTORM = 6;
    public static final int HEAVY_SANDSTORM = 7;
    public static final int SNOW = 8;
    public static final int SUNNY = 9;
    public static final int SWAMP = 10;
    public static final int CELENOR_FOREST = 11;
    public static final int FYDDNYMED = 12;

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
        return switch (id) {
            case CAVE -> new Cave();
            case DROUGHT -> new Drought();
            case FOG -> new Fog(Intensity.NORMAL);
            case HEAVY_FOG -> new Fog(Intensity.HEAVY);
            case RAIN -> new Rain();
            case RAIN_THUNDER -> new Rain(true);
            case SANDSTORM -> new SandStorm(Intensity.NORMAL);
            case HEAVY_SANDSTORM -> new SandStorm(Intensity.HEAVY);
            case SNOW -> new Snow();
            case SWAMP -> new Swamp();
            case CELENOR_FOREST -> new CelenorForest();
            default -> new Sunny();
        };
    }
}
