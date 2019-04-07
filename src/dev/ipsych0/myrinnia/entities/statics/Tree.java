package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.tiles.Tiles;

import java.awt.*;

public class Tree extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = -524381157898161854L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private boolean isWoodcutting = false;
    private int woodcuttingTimer = 0;
    private int minAttempts = 3, maxAttempts = 6;
    private int random = 0;
    private int attempts = 0;

    public Tree(float x, float y) {
        super(x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);

        isNpc = true;
        attackable = false;

    }

    @Override
    public void tick() {
        if (isWoodcutting) {
            if (Handler.get().invIsFull(Item.regularLogs)) {
                woodcuttingTimer = 0;
                speakingTurn = -1;
                interact();
                isWoodcutting = false;
            }
            if (Player.isMoving || Handler.get().getMouseManager().isLeftPressed() &&
                    !Handler.get().getPlayer().hasLeftClickedUI(new Rectangle(Handler.get().getMouseManager().getMouseX(), Handler.get().getMouseManager().getMouseY(), 1, 1))) {
                woodcuttingTimer = 0;
                speakingTurn = 0;
                isWoodcutting = false;
                return;
            }
            if (random != 0) {
                if (attempts == random) {
                    attempts = 0;
                    isWoodcutting = false;
                    this.active = false;
                    this.die();
                }
            }

            woodcuttingTimer++;

            if (woodcuttingTimer >= 180) {
                System.out.println(random + " and " + attempts);
                int roll = Handler.get().getRandomNumber(1, 100);
                if (roll < 70) {
                    Handler.get().giveItem(Item.regularLogs, Handler.get().getRandomNumber(1, 3));
                    Handler.get().sendMsg("You succesfully chopped some logs.");
                    Handler.get().getSkillsUI().getSkill(SkillsList.WOODCUTTING).addExperience(20);
                    attempts++;

                } else {
                    Handler.get().sendMsg("Your hatchet bounced off the tree...");
                    attempts++;
                }
                speakingTurn = 0;
                woodcuttingTimer = 0;

                if (attempts == minAttempts - 1) {
                    random = Handler.get().getRandomNumber(minAttempts, maxAttempts);
                }
            }

        }
    }

    @Override
    public void die() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.tree, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {
        if (this.speakingTurn == -1) {
            speakingTurn++;
            return;
        }
        if (this.speakingTurn == 0) {
            if (Handler.get().playerHasSkillLevel(SkillsList.WOODCUTTING, Item.regularLogs)) {
                if (Handler.get().playerHasItemType(ItemType.AXE)) {
                    Handler.get().sendMsg("Chop chop...");
                    speakingTurn = 1;
                    isWoodcutting = true;
                } else {
                    Handler.get().sendMsg("You need an axe to chop this tree.");
                }
            } else {
                Handler.get().sendMsg("You need a woodcutting level of " + Handler.get().getSkillResource(SkillsList.WOODCUTTING, Item.regularLogs).getLevelRequirement() + " to chop this tree.");
            }
        }
    }

    @Override
    public void postRender(Graphics g) {
        if (isWoodcutting) {
            g.drawImage(Assets.woodcuttingIcon, (int) (Handler.get().getPlayer().getX() - Handler.get().getGameCamera().getxOffset()), (int) (Handler.get().getPlayer().getY() - Handler.get().getGameCamera().getyOffset() - 32), 32, 32, null);
        }

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Tree(xSpawn, ySpawn));
    }

    @Override
    protected void updateDialogue() {

    }

}
