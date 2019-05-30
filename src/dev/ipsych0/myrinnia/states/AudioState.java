package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.audio.AudioManager;
import dev.ipsych0.myrinnia.audio.Source;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.SliderBar;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class AudioState extends State {

    private Rectangle overlay;
    private UIImageButton muteSoundButton, muteSfxButton;
    private SliderBar musicSlider, sfxSlider;
    private UIManager uiManager;

    public AudioState() {
        this.uiManager = new UIManager();

        overlay = new Rectangle(Handler.get().getWidth() / 2 - 320, 160, 640, 417);

        try{
            loadAudioSettings();
        } catch (Exception e){
            musicSlider = new SliderBar(overlay.x + 12, overlay.y + 96, 101, 20, 100);
            sfxSlider = new SliderBar(overlay.x + 12, overlay.y + 224, 101, 20, 100);
        }


        muteSoundButton = new UIImageButton(overlay.x + 136, overlay.y + 268, 32, 32, Assets.genericButton);
        muteSfxButton = new UIImageButton(overlay.x + 136, overlay.y + 300, 32, 32, Assets.genericButton);

        uiManager.addObject(muteSoundButton);
        uiManager.addObject(muteSfxButton);
        uiManager.addObject(musicSlider);
        uiManager.addObject(sfxSlider);

    }

    private void loadAudioSettings(){
        // Read audio properties from file
        String sfxVolume = Handler.get().loadProperty("sfxVolume");
        String soundVolume = Handler.get().loadProperty("musicVolume");
        String sfxMuted = Handler.get().loadProperty("sfxMuted");
        String soundMuted = Handler.get().loadProperty("soundMuted");

        // Get the slider percentage
        int musicPos = Integer.parseInt(soundVolume);
        int sfxPos = Integer.parseInt(sfxVolume);
        musicSlider = new SliderBar(overlay.x + 12, overlay.y + 96, 101, 20, musicPos);
        sfxSlider = new SliderBar(overlay.x + 12, overlay.y + 224, 101, 20, sfxPos);

        // Set muted settings
        AudioManager.sfxMuted = Boolean.parseBoolean(sfxMuted);
        AudioManager.soundMuted = Boolean.parseBoolean(soundMuted);

        // Get volume float values
        double sfxVolumeD = Double.parseDouble(sfxVolume);
        double soundVolumeD = Double.parseDouble(soundVolume);
        float sfxRatio = (float)(sfxVolumeD / 100.0);
        float soundRatio = (float)(soundVolumeD / 100.0);

        // Set volumes
        AudioManager.sfxVolume = AudioManager.sfxVolume * sfxRatio;
        AudioManager.musicVolume = AudioManager.musicVolume * soundRatio;
    }

    @Override
    public void tick() {
        Rectangle mouse = Handler.get().getMouse();

        if (muteSoundButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                if (!AudioManager.soundMuted) {
                    AudioManager.soundMuted = true;
                    Handler.get().saveProperty("soundMuted", "true");
                    for (Source s : AudioManager.musicFiles)
                        s.delete();
                } else {
                    Handler.get().saveProperty("soundMuted", "false");
                    AudioManager.soundMuted = false;
                    if (SettingState.previousState == Handler.get().getGame().pauseState) {
                        Handler.get().playMusic(Handler.get().getPlayer().getZone());
                    }
                    hasBeenPressed = false;
                }
            }
            hasBeenPressed = false;
        }

        if (muteSfxButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                if (!AudioManager.sfxMuted) {
                    AudioManager.sfxMuted = true;
                    Handler.get().saveProperty("sfxMuted", "true");
                    for (Source s : AudioManager.soundfxFiles)
                        s.delete();
                } else {
                    Handler.get().saveProperty("sfxMuted", "false");
                    AudioManager.sfxMuted = false;
                    hasBeenPressed = false;
                }
            }
            hasBeenPressed = false;
        }


        uiManager.tick();

        if (musicSlider.isChanged()) {
            changeMusicVolume(musicSlider.getSliderXPos());
        }
        if (sfxSlider.isChanged()) {
            changeSfxVolume(sfxSlider.getSliderXPos());
        }

        if (sfxSlider.isChanged() && SliderBar.released) {
            System.out.println("test1");
            Handler.get().saveProperty("sfxVolume", Integer.toString(sfxSlider.getSliderXPos()));
            SliderBar.released = false;
        } else if (musicSlider.isChanged() && SliderBar.released) {
            System.out.println("test2");
            Handler.get().saveProperty("musicVolume", Integer.toString(musicSlider.getSliderXPos()));
            SliderBar.released = false;
        }

    }

    @Override
    public void render(Graphics2D g) {
        uiManager.render(g);

        Text.drawString(g, "Music:", overlay.x + 8, overlay.y + 32, false, Color.YELLOW, Assets.font24);
        Text.drawString(g, "Volume:", overlay.x + 8, overlay.y + 80, false, Color.YELLOW, Assets.font20);

        Text.drawString(g, "Sound Effects:", overlay.x + 8, overlay.y + 160, false, Color.YELLOW, Assets.font24);
        Text.drawString(g, "Volume:", overlay.x + 8, overlay.y + 208, false, Color.YELLOW, Assets.font20);

        Text.drawString(g, "Mute Music:", overlay.x + 8, overlay.y + 288, false, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Mute Sfx:", overlay.x + 8, overlay.y + 320, false, Color.YELLOW, Assets.font20);

        if (AudioManager.soundMuted) {
            Text.drawString(g, "X", muteSoundButton.x + 16, muteSoundButton.y + 16, true, Color.RED, Assets.font20);
        }

        if (AudioManager.sfxMuted) {
            Text.drawString(g, "X", muteSfxButton.x + 16, muteSfxButton.y + 16, true, Color.RED, Assets.font20);
        }


    }

    private void changeMusicVolume(int percentage) {
        double volumeD = (double) percentage / 100.0;
        float newVolume = (float) (volumeD * 0.4);
        AudioManager.musicVolume = newVolume;
        for (Source s : AudioManager.musicFiles) {
            s.setVolume(newVolume);
            s.setFadeOutVolume(newVolume);
        }
    }

    private void changeSfxVolume(int percentage) {
        double volumeD = (double) percentage / 100.0;
        float newVolume = (float) (volumeD * 0.15);
        AudioManager.sfxVolume = newVolume;
        for (Source s : AudioManager.soundfxFiles) {
            s.setVolume(newVolume);
            s.setFadeOutVolume(newVolume);
        }
    }
}
