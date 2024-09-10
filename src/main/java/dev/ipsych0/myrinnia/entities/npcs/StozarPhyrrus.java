package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.BurningHasteAbility;
import dev.ipsych0.myrinnia.abilities.data.AbilityManager;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;

import java.awt.*;

public class StozarPhyrrus extends Creature {

    private Player player;
    public static boolean hasLitTorches;
    public static boolean hasSpoken;

    public StozarPhyrrus(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;
        speed = 1.0;

        player = Handler.get().getPlayer();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 0:
                if (hasLitTorches) {
                    speakingTurn = 6;
                }
                if (AbilityManager.abilityMap.get(BurningHasteAbility.class).isUnlocked()) {
                    speakingTurn = 8;
                }
                break;
            case 5:
                if (!Handler.get().playerHasItemType(ItemType.FIRE_SOURCE)) {
                    Handler.get().giveItem(Item.unlitCandle, 1);
                    Handler.get().giveItem(Item.matchbox, 1);
                }
                hasSpoken = true;
                break;
            case 8:
                // Unlock new ability!
                if (!AbilityManager.abilityMap.get(BurningHasteAbility.class).isUnlocked()) {
                    AbilityManager.abilityMap.get(BurningHasteAbility.class).setUnlocked(true);
                }
                speakingCheckpoint = 8;
                break;
        }
    }
}
