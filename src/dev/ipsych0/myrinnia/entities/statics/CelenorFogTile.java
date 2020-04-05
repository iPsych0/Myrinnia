package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.worlds.Fog;

import java.awt.*;

public class CelenorFogTile extends StaticEntity {

    private Player player;
    private Fog fog = new Fog(Fog.Intensity.HEAVY);

    public CelenorFogTile(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = false;
        attackable = false;
        isNpc = false;
        player = Handler.get().getPlayer();
        setOverlayDrawn(false);
    }

    @Override
    public void tick() {
        if (player.getY() < this.getY()) {
            player.setY(this.y + Tile.TILEHEIGHT);
            interact();
            Handler.get().sendMsg("The fog is too thick to see anything. It's not safe to enter the forest like this.");
        }
        if (player.getY() < (21 * 32)) {
            Handler.get().getWorld().fadeInWeatherEffect(fog);
        } else if (player.getY() > (21 * 32)) {
            Handler.get().getWorld().fadeOutWeatherEffect(fog);
        }
    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {

    }
}
