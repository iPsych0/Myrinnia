package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;

public class StozarCurator extends Creature {

    private Player player;

    public StozarCurator(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
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
                if (player.getY() < this.getY()) {
                    script.getDialogues().get(0).setNextId(1);
                } else {
                    script.getDialogues().get(0).setNextId(3);
                }
                break;
            case 5:
                if (player.getY() < this.getY()) {
                    player.setY(this.getY() + Tile.TILEHEIGHT * 2 + 16);
                } else {
                    player.setY(this.getY() - Tile.TILEHEIGHT);
                }
                speakingTurn = -1;
                break;
        }
    }

    @Override
    public boolean isNear(Entity closest) {
        return closest.getInteractionBounds(-40, -40, 80, 128)
                .intersects(Handler.get().getPlayer().getCollisionBounds(0, 0));
    }
}
