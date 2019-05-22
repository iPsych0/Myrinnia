package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class SettingState extends State {

    /**
     *
     */
    private static final long serialVersionUID = -5598711872871726397L;
    private UIManager uiManager;
    private UIImageButton graphicsButton, controlsButton, audioButton, returnButton;
    private UIImageButton selectedButton;
    public static State previousState;
    public static State selectedState;
    private Rectangle overlay;
    private Color selectedColor = new Color(0, 255, 255, 62);

    public SettingState() {
        this.uiManager = new UIManager();

        overlay = new Rectangle(Handler.get().getWidth() / 2 - 320, 160, 640, 417);

        /*
         * Graphics Button
         */
        graphicsButton = new UIImageButton(Handler.get().getWidth() / 2 - 192, 96, 128, 64, Assets.genericButton);
        uiManager.addObject(graphicsButton);

        /*
         * Audio Button
         */
        audioButton = new UIImageButton(Handler.get().getWidth() / 2 - 64, 96, 128, 64, Assets.genericButton);
        uiManager.addObject(audioButton);

        /*
         * Controls Button
         */
        controlsButton = new UIImageButton(Handler.get().getWidth() / 2 + 64, 96, 128, 64, Assets.genericButton);
        uiManager.addObject(controlsButton);


        /*
         * The return button to the main menu
         */
        returnButton = new UIImageButton(Handler.get().getWidth() / 2 - 112, Handler.get().getHeight() - 112, 224, 96, Assets.genericButton);
        uiManager.addObject(returnButton);

        selectedButton = graphicsButton;

    }

    @Override
    public void tick() {

        Rectangle mouse = Handler.get().getMouse();

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
                selectedState = Handler.get().getGame().graphicsState;
                selectedButton = graphicsButton;
                hasBeenPressed = false;
            }
        }

        this.uiManager.tick();

        selectedState.tick();

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.genericButton[2], overlay.x, overlay.y, overlay.width, overlay.height, null);
        this.uiManager.render(g);

        g.setColor(selectedColor);
        g.fillRect(selectedButton.x, selectedButton.y, selectedButton.width, selectedButton.height);

        Text.drawString(g, "Settings", Handler.get().getWidth() / 2, 48, true, Color.YELLOW, Assets.font32);

        Text.drawString(g, "Graphics", graphicsButton.x + graphicsButton.width / 2, graphicsButton.y + graphicsButton.height / 2, true, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Audio", audioButton.x + audioButton.width / 2, audioButton.y + audioButton.height / 2, true, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Controls", controlsButton.x + controlsButton.width / 2, controlsButton.y + controlsButton.height / 2, true, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Return", returnButton.x + returnButton.width / 2, returnButton.y + returnButton.height / 2, true, Color.YELLOW, Assets.font32);

        selectedState.render(g);
    }


}
