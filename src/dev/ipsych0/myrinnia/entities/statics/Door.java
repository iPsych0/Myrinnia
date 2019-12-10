package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.crafting.ui.CraftingUI;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;

public class Door extends StaticEntity {

    public Door(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "hasKeys":
                if(Handler.get().playerHasItem(Item.keys, 1)){
                    return true;
                }
                return false;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Door(x, y, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 1:
                Handler.get().goToWorld(Zone.Street, 25, 31);
                speakingTurn = -1;
                break;
        }
    }
}
