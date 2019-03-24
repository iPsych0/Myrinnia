package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.crafting.ui.CraftingUI;
import dev.ipsych0.myrinnia.chatwindow.ChatDialogue;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;

public class CraftingStation extends StaticEntity {

    /**
     *
     */
    private static final long serialVersionUID = -8804679431303966524L;

    public CraftingStation(float x, float y) {
        super(x, y, 64, 64);

        script = Utils.loadScript("craftingstation.json");

        bounds.y = 16;
        bounds.height -= 12;

        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.mainhandSlot, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);

    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    public void die() {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "openCraftingWindow":
                if (!CraftingUI.isOpen) {
                    Handler.get().getCraftingUI().openWindow();
                }
                return true;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {

    }

    @Override
    public String getName() {
        return "Crafting Station";
    }
}
