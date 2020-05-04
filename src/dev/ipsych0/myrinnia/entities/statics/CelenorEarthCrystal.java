package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;

public class CelenorEarthCrystal extends StaticEntity {

    public static boolean puzzleCompleted;
    private boolean abilitiesReceived;

    public CelenorEarthCrystal(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = true;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        if (!puzzleCompleted || abilitiesReceived) {
            g.drawImage(Assets.celenorUnchargedCrystal, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()),
                    width, height, null);
        }
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new CelenorEarthCrystal(x, y, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "hasCompletedPuzzle":
                return puzzleCompleted;
            case "hasReceivedAbilities":
                return abilitiesReceived;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 3:
                Handler.get().sendMsg("Abilities learnt!");
                abilitiesReceived = true;
                break;
        }
    }
}
