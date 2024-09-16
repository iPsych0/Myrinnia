package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.quests.QuestList;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Map;

@Slf4j
public class AemirPond extends Entity {

    public AemirPond(double x, double y, int width, int height, Map<String,String> props) {
        super(x, y, width, height, props);
        solid = false;
        attackable = false;
        isNpc = true;
        staticNpc = true;
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
        Handler.get().getWorld().getEntityManager().addEntity(new AemirPond(x, y, width, height, props));
    }

    @Override
    protected void updateDialogue() {

    }
}
