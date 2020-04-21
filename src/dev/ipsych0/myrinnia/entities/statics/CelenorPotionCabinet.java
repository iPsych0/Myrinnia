package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;

public class CelenorPotionCabinet extends StaticEntity {

    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);
    private boolean hasMovedLeft, isMovingLeft;
    private int slideTimer;

    public CelenorPotionCabinet(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = true;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {
        // Slide the bookcase left slowly
        if (isMovingLeft) {
            slideTimer++;
            if (slideTimer % 2 == 0) {
                this.x -= 1;
                // When reached left wall, stop sliding
                if (this.x <= 21 * Tile.TILEWIDTH) {
                    this.x = 21d * Tile.TILEWIDTH;
                    isMovingLeft = false;
                    hasMovedLeft = true;
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.celenorPotionCabinetTop, (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
        g.drawImage(Assets.celenorPotionCabinetShelves, (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new CelenorPotionCabinet(x, y, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            // Check if all books are read and only do puzzle when it hasn't moved left yet
            case "hasReadAllBooks":
                return (Boolean) quest.getCheckValue("book1") &&
                        (Boolean) quest.getCheckValue("book2") &&
                        (Boolean) quest.getCheckValue("book3") &&
                        !hasMovedLeft;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 3:
                // TODO: OPEN PUZZLE SCREEN
                speakingTurn = -1;
                break;
            case 4:
                if (!hasMovedLeft && !isMovingLeft) {
                    isMovingLeft = true;
                    Handler.get().getPlayer().setMovementAllowed(false);
                }
                break;
        }
    }
}
