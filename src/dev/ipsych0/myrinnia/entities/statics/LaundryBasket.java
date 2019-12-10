package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.items.Item;

import java.awt.*;

public class LaundryBasket extends StaticEntity {

    public LaundryBasket(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
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

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new LaundryBasket(x, y, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 1:
                if (!Handler.get().playerHasItem(Item.keys, 1)) {
                    Handler.get().giveItem(Item.keys, 1);
                } else {
                    script.getDialogues().get(0).setText("Er ligt alleen maar was in de mand.");
                }
                speakingTurn = -1;
                break;
        }
    }
}
