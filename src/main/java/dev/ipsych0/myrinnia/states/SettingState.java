package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class SettingState extends State {

    /**
     *
     */
    private static final long serialVersionUID = -5598711872871726397L;
    private UIManager uiManager;
    private UIImageButton generalButton, graphicsButton, controlsButton, audioButton, returnButton;
    private UIImageButton selectedButton;
    public static State previousState;
    public static State selectedState;
    private Rectangle overlay;

    public SettingState() {
        this.uiManager = new UIManager();

        overlay = new Rectangle(Handler.get().getWidth() / 2 - 320, 160, 640, 417);

        /*
         * General Button
         */
        generalButton = new UIImageButton(Handler.get().getWidth() / 2 - 256, 96, 128, 64, Assets.genericButton);
        uiManager.addObject(generalButton);

        /*
         * Graphics Button
         */
        graphicsButton = new UIImageButton(Handler.get().getWidth() / 2 - 128, 96, 128, 64, Assets.genericButton);
        uiManager.addObject(graphicsButton);

        /*
         * Audio Button
         */
        audioButton = new UIImageButton(Handler.get().getWidth() / 2, 96, 128, 64, Assets.genericButton);
        uiManager.addObject(audioButton);

        /*
         * Controls Button
         */
        controlsButton = new UIImageButton(Handler.get().getWidth() / 2 + 128, 96, 128, 64, Assets.genericButton);
        uiManager.addObject(controlsButton);


        /*
         * The return button to the main menu
         */
        returnButton = new UIImageButton(Handler.get().getWidth() / 2 - 112, Handler.get().getHeight() - 112, 224, 96, Assets.genericButton);
        uiManager.addObject(returnButton);

        selectedButton = generalButton;

    }

    @Override
    public void tick() {

        Rectangle mouse = Handler.get().getMouse();

        if (generalButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                selectedState = Handler.get().getGame().generalSettingsState;
                selectedButton = generalButton;
                hasBeenPressed = false;
            }
        }

        if (graphicsButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                selectedState = Handler.get().getGame().graphicsState;
                selectedButton = graphicsButton;
                hasBeenPressed = false;
            }
        }

        if (audioButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                selectedState = Handler.get().getGame().audioState;
                selectedButton = audioButton;
                hasBeenPressed = false;
            }
        }

        if (controlsButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                selectedState = Handler.get().getGame().controlsState;
                selectedButton = controlsButton;
                hasBeenPressed = false;
            }
        }


        if (returnButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                State.setState(new UITransitionState(previousState));
                selectedState = Handler.get().getGame().generalSettingsState;
                selectedButton = generalButton;
                hasBeenPressed = false;
            }
        }

        this.uiManager.tick();

        selectedState.tick();

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.mainBackground, 0, 0, null);
        g.drawImage(Assets.genericButton[1], overlay.x, overlay.y, overlay.width, overlay.height, null);
        g.drawImage(Assets.genericButton[1], Handler.get().getWidth() / 2 - 192, 24, 384, 48, null);
        this.uiManager.render(g);

        g.setColor(Colors.selectedColor);
        g.fillRect(selectedButton.x, selectedButton.y, selectedButton.width, selectedButton.height);

        Text.drawString(g, "Settings", Handler.get().getWidth() / 2, 48, true, Color.YELLOW, Assets.font32);

        Text.drawString(g, "General", generalButton.x + generalButton.width / 2, generalButton.y + generalButton.height / 2, true, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Graphics", graphicsButton.x + graphicsButton.width / 2, graphicsButton.y + graphicsButton.height / 2, true, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Audio", audioButton.x + audioButton.width / 2, audioButton.y + audioButton.height / 2, true, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Controls", controlsButton.x + controlsButton.width / 2, controlsButton.y + controlsButton.height / 2, true, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Return", returnButton.x + returnButton.width / 2, returnButton.y + returnButton.height / 2, true, Color.YELLOW, Assets.font32);

        selectedState.render(g);
    }


}
