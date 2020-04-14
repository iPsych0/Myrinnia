package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.shops.ShopWindow;

import java.awt.*;

public class CelenorCraftsman extends ShopKeeper {


    /**
     *
     */
    private static final long serialVersionUID = -3340636213278064668L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);

    public CelenorCraftsman(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    public void die() {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "openShop":
                if (!ShopWindow.isOpen) {
                    ShopWindow.open();
                }
                break;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
        return false;
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new CelenorCraftsman(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, lastFaced));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 4:
                quest.addNewCheck("clue1", true);

                // If we have also received clue 2, then advance to next step
                if (quest.getQuestSteps().get(2).isFinished() && !quest.getQuestSteps().get(3).isFinished() && (Boolean) quest.getCheckValue("clue2")) {
                    quest.nextStep();
                    Handler.get().sendMsg("You should return to Elenthir with these clues.");
                }

                // Remove the quest dialogue when clue obtained
                script.getDialogues().get(1).getOptions().remove(1);
                break;
        }
    }

}
