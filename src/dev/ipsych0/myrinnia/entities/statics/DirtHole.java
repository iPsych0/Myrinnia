package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.chatwindow.ChatDialogue;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.tiles.Tiles;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;

public class DirtHole extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = -342972644048517256L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private String[] firstDialogue = {"Upon closer inspection you find that you can climb down this hole."};
    private String[] secondDialogue = {"Climb down.", "No thanks, I'm fine."};

    public DirtHole(float x, float y) {
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
    public void render(Graphics g) {
        g.drawImage(Assets.eruptionI, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {
        switch (speakingTurn) {

            case 0:
                speakingTurn++;
                return;

            case 1:
                chatDialogue = new ChatDialogue(firstDialogue);
                speakingTurn++;
                break;

            case 2:
                if (chatDialogue == null) {
                    speakingTurn = 1;
                    break;
                }
                chatDialogue = new ChatDialogue(secondDialogue);
                speakingTurn++;
                break;
            case 3:
                if (chatDialogue == null) {
                    speakingTurn = 1;
                    break;
                }

                if (chatDialogue.getChosenOption().getOptionID() == 0) {
                    chatDialogue = null;
                    Handler.get().goToWorld(Zone.IslandUnderground, 3008, 3392);
                    break;
                } else if (chatDialogue.getChosenOption().getOptionID() == 1) {
                    chatDialogue = null;
                    speakingTurn = 1;
                    break;
                }
        }
    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new DirtHole(xSpawn, ySpawn));
    }

    @Override
    protected void updateDialogue() {

    }

}
