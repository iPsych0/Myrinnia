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
    private Rectangle fullScreenButton, controlsButton, muteSoundButton, returnButton;
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
        uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 272, 226, 96, Assets.genericButton));
        fullScreenButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 272, 226, 96);

        /*
         * Controls Button
         */
        uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 376, 226, 96, Assets.genericButton));
        controlsButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 376, 226, 96);

        /*
         * Mute Sound
         */
        uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 480, 226, 96, Assets.genericButton));
        muteSoundButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 480, 226, 96);

        /*
         * The return button to the main menu
         */
        uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 584, 226, 96, Assets.genericButton));
        returnButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 584, 226, 96);

        soundPopup = new Rectangle(Handler.get().getWidth() / 2 - 153, 224, 306, 58);
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
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Handler.get().getWidth(), Handler.get().getHeight());
//			g.drawImage(Assets.craftWindow, -40, -40, 1040, 800, null);
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
            Text.drawString(g, "Settings", Handler.get().getWidth() / 2, 180, true, Color.YELLOW, Assets.font32);
        else
            Text.drawString(g, "Game Paused!", Handler.get().getWidth() / 2, 180, true, Color.YELLOW, Assets.font32);

        String screenSize = Handler.get().getGame().getDisplay().isFullScreen() ? "Windowed Mode" : "Fullscreen Mode";
        Text.drawString(g, screenSize, Handler.get().getWidth() / 2, 320, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Controls", Handler.get().getWidth() / 2, 424, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Mute Sounds", Handler.get().getWidth() / 2, 528, true, Color.YELLOW, Assets.font32);
        Text.drawString(g, "Return", Handler.get().getWidth() / 2, 632, true, Color.YELLOW, Assets.font32);
    }


}
