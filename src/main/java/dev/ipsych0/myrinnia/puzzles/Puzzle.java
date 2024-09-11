package dev.ipsych0.myrinnia.puzzles;

import dev.ipsych0.myrinnia.utils.Action;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;

@Getter
@Setter
abstract class Puzzle implements Serializable, Action {

    private static final long serialVersionUID = 1393761869484926871L;
    boolean completed;

    public abstract void tick();

    public abstract void render(Graphics2D g);



}
