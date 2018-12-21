package dev.ipsych0.myrinnia.audio;

import org.lwjgl.openal.AL10;

public class Source {

    private int sourceId;
    private boolean fadingIn, fadingOut;
    private float fadeInVolume = 0.0f, fadeOutVolume = 0.4f;
    private int fadingTimer = 0;

    public Source() {
        sourceId = AL10.alGenSources();
    }

    public void playMusic(int buffer) {
        AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
        AL10.alSourcePlay(sourceId);
    }

    public void playEffect(int buffer) {
        stop();
        AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
        AL10.alSourcePlay(sourceId);
    }

    public void delete() {
        stop();
        AL10.alDeleteSources(sourceId);
    }

    public void pause() {
        AL10.alSourcePause(sourceId);
    }

    public void continuePlaying() {
        AL10.alSourcePlay(sourceId);
    }

    public void stop() {
        AL10.alSourceStop(sourceId);
    }

    public void setVelocity(float x, float y) {
        AL10.alSource3f(sourceId, AL10.AL_VELOCITY, x, y, 2);
    }

    public void setLooping(boolean loop) {
        AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    public boolean isPlaying() {
        return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }

    public void setVolume(float volume) {
        AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
    }

    public void setPitch(float pitch) {
        AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
    }

    public void setPosition(float x, float y) {
        AL10.alSource3f(sourceId, AL10.AL_POSITION, x, y, 2);
    }

    public boolean isFadingIn() {
        return fadingIn;
    }

    public void setFadingIn(boolean fadingIn) {
        this.fadingIn = fadingIn;
    }

    public boolean isFadingOut() {
        return fadingOut;
    }

    public void setFadingOut(boolean fadingOut) {
        this.fadingOut = fadingOut;
    }

    public float getFadeInVolume() {
        return fadeInVolume;
    }

    public void setFadeInVolume(float fadeInVolume) {
        this.fadeInVolume = fadeInVolume;
    }

    public float getFadeOutVolume() {
        return fadeOutVolume;
    }

    public void setFadeOutVolume(float fadeOutVolume) {
        this.fadeOutVolume = fadeOutVolume;
    }

    public int getFadingTimer() {
        return fadingTimer;
    }

    public void setFadingTimer(int fadingTimer) {
        this.fadingTimer = fadingTimer;
    }

}
