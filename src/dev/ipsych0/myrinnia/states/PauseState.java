package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.audio.AudioManager;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.ui.DialogueBox;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class PauseState extends State {

    /**
     *
     */
    private static final long serialVersionUID = -4884725655029471387L;
    private UIManager uiManager;
    private Rectangle resumeButton, settingsButton, quitButton;

    private DialogueBox dBox;
    private static String[] answers = {"Yes", "No"};
    private static String message = "If you exit without saving, you will lose your progress! Are you sure you wish to quit?";
    private static boolean makingChoice = false;

    public PauseState() {
        super();
        uiManager = new UIManager();

        /*
         * Resume Game Button
         */
        uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 376, 226, 96, Assets.genericButton));
        resumeButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 376, 226, 96);

        /*
         * Settings Button
         */
        uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 480, 226, 96, Assets.genericButton));
        settingsButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 480, 226, 96);

        /*
         * Quit Game Button
         */
        uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 584, 226, 96, Assets.genericButton));
        quitButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 584, 226, 96);

        dBox = new DialogueBox(Handler.get().getWidth() / 2 - 144, Handler.get().getHeight() / 2 - 80, 288, 160, answers, message, false);

    }

    @Override
    public void tick() {

        Rectangle mouse = Handler.get().getMouse();

        if (resumeButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed && !makingChoice) {
                MouseManager.justClosedUI = true;
                State.setState(new UITransitionState(Handler.get().getGame().gameState));
                hasBeenPressed = false;
                dBox.setPressedButton(null);
                makingChoice = false;
                DialogueBox.isOpen = false;
            }
        }

        if (settingsButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed && !makingChoice) {
                // Stop loading this UIManager and go to the settings screen
                SettingState.previousState = this;
                State.setState(new UITransitionState(Handler.get().getGame().settingState));
                hasBeenPressed = false;
                dBox.setPressedButton(null);
                makingChoice = false;
                DialogueBox.isOpen = false;
                return;
            }
        }

        if (quitButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed && !makingChoice) {
                makingChoice = true;
                hasBeenPressed = false;
                DialogueBox.isOpen = true;
                DialogueBox.hasBeenPressed = false;
            }
        }

        // If player is making a choice, show the dialoguebox
        if (makingChoice)
            dBox.tick();

        confirmExit();

        uiManager.tick();

    }

    private void confirmExit() {
        if (makingChoice && dBox.getPressedButton() != null) {
            if ("Yes".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[0])) {
                AudioManager.cleanUp();
                System.exit(0);
            } else if ("No".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[0])) {
                Handler.get().playEffect("ui/ui_button_click.wav");
                dBox.setPressedButton(null);
                makingChoice = false;
                hasBeenPressed = false;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Handler.get().getWidth(), Handler.get().getHeight());
        uiManager.render(g);

        // Render the text in the main menu
        Text.drawString(g, "Game Paused!", Handler.get().getWidth() / 2, 180, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Resume Game", Handler.get().getWidth() / 2, 424, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Settings", Handler.get().getWidth() / 2, 528, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Quit Game", Handler.get().getWidth() / 2, 632, true, Color.YELLOW, Assets.font32);

        // If player is making a choice, show the dialoguebox
        if (makingChoice)
            dBox.render(g, Color.RED);
    }

}
