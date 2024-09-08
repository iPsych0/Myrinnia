package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.publishers.CraftingPublisher;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.subscribers.CraftingSubscriber;

import java.awt.*;

public class PortAzureDuncan extends Creature {

    private Quest quest = Handler.get().getQuest(QuestList.PreparingYourJourney);
    private static boolean hasDiscoveredRecipes;

    public PortAzureDuncan(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;
        walker = false;

        aDown = new Animation(250, Assets.portAzureDuncanDown);
        aLeft = new Animation(250, Assets.portAzureDuncanLeft);
        aRight = new Animation(250, Assets.portAzureDuncanRight);
        aUp = new Animation(250, Assets.portAzureDuncanUp);
        aDefault = aDown;

        new CraftingSubscriber(CraftingPublisher.get(), true, (craftedItem) -> {
            updateTutorial((ItemStack) craftedItem);
        });
    }

    private void updateTutorial(ItemStack result) {
        // For tutorial quest
        if (Handler.get().questInProgress(QuestList.PreparingYourJourney)) {
            Quest quest = Handler.get().getQuest(QuestList.PreparingYourJourney);
            if (result.getItem() == Item.simpleSword ||
                    result.getItem() == Item.simpleBow ||
                    result.getItem() == Item.simpleStaff) {
                if (!quest.getQuestSteps().get(2).isFinished()) {
                    quest.nextStep();
                }
            }
        }
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
            case "hasCompletedQuests":
                if (Handler.get().questCompleted(QuestList.GatheringYourStuff)) {
                    return true;
                }
                break;
            case "hasMaterials":
                if (Handler.get().playerHasItem(Item.azuriteOre, 5) && Handler.get().playerHasItem(Item.palmWood, 2)) {
                    return true;
                }
                break;
            case "hasPickaxe":
                if (Handler.get().playerHasItemType(ItemType.PICKAXE)) {
                    return true;
                }
                break;
            case "hasCraftedItem":
                if (Handler.get().getQuest(QuestList.PreparingYourJourney).getQuestSteps().get(2).isFinished()) {
                    return true;
                }
                break;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                break;
        }
        return false;
    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new PortAzureDuncan(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, lastFaced));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 14:
                if (speakingCheckpoint != 14) {
                    speakingCheckpoint = 14;
                }
                if (quest.getState() == QuestState.NOT_STARTED) {
                    quest.setState(QuestState.IN_PROGRESS);
                }
                break;
            case 17:
                if (!Handler.get().playerHasItemType(ItemType.PICKAXE)) {
                    Handler.get().giveItem(Item.simplePickaxe, 1);
                    quest.nextStep();
                }
                break;
            case 20:
                if (speakingCheckpoint != 20) {
                    speakingCheckpoint = 20;
                }
                if (!quest.getQuestSteps().get(1).isFinished()) {
                    unlockRecipes();
                    quest.nextStep();
                }
                break;
            case 21:
                if (speakingCheckpoint != 21) {
                    speakingCheckpoint = 21;
                }
                if (quest.getState() == QuestState.IN_PROGRESS) {
                    quest.nextStep();
                    quest.setState(QuestState.COMPLETED);
                }
                break;
        }
    }

    public static void unlockRecipes() {
        if (!hasDiscoveredRecipes) {
            Handler.get().discoverRecipe(Item.simpleSword);
            Handler.get().discoverRecipe(Item.simpleBow);
            Handler.get().discoverRecipe(Item.simpleStaff);
            hasDiscoveredRecipes = true;
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        if (Handler.get().hasQuestReqs(QuestList.PreparingYourJourney) && quest.getState() == QuestState.NOT_STARTED) {
            g.drawImage(Assets.exclamationIcon, (int) (x - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - 32 - Handler.get().getGameCamera().getyOffset()), null);
        }
    }
}
