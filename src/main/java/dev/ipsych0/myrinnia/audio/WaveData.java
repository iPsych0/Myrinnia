package dev.ipsych0.myrinnia.audio;

import lombok.extern.slf4j.Slf4j;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Slf4j
public class WaveData {

    final int format;
    final int samplerate;
    private final int totalBytes;
    final ByteBuffer data;

    private final AudioInputStream audioStream;
    private final byte[] dataArray;

    private WaveData(AudioInputStream stream) {
        this.audioStream = stream;
        AudioFormat audioFormat = stream.getFormat();
        format = getOpenAlFormat(audioFormat.getChannels(), audioFormat.getSampleSizeInBits());
        this.samplerate = (int) audioFormat.getSampleRate();
        int bytesPerFrame = audioFormat.getFrameSize();
        this.totalBytes = (int) (stream.getFrameLength() * bytesPerFrame);
        this.data = BufferUtils.createByteBuffer(totalBytes);
        this.dataArray = new byte[totalBytes];
        loadData();
    }

    void dispose() {
        try {
            audioStream.close();
            data.clear();
        } catch (IOException e) {
            log.error("Exception", e);
        }
    }

    private void loadData() {
        try {
            int bytesRead = audioStream.read(dataArray, 0, totalBytes);
            data.clear();
            data.put(dataArray, 0, bytesRead);
            data.flip();
        } catch (IOException e) {
            log.error("Exception", e);
            log.error("Couldn't read bytes from audio stream!");
        }
    }


    public static WaveData create(String file) {
        WaveData wavStream;
        try (InputStream in = new FileInputStream(file)) {
            InputStream bufferedIn = new BufferedInputStream(in);
            try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn)) {
                wavStream = new WaveData(audioIn);
            }
        } catch (Exception e) {
            log.error("Exception", e);
            return null;
        }
        return wavStream;
    }


    private static int getOpenAlFormat(int channels, int bitsPerSample) {
        if (channels == 1) {
            return bitsPerSample == 8 ? AL10.AL_FORMAT_MONO8 : AL10.AL_FORMAT_MONO16;
        } else {
            return bitsPerSample == 8 ? AL10.AL_FORMAT_STEREO8 : AL10.AL_FORMAT_STEREO16;
        }
    }

}
