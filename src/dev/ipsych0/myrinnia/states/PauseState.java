package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.audio.AudioManager;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.ui.DialogueBox;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.SaveManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class PauseState extends State {

    /**
     *
     */
    private static final long serialVersionUID = -4884725655029471387L;
    private UIManager uiManager;
    private Rectangle resumeButton, settingsButton, saveButton, quitButton;

    private DialogueBox exitDbox;
    private DialogueBox saveDbox;
    private static String[] answers = {"Yes", "No"};
    private static String exitMessage = "If you exit without saving, you will lose your progress! Are you sure you wish to quit?";
    private static String saveMessage = "WARNING: Saving now will overwrite your current save file. Do you want to continue?";

    public PauseState() {
        uiManager = new UIManager();

        /*
         * Resume Game Button
         */
        resumeButton = new Rectangle(Handler.get().getWidth() / 2 - 112, Handler.get().getHeight() / 2 - 160, 224, 96);
        uiManager.addObject(new UIImageButton(resumeButton, Assets.genericButton));

        /*
         * Settings Button
         */
        settingsButton = new Rectangle(Handler.get().getWidth() / 2 - 112, Handler.get().getHeight() / 2 - 48, 224, 96);
        uiManager.addObject(new UIImageButton(settingsButton, Assets.genericButton));

        /*
         * Save Button
         */
        saveButton = new Rectangle(Handler.get().getWidth() / 2 - 112, Handler.get().getHeight() / 2 + 64, 224, 96);
        uiManager.addObject(new UIImageButton(saveButton, Assets.genericButton));

        /*
         * Quit Game Button
         */
        quitButton = new Rectangle(Handler.get().getWidth() / 2 - 112, Handler.get().getHeight() - 112, 224, 96);
        uiManager.addObject(new UIImageButton(quitButton, Assets.genericButton));

        exitDbox = new DialogueBox(Handler.get().getWidth() / 2 - 144, Handler.get().getHeight() / 2 - 80, 288, 160, answers, exitMessage, null);

        saveDbox = new DialogueBox(Handler.get().getWidth() / 2 - 144, Handler.get().getHeight() / 2 - 80, 288, 160, answers, saveMessage, null);

    }

    @Override
    public void tick() {

        Rectangle mouse = Handler.get().getMouse();

        if (resumeButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed && !exitDbox.isMakingChoice() && !saveDbox.isMakingChoice()) {
                MouseManager.justClosedUI = true;
                State.setState(new UITransitionState(Handler.get().getGame().gameState));
                hasBeenPressed = false;
                exitDbox.close();
                saveDbox.close();
            }
        }

        if (settingsButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed && !exitDbox.isMakingChoice() && !saveDbox.isMakingChoice()) {
                // Stop loading this UIManager and go to the settings screen
                SettingState.previousState = this;
                SettingState.selectedState = Handler.get().getGame().generalSettingsState;
                State.setState(new UITransitionState(Handler.get().getGame().settingState));
                hasBeenPressed = false;
                exitDbox.close();
                saveDbox.close();
                return;
            }
        }

        if (saveButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed && !exitDbox.isMakingChoice() && !saveDbox.isMakingChoice()) {
                hasBeenPressed = false;
                saveDbox.open();
            }
        }

        if (quitButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed && !exitDbox.isMakingChoice() && !saveDbox.isMakingChoice()) {
                hasBeenPressed = false;
                exitDbox.open();
            }
        }

        // If player is making a choice, show the dialoguebox
        if (exitDbox.isMakingChoice())
            exitDbox.tick();

        if (saveDbox.isMakingChoice())
            saveDbox.tick();

        confirmExit();

        uiManager.tick();

    }

    private void confirmExit() {
        if (exitDbox.isMakingChoice() && exitDbox.getPressedButton() != null) {
            if ("Yes".equalsIgnoreCase(exitDbox.getPressedButton().getButtonParam()[0])) {
                // Close window without visual delay
                Frame frame = Handler.get().getGame().getDisplay().getFrame();
                frame.setVisible(false);
                frame.dispose();

                // Clean up audio files
                AudioManager.cleanUp();
                System.exit(0);
            } else if ("No".equalsIgnoreCase(exitDbox.getPressedButton().getButtonParam()[0])) {
                Handler.get().playEffect("ui/ui_button_click.wav");
                exitDbox.close();
            }
        }

        if (saveDbox.isMakingChoice() && saveDbox.getPressedButton() != null) {
            if ("Yes".equalsIgnoreCase(saveDbox.getPressedButton().getButtonParam()[0])) {
                SaveManager.savehandler();
                saveDbox.close();
            } else if ("No".equalsIgnoreCase(saveDbox.getPressedButton().getButtonParam()[0])) {
                Handler.get().playEffect("ui/ui_button_click.wav");
                saveDbox.close();
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        uiManager.render(g);

        // Render the text in the main menu
        g.drawImage(Assets.genericButton[1], Handler.get().getWidth() / 2 - 192, 24, 384, 48, null);
        Text.drawString(g, "Game Paused!", Handler.get().getWidth() / 2, 48, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Resume Game", Handler.get().getWidth() / 2, resumeButton.y + resumeButton.height / 2, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Settings", Handler.get().getWidth() / 2, settingsButton.y + settingsButton.height / 2, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Save Game", Handler.get().getWidth() / 2, saveButton.y + saveButton.height / 2, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Quit Game", Handler.get().getWidth() / 2, quitButton.y + quitButton.height / 2, true, Color.YELLOW, Assets.font32);

        // If player is making a choice, show the dialoguebox
        if (exitDbox.isMakingChoice())
            exitDbox.render(g, Color.RED);

        if (saveDbox.isMakingChoice())
            saveDbox.render(g, Color.RED);
    }

}
