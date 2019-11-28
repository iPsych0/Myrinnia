package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.skills.SkillsList;

import java.awt.*;

public class PortAzureDuncan extends Creature {

    private Quest quest = Handler.get().getQuest(QuestList.MiningAndCrafting);

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
                if (Handler.get().questCompleted(QuestList.WoodcuttingAndFishing)) {
                    return true;
                }
                break;
            case "hasMaterials":
                if (Handler.get().playerHasItem(Item.azuriteOre, 5) && Handler.get().playerHasItem(Item.lightWood, 2)) {
                    return true;
                }
                break;
            case "hasPickaxe":
                if (Handler.get().playerHasItemType(ItemType.PICKAXE)) {
                    return true;
                }
                break;
            case "hasCraftedItem":
                if (Handler.get().getQuest(QuestList.MiningAndCrafting).getQuestSteps().get(1).isFinished()) {
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
                    quest.addStep("Gather 5 Azurite Ore and 2 Lightwood.");
                }
                break;
            case 17:
                if (!Handler.get().playerHasItemType(ItemType.PICKAXE)) {
                    Handler.get().giveItem(Item.simplePickaxe, 1);
                }
                break;
            case 20:
                if (speakingCheckpoint != 20) {
                    speakingCheckpoint = 20;
                }
                if (!quest.getQuestSteps().get(0).isFinished()) {
                    Handler.get().discoverRecipe(Item.beginnersSword);
                    Handler.get().discoverRecipe(Item.beginnersBow);
                    Handler.get().discoverRecipe(Item.beginnersStaff);
                    quest.nextStep();
                    quest.addStep("Create your weapon of choice!");
                }
                break;
            case 21:
                if (speakingCheckpoint != 21) {
                    speakingCheckpoint = 21;
                }
                if (quest.getState() == QuestState.IN_PROGRESS) {
                    quest.nextStep();
                    quest.setState(QuestState.COMPLETED);
                    Handler.get().getSkill(SkillsList.CRAFTING).addExperience(50);
                    Handler.get().getSkill(SkillsList.MINING).addExperience(50);
                }
                break;
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        if (Handler.get().hasQuestReqs(QuestList.MiningAndCrafting) && quest.getState() == QuestState.NOT_STARTED) {
            g.drawImage(Assets.exclamationIcon, (int) (x - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - 32 - Handler.get().getGameCamera().getyOffset()), null);
        }
    }
}
