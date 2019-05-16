package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.utils.SaveManager;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;

public class SavingShrine extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = -8566165980826138340L;

    public SavingShrine(float x, float y) {
        super(x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);

        script = Utils.loadScript("savingshrine.json");

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
    public void render(Graphics2D g) {
        g.drawImage(Assets.equipScreen, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
        g.drawImage(Assets.equipScreen, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - 32 - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "saveGame":
                SaveManager.savehandler();
                return true;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    public int getSpeakingTurn() {
        return speakingTurn;
    }

    public void setSpeakingTurn(int speakingTurn) {
        this.speakingTurn = speakingTurn;
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {

    }

    public String getName() {
        return "Saving Shrine";
    }


}