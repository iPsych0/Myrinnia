package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.entities.Entity;import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;

public class RopeLadderTile extends Entity {

    public RopeLadderTile() {
        
        solid = false;
        attackable = false;
        isNpc = false;
        setOverlayDrawn(false);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.ropeLadderMapTile,
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
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
