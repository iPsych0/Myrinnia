package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.QuestList;

import java.awt.*;

public class CaptainIsaac extends Creature {

    public CaptainIsaac(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
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
        g.drawImage(Assets.portAzureSailor,
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "hasCompletedQuests":
                if (Handler.get().questCompleted(QuestList.WaterMagic)) {
                    return true;
                }
                break;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
        return false;
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 4:
                Handler.get().sendMsg("You finished the tutorial! More will come soon!");
                speakingTurn = -1;
                break;
        }
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {

    }
}
