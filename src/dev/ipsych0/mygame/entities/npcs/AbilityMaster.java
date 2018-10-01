package dev.ipsych0.mygame.entities.npcs;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.abilities.Ability;
import dev.ipsych0.mygame.abilities.EruptionAbility;
import dev.ipsych0.mygame.character.CharacterStats;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.shop.AbilityShopWindow;
import dev.ipsych0.mygame.shop.ShopWindow;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class AbilityMaster extends AbilityTrainer implements Serializable {


    private static final long serialVersionUID = 4508934222180048865L;

    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private ArrayList<Ability> abilities;
    private String[] firstDialogue = {"I would like to learn new abilities.", "Leave."};

    public AbilityMaster(float x, float y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        abilities = new ArrayList<>();
        abilities.add(Handler.get().getAbilityManager().getPlayerHUD().getSlottedAbilities().get(0).getAbility());
        abilities.add(Handler.get().getAbilityManager().getPlayerHUD().getSlottedAbilities().get(1).getAbility());
        abilities.add(Handler.get().getAbilityManager().getPlayerHUD().getSlottedAbilities().get(2).getAbility());
        abilities.add(Handler.get().getAbilityManager().getPlayerHUD().getSlottedAbilities().get(3).getAbility());


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
    public void interact() {
        switch (speakingTurn) {

            case 0:
                speakingTurn++;
                return;

            case 1:
                if (!AbilityShopWindow.isOpen) {
                    chatDialogue = new ChatDialogue(firstDialogue);
                    speakingTurn++;
                    break;
                } else {
                    speakingTurn = 1;
                    break;
                }
            case 2:
                if (chatDialogue == null) {
                    speakingTurn = 1;
                    break;
                }
                if (chatDialogue.getChosenOption().getOptionID() == 0) {
                    AbilityShopWindow.isOpen = true;
                    chatDialogue = null;
                    speakingTurn = 1;
                    break;
                }
        }
    }
}
