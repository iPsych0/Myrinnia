package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class Rock extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = -8123420086619425263L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private boolean isMining = false;
    private int miningTimer = 0;
    private int minAttempts = 4, maxAttempts = 9;
    private int random = 0;
    private int attempts = 0;
    private Item ore;
    private int experience;

    public Rock(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        isNpc = true;
        attackable = false;

        if (name.equalsIgnoreCase("Azurite Rock")) {
            ore = Item.azuriteOre;
            experience = 10;
        }
    }

    @Override
    public void tick() {
        if (isMining) {
            if (Handler.get().invIsFull(ore)) {
                miningTimer = 0;
                speakingTurn = -1;
                interact();
                isMining = false;
            }
            if (Player.isMoving || Handler.get().getMouseManager().isLeftPressed() &&
                    !Handler.get().getPlayer().hasLeftClickedUI(Handler.get().getMouse())) {
                miningTimer = 0;
                speakingTurn = 0;
                isMining = false;
                return;
            }
            if (random != 0) {
                if (attempts == random) {
                    attempts = 0;
                    isMining = false;
                    this.active = false;
                    this.die();
                }
            }

            miningTimer++;

            if (miningTimer >= 180) {
                int roll = Handler.get().getRandomNumber(1, 100);
                if (roll < 70) {
                    Handler.get().giveItem(ore, 1);
                    Handler.get().sendMsg("You successfully mined some ore!");
                    Handler.get().getSkillsUI().getSkill(SkillsList.MINING).addExperience(experience);
                    attempts++;

                } else {
                    Handler.get().sendMsg("You missed the swing...");
                    attempts++;

                }
                speakingTurn = 0;
                miningTimer = 0;

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
        g.drawImage(Assets.azuriteRock, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {
        if (this.speakingTurn == -1) {
            speakingTurn++;
            return;
        }
        if (this.speakingTurn == 0) {
            if (Handler.get().playerHasSkillLevel(SkillsList.MINING, ore)) {
                if (Handler.get().playerHasItemType(ItemType.PICKAXE)) {
                    Handler.get().sendMsg("Mining...");
                    speakingTurn = 1;
                    isMining = true;
                } else {
                    Handler.get().sendMsg("You need a pickaxe to mine this rock.");
                }
            } else {
                Handler.get().sendMsg("You need a mining level of " + Handler.get().getSkillResource(SkillsList.MINING, ore).getLevelRequirement() + " to mine this rock.");
            }
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        g.drawImage(Assets.miningIcon, (int) (x + width / 2 - 16 - Handler.get().getGameCamera().getxOffset()), (int) (y - 36 - Handler.get().getGameCamera().getyOffset()), 32, 32, null);
        if (isMining) {
            StringBuilder pending = new StringBuilder();
            int dots = (int) Math.ceil(miningTimer / 30d);
            for (int i = 0; i < dots; i++) {
                pending.append(".");
            }

            Text.drawString(g, pending.toString(), (int) (Handler.get().getPlayer().getX() + 16 - Handler.get().getGameCamera().getxOffset()),
                    (int) (Handler.get().getPlayer().getY() - 16 - Handler.get().getGameCamera().getyOffset()), true, Color.YELLOW, Assets.font24);
        }

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Rock(xSpawn, ySpawn, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {

    }

}
