package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class FishingSpot extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = -4511991258183891329L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private Animation spinning;
    private boolean isFishing = false;
    private int fishingTimer = 0;
    private int minAttempts = 4, maxAttempts = 9;
    private int random = 0;
    private int attempts = 0;
    private Item fish;
    private int experience;

    public FishingSpot(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        isNpc = true;
        attackable = false;
        spinning = new Animation(125, Assets.whirlpool);

        if (name.equalsIgnoreCase("Mackerel Fishing Spot")) {
            fish = Item.mackerelFish;
            experience = 10;
        }
    }

    @Override
    public void tick() {
        spinning.tick();
        if (isFishing) {
            if (Handler.get().invIsFull(fish)) {
                fishingTimer = 0;
                speakingTurn = -1;
                interact();
                isFishing = false;
            }
            if (Player.isMoving || Handler.get().getMouseManager().isLeftPressed() &&
                    !Handler.get().getPlayer().hasLeftClickedUI(Handler.get().getMouse())) {
                fishingTimer = 0;
                speakingTurn = 0;
                isFishing = false;
                return;
            }
            if (random != 0) {
                if (attempts == random) {
                    attempts = 0;
                    isFishing = false;
                    this.active = false;
                    this.die();
                }
            }

            fishingTimer++;

            if (fishingTimer >= 180) {
                int roll = Handler.get().getRandomNumber(1, 100);
                if (roll < 70) {
                    Handler.get().giveItem(fish, 1);
                    Handler.get().sendMsg("You caught something!");
                    Handler.get().getSkillsUI().getSkill(SkillsList.FISHING).addExperience(experience);
                    attempts++;
                } else {
                    Handler.get().sendMsg("The fish got away...");
                    attempts++;
                }
                speakingTurn = 1;
                fishingTimer = 0;

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
        g.drawImage(spinning.getCurrentFrame(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {
        if (this.speakingTurn == -1) {
            speakingTurn++;
            return;
        }
        if (this.speakingTurn == 0) {
            if (Handler.get().playerHasSkillLevel(SkillsList.FISHING, fish)) {
                if (Handler.get().playerHasItemType(ItemType.FISHING_ROD)) {
                    Handler.get().sendMsg("Fishing...");
                    speakingTurn = 1;
                    isFishing = true;
                } else {
                    Handler.get().sendMsg("You need a fishing rod to catch fish.");
                }
            } else {
                Handler.get().sendMsg("You need a fishing level of " + Handler.get().getSkillResource(SkillsList.FISHING, fish).getLevelRequirement() + " to catch this type of fish.");
            }
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        g.drawImage(Assets.fishingIcon, (int) (x + width / 2 - 16 - Handler.get().getGameCamera().getxOffset()), (int) (y - 36 - Handler.get().getGameCamera().getyOffset()), 32, 32, null);
        if (isFishing) {
            StringBuilder pending = new StringBuilder();
            int dots = (int) Math.ceil(fishingTimer / 30);
            for (int i = 0; i < dots; i++) {
                pending.append(".");
            }

            int xOffset = width / 32 - 1;

            Text.drawString(g, pending.toString(), (int) (Handler.get().getPlayer().getX() + (xOffset * 16) - Handler.get().getGameCamera().getxOffset()),
                    (int) (Handler.get().getPlayer().getY() - 16 - Handler.get().getGameCamera().getyOffset()), true, Color.YELLOW, Assets.font24);
        }

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new FishingSpot(xSpawn, ySpawn, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {

    }
}
