package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;

public class PortAzureDouglas extends Creature {

    private Quest quest = Handler.get().getQuest(QuestList.WoodcuttingAndFishing);
    private boolean tipDisplayed = false;
    private boolean scriptChanged;

    public PortAzureDouglas(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;

        aDown = new Animation(250, Assets.portAzureDouglasDown);
        aLeft = new Animation(250, Assets.portAzureDouglasLeft);
        aRight = new Animation(250, Assets.portAzureDouglasRight);
        aUp = new Animation(250, Assets.portAzureDouglasUp);
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
            case "hasCompletedBountyHunter":
                if (Handler.get().questCompleted(QuestList.BountyHunter)) {
                    return true;
                }
                break;
            case "has5logs":
                if (Handler.get().questInProgress(QuestList.WoodcuttingAndFishing) && Handler.get().playerHasItem(Item.lightWood, 5)) {
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
        Handler.get().getWorld().getEntityManager().addEntity(new PortAzureDouglas(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, lastFaced));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 5:
                speakingCheckpoint = 5;
                if (!Handler.get().questStarted(QuestList.WoodcuttingAndFishing)) {
                    quest.setState(QuestState.IN_PROGRESS);
                    Handler.get().addQuestStep(QuestList.WoodcuttingAndFishing, "Cut 5 logs from weak palm trees in Sunrise Sands.");
                }
                break;
            case 7:
                if (!scriptChanged && Handler.get().questCompleted(QuestList.WoodcuttingAndFishing)) {
                    scriptChanged = true;
                    script = Utils.loadScript("port_azure_douglas2.json");
                    speakingCheckpoint = 0;
                    speakingTurn = 0;
                    break;
                }
                speakingCheckpoint = 7;
                if (Handler.get().questInProgress(QuestList.WoodcuttingAndFishing) && Handler.get().playerHasItem(Item.lightWood, 5)) {
                    Handler.get().giveItem(Item.simpleFishingRod, 1);
                    Handler.get().removeItem(Item.lightWood, 5);
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
    public void postRender(Graphics2D g) {
        if (Handler.get().hasQuestReqs(QuestList.WoodcuttingAndFishing) && quest.getState() == QuestState.NOT_STARTED) {
            g.drawImage(Assets.exclamationIcon, (int) (x - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - 32 - Handler.get().getGameCamera().getyOffset()), null);
        }
    }
}
