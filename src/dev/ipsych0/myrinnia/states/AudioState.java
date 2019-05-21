package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.audio.AudioManager;
import dev.ipsych0.myrinnia.audio.Source;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class AudioState extends State {

    private Rectangle overlay;
    private Rectangle soundPopup;
    private UIImageButton muteSoundButton, unmuteSoundButton;
    private UIManager uiManager;
    public static boolean displaySoundPressed = false;
    public static int displaySoundTimer = 0;

    public AudioState() {
        this.uiManager = new UIManager();

        overlay = new Rectangle(Handler.get().getWidth() / 2 - 320, 160, 640, 417);

        muteSoundButton = new UIImageButton(overlay.x + 24, overlay.y + 32, 32, 32, Assets.genericButton);
        unmuteSoundButton = new UIImageButton(overlay.x + 56, overlay.y + 32, 32, 32, Assets.genericButton);

        uiManager.addObject(muteSoundButton);
        uiManager.addObject(unmuteSoundButton);

        soundPopup = new Rectangle(Handler.get().getWidth() / 2 - 153, 80, 306, 64);
    }

    @Override
    public void tick() {
        Rectangle mouse = Handler.get().getMouse();

        if (muteSoundButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                displaySoundTimer = 0;
                displaySoundPressed = true;
                if (!Handler.get().isSoundMuted()) {
                    Handler.get().setSoundMuted(true);
                    Handler.get().saveProperty("muted", "true");
                    for (Source s : AudioManager.soundfxFiles)
                        s.delete();
                    for (Source s : AudioManager.musicFiles)
                        s.delete();
                }
                hasBeenPressed = false;
            }
        }
        if(unmuteSoundButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                Handler.get().saveProperty("muted", "false");
                Handler.get().setSoundMuted(false);
                if (SettingState.previousState == Handler.get().getGame().pauseState) {
                    Handler.get().playMusic(Handler.get().getPlayer().getZone());
                }
                hasBeenPressed = false;
            }
        }

        uiManager.tick();
    }

    @Override
    public void render(Graphics2D g) {
        uiManager.render(g);

        Text.drawString(g, "Music:", overlay.x + 8, overlay.y + 32, false, Color.YELLOW, Assets.font24);
        Text.drawString(g, "Volume:", overlay.x + 8, overlay.y + 80, false, Color.YELLOW, Assets.font20);

        Text.drawString(g, "Sound Effects:", overlay.x + 8, overlay.y + 160, false, Color.YELLOW, Assets.font24);
        Text.drawString(g, "Volume:", overlay.x + 8, overlay.y + 208, false, Color.YELLOW, Assets.font20);

        Text.drawString(g, "Mute Music:", overlay.x + 8, overlay.y + 288, false, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Mute Sound Effects:", overlay.x + 8, overlay.y + 320, false, Color.YELLOW, Assets.font20);

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
    }
}
