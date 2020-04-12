package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;

public class CelenorNevarth extends Creature {

    private boolean hasMoved;
    private Rectangle caveExit = new Rectangle(3432, 1616, 112, 16);
    private Rectangle caveEntrance = new Rectangle(3456, 2016, 32, 48);
    private Player player;

    public CelenorNevarth(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
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

        if (Handler.get().questCompleted(QuestList.ExtrememistBeliefs)) {
            if (player.getCollisionBounds(0, 0).intersects(caveEntrance)) {
                player.setX(108 * 32);
                player.setY(49 * 32);
            } else if (player.getCollisionBounds(0, 0).intersects(caveExit)) {
                player.setX(108 * 32);
                player.setY(65 * 32);
            }
        }
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
    protected boolean choiceConditionMet(String condition) {
        if ("hasPermission".equals(condition)) {
            return Handler.get().questCompleted(QuestList.ExtrememistBeliefs);
        }
        return false;
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 3:
                if (!hasMoved) {
                    hasMoved = true;
                    this.x -= Tile.TILEWIDTH;
                }
        }
    }
}
