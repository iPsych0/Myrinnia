package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.entities.npcs.Choice;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;

public class CelenorRopeUp extends StaticEntity {

    private Player player;

    public CelenorRopeUp(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = false;
        attackable = false;
        isNpc = true;
        player = Handler.get().getPlayer();

        Choice climbOption = script.getDialogues().get(4).getOptions().get(0);
        climbOption.setText(climbOption.getText().replaceFirst("down", "up"));
    }

    @Override
    public void tick() {

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
        Handler.get().getWorld().getEntityManager().addEntity(new CelenorRopeUp(x, y, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 0:
                speakingTurn = 4;
                break;
            case 3:
                player.setX(56 * Tile.TILEWIDTH - 16);
                player.setY(6 * Tile.TILEHEIGHT);
                speakingTurn = -1;
                break;
        }
    }
}
