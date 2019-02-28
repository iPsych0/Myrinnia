package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.chatwindow.ChatDialogue;
import dev.ipsych0.myrinnia.entities.statics.StaticEntity;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.tiles.Tiles;
import dev.ipsych0.myrinnia.utils.SaveManager;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;

public class TestNPC extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = -8566165980826138340L;
    private Script script;

    public TestNPC(float x, float y) {
        super(x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);

        script = Utils.loadScript("testnpc1.json");

        solid = true;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void die() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.teleportShrine2, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
        g.drawImage(Assets.teleportShrine1, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - 32 - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {
        if (this.getSpeakingTurn() == 0) {
            speakingTurn++;
            return;
        } else if (this.getSpeakingTurn() == 1) {
            chatDialogue = new ChatDialogue(new String[]{script.getDialogues().get(0).getText()});
            speakingTurn++;
        } else if (this.getSpeakingTurn() == 2) {
            if (chatDialogue == null) {
                speakingTurn = 1;
                return;
            }
            chatDialogue = new ChatDialogue(new String[]{script.getDialogues().get(1).getText()});
            speakingTurn++;
        } else if (this.getSpeakingTurn() == 3) {
            if (chatDialogue == null) {
                speakingTurn = 1;
                return;
            }
            if (chatDialogue.getChosenOption().getOptionID() == 0) {
                speakingTurn = 1;
                chatDialogue = null;

                // Save the game
                SaveManager.savehandler();
                Handler.get().sendMsg("Game Saved!");
            } else {
                speakingTurn = 1;
                chatDialogue = null;
            }
        }
    }

    public int getSpeakingTurn() {
        return speakingTurn;
    }

    public void setSpeakingTurn(int speakingTurn) {
        this.speakingTurn = speakingTurn;
    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    public void respawn() {

    }

}