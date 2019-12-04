package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;

import java.awt.*;

public class ShamrockSebastian extends Creature {


    private static final long serialVersionUID = 101550362959052644L;

    public ShamrockSebastian(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
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
        g.drawImage(getAnimationByLastFaced(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
//        switch (condition) {
//            case "hasStarted":
//                return true;
//                break;
//            default:
//                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
//                return false;
//        }
        return false;
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {

        }
    }

    @Override
    public void postRender(Graphics2D g) {
        if (Handler.get().hasQuestReqs(QuestList.WeDelvedTooDeep) && Handler.get().getQuest(QuestList.WeDelvedTooDeep).getState() == QuestState.NOT_STARTED) {
            g.drawImage(Assets.exclamationIcon, (int) (x - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - 32 - Handler.get().getGameCamera().getyOffset()), null);
        }
    }

    @Override
    public void respawn() {

    }

}