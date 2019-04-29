package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.tiles.Tiles;

import java.awt.*;

public class DirtHole extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = -342972644048517256L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();

    private DirtHole(float x, float y) {
        super(x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);

        isNpc = true;
        attackable = false;
    }

    @Override
    public void tick() {

    }

    @Override
    public void die() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.eruptionI, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new DirtHole(xSpawn, ySpawn));
    }

    @Override
    protected void updateDialogue() {

    }

    public String getName() {
        return "Dirt hole";
    }


}
