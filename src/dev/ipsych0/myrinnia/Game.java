package dev.ipsych0.myrinnia;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.Serializable;

import dev.ipsych0.myrinnia.audio.AudioManager;
import dev.ipsych0.myrinnia.display.Display;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.gfx.GameCamera;
import dev.ipsych0.myrinnia.input.KeyManager;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.states.ControlsState;
import dev.ipsych0.myrinnia.states.GameState;
import dev.ipsych0.myrinnia.states.MenuState;
import dev.ipsych0.myrinnia.states.PauseState;
import dev.ipsych0.myrinnia.states.RecapState;
import dev.ipsych0.myrinnia.states.SettingState;
import dev.ipsych0.myrinnia.states.State;

public class Game implements Runnable, Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1320270378451615572L;
	private Display display;
	private int width, height;
	private String title;
	private int ticks = 0;
	private int framesPerSecond = 0;

	private boolean running = false;
	private transient Thread thread;

	private transient BufferStrategy bs;
	private transient Graphics g;

	// States
	public State gameState;
	public State menuState;
	public State settingState;
	public State controlsState;
	public State pauseState;
	public State recapState;

	// Input
	private KeyManager keyManager;
	private MouseManager mouseManager;

	// Camera
	private GameCamera gameCamera;
	
	private static Game game;
	private static Handler handler;

	public static Game get() {
		if(game == null) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			double width = screenSize.getWidth();
			double height = screenSize.getHeight();
			game = new Game("Elements of Myrinnia Pre-Alpha Development v0.7", (int)width, (int)height);
		}
		return game;
	}
	
	private Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}

	private void init() {
		display = new Display(title, width, height);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();
		
		handler = Handler.get();
		// Create instance of Handler & gamecamera
		gameCamera = new GameCamera(0, 0);

		// Create the different states for menus/game
		menuState = new MenuState();
		gameState = new GameState();
		settingState = new SettingState();
		controlsState = new ControlsState();
		pauseState = new PauseState();
		recapState = new RecapState();
				

		AudioManager.init();
		AudioManager.setListenerData();

		// Set the initial state to the menu state
		State.setState(menuState);

	}

	private void tick() {
		mouseManager.tick();
		keyManager.tick();
		AudioManager.tick();
		if (State.getState() != null) {
			State.getState().tick();
		}
	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		// Clear screen
		g.clearRect(0, 0, width, height);
		// Draw here

		if (State.getState() != null) {
			State.getState().render(g);
		}

		// End draw
		bs.show();
		g.dispose();
	}

	@Override
	public void run() {

		init();
		//
		// int fps = 60;
		// double timePerTick = 1000000000 / fps;
		// double delta = 0;
		// long now;
		// long lastTime = System.nanoTime();
		// long timer = 0;
		//
		//
		// while(running){
		// now = System.nanoTime();
		// delta += (now - lastTime) / timePerTick;
		// timer += now - lastTime;
		// lastTime = now;
		//
		// if(delta >= 1){
		// tick();
		// render();
		// ticks++;
		// delta--;
		// }
		//
		// if(timer >= 1000000000){
		// framesPerSecond = ticks;
		// ticks = 0;
		// timer = 0;
		// }
		// }
		//
		// stop();

		// This value would probably be stored elsewhere.
		final double GAME_HERTZ = 60.0;
		// Calculate how many ms each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		// At the very most we will update the game this many times before a new render.
		// If you're worried about visual hitches more than perfect timing, set this to
		// 1.
		final int MAX_UPDATES_BEFORE_RENDER = 1;
		// We will need the last update time.
		double lastUpdateTime = System.nanoTime();
		// Store the last time we rendered.
		double lastRenderTime = System.nanoTime();

		// If we are able to get as high as this FPS, don't render again.
		final double TARGET_FPS = 60;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

		// Simple way of finding FPS.
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);

		while (running) {
			double now = System.nanoTime();
			int updateCount = 0;
			ticks++;

			// Do as many game updates as we need to, potentially playing catchup.
			while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
				tick();
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}

			// If for some reason an update takes forever, we don't want to do an insane
			// number of catchups.
			// If you were doing some sort of game that needed to keep EXACT time, you would
			// get rid of this.
			if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
				lastUpdateTime = now - TIME_BETWEEN_UPDATES;
			}

			// Render. To do so, we need to calculate interpolation for a smooth render.
			render();
			lastRenderTime = now;

			// Update the frames we got.

			int thisSecond = (int) (lastUpdateTime / 1000000000);

			if (thisSecond > lastSecondTime) {
				framesPerSecond = ticks;
				lastSecondTime = thisSecond;
				ticks = 0;
			}

			// Yield until it has been at least the target time between renders. This saves
			// the CPU from hogging.
			while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
				Thread.yield();

				// This stops the app from consuming all your CPU. It makes this slightly less
				// accurate, but is worth it.
				// You can remove this line and it will still work (better), your CPU just
				// climbs on certain OSes.
				try {
					Thread.sleep(1);
				} catch (Exception e) {
				}

				now = System.nanoTime();
			}

		}

		stop();
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public int getFramesPerSecond() {
		return framesPerSecond;
	}

	public void setFramesPerSecond(int framesPerSecond) {
		this.framesPerSecond = framesPerSecond;
	}

	public void setKeyManager(KeyManager keyManager) {
		this.keyManager = keyManager;
	}

	public void setMouseManager(MouseManager mouseManager) {
		this.mouseManager = mouseManager;
	}

	public void setGameCamera(GameCamera gameCamera) {
		this.gameCamera = gameCamera;
	}

	public static void setHandler(Handler handler) {
		Game.handler = handler;
	}
}