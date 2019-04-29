package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;

public class GameState extends State {

    /**
     *
     */
    private static final long serialVersionUID = 1598032694820560072L;

    public GameState() {
        super();
        // Setup new game world
        Handler.get().setWorld(Handler.get().getWorldHandler().getWorldsMap().get(Zone.PortAzure));

    }

    @Override
    public void tick() {
        Handler.get().getWorldHandler().tick();
    }

    @Override
    public void render(Graphics2D g) {
        Handler.get().getWorldHandler().render(g);
    }
}
