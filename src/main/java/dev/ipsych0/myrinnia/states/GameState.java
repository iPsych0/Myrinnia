package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.worlds.Zone;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
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
        try {
            Handler.get().getWorldHandler().tick();
        } catch (Exception e) {
            System.err.printf("Something went wrong updating the world!\n%s", e);
        }
    }

    @Override
    public void render(Graphics2D g) {
        try {
            Handler.get().getWorldHandler().render(g);
        } catch (Exception e) {
            System.err.printf("Something went wrong rendering the world!\n%s", e);
        }
    }
}
