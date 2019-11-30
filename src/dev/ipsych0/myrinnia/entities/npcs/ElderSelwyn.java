package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;

import java.awt.*;

public class ElderSelwyn extends Creature {


    private static final long serialVersionUID = 101550362959052644L;
    private Quest quest = Handler.get().getQuest(QuestList.WaterMagic);

    public ElderSelwyn(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        walker = false;
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
        g.drawImage(Assets.elderSelwyn,
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "hasCompletedMiningAndCrafting":
                if (Handler.get().questCompleted(QuestList.MiningAndCrafting)) {
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
            case 5:
                if (quest.getState() == QuestState.NOT_STARTED) {
                    quest.setState(QuestState.IN_PROGRESS);
                    quest.addStep("Get your first Ability from Selwyn.");
                    speakingCheckpoint = 5;
                }
                break;
            case 6:
                if (speakingCheckpoint != 6) {
                    speakingCheckpoint = 7;
                    quest.nextStep();
                }
        }
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {

    }

}