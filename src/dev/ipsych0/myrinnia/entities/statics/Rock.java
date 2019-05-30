package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.tiles.Tile;

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
    private int minAttempts = 3, maxAttempts = 6;
    private int random = 0;
    private int attempts = 0;

    public Rock(float x, float y) {
        super(x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);

        isNpc = true;
        attackable = false;
    }

    @Override
    public void tick() {
        if (isMining) {
            if (Handler.get().invIsFull(Item.regularOre)) {
                miningTimer = 0;
                speakingTurn = -1;
                interact();
                isMining = false;
            }
            if (Player.isMoving || Handler.get().getMouseManager().isLeftPressed() &&
                    !Handler.get().getPlayer().hasLeftClickedUI(new Rectangle(Handler.get().getMouseManager().getMouseX(), Handler.get().getMouseManager().getMouseY(), 1, 1))) {
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
                System.out.println(random + " and " + attempts);
                int roll = Handler.get().getRandomNumber(1, 100);
                if (roll < 60) {
                    Handler.get().giveItem(Item.regularOre, Handler.get().getRandomNumber(1, 3));
                    Handler.get().sendMsg("You succesfully mined some ore!");
                    Handler.get().getSkillsUI().getSkill(SkillsList.MINING).addExperience(10);
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
        g.drawImage(Assets.rock, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {
        if (this.speakingTurn == -1) {
            speakingTurn++;
            return;
        }
        if (this.speakingTurn == 0) {
            if (Handler.get().playerHasSkillLevel(SkillsList.MINING, Item.regularOre)) {
                if (Handler.get().playerHasItemType(ItemType.PICKAXE)) {
                    Handler.get().sendMsg("Mining...");
                    speakingTurn = 1;
                    isMining = true;
                } else {
                    Handler.get().sendMsg("You need a pickaxe to mine this rock.");
                }
            } else {
                Handler.get().sendMsg("You need a mining level of " + Handler.get().getSkillResource(SkillsList.MINING, Item.regularOre).getLevelRequirement() + " to mine this rock.");
            }
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        if (isMining) {
            g.setColor(Color.WHITE);
            g.drawImage(Assets.miningIcon, (int) (Handler.get().getPlayer().getX() - Handler.get().getGameCamera().getxOffset()), (int) (Handler.get().getPlayer().getY() - Handler.get().getGameCamera().getyOffset() - 32), width, height, null);
        }

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Rock(xSpawn, ySpawn));
    }

    @Override
    protected void updateDialogue() {

    }

}
