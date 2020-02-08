package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.statics.RopeLadderTile;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.worlds.Zone;
import dev.ipsych0.myrinnia.worlds.ZoneTile;

import java.awt.*;

public class ShamrockAaron extends Creature {

    private boolean ropeAdded;

    public ShamrockAaron(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;

        Handler.get().getQuest(QuestList.WeDelvedTooDeep).nextStep();
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
                Object defeated = Handler.get().getQuest(QuestList.WeDelvedTooDeep).getCheckValue("trollDefeated");
                if (defeated != null && (Boolean) defeated) {
                    speakingTurn = 1;
                    speakingCheckpoint = 1;
                }
                break;
            case 1:
                if (Handler.get().playerHasItem(Item.rope, 1)) {
                    speakingTurn = 3;
                    speakingCheckpoint = 3;
                }
                break;
            case 4:
                if (!ropeAdded) {
                    ZoneTile level3Rope = new ZoneTile(Zone.ShamrockMines3, 84 * 32, 19 * 32, 32, 32, 41, 38, null, null, Direction.UP);
                    Handler.get().getWorld().addRuntimeZoneTile(level3Rope);
                    Handler.get().getWorld().getEntityManager().addRuntimeEntity(new RopeLadderTile(2688, 544, 32, 96, null, 0, null, null, null, null));
                    ropeAdded = true;
                    speakingCheckpoint = 4;
                    active = false;
                    remove("Miner Robert");
                    remove("Miner Albert");
                    Handler.get().getQuest(QuestList.WeDelvedTooDeep).nextStep();
                }
                break;
        }
    }

    private void remove(String name) {
        for (Entity e : Handler.get().getWorld().getEntityManager().getEntities()) {
            if (e.getName() != null && e.getName().equalsIgnoreCase(name)) {
                e.setActive(false);
                break;
            }
        }
    }
}
