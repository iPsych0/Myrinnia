package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.shops.AbilityShopWindow;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class AbilityMaster extends AbilityTrainer implements Serializable {


    private static final long serialVersionUID = 4508934222180048865L;

    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private ArrayList<Ability> abilities;

    public AbilityMaster(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        abilities = new ArrayList<>();
        abilities.addAll(Handler.get().getAbilityManager().getAllAbilities());
        abilityShopWindow = new AbilityShopWindow(abilities);

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.shopKeeper, (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new AbilityMaster(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "has1000gold":
                if (Handler.get().playerHasItem(Item.coins, resetCost)) {
                    resetSkillPoints();
                    // Change the cost of resetting in the text
                    Dialogue infoMsg = script.getDialogues().get(1);
                    infoMsg.setText(infoMsg.getText().replaceAll("\\d+", String.valueOf(resetCost)));
                    Choice confirm = script.getDialogues().get(2).getOptions().get(0);
                    confirm.setText(confirm.getText().replaceAll("\\d+", String.valueOf(resetCost)));
                    return true;
                }
                break;
            case "openShop":
                if (!AbilityShopWindow.isOpen) {
//                    Handler.get().getQuest(QuestList.AMysteriousFinding).getRequirements()[0].setTaskDone(true);
                    AbilityShopWindow.open();
                }
                return true;
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
    public String getName() {
        return "Ability Master";
    }
}
