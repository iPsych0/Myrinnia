package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class MonologueState extends AbstractTransitionState {

    private State newState;
    private String[] dialogues;
    private UIImageButton continueButton;
    private UIManager uiManager;
    private int index;
    private StringBuilder sb;
    private static final int CHARS_PER_SECOND = 20;
    private static final int CHAR_PER_TICK = 60 / CHARS_PER_SECOND;
    private int timer;
    private int charIndex;
    private int commaTimer, fullStopTimer;
    private boolean stoppingComma, stoppingFullStop;

    public MonologueState(State newState, String[] dialogues) {
        this.newState = newState;
        this.dialogues = dialogues;

        sb = new StringBuilder();
        continueButton = new UIImageButton(Handler.get().getWidth() / 2 - 160, Handler.get().getHeight() - 128, 320, 96, Assets.genericButton);
        uiManager = new UIManager();
        uiManager.addObject(continueButton);
    }

    @Override
    public void tick() {

        // If we reached the last index, change to the new state
        if (index == dialogues.length) {
            newState.tick();
            if (alpha == 0) {
                State.setState(newState);
            }
        } else {
            uiManager.tick();

            //
            if (stoppingComma) {
                if (10 > commaTimer) {
                    commaTimer++;
                    return;
                } else {
                    stoppingComma = false;
                    commaTimer = 0;
                }
            }
            if (stoppingFullStop) {
                if (20 > fullStopTimer) {
                    fullStopTimer++;
                    return;
                } else {
                    stoppingFullStop = false;
                    fullStopTimer = 0;
                }
            }

            timer++;

            // Every 3 ticks, append a character if we're not at the end yet
            boolean outOfBounds = !(charIndex < dialogues[index].length());
            if (!stoppingComma && !stoppingFullStop && timer >= CHAR_PER_TICK && !outOfBounds) {
                sb.append(dialogues[index].charAt(charIndex));

                if (dialogues[index].charAt(charIndex) == ',') {
                    stoppingComma = true;
                } else if (dialogues[index].charAt(charIndex) == '.') {
                    stoppingFullStop = true;
                }

                charIndex++;
                timer = 0;
                if (charIndex % Handler.get().getRandomNumber(2, 3) == 0) {
                    Handler.get().playEffect("ui/textbox_type.wav");
                }
            }

            Rectangle mouse = Handler.get().getMouse();
            if (continueButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                hasBeenPressed = false;
                // If we press continue while still appending, render the whole string immediately
                if (sb.length() != dialogues[index].length()) {
                    sb.delete(0, dialogues[index].length());
                    sb.append(dialogues[index]);
                    charIndex = dialogues[index].length();
                } else {
                    // If we press continue when whole string is ready, we proceed to next monologue
                    sb.delete(0, dialogues[index].length());
                    index++;
                    charIndex = 0;
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (index == dialogues.length) {
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
            String[] lines = Text.splitIntoLine(sb.toString(), 50);
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
