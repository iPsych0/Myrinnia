package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.statics.VegetablePatch;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;

public class MalachiteTony extends Creature {

    private static final long serialVersionUID = 101550362959052644L;
    private boolean hasGivenSeeds;

    public MalachiteTony(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
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
        g.drawImage(getAnimationByLastFaced(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

//    @Override
//    protected boolean choiceConditionMet(String condition) {
//        switch (condition) {
//            case "hasStartedBounty":
//                Bounty bounty = BountyManager.get().getBountyByZoneAndTask(Zone.ShamrockTown, "It's mine");
//                if (bounty.isAccepted() && !bounty.isCompleted()) {
//                    return true;
//                }
//                break;
//            default:
//                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
//                return false;
//        }
//        return false;
//    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 11:
                if (!hasGivenSeeds) {
                    hasGivenSeeds = true;
                    Handler.get().giveItem(Item.tomatoSeeds, 2);
                    Handler.get().addTip(new TutorialTip("You may plant the tomatoes in the Vegetable Patch outside Tony's house."));
                    speakingCheckpoint = 12;
                }
                break;
            case 12:
                if (VegetablePatch.tutorialDone) {
                    script = Utils.loadScript("malachite_tony2.json");
                    speakingCheckpoint = 0;
                    speakingTurn = -1;
                    interact();
                }
                break;
        }
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {

    }

}