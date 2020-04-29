package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;

import java.awt.*;

public class CelenorGrottoWater extends StaticEntity {

    public static boolean potionUsed;
    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);

    public CelenorGrottoWater(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
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
        Handler.get().getWorld().getEntityManager().addEntity(new CelenorGrottoWater(x, y, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        if ("hasPotion".equals(condition)) {
            return Handler.get().playerHasItem(Item.potionOfDecontamination, 1) && !potionUsed;
        }
        return false;
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 2:
                if (!potionUsed) {
                    potionUsed = true;
                    Handler.get().removeItem(Item.potionOfDecontamination, 1);
                }
                speakingTurn = -1;
                break;
        }
    }
}
