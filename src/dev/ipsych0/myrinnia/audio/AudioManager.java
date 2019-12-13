package dev.ipsych0.myrinnia.audio;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.worlds.Zone;
import org.lwjgl.openal.*;
import org.lwjgl.system.MemoryStack;

import java.io.FileNotFoundException;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.*;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class AudioManager {

    private static String deviceName;
    private static long device;
    private static int[] attributes = {0};
    private static long context;
    private static ALCCapabilities alcCapabilities;
    private static ALCapabilities alCapabilities;
    private static List<Integer> buffers = new ArrayList<>();
    public static List<Source> musicFiles = new ArrayList<>();
    public static List<Source> soundfxFiles = new ArrayList<>();
    private static Map<String, Integer> soundMap = new HashMap<>();
    private static Zone zone;
    public static float musicVolume = 0.4f, sfxVolume = 0.2f;
    public static boolean soundMuted, sfxMuted;

    public static void init() {
        deviceName = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
        device = ALC10.alcOpenDevice(deviceName);
        context = ALC10.alcCreateContext(device, attributes);

        ALC10.alcMakeContextCurrent(context);

        alcCapabilities = ALC.createCapabilities(device);
        alCapabilities = AL.createCapabilities(alcCapabilities);

    }

    public static void tick() {
        // Check for music that has ended to clean up
        if (!musicFiles.isEmpty()) {
            Collection<Source> deleted = new ArrayList<>();
            for (Source s : musicFiles) {
                if (s.isFadingIn())
                    fadeIn(s);
                else if (s.isFadingOut())
                    fadeOut(s);
                if (!s.isPlaying()) {
                    deleted.add(s);
                    s.delete();
                }
            }
            musicFiles.removeAll(deleted);
        }

        // Check for sound effects that have ended to clean up
        if (!soundfxFiles.isEmpty()) {
            Collection<Source> deleted = new ArrayList<>();
            for (Source s : soundfxFiles) {
                if (!s.isPlaying()) {
                    deleted.add(s);
                    s.delete();
                }
            }
            soundfxFiles.removeAll(deleted);
        }
    }

    private static void fadeIn(Source s) {
        s.setFadingTimer(s.getFadingTimer() + 1);
        if (s.getFadingTimer() > 150) {
            s.setFadeInVolume(s.getFadeInVolume() + (musicVolume / 0.4f * 0.002f));
            s.setVolume(s.getFadeInVolume());
            if (s.getFadeInVolume() >= musicVolume) {
                s.setFadingIn(false);
            }
        }
    }

    private static void fadeOut(Source s) {
        s.setFadeOutVolume(s.getFadeOutVolume() - (musicVolume / 0.4f * 0.002f));
        s.setVolume(s.getFadeOutVolume());
        if (s.getFadeOutVolume() <= 0.0f) {
            s.setFadingOut(false);
            s.delete();
        }
    }

    public static void setListenerData() {
        AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
        AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
    }

    public static void cleanUp() {
        for (int buffer : buffers) {
            AL10.alDeleteBuffers(buffer);
        }
        ALC10.alcDestroyContext(context);
        ALC10.alcCloseDevice(device);
    }

    public static int playOggSound() {
        ShortBuffer rawAudioBuffer;

        int channels;
        int sampleRate;

        try (MemoryStack stack = stackPush()) {
            //Allocate space to store return information from the function
            IntBuffer channelsBuffer = stack.mallocInt(1);
            IntBuffer sampleRateBuffer = stack.mallocInt(1);

            rawAudioBuffer = stb_vorbis_decode_filename("sound.ogg", channelsBuffer, sampleRateBuffer);

            //Retreive the extra information that was stored in the buffers by the function
            channels = channelsBuffer.get(0);
            sampleRate = sampleRateBuffer.get(0);
        }

        //Find the correct OpenAL format
        int format = -1;
        if (channels == 1) {
            format = AL_FORMAT_MONO16;
        } else if (channels == 2) {
            format = AL_FORMAT_STEREO16;
        }

        //Request space for the buffer
        int bufferPointer = alGenBuffers();

        //Send the data to OpenAL
        alBufferData(bufferPointer, format, rawAudioBuffer, sampleRate);

        //Free the memory allocated by STB
        free(rawAudioBuffer);

        return bufferPointer;
    }

    public static int loadSound(String file) throws FileNotFoundException {
        // If the sound has been loaded already, load the same buffer to prevent memory consumption
        if (soundMap.containsKey(file)) {
            return soundMap.get(file);
        }

        if (file.endsWith(".wav")) {
            return loadWav(file);
        } else if (file.endsWith(".ogg")) {
            return loadOgg(file);
        } else {
            throw new IllegalArgumentException("Audio format not supported! Supported formats: .wav, .ogg");
        }
    }

    private static int loadOgg(String file) throws FileNotFoundException {
        ShortBuffer rawAudioBuffer;

        String fixedFile;
        if (!Handler.isJar) {
            fixedFile = file.replaceFirst("/", Handler.resourcePath);
        } else {
            fixedFile = Handler.jarFile.getParentFile().getAbsolutePath() + file;
        }

        int channels;
        int sampleRate;

        try (MemoryStack stack = stackPush()) {
            //Allocate space to store return information from the function
            IntBuffer channelsBuffer = stack.mallocInt(1);
            IntBuffer sampleRateBuffer = stack.mallocInt(1);

            rawAudioBuffer = stb_vorbis_decode_filename(fixedFile, channelsBuffer, sampleRateBuffer);

            if (rawAudioBuffer == null)
                throw new FileNotFoundException("Could not find file: " + file);

            //Retreive the extra information that was stored in the buffers by the function
            channels = channelsBuffer.get(0);
            sampleRate = sampleRateBuffer.get(0);
        }

        //Find the correct OpenAL format
        int format = -1;
        if (channels == 1) {
            format = AL_FORMAT_MONO16;
        } else if (channels == 2) {
            format = AL_FORMAT_STEREO16;
        }

        //Request space for the buffer
        int buffer = alGenBuffers();

        //Send the data to OpenAL
        alBufferData(buffer, format, rawAudioBuffer, sampleRate);

        //Free the memory allocated by STB
        free(rawAudioBuffer);

        buffers.add(buffer);
        soundMap.put(file, buffer);

        return buffer;
    }

    private static int loadWav(String file) throws FileNotFoundException {
        int buffer = AL10.alGenBuffers();
        buffers.add(buffer);
        soundMap.put(file, buffer);
        WaveData waveFile = WaveData.create(file);

        if (waveFile == null) {
            throw new FileNotFoundException("File not found: " + file);
        }

        AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        return buffer;
    }

    public static void fadeSongs(Zone zone, int buffer) {
        if (musicFiles.size() > 0) {
            if (AudioManager.zone != null) {
                if (!AudioManager.zone.getMusicFile().equals(zone.getMusicFile())) {
                    AudioManager.zone = zone;
                    musicFiles.add(new Source());
                    if (musicFiles.size() > 2) {
                        for (int i = 1; i < musicFiles.size() - 1; i++) {
                            musicFiles.get(i).delete();
                        }
                    } else {
                        musicFiles.get(0).setFadingOut(true);
                    }
                    musicFiles.get(musicFiles.size() - 1).setVolume(0.0f);
                    musicFiles.get(musicFiles.size() - 1).setFadingIn(true);
                    musicFiles.get(musicFiles.size() - 1).setLooping(true);
                    musicFiles.get(musicFiles.size() - 1).playMusic(buffer);
                } else {
                    AudioManager.zone = zone;
                    for (int i = 0; i < musicFiles.size() - 1; i++) {
                        musicFiles.get(i).setFadingOut(true);
                    }
                }
            }
        } else {
            AudioManager.zone = zone;
            musicFiles.add(new Source());
            musicFiles.get(musicFiles.size() - 1).setVolume(musicVolume);
            musicFiles.get(musicFiles.size() - 1).setLooping(true);
            musicFiles.get(musicFiles.size() - 1).playMusic(buffer);
        }
    }

    public static void fadeSongs(String newSong, int buffer) {
        if (musicFiles.size() > 0) {
            if (AudioManager.zone != null) {
                if (!AudioManager.zone.getMusicFile().equals(newSong)) {
                    musicFiles.add(new Source());
                    if (musicFiles.size() > 2) {
                        for (int i = 1; i < musicFiles.size() - 1; i++) {
                            musicFiles.get(i).delete();
                        }
                    } else {
                        musicFiles.get(0).setFadingOut(true);
                    }
                    musicFiles.get(musicFiles.size() - 1).setVolume(0.0f);
                    musicFiles.get(musicFiles.size() - 1).setFadingIn(true);
                    musicFiles.get(musicFiles.size() - 1).setLooping(true);
                    musicFiles.get(musicFiles.size() - 1).playMusic(buffer);
                } else {
                    for (int i = 0; i < musicFiles.size() - 1; i++) {
                        musicFiles.get(i).setFadingOut(true);
                    }
                }
            }
        } else {
            musicFiles.add(new Source());
            musicFiles.get(musicFiles.size() - 1).setVolume(musicVolume);
            musicFiles.get(musicFiles.size() - 1).setLooping(true);
            musicFiles.get(musicFiles.size() - 1).playMusic(buffer);
        }
    }

}
