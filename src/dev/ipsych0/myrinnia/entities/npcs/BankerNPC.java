package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.bank.BankUI;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.io.Serializable;

public class BankerNPC extends Banker implements Serializable {

    private static final long serialVersionUID = -4843560960961688987L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();

    public BankerNPC(float x, float y) {
        super(x, y);

        script = Utils.loadScript("banker.json");
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.femaleBanker, (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - 16 - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    public void die() {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "openBank":
                if (!BankUI.isOpen) {
                    BankUI.open();
                }
                return true;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new BankerNPC(xSpawn, ySpawn));
    }

    @Override
    protected void updateDialogue() {

    }

    public String getName() {
        return "Banker";
    }

}
