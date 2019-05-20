package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Game;
import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.audio.AudioManager;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.SaveManager;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MenuState extends State {

    /**
     *
     */
    private static final long serialVersionUID = 408918728311321161L;
    private UIManager uiManager;
    private UIImageButton newGameButton, continueButton, settingsButton, quitButton;

    public MenuState() {
        super();
        uiManager = new UIManager();

        int yOffset = 0;
        // Hide continue button if file not found
        Path path = Paths.get(Handler.resourcePath + "savegames/save.dat");
        if (Files.exists(path)) {
            /*
             * Continue Button
             */
            yOffset = 112;
            continueButton = new UIImageButton(Handler.get().getWidth() / 2 - 112, 224, 224, 96, Assets.genericButton);
            uiManager.addObject(new UIImageButton(continueButton, Assets.genericButton));
        }

        /*
         * New Game Button
         */
        newGameButton = new UIImageButton(Handler.get().getWidth() / 2 - 112, 224 + yOffset, 224, 96, Assets.genericButton);
        uiManager.addObject(newGameButton);

        /*
         * Settings Button
         */
        settingsButton = new UIImageButton(Handler.get().getWidth() / 2 - 112, 336 + yOffset, 224, 96, Assets.genericButton);
        uiManager.addObject(settingsButton);

        /*
         * Quit Game Button
         */
        quitButton = new UIImageButton(Handler.get().getWidth() / 2 - 112, Handler.get().getHeight() - 112, 224, 96, Assets.genericButton);
        uiManager.addObject(quitButton);

    }

    @Override
    public void tick() {

        Rectangle mouse = Handler.get().getMouse();

        if (newGameButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                State.setState(new UITransitionState(Handler.get().getGame().gameState));
                Handler.get().playMusic(Zone.PortAzure);
                hasBeenPressed = false;
            }
        }

        if (continueButton != null && continueButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                SaveManager.loadHandler();
                State.setState(new UITransitionState(Handler.get().getGame().recapState));
                hasBeenPressed = false;
            }
        }

        if (settingsButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                // Stop loading this UIManager and go to the settings screen
                State.setState(new UITransitionState(Handler.get().getGame().settingState));
                SettingState.previousState = this;
                SettingState.selectedState = Handler.get().getGame().graphicsState;
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
    public void render(Graphics2D g) {
        uiManager.render(g);

        // Render the text in the main menu
        Text.drawString(g, "Elements of Myrinnia", Handler.get().getWidth() / 2, 48, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Current version: " + Game.CURRENT_VERSION, Handler.get().getWidth() / 2, 80, true, Color.YELLOW, Assets.font20);

        if (continueButton != null) {
            Text.drawString(g, "Continue", continueButton.x + continueButton.width / 2, continueButton.y + continueButton.height / 2, true, Color.YELLOW, Assets.font32);
        }
        Text.drawString(g, "New Game", newGameButton.x + newGameButton.width / 2, newGameButton.y + newGameButton.height / 2, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Settings", settingsButton.x + settingsButton.width / 2, settingsButton.y + settingsButton.height / 2, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Quit Game", quitButton.x + quitButton.width / 2, quitButton.y + quitButton.height / 2, true, Color.YELLOW, Assets.font32);
    }

}
