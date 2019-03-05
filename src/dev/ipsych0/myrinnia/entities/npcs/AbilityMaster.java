package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.chatwindow.ChatDialogue;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.shops.AbilityShopWindow;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AbilityMaster extends AbilityTrainer implements Serializable {


    private static final long serialVersionUID = 4508934222180048865L;

    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private ArrayList<Ability> abilities;
    private Script script;

    public AbilityMaster(float x, float y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        abilities = new ArrayList<>();

        abilities.addAll(Handler.get().getAbilityManager().getAllAbilities());

        script = Utils.loadScript("ability_master.json");

        abilityShopWindow = new AbilityShopWindow(abilities);

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.lorraine, (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    public void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new AbilityMaster(xSpawn, ySpawn));
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
            case "has1000gold":
                if(Handler.get().playerHasItem(Item.coins, 1000)) {
                    resetSkillPoints();
                    return true;
                }
                return false;
            case "openShop":
                if (!AbilityShopWindow.isOpen) {
                    Handler.get().getQuest(QuestList.AMysteriousFinding).getRequirements()[0].setTaskDone(true);
                    AbilityShopWindow.open();
                }
                return true;
            default:
                System.err.println("CHOICE CONDITION '" + choice.getChoiceCondition().getCondition() + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    protected void updateDialogue() {

    }
}
