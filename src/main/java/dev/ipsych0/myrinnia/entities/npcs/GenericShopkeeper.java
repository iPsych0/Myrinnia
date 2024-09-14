package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class GenericShopkeeper extends ShopKeeper {


    private static final long serialVersionUID = -3340636213278064668L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();

    public GenericShopkeeper() {
        
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    public void die() {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "openShop":
                if (!ShopWindow.isOpen) {
                    ShopWindow.open();
                }
                break;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
        return false;
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {
    }

    @Override
    protected void updateDialogue() {

    }

}
