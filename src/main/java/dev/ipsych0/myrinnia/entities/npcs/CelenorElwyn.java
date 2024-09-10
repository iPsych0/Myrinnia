package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;

import java.awt.*;

public class CelenorElwyn extends Creature {

    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);

    public CelenorElwyn(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;
        speed = 1.0;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
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
            case 0:
                if (quest.getQuestSteps().get(2).isFinished() && !quest.getQuestSteps().get(3).isFinished()) {
                    speakingTurn = 2;
                }
                if (quest.getState() == QuestState.COMPLETED) {
                    speakingTurn = 7;
                }
                break;
            case 6:
                quest.addNewCheck("clue3", true);

                // If we have also received the other clues, then advance to next step
                if (quest.getQuestSteps().get(2).isFinished() && !quest.getQuestSteps().get(3).isFinished() &&
                        (Boolean) quest.getCheckValueWithDefault("clue1", false) &&
                        (Boolean) quest.getCheckValueWithDefault("clue2", false)) {
                    quest.nextStep();
                    Handler.get().sendMsg("You should return to Elenthir with these clues.");
                }
                break;
        }
    }
}
