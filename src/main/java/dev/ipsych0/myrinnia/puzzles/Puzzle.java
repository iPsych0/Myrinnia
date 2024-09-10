package dev.ipsych0.myrinnia.puzzles;

import dev.ipsych0.myrinnia.utils.OnTaskCompleted;

import java.awt.*;
import java.io.Serializable;

abstract class Puzzle implements Serializable, OnTaskCompleted {

    /**
     *
     */
    private static final long serialVersionUID = 1393761869484926871L;
    boolean completed;

    public abstract void tick();

    public abstract void render(Graphics2D g);

    public boolean isCompleted() {
        return completed;
    }

    void setCompleted(boolean completed) {
        this.completed = completed;
    }


}
