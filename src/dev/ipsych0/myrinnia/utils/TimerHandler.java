package dev.ipsych0.myrinnia.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TimerHandler implements Serializable {

    private static final long serialVersionUID = 2275149946122389085L;
    private List<Timer> timers = new ArrayList<>();
    private List<Timer> toBeAdded = new ArrayList<>();
    private static TimerHandler instance;

    private TimerHandler() {
    }

    public static TimerHandler get() {
        if (instance == null) {
            instance = new TimerHandler();
        }
        return instance;
    }

    /**
     * Add the timers to a temporary list to avoid concurrent modification
     *
     * @param timer Timer task
     */
    public void addTimer(Timer timer) {
        toBeAdded.add(timer);
    }

    public void tick() {
        Iterator<Timer> it = timers.iterator();
        while (it.hasNext()) {
            Timer t = it.next();

            t.tick();

            if (t.isFinished()) {
                it.remove();
            }
        }

        // Add the new timers when done iterating over the current set
        timers.addAll(toBeAdded);
        toBeAdded.clear();
    }
}
