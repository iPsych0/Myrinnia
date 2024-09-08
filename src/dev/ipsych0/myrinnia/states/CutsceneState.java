package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.cutscenes.Cutscene;

import java.awt.*;

public class CutsceneState extends State {

    private Cutscene cutscene;

    public CutsceneState(Cutscene cutscene) {
        this.cutscene = cutscene;
    }

    @Override
    public void tick() {
        cutscene.tick();
        if (cutscene.isFinished()) {
            State.setState(Handler.get().getGame().gameState);
            Handler.get().getGameCamera().centerOnEntity(Handler.get().getPlayer());
        }
    }

    @Override
    public void render(Graphics2D g) {
        Handler.get().getGame().gameState.render(g);
        cutscene.render(g);
    }
}
