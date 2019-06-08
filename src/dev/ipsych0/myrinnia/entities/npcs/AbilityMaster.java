package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.QuestList;
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

    public AbilityMaster(float x, float y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        abilities = new ArrayList<>();
        abilities.addAll(Handler.get().getAbilityManager().getAllAbilities());
        script = Utils.loadScript("ability_master.json");
        abilityShopWindow = new AbilityShopWindow(abilities);

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.shopKeeperMerchantWater, (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - 16 - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new AbilityMaster(xSpawn, ySpawn));
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "has1000gold":
                if(Handler.get().playerHasItem(Item.coins, 1000)) {
                    resetSkillPoints();
                    return true;
                }
                break;
            case "openShop":
                if (!AbilityShopWindow.isOpen) {
                    Handler.get().getQuest(QuestList.AMysteriousFinding).getRequirements()[0].setTaskDone(true);
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
