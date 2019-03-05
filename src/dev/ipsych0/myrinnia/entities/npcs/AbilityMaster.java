package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.chatwindow.ChatDialogue;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.shops.AbilityShopWindow;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    public void render(Graphics g) {
        g.drawImage(Assets.lorraine, (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    public void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new AbilityMaster(xSpawn, ySpawn));
    }

    @Override
    protected boolean choiceConditionMet(Choice choice) {
        switch (choice.getChoiceCondition().getCondition()) {
            case "has1000gold":
                if(Handler.get().playerHasItem(Item.coins, 1000)) {
                    resetSkillPoints();
                    return true;
                }
                return false;
            case "openShop":
                if (!AbilityShopWindow.isOpen) {
                    Handler.get().getQuest(QuestList.AMysteriousFinding).getRequirements()[0].setTaskDone(true);
                    AbilityShopWindow.open();
                }
                return true;
            default:
                System.err.println("CHOICE CONDITION '" + choice.getChoiceCondition().getCondition() + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    protected void updateDialogue() {

    }
}
