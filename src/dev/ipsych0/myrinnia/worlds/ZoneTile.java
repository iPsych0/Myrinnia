package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;

import java.awt.*;

public class ZoneTile extends Rectangle {

    private Zone zone;
    private String customZoneName;
    private String customZoneMusic;
    private int goToX, goToY;

    public ZoneTile(Zone zone, int fromX, int fromY, int width, int height, int goToX, int goToY, String customZoneName, String customZoneMusic) {
        super(fromX, fromY, width, height);

        this.goToX = goToX;
        this.goToY = goToY;
        this.zone = zone;
        this.customZoneName = customZoneName;
        this.customZoneMusic = customZoneMusic;
    }

    public void tick() {
        if(this.intersects(Handler.get().getPlayer().getCollisionBounds(0, 0))){
            Handler.get().goToWorld(zone, goToX, goToY, customZoneName, customZoneMusic);
        }
    }

    public void render(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.drawRect(x - (int) Handler.get().getGameCamera().getxOffset(), y - (int) Handler.get().getGameCamera().getyOffset(), width, height);
    }
}
