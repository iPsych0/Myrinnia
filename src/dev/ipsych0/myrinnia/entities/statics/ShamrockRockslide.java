package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;

import java.awt.*;

public class ShamrockRockslide extends StaticEntity {

    private Quest quest = Handler.get().getQuest(QuestList.WeDelvedTooDeep);

    public ShamrockRockslide(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = true;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.rockSlide,
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "questStarted":
                if (quest.getState() == QuestState.IN_PROGRESS && !quest.getQuestSteps().get(0).isFinished()) {
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
    protected void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 7:
                speakingCheckpoint = 7;
                if (!quest.getQuestSteps().get(0).isFinished()) {
                    quest.nextStep();
                }
                break;
        }
    }
}
