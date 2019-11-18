package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;

public class BountyBoardAzure extends StaticEntity {

    private static final long serialVersionUID = 4925882540927003315L;

    public BountyBoardAzure(float x, float y, int width, int height) {
        super(x, y, width, height);

        bounds.x = 0;
        bounds.y = height / 2;
        bounds.height = height / 2;
        bounds.width = width;

        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.bountyBoard2, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void interact() {
        Handler.get().sendMsg("Coming soon!");
        // TODO: OPEN AZURE BOUNTY BOARD!
    }

    @Override
    public void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {

    }

    @Override
    public String getName() {
        return "Bounty Board";
    }
}
