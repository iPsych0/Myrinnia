package dev.ipsych0.mygame.audio;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	public static Zone zone;
	
	public static void init(Handler handlerClass) {
		deviceName = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
		device = ALC10.alcOpenDevice(deviceName);
		context = ALC10.alcCreateContext(device, attributes);
		
		ALC10.alcMakeContextCurrent(context);
		
		alcCapabilities = ALC.createCapabilities(device);
		alCapabilities = AL.createCapabilities(alcCapabilities);
		
		handler = handlerClass;
	}
	
	public static void tick() {
		// Check for music that has ended to clean up
		if(!musicFiles.isEmpty()) {
			Collection<Source> deleted = new ArrayList<Source>();
			for(Source s : musicFiles) {
				if(s.isFadingIn()) 
					fadeIn(s);
				else if(s.isFadingOut())
					fadeOut(s);
				if(!s.isPlaying()) {
					deleted.add(s);
					s.delete();
				}
			}
			musicFiles.removeAll(deleted);
		}
		
		// Check for sound effects that have ended to clean up
		if(!soundfxFiles.isEmpty()) {
			Collection<Source> deleted = new ArrayList<Source>();
			for(Source s : soundfxFiles) {
				if(!s.isPlaying()) {
					deleted.add(s);
					s.delete();
				}
			}
			soundfxFiles.removeAll(deleted);
		}
	}
	
	private static void fadeIn(Source s) {
		s.setFadingTimer(s.getFadingTimer()+1);
		if(s.getFadingTimer() > 150) {
			s.setFadeInVolume(s.getFadeInVolume()+0.002f);
			s.setVolume(s.getFadeInVolume());
			if(s.getFadeInVolume() >= 0.4f) {
				s.setFadingIn(false);
			}
		}
	}
	
	private static void fadeOut(Source s) {
		s.setFadeOutVolume(s.getFadeOutVolume()-0.002f);
		s.setVolume(s.getFadeOutVolume());
		if(s.getFadeOutVolume() <= 0.0f) {
			s.setFadingOut(false);
			s.delete();
		}
	}
	
	public static void setListenerData() {
		AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}
	
	public static void cleanUp() {
		for(int buffer : buffers) {
			AL10.alDeleteBuffers(buffer);
		}
		ALC10.alcDestroyContext(context);
		ALC10.alcCloseDevice(device);
	}
	
	public static int loadSound(File file) throws FileNotFoundException{
		  int buffer = AL10.alGenBuffers();
		  buffers.add(buffer);
		  BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
		  WaveData waveFile = WaveData.create(is);
		  AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
		  waveFile.dispose();
		  return buffer;
		 }

}
