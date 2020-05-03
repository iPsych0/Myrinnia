package dev.ipsych0.myrinnia.utils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Timer implements Serializable {

    private static final long serialVersionUID = -746649186843836217L;
    private long startTime, duration;
    private TimeUnit timeUnit;
    private OnTaskCompleted onTaskCompleted;
    private boolean finished;

    public Timer(long duration, TimeUnit timeUnit, OnTaskCompleted onTaskCompleted) {
        this.startTime = System.currentTimeMillis();
        this.timeUnit = timeUnit;
        this.onTaskCompleted = onTaskCompleted;
        setTimeConversion(duration);
    }

    public void tick() {
        if (!finished) {
            long currentTime = System.currentTimeMillis();
            if (((currentTime - startTime)) >= duration) {
                onTaskCompleted.onComplete();
                finished = true;
            }
        }
    }

    private void setTimeConversion(long duration) {
        switch (timeUnit) {
            case SECONDS:
                this.duration = (duration * 1000L);
                break;
            case MINUTES:
                this.duration = (duration * 1000L * 60L);
                break;
            case HOURS:
                this.duration = (duration * 1000L * 60L * 60L);
                break;
            case DAYS:
                this.duration = (duration * 1000L * 60L * 60L * 24L);
                break;
            default:
                this.duration = duration;
                break;
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
