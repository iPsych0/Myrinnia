package dev.ipsych0.mygame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.ipsych0.mygame.display.Display;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.GameCamera;
import dev.ipsych0.mygame.input.KeyManager;
import dev.ipsych0.mygame.input.MouseManager;
import dev.ipsych0.mygame.states.GameState;
import dev.ipsych0.mygame.states.MenuState;
import dev.ipsych0.mygame.states.State;
import dev.ipsych0.mygame.utils.SaveManager;

public class Game implements Runnable {

	private Display display;
	private int width, height;
	public String title;
	public final int screenWidth = 960;//768
	public final int screenHeight = 720;//576
	public int posX = 0;
	public int posY = 0;
	private int ticks = 0;
	public int framesPerSecond = 0;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	// States
	public State gameState;
	public State menuState;
	public State settingsState;
	
	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	// Camera
	private GameCamera gameCamera;
	
	private SaveManager saveManager;
	
	// Handler
	
	private Handler handler;
	
	public Game(String title, int width, int height){
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	private void init(){
		display = new Display(title, width, height);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();
		
		handler = new Handler(this);
		gameCamera = new GameCamera(handler, 0, 0);
		
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		State.setState(menuState);
		saveManager = new SaveManager(handler);
	}
	
	private void tick(){
		mouseManager.tick();
		keyManager.tick();
		if(State.getState() != null){
			State.getState().tick();
		}
	}
	
	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		// Clear screen
		g.clearRect(0, 0, width, height);
		// Draw here
		
		if(State.getState() != null){
			State.getState().render(g);
		}
		
		
		
		//End draw
		bs.show();
		g.dispose();
	}
	
	
	@Override
	public void run(){
		
		init();
		
//		int fps = 60;
//		double timePerTick = 1000000000 / fps;
//		double delta = 0;
//		long now;
//		long lastTime = System.nanoTime();
//		long timer = 0;
//		
//		
//		while(running){
//			now = System.nanoTime();
//			delta += (now - lastTime) / timePerTick;
//			timer += now - lastTime;
//			lastTime = now;
//			
//			if(delta >= 1){
//				tick();
//				render();
//				ticks++;
//				delta--;
//			}
//			
//			if(timer >= 1000000000){
//				framesPerSecond = ticks;
//				ticks = 0;
//				timer = 0;
//			}
//		}
		
		//This value would probably be stored elsewhere.
	      final double GAME_HERTZ = 60.0;
	      //Calculate how many ns each frame should take for our target game hertz.
	      final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
	      //At the very most we will update the game this many times before a new render.
	      //If you're worried about visual hitches more than perfect timing, set this to 1.
	      final int MAX_UPDATES_BEFORE_RENDER = 5;
	      //We will need the last update time.
	      double lastUpdateTime = System.nanoTime();
	      //Store the last time we rendered.
	      double lastRenderTime = System.nanoTime();
	      
	      //If we are able to get as high as this FPS, don't render again.
	      final double TARGET_FPS = 60;
	      final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
	      
	      //Simple way of finding FPS.
	      int lastSecondTime = (int) (lastUpdateTime / 1000000000);
	      
	      while (running) {
	         double now = System.nanoTime();
	         int updateCount = 0;
	         ticks++;
	         

             //Do as many game updates as we need to, potentially playing catchup.
            while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
            {
               tick();
               lastUpdateTime += TIME_BETWEEN_UPDATES;
               updateCount++;
            }
   
            //If for some reason an update takes forever, we don't want to do an insane number of catchups.
            //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
            if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
            {
               lastUpdateTime = now - TIME_BETWEEN_UPDATES;
            }
         
            //Render. To do so, we need to calculate interpolation for a smooth render.
            render();
            lastRenderTime = now;
         
            //Update the frames we got.
            int thisSecond = (int) (lastUpdateTime / 1000000000);
            
            if (thisSecond > lastSecondTime) {
	            framesPerSecond = ticks;
	            lastSecondTime = thisSecond;
	            ticks = 0;
            }
         
            //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
            while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES){
		        Thread.yield();
		    
		        //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
		        //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
		        //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
		        try {Thread.sleep(1);} catch(Exception e) {} 
		    
		        now = System.nanoTime();
            }

	      }
		
		stop();
	}
	
	public KeyManager getKeyManager(){
		return keyManager;
	}
	
	public MouseManager getMouseManager(){
		return mouseManager;
	}
	
	public GameCamera getGameCamera(){
		return gameCamera;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public synchronized void start(){
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
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
}
