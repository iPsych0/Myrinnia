package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.audio.AudioManager;
import dev.ipsych0.myrinnia.audio.Source;
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
    private UIImageButton fullScreenButton, controlsButton, muteSoundButton, returnButton;
    private Rectangle soundPopup;
    private boolean displaySoundPressed = false;
    private int displaySoundTimer = 0;
    public static State previousState;

    public SettingState() {
        super();
        this.uiManager = new UIManager();

        /*
         * Fullscreen Button
         */
        fullScreenButton = new UIImageButton(Handler.get().getWidth() / 2 - 112, 224, 224, 96, Assets.genericButton);
        uiManager.addObject(fullScreenButton);

        /*
         * Controls Button
         */
        controlsButton = new UIImageButton(Handler.get().getWidth() / 2 - 112, 336, 224, 96, Assets.genericButton);
        uiManager.addObject(controlsButton);

        /*
         * Mute Sound
         */
        muteSoundButton = new UIImageButton(Handler.get().getWidth() / 2 - 112, 448, 224, 96, Assets.genericButton);
        uiManager.addObject(muteSoundButton);

        /*
         * The return button to the main menu
         */
        returnButton = new UIImageButton(Handler.get().getWidth() / 2 - 112, Handler.get().getHeight() - 112, 224, 96, Assets.genericButton);
        uiManager.addObject(returnButton);

        soundPopup = new Rectangle(Handler.get().getWidth() / 2 - 153, 80, 306, 64);
    }

    @Override
    public void tick() {

        Rectangle mouse = Handler.get().getMouse();

        if(fullScreenButton.contains(mouse)){
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                hasBeenPressed = false;
                Handler.get().getGame().getDisplay().toggleFullScreen();
            }
        }

        if (controlsButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                State.setState(new UITransitionState(Handler.get().getGame().controlsState));
                hasBeenPressed = false;
            }
        }

        if (muteSoundButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                displaySoundTimer = 0;
                displaySoundPressed = true;
                if (!Handler.get().isSoundMuted()) {
                    Handler.get().setSoundMuted(true);
                    Handler.get().saveProperty("muted","true");
                    for (Source s : AudioManager.soundfxFiles)
                        s.delete();
                    for (Source s : AudioManager.musicFiles)
                        s.delete();
                } else {
                    Handler.get().saveProperty("muted","false");
                    Handler.get().setSoundMuted(false);
                    if(previousState == Handler.get().getGame().pauseState) {
                        Handler.get().playMusic(Handler.get().getPlayer().getZone());
                    }
                }
                hasBeenPressed = false;
            }
        }

        if (returnButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                State.setState(new UITransitionState(previousState));
                hasBeenPressed = false;
                displaySoundPressed = false;
                displaySoundTimer = 0;
            }
        }

        this.uiManager.tick();

    }

    @Override
    public void render(Graphics2D g) {
        this.uiManager.render(g);

        if (displaySoundPressed) {
            displaySoundTimer++;
            if (displaySoundTimer <= 120) {
                g.drawImage(Assets.chatwindow, soundPopup.x, soundPopup.y, soundPopup.width, soundPopup.height, null);
                if (!Handler.get().isSoundMuted())
                    Text.drawString(g, "Sound unmuted!", soundPopup.x + soundPopup.width / 2, soundPopup.y + soundPopup.height / 2, true, Color.YELLOW, Assets.font20);
                else
                    Text.drawString(g, "Sound muted!", soundPopup.x + soundPopup.width / 2, soundPopup.y + soundPopup.height / 2, true, Color.YELLOW, Assets.font20);
            } else {
                displaySoundTimer = 0;
                displaySoundPressed = false;
            }
        }

        if (previousState == Handler.get().getGame().menuState)
            Text.drawString(g, "Settings", Handler.get().getWidth() / 2, 48, true, Color.YELLOW, Assets.font32);
        else
            Text.drawString(g, "Game Paused!", Handler.get().getWidth() / 2, 48, true, Color.YELLOW, Assets.font32);

        String screenSize = Handler.get().getGame().getDisplay().isFullScreen() ? "Windowed Mode" : "Fullscreen Mode";
        Text.drawString(g, screenSize, Handler.get().getWidth() / 2, fullScreenButton.y + fullScreenButton.height / 2, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Controls", Handler.get().getWidth() / 2, controlsButton.y + controlsButton.height / 2, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Mute Sounds", Handler.get().getWidth() / 2, muteSoundButton.y + muteSoundButton.height / 2, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Return", Handler.get().getWidth() / 2, returnButton.y + returnButton.height / 2, true, Color.YELLOW, Assets.font32);
    }


}
