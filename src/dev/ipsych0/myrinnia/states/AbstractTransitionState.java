package dev.ipsych0.myrinnia.states;

import java.awt.*;

public abstract class AbstractTransitionState extends State {

    /**
     *
     */
    private static final long serialVersionUID = -8450007202214676369L;
    protected float alpha = 1.0f;

    public AbstractTransitionState() {
        super();
    }

    @Override
    public abstract void tick();

    @Override
    public abstract void render(Graphics g);
}
