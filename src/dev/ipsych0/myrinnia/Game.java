package dev.ipsych0.myrinnia;

import dev.ipsych0.myrinnia.audio.AudioManager;
import dev.ipsych0.myrinnia.display.Display;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.gfx.GameCamera;
import dev.ipsych0.myrinnia.input.KeyManager;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.states.*;
import dev.ipsych0.myrinnia.utils.TimerHandler;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Game implements Runnable, Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1320270378451615572L;

    public static final String CURRENT_VERSION = "v0.0.92";
    public static final String TITLE_BAR = "Elements of Myrinnia Alpha " + CURRENT_VERSION;
    private Display display;
    private int width, height;
    private String title;
    private int ticks = 0;
    private int framesPerSecond = 0;

    private boolean running = false;
    private transient Thread mainThread;

    private transient BufferStrategy bs;
    private transient Graphics g;

    // States
    public State gameState;
    public State menuState;
    public State settingState;
    public State controlsState;
    public State pauseState;
    public State recapState;
    public State graphicsState;
    public State audioState;
    public State generalSettingsState;

    // Input
    private KeyManager keyManager;
    private MouseManager mouseManager;

    // Camera
    private GameCamera gameCamera;

    private static Game game;
    private static Handler handler;
    private static final int MIN_RES_WIDTH = 1366;
    private static final int MIN_RES_HEIGHT = 768;

    private Map<RenderingHints.Key, Object> renderHintMap;
    private Map<?, ?> desktopHints = (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");

    public static Game get() {
        if (game == null) {
            game = new Game(TITLE_BAR, MIN_RES_WIDTH, MIN_RES_HEIGHT);
        }
        return game;
    }

    private Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        renderHintMap = new HashMap<>();

        setQualityRendering();

        // Add fractional metrics so text will be rendered with more accurate position
        renderHintMap.put(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);

//        renderHintMap.put(RenderingHints.KEY_INTERPOLATION,
//                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    }

    private void init() {
        EventQueue.invokeLater(() -> {
            display = new Display(title, width, height);
            addListeners();
        });

        Assets.init();

        handler = Handler.get();
        loadSettings();

        // Create instance of Handler & gamecamera
        gameCamera = new GameCamera(0, 0);

        // Create the different states for menus/game
        menuState = new MenuState();
        gameState = new GameState();
        settingState = new SettingState();
        controlsState = new ControlsState();
        pauseState = new PauseState();
        recapState = new RecapState();
        graphicsState = new GraphicsState();
        audioState = new AudioState();
        generalSettingsState = new GeneralSettingsState();


        AudioManager.init();
        AudioManager.setListenerData();

        // Set the initial state to the menu state
        State.setState(menuState);

        display.setInitialized(true);
    }

    public void addListeners() {
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseWheelListener(mouseManager);
    }

    private void loadSettings(){
        keyManager.loadKeybinds();
    }

    public void setRenderingHint(RenderingHints.Key key, Object value) {
        renderHintMap.put(key, value);
    }

    public void setPerformanceRendering() {
        renderHintMap.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        renderHintMap.put(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        renderHintMap.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);
    }

    public void setQualityRendering() {
        renderHintMap.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        renderHintMap.put(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        renderHintMap.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
    }

    public void setDefaultRendering() {
        renderHintMap.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        renderHintMap.put(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        renderHintMap.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_DEFAULT);
    }

    private void tick() {
        mouseManager.tick();
        keyManager.tick();
        AudioManager.tick();
        TimerHandler.get().tick();
        if (State.getState() != null) {
            State.getState().tick();
        }
    }

    public void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D)g;

        // Add user's default text rendering settings for prettier fonts
        if (desktopHints != null) {
            g2d.setRenderingHints(desktopHints);
        }

        // Set the chosen rendering hints
        g2d.setRenderingHints(renderHintMap);

        // Scale the screen based on original size
        g2d.scale(Display.scaleX, Display.scaleY);

        // Clear screen
        g2d.clearRect(0, 0, width, height);

        if (State.getState() != null) {
            State.getState().render(g2d);
        }

        // End draw
        bs.show();
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        g2d.dispose();
    }

    @Override
    public void run() {

        init();

        // This value would probably be stored elsewhere.
        final double GAME_HERTZ = 60.0;
        // Calculate how many ms each frame should take for our target game hertz.
        final double TIME_BETWEEN_UPDATES = 1000000000d / GAME_HERTZ;
        // At the very most we will update the game this many times before a new render.
        // If you're worried about visual hitches more than perfect timing, set this to
        // 1.
        final int MAX_UPDATES_BEFORE_RENDER = 1;
        // We will need the last update time.
        double lastUpdateTime = System.nanoTime();
        // Store the last time we rendered.

        // If we are able to get as high as this FPS, don't render again.
        final double TARGET_FPS = 60.0;

        // Simple way of finding FPS.
        int lastSecondTime = (int) (lastUpdateTime / 1000000000d);

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

            // Update the frames we got.
            int thisSecond = (int) (lastUpdateTime / 1000000000d);

            if (thisSecond > lastSecondTime) {
                framesPerSecond = ticks;
                lastSecondTime = thisSecond;
                ticks = 0;
            }

            render();

            // Yield until it has been at least the target time between renders. This saves
            // the CPU from hogging.
            while (now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                Thread.yield();

                // This stops the app from consuming all your CPU. It makes this slightly less
                // accurate, but is worth it.
                // You can remove this line and it will still work (better), your CPU just
                // climbs on certain OSes.
                try {
                    Thread.sleep(1);
                } catch (Exception ignored) {
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
        mainThread = new Thread(this, "Main Thread");
        mainThread.start();
    }

    private synchronized void stop() {
        if (!running)
            return;
        running = false;
        try {
            mainThread.join();
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isRunning() {
        return running;
    }
}
