package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.states.monologues.Monologue;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.utils.TextWriter;

import java.awt.*;

public class MonologueState extends AbstractTransitionState {

    private State newState;
    private String[] monologues;
    private UIImageButton continueButton;
    private UIManager uiManager;
    private TextWriter textWriter;

    public MonologueState(State newState, Monologue monologue) {
        this.newState = newState;
        this.monologues = monologue.getMonologues();
        this.textWriter = new TextWriter(monologues);

        continueButton = new UIImageButton(Handler.get().getWidth() / 2 - 160, Handler.get().getHeight() - 128, 320, 96, Assets.genericButton);
        uiManager = new UIManager();
        uiManager.addObject(continueButton);
    }

    @Override
    public void tick() {

        // If we reached the last index, change to the new state
        if (textWriter.getCurrentText() == monologues.length) {
            newState.tick();
            if (alpha == 0) {
                State.setState(newState);
            }
        } else {
            uiManager.tick();

            Rectangle mouse = Handler.get().getMouse();
            if (continueButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                hasBeenPressed = false;
                textWriter.nextDialogue();
            }
        }

        if (textWriter.getCurrentText() < monologues.length) {
            textWriter.tick();
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (textWriter.getCurrentText() == monologues.length) {
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
        } else {
            String[] lines = Text.splitIntoLine(textWriter.getSb().toString(), 50);
            for (int i = 0; i < lines.length; i++) {
                Text.drawString(g, lines[i], Handler.get().getWidth() / 2, Handler.get().getHeight() / 2 - 48 - (lines.length * 16 - 16) + (i * 32), true, Color.YELLOW, Assets.font32);
            }
            uiManager.render(g);
            Text.drawString(g, "Continue", continueButton.x + continueButton.width / 2, continueButton.y + continueButton.height / 2, true, Color.YELLOW, Assets.font32);
        }
    }

    public State getNewState() {
        return newState;
    }

    public void setNewState(State newState) {
        this.newState = newState;
    }
}
