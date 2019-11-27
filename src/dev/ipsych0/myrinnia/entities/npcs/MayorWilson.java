package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;

import java.awt.*;

public class MayorWilson extends Creature {


    private static final long serialVersionUID = 101550362959052644L;

    public MayorWilson(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        walker = false;
        solid = true;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void die() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.portAzureMayor,
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "hasObtainedWeapon":
                if (Handler.get().questInProgress(QuestList.BountyHunter) && Handler.get().getQuest(QuestList.BountyHunter).getQuestSteps().get(0).isFinished()) {
                    return true;
                }
                if (Handler.get().questCompleted(QuestList.BountyHunter)) {
                    script.getDialogues().get(2).setText("Once you have learned all skills, you should ask Captain Isaac to take you with him! You can find him on the docks south of here.");
                }
                break;
            case "allQuestsDone":
                if (!Handler.get().questCompleted(QuestList.BountyHunter)) {
                    speakingTurn = -1;
                    break;
                }
                return true;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
        return false;
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 0:
                if (Handler.get().questInProgress(QuestList.BountyHunter) && Handler.get().getQuest(QuestList.BountyHunter).getQuestSteps().get(0).isFinished()) {
                    script.getDialogues().get(0).setText("Welcome back!");
                }
                break;
            case 2:
                if (!Handler.get().questCompleted(QuestList.BountyHunter) && !Handler.get().questStarted(QuestList.BountyHunter)) {
                    Handler.get().addTip(new TutorialTip("Press Q to open your Quest Journal."));
                    Handler.get().getQuest(QuestList.BountyHunter).setState(QuestState.IN_PROGRESS);
                    Handler.get().addQuestStep(QuestList.BountyHunter, "Choose your first weapon from the store.");
                }
                break;
            case 6:
                if (Handler.get().questInProgress(QuestList.BountyHunter) && !Handler.get().getQuest(QuestList.BountyHunter).getQuestSteps().get(1).isFinished()) {
                    Handler.get().getQuest(QuestList.BountyHunter).nextStep();
                    Handler.get().addQuestStep(QuestList.BountyHunter, "Choose a bounty target from the board.");
                }
                break;
            case 7:
                // If bounty hunter is done, but woodcutting/fishing isn't, change dialogue
                if (Handler.get().questCompleted(QuestList.BountyHunter) && !Handler.get().questCompleted(QuestList.WoodcuttingAndFishing)) {
                    script.getDialogues().get(8).setText("You could see Douglas. He has a workshop in Sunshine Coast, the northern most point of the island.");
                }
                // If bounty hunter is done, but woodcutting/fishing isn't, change dialogue
                else if (Handler.get().questCompleted(QuestList.WoodcuttingAndFishing) && !Handler.get().questCompleted(QuestList.MiningAndCrafting)) {
                    script.getDialogues().get(8).setText("You should pay Port Azure's workshop a visit! I heard that they could use some hands.");
                }
                // If bounty hunter is done, but woodcutting/fishing isn't, change dialogue
                else if (Handler.get().questCompleted(QuestList.MiningAndCrafting)) {
                    script.getDialogues().get(8).setText("Captain Isaac is about to depart to the mainland. You might want to have a chat with him!");
                }
                break;
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        if (Handler.get().hasQuestReqs(QuestList.BountyHunter) && Handler.get().getQuest(QuestList.BountyHunter).getState() == QuestState.NOT_STARTED) {
            g.drawImage(Assets.exclamationIcon, (int) (x - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - 32 - Handler.get().getGameCamera().getyOffset()), null);
        }
    }

    @Override
    public void respawn() {

    }

}