package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.crafting.ui.CraftingUI;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;

import java.awt.*;

public class CraftingStation extends StaticEntity {

    /**
     *
     */
    private static final long serialVersionUID = -8804679431303966524L;

    public CraftingStation(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

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
        g.drawImage(Assets.craftingIcon, (int) (x + width / 2 - 16 - Handler.get().getGameCamera().getxOffset()), (int) (y - 36 - Handler.get().getGameCamera().getyOffset()), 32, 32, null);
    }

    @Override
    public void die() {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "openCraftingWindow":
                if (!CraftingUI.isOpen) {
                    Handler.get().getCraftingUI().openWindow();
                }
                return true;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 0:
                if (Handler.get().getQuest(QuestList.PreparingYourJourney).getState() == QuestState.NOT_STARTED ||
                        Handler.get().questInProgress(QuestList.PreparingYourJourney) && !Handler.get().getQuest(QuestList.PreparingYourJourney).getQuestSteps().get(0).isFinished() ||
                        Handler.get().questInProgress(QuestList.PreparingYourJourney) && !Handler.get().getQuest(QuestList.PreparingYourJourney).getQuestSteps().get(1).isFinished()) {
                    Handler.get().sendMsg("You should talk to Duncan first to learn how this works.");
                    speakingTurn = -1;
                    break;
                }
        }
    }
}
