package dev.ipsych0.mygame.audio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.worlds.Zone;

public class AudioManager {
	
	private static String deviceName;
	private static long device;
	private static int[] attributes = {0};
	private static long context;
	private static ALCCapabilities alcCapabilities;
	private static ALCapabilities alCapabilities;
	private static List<Integer> buffers = new ArrayList<Integer>();
	private static Handler handler;
	public static LinkedList<Source> musicFiles = new LinkedList<>();
	public static LinkedList<Source> soundfxFiles = new LinkedList<>();
	public static Map<Zone, Source> musicMap = new HashMap<Zone, Source>();
	
	public static void init(Handler handlerClass) {
		deviceName = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
		device = ALC10.alcOpenDevice(deviceName);
		context = ALC10.alcCreateContext(device, attributes);
		ALC10.alcMakeContextCurrent(context);
		alcCapabilities = ALC.createCapabilities(device);
		alCapabilities = AL.createCapabilities(alcCapabilities);
		handler = handlerClass;
		
		if(alCapabilities.OpenAL10) {
			System.out.println("Working!");
		}
	}
	
	public static void setListenerData() {
		AL10.alListener3f(AL10.AL_POSITION, handler.getPlayer().getX(), handler.getPlayer().getY(), 2);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 2);
	}
	
	public static void setListenerData(float x, float y) {
		AL10.alListener3f(AL10.AL_POSITION, x, y, 2);
		AL10.alListener3f(AL10.AL_VELOCITY, x, y, 2);
	}
	
	public static void cleanUp() {
		for(int buffer : buffers) {
			AL10.alDeleteBuffers(buffer);
		}
		ALC10.alcDestroyContext(context);
		ALC10.alcCloseDevice(device);
	}
	
	public static int loadSound(String file) {
		int buffer = AL10.alGenBuffers();
		buffers.add(buffer);
		WaveData waveFile = WaveData.create(file);
		AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
		return buffer;
	}

}
