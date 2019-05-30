package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;

import java.awt.*;

public class UITransitionState extends AbstractTransitionState {

    /**
     *
     */
    private static final long serialVersionUID = -8513594300991244715L;
    private State newState;

    public UITransitionState(State newState) {
        this.newState = newState;
    }

    @Override
    public void tick() {
        newState.tick();
        if (alpha == 0) {
            State.setState(newState);
        }
    }

    @Override
    public void render(Graphics2D g) {
        newState.render(g);

        // Fade from black
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g.setComposite(ac);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Handler.get().getWidth(), Handler.get().getHeight());
        if (alpha - (0.5 / 60) < 0)
            alpha = 0;
        else
            alpha -= (0.5 / 60);

        g.dispose();
    }

    public State getNewState() {
        return newState;
    }

    public void setNewState(State newState) {
        this.newState = newState;
    }

}
