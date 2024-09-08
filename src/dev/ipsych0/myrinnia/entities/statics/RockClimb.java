package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;

public class RockClimb extends StaticEntity {

    private Creature.Direction direction;
    private Player player;
    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);

    public RockClimb(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = true;
        attackable = false;
        isNpc = true;
        player = Handler.get().getPlayer();
    }

    @Override
    public void tick() {
        if (direction != null) {
            player.setX(this.x);
            if (direction == Creature.Direction.UP) {
                player.setY(player.getY() - 1);
                if (player.getY() <= this.y - Tile.TILEHEIGHT - 8) {
                    direction = null;
                    player.setMovementAllowed(true);
                }
            } else {
                player.setY(player.getY() + 1);
                if (player.getY() >= this.y + this.height) {
                    direction = null;
                    player.setMovementAllowed(true);
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {

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
                if (!quest.getQuestSteps().get(0).isFinished()) {
                    speakingTurn = 3;
                }
                break;
            case 2:
                if (player.getY() <= this.y) {
                    direction = Creature.Direction.DOWN;
                } else if (player.getY() >= (this.y + this.height / 2d)) {
                    direction = Creature.Direction.UP;
                }
                speakingTurn = -1;
                player.setMovementAllowed(false);
                break;
        }
    }
}
