package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;

import java.awt.*;

public class PortAzureMary extends Creature {

    private Quest quest = Handler.get().getQuest(QuestList.WoodcuttingAndFishing);
    private boolean tipDisplayed = false;

    public PortAzureMary(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = true;
        attackable = false;
        isNpc = true;

        aDown = new Animation(250, Assets.genericFemale1Down);
        aLeft = new Animation(250, Assets.genericFemale1Left);
        aRight = new Animation(250, Assets.genericFemale1Right);
        aUp = new Animation(250, Assets.genericFemale1Up);
        aDefault = aDown;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "has5fishandquest":
                if (Handler.get().questInProgress(QuestList.WoodcuttingAndFishing) && Handler.get().playerHasItem(Item.regularFish, 5)) {
                    return true;
                }
                break;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
        return false;
    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new PortAzureMary(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 5:
                if (!Handler.get().questStarted(QuestList.WoodcuttingAndFishing)) {
                    quest.setState(Quest.QuestState.IN_PROGRESS);
                    Handler.get().addQuestStep(QuestList.WoodcuttingAndFishing, "Cut 5 logs from weak palm trees in Sunrise Sands.");
                }
                break;
            case 6:
                if (Handler.get().questInProgress(QuestList.WoodcuttingAndFishing) && !quest.getQuestSteps().get(0).isFinished()) {
                    Handler.get().giveItem(Item.regularFish, 1);
                    quest.nextStep();
                    Handler.get().addQuestStep(QuestList.WoodcuttingAndFishing, "Deliver 5 fish to Mary in Port Azure.");
                }
                break;
            case 8:
                if(!tipDisplayed){
                    Handler.get().addTip(new TutorialTip("You can eat fish or other food in Myrinnia to restore some health. Food have cooldowns on how often you can use them, so it is best to save them for emergency cases."));
                    tipDisplayed = true;
                }
                break;
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
