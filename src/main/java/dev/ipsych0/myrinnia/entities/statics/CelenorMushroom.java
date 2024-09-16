package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;

import java.awt.*;
import java.util.Map;

public class CelenorMushroom extends StaticEntity {

    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);

    public CelenorMushroom(double x, double y, int width, int height, Map<String,String> props) {
        super(x, y, width, height, props);
        solid = true;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.amanitaMushroom, (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()),
                width, height, null);
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new CelenorMushroom(x, y, width, height, props));
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        if ("isAtQuestStep".equals(condition)) {
            return !Handler.get().playerHasItem(Item.amanitaMushroom, 1) &&
                    quest.getQuestSteps().get(7).isFinished() && !quest.getQuestSteps().get(8).isFinished();
        }
        return false;
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 2:
                Handler.get().giveItem(Item.amanitaMushroom, 1);
                speakingTurn = -1;
                break;
        }
    }
}
