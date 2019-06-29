package dev.ipsych0.myrinnia.display;

import dev.ipsych0.myrinnia.Game;

public class RenderThread extends Thread {

    private Game game;

    public RenderThread(Game game, String threadName) {
        super(threadName);
        this.game = game;
    }

    @Override
    public void run() {
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
        double lastRenderTime;

        // If we are able to get as high as this FPS, don't render again.
        final double TARGET_FPS = 60.0;
        final double TARGET_TIME_BETWEEN_RENDERS = 1000000000d / TARGET_FPS;

        // Simple way of finding FPS.
        int lastSecondTime = (int) (lastUpdateTime / 1000000000d);

        while (game.isRunning()) {
            double now = System.nanoTime();
            int updateCount = 0;

            // Do as many game updates as we need to, potentially playing catchup.
            while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                lastUpdateTime += TIME_BETWEEN_UPDATES;
                updateCount++;
            }

            game.render();

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
                lastSecondTime = thisSecond;
            }

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
    }
}
