package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;

import java.awt.*;

public class PollutedWaterSource extends GenericObject {

    private final Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);
    private boolean questProgressed;

    public PollutedWaterSource(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
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
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new GenericObject(x, y, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        if ("hasBucket".equals(condition)) {
            return Handler.get().playerHasItem(Item.bucket, 1);
        } else if ("hasNoPollutedBucket".equals(condition)) {
            return !Handler.get().playerHasItem(Item.pollutedBucket, 1) && quest.getState() == QuestState.IN_PROGRESS;
        }
        return false;
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 3:
                if (!questProgressed) {
                    questProgressed = true;
                    quest.nextStep();
                    Handler.get().sendMsg("I should return to Porewit and tell him my findings.");
                }
                Handler.get().removeItem(Item.bucket, 1);
                Handler.get().giveItem(Item.pollutedBucket, 1);
                break;
        }
    }
}
