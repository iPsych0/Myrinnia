package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.utils.Text;

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
    private int minAttempts = 4, maxAttempts = 9;
    private int random = 0;
    private int attempts = 0;
    private Item logs;
    private int experience;

    public Tree(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        isNpc = true;
        attackable = false;

        if (name.equalsIgnoreCase("Weak Palm Tree")) {
            logs = Item.lightWood;
            experience = 10;
            bounds.x = 32;
            bounds.y = 104;
            bounds.width = 32;
            bounds.height = 16;
        } else {
            throw new IllegalArgumentException("Tree name not found: " + name);
        }

    }

    @Override
    public void tick() {
        if (isWoodcutting) {
            if (Handler.get().invIsFull(logs)) {
                woodcuttingTimer = 0;
                speakingTurn = -1;
                interact();
                isWoodcutting = false;
            }
            if (Player.isMoving || Handler.get().getMouseManager().isLeftPressed() &&
                    !Handler.get().getPlayer().hasLeftClickedUI(Handler.get().getMouse())) {
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
                int roll = Handler.get().getRandomNumber(1, 100);
                if (roll < 70) {
                    Handler.get().giveItem(logs, 1);
                    Handler.get().sendMsg("You successfully chopped some logs.");
                    Handler.get().getSkillsUI().getSkill(SkillsList.WOODCUTTING).addExperience(experience);
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
    public void render(Graphics2D g) {
        g.drawImage(Assets.weakPalmTree, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {
        if (this.speakingTurn == -1) {
            speakingTurn++;
            return;
        }
        if (this.speakingTurn == 0) {
            if (Handler.get().playerHasSkillLevel(SkillsList.WOODCUTTING, logs)) {
                if (Handler.get().playerHasItemType(ItemType.AXE)) {
                    Handler.get().sendMsg("Chop chop...");
                    speakingTurn = 1;
                    isWoodcutting = true;
                } else {
                    Handler.get().sendMsg("You need an axe to chop this tree.");
                }
            } else {
                Handler.get().sendMsg("You need a woodcutting level of " + Handler.get().getSkillResource(SkillsList.WOODCUTTING, logs).getLevelRequirement() + " to chop this tree.");
            }
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        g.drawImage(Assets.woodcuttingIcon, (int) (x + width / 2 - 16 - Handler.get().getGameCamera().getxOffset()), (int) (y - 36 - Handler.get().getGameCamera().getyOffset()), 32, 32, null);
        if (isWoodcutting) {
            StringBuilder pending = new StringBuilder();
            int dots = (int) Math.ceil(woodcuttingTimer / 30);
            for (int i = 0; i < dots; i++) {
                pending.append(".");
            }

            int xOffset = width / 32 - 1;

            Text.drawString(g, pending.toString(), (int) (Handler.get().getPlayer().getX() + (xOffset * 16 ) - Handler.get().getGameCamera().getxOffset()),
                    (int) (Handler.get().getPlayer().getY() - 16 - Handler.get().getGameCamera().getyOffset()), true, Color.YELLOW, Assets.font24);
        }

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Tree(xSpawn, ySpawn, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {

    }

}
