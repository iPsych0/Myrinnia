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

    public static final int TEMPERATE_CLIMATE = 10;
    public static final int COLD_CLIMATE = 11;
    public static final int HOT_CLIMATE = 12;

    private List<Weather> weathers = new ArrayList<>();
    private List<Double> probabilities = new ArrayList<>();

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

    /**
     * Constructor for setting climates:
     *
     * @param weatherID
     */
    public Climate(int weatherID) {
        setClimateProbabilities(weatherID);
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

    private void setClimateProbabilities(int id) {
        switch (id) {
            case COLD_CLIMATE:
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
                break;
            case HOT_CLIMATE:
                weathers.add(new Drought());
                weathers.add(new SandStorm());
                weathers.add(new Sunny());

                probabilities.add(0.65);
                probabilities.add(0.15);
                probabilities.add(0.2);
                break;
            default:
                // Temperate climate
                weathers.add(new Rain());
                weathers.add(new Rain(true));
                weathers.add(new Sunny());

                probabilities.add(0.25);
                probabilities.add(0.15);
                probabilities.add(0.6);
                break;

        }
    }
}
