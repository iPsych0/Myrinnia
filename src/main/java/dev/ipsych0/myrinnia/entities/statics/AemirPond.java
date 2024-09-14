package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.quests.QuestList;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class AemirPond extends Entity {

    public AemirPond() {
        
        solid = false;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "questCompleted":
                return Handler.get().getQuest(QuestList.ExtrememistBeliefs).getQuestSteps().get(9).isFinished();
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    public void respawn() {
        AemirPond respawn = new AemirPond();
        respawn.setX(x);
        respawn.setY(y);
        Handler.get().getWorld().getEntityManager().addEntity(respawn);
    }

    @Override
    protected void updateDialogue() {

    }
}
