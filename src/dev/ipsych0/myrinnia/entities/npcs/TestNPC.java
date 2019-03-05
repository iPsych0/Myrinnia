package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.chatwindow.ChatDialogue;
import dev.ipsych0.myrinnia.entities.statics.StaticEntity;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestStep;
import dev.ipsych0.myrinnia.tiles.Tiles;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.util.List;

public class TestNPC extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = -8566165980826138340L;
    private Script script;

    public TestNPC(float x, float y) {
        super(x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);

        script = Utils.loadScript("testnpc1.json");

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
    public void render(Graphics g) {
        g.drawImage(Assets.barrel1, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {
        // If the conversation was reset, reinitialize the first time we interact again
        if (speakingTurn == -1) {
            chatDialogue = null;
            speakingTurn = 0;
            return;
        }
        // If there is only text to be displayed, advance to the next conversation
        if (script.getDialogues().get(speakingTurn).getText() != null) {
            chatDialogue = new ChatDialogue(new String[]{script.getDialogues().get(speakingTurn).getText()});
            chatDialogue.setChosenOption(null);
            updateDialogue();
            setSpeakingTurn(script.getDialogues().get(speakingTurn).getNextId());
        } else {
            // If there is a choice menu and we selected a choice, handle the choice logic
            if (chatDialogue != null && chatDialogue.getChosenOption() != null) {
                Choice choice = script.getDialogues().get(speakingTurn).getOptions().get(chatDialogue.getChosenOption().getOptionID());
                // If there is a condition to proceed with the conversation, check it
                if (choice.getChoiceCondition() != null) {
                    if (choiceConditionMet(choice)) {
                        // If we meet the condition, proceed
                        setSpeakingTurn(choice.getNextId());
                    } else {
                        // If we don't meet the condition, return to whatever menu falseId points to
                        setSpeakingTurn(choice.getChoiceCondition().getFalseId());
                    }
                    chatDialogue.setChosenOption(null);
                    interact();
                    return;
                } else {
                    // If there is no condition, we can always proceed
                    if (chatDialogue.getMenuOptions().length > 1) {
                        setSpeakingTurn(choice.getNextId());
                    }
                    chatDialogue.setChosenOption(null);
                    interact();
                    return;
                }
            }

            // Update the list of dialogue choices
            List<Choice> choiceList = script.getDialogues().get(speakingTurn).getOptions();
            String[] choices = new String[choiceList.size()];
            for (int i = 0; i < choiceList.size(); i++) {
                choices[i] = choiceList.get(i).getText();
            }
            chatDialogue = new ChatDialogue(choices);
            updateDialogue();
        }
    }

    @Override
    protected boolean choiceConditionMet(Choice choice) {
        switch (choice.getChoiceCondition().getCondition()) {
            case "has100gold":
                if (!Handler.get().playerHasItem(Item.coins, 100)) {
                    return false;
                }
                break;
            default:
                System.err.println("CHOICE CONDITION '" + choice.getChoiceCondition().getCondition() + "' NOT PROGRAMMED!");
                return false;
        }
        return true;
    }

    @Override
    protected void updateDialogue() {
        Handler.get().sendMsg(speakingTurn);
        switch (speakingTurn) {
            case 0:
                if (Handler.get().getQuest(QuestList.TheThirdQuest).getState() == Quest.QuestState.NOT_STARTED) {
                    Handler.get().getQuest(QuestList.TheThirdQuest).setState(Quest.QuestState.IN_PROGRESS);
                    Handler.get().getQuest(QuestList.TheThirdQuest).getQuestSteps().add(new QuestStep("Listen to the TestNPC's story."));
                }
                break;
            case 1:
                Handler.get().giveItem(Item.testPickaxe, 1);
                break;
            case 2:
                Handler.get().removeItem(Item.testPickaxe, 1);
                break;
            case 3:
                if (Handler.get().getQuest(QuestList.TheThirdQuest).getState() == Quest.QuestState.IN_PROGRESS) {
                    Handler.get().getQuest(QuestList.TheThirdQuest).nextStep();
                    Handler.get().getQuest(QuestList.TheThirdQuest).setState(Quest.QuestState.COMPLETED);
                }
                Handler.get().giveItem(Item.coins, 100);
                break;
            case 4:
                Handler.get().removeItem(Item.coins, 100);
                break;
            case 5:
                Handler.get().giveItem(Item.testSword, 1);
                break;
            case 6:
                Handler.get().removeItem(Item.testSword, 1);
                break;
        }
    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    public void respawn() {

    }

}