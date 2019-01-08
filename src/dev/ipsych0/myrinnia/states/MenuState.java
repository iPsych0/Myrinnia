package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Game;
import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.audio.AudioManager;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.ui.UIObject;
import dev.ipsych0.myrinnia.utils.SaveManager;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.worlds.Zone;

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
    private boolean loaded = false;
    private Rectangle newGameButton, continueButton, settingsButton, quitButton;
    private boolean displayError = false;
    private int errorDisplayTimer = 0;
    private Rectangle errorPopup;

    public MenuState() {
        super();
        uiManager = new UIManager();

        /*
         * New Game Button
         */
        uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 376, 226, 96, Assets.genericButton));
        newGameButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 376, 226, 96);


        /*
         * Continue Button
         */
        uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 480, 226, 96, Assets.genericButton));
        continueButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 480, 226, 96);

        /*
         * Settings Button
         */
        uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 584, 226, 96, Assets.genericButton));
        settingsButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 584, 226, 96);

        /*
         * Quit Game Button
         */
        uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 688, 226, 96, Assets.genericButton));
        quitButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 688, 226, 96);

        errorPopup = new Rectangle(Handler.get().getWidth() / 2 - 153, 224, 306, 58);


    }

    @Override
    public void tick() {

        Rectangle mouse = Handler.get().getMouse();

        for (UIObject o : uiManager.getObjects()) {
            if (o.getBounds().contains(mouse)) {
                o.setHovering(true);
            } else {
                o.setHovering(false);
            }
        }

        if (newGameButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                State.setState(new UITransitionState(Handler.get().getGame().gameState));
                Handler.get().playMusic(Zone.Island);
                hasBeenPressed = false;
            }
        }

        if (continueButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                Path path = Paths.get("res/savegames/save.dat");
                if (Files.notExists(path)) {
                    System.out.println(path.toString() + " does not exist.");
                    hasBeenPressed = false;
                    displayError = true;
                    errorDisplayTimer = 0;
                    return;
                } else {
                    SaveManager.loadHandler();
                    State.setState(new UITransitionState(Handler.get().getGame().recapState));
                    hasBeenPressed = false;
                    return;
                }
            }
        }

        if (settingsButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                // Stop loading this UIManager and go to the settings screen
                loaded = false;
                State.setState(new UITransitionState(Handler.get().getGame().settingState));
                SettingState.previousState = this;
                hasBeenPressed = false;
                displayError = false;
                errorDisplayTimer = 0;
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
//			g.drawImage(Assets.craftWindow, -40, -40, 1040, 800, null);
        uiManager.render(g);

        if (displayError) {
            errorDisplayTimer++;
            if (errorDisplayTimer <= 120) {
                g.drawImage(Assets.chatwindow, errorPopup.x, errorPopup.y, errorPopup.width, errorPopup.height, null);
                Text.drawString(g, "You do not have a save file yet!", errorPopup.x + errorPopup.width / 2, errorPopup.y + errorPopup.height / 2, true, Color.YELLOW, Assets.font20);
            } else {
                errorDisplayTimer = 0;
                displayError = false;
            }
        }

        // Render the text in the main menu
        Text.drawString(g, "Welcome to Myrinnia", Handler.get().getWidth() / 2, 192, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Current version: " + Game.CURRENT_VERSION, Handler.get().getWidth() / 2, 224, true, Color.YELLOW, Assets.font20);
        Text.drawString(g, "New Game", Handler.get().getWidth() / 2, 424, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Continue", Handler.get().getWidth() / 2, 528, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Settings", Handler.get().getWidth() / 2, 632, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Quit Game", Handler.get().getWidth() / 2, 736, true, Color.YELLOW, Assets.font32);
    }

}
