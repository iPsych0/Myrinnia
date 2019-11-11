package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.statics.StaticEntity;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;

public class ElderSelwyn extends StaticEntity {


    private static final long serialVersionUID = 101550362959052644L;

    public ElderSelwyn(float x, float y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        script = Utils.loadScript("testnpc1.json");
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
        g.drawImage(Assets.portAzureMayor,
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "has100gold":
                if (Handler.get().playerHasItem(Item.coins, 100)) {
                    return true;
                }
                break;
            case "has1000gold":
                if (Handler.get().playerHasItem(Item.coins, 1000)) {
                    return true;
                }
                break;
            case "has1testsword":
                if (Handler.get().playerHasItem(Item.testSword, 1)) {
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
    protected void updateDialogue() {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {

    }

    public String getName() {
        return "Elder Selwyn";
    }


}