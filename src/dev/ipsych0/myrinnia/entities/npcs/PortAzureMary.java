package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;

import java.awt.*;

public class PortAzureMary extends Creature {

    private Quest quest = Handler.get().getQuest(QuestList.WoodcuttingAndFishing);

    public PortAzureMary(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
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
                if (Handler.get().questInProgress(QuestList.WoodcuttingAndFishing) && Handler.get().playerHasItem(Item.mackerelFish, 5)) {
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
        Handler.get().getWorld().getEntityManager().addEntity(new PortAzureMary(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, lastFaced));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 6:
                if (Handler.get().questInProgress(QuestList.WoodcuttingAndFishing) && Handler.get().playerHasItem(Item.mackerelFish, 5)) {
                    Handler.get().removeItem(Item.mackerelFish, 5);
                    quest.nextStep();
                }
                break;
            case 7:
                Handler.get().giveItem(Item.coins, 50);
                quest.setState(QuestState.COMPLETED);
                break;
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
