package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.puzzles.PotionSort;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;

public class CelenorPotionCabinet extends StaticEntity {

    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);
    private boolean hasMovedLeft, isMovingLeft;
    private int slideTimer;
    private PotionSort potionSort;
    private Player player;
    private int hintMsgWalkTimer = 0;
    private boolean showHint = true;

    public CelenorPotionCabinet(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = true;
        attackable = false;
        isNpc = true;

        player = Handler.get().getPlayer();
        potionSort = new PotionSort(() -> {
            PotionSort.isOpen = false;
            isMovingLeft = true;

            speakingTurn = 4;
            player.setMovementAllowed(false);
            player.setClosestEntity(this);
            interact();
            Handler.get().playEffect("misc/cabinet_sliding.ogg");
            quest.nextStep();
        });
    }

    @Override
    public void tick() {
        // Slide the bookcase left slowly
        if (isMovingLeft) {
            slideTimer++;
            if (slideTimer % 2 == 0) {
                this.x -= 1;
                // When reached left wall, stop sliding
                if (this.x <= 46 * Tile.TILEWIDTH) {
                    this.x = 46 * Tile.TILEWIDTH;
                    isMovingLeft = false;
                    hasMovedLeft = true;
                    player.setMovementAllowed(true);
                    player.setClosestEntity(null);
                }
            }
        }

        if (PotionSort.isOpen) {
            potionSort.tick();
        }

        if (PotionSort.isOpen && Player.isMoving || PotionSort.isOpen && potionSort.hasExited()) {
            speakingTurn = 5;
            player.setMovementAllowed(false);
            player.setClosestEntity(this);
            interact();
            showHint = true;
        }

        if (showHint) {
            hintMsgWalkTimer++;
            if (hintMsgWalkTimer >= 30) {
                showHint = false;
                hintMsgWalkTimer = 0;
                player.setMovementAllowed(true);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.celenorPotionCabinetTop, (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
        g.drawImage(Assets.celenorPotionCabinetShelves, (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y + 32 - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    public void postRender(Graphics2D g) {
        if (PotionSort.isOpen) {
            potionSort.render(g);
        }
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
                return (Boolean) quest.getCheckValueWithDefault("book1", false) &&
                        (Boolean) quest.getCheckValueWithDefault("book2", false) &&
                        (Boolean) quest.getCheckValueWithDefault("book3", false) &&
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
                PotionSort.isOpen = true;
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
