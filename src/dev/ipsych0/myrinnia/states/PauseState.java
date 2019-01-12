package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.audio.AudioManager;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.ui.UIObject;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class PauseState extends State {

    /**
     *
     */
    private static final long serialVersionUID = -4884725655029471387L;
    private UIManager uiManager;
    private boolean loaded = false;
    private Rectangle resumeButton, settingsButton, quitButton;

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


    }

    @Override
    public void tick() {

        Rectangle mouse = Handler.get().getMouse();

        if (resumeButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                State.setState(new UITransitionState(Handler.get().getGame().gameState));
                hasBeenPressed = false;
            }
        }

        if (settingsButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                // Stop loading this UIManager and go to the settings screen
                loaded = false;
                SettingState.previousState = this;
                State.setState(new UITransitionState(Handler.get().getGame().settingState));
                hasBeenPressed = false;
            }
        }

        if (quitButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                AudioManager.cleanUp();
                System.exit(0);
                hasBeenPressed = false;
            }
        }

        uiManager.tick();

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Handler.get().getWidth(), Handler.get().getHeight());
        uiManager.render(g);

        // Render the text in the main menu
        Text.drawString(g, "Game Paused!", Handler.get().getWidth() / 2, 180, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Resume Game", Handler.get().getWidth() / 2, 424, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Settings", Handler.get().getWidth() / 2, 528, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Quit Game", Handler.get().getWidth() / 2, 632, true, Color.YELLOW, Assets.font32);
    }

}
