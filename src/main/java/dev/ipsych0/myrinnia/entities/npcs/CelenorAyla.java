package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;

import java.awt.*;

public class CelenorAyla extends Entity {

    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);

    public CelenorAyla() {
        
        solid = true;
        attackable = false;
        isNpc = true;
        this.stats.setMovementSpeed(1.0);
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
                    speakingTurn = 3;
                }
                break;
            case 9:
                quest.addNewCheck("clue2", true);

                // If we have also received the other clues, then advance to next step
                if (quest.getQuestSteps().get(2).isFinished() && !quest.getQuestSteps().get(3).isFinished() &&
                        (Boolean) quest.getCheckValueWithDefault("clue1", false) &&
                        (Boolean) quest.getCheckValueWithDefault("clue3", false)) {
                    quest.nextStep();
                    Handler.get().sendMsg("You should return to Elenthir with these clues.");
                }
                break;
        }
    }
}
