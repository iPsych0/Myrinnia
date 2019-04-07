package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.tiles.Tiles;

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

    public FishingSpot(float x, float y) {
        super(x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);

        isNpc = true;
        attackable = false;
        spinning = new Animation(125, Assets.whirlpool);
    }

    @Override
    public void tick() {
        spinning.tick();
        if (isFishing) {
            if (Handler.get().invIsFull(Item.regularFish)) {
                fishingTimer = 0;
                speakingTurn = -1;
                interact();
                isFishing = false;
            }
            if (Player.isMoving || Handler.get().getMouseManager().isLeftPressed() &&
                    !Handler.get().getPlayer().hasLeftClickedUI(new Rectangle(Handler.get().getMouseManager().getMouseX(), Handler.get().getMouseManager().getMouseY(), 1, 1))) {
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
                System.out.println(random + " and " + attempts);
                int roll = Handler.get().getRandomNumber(1, 100);
                if (roll < 60) {
                    Handler.get().giveItem(Item.regularFish, 1);
                    Handler.get().sendMsg("You caught something!");
                    Handler.get().getSkillsUI().getSkill(SkillsList.FISHING).addExperience(10);
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
    public void render(Graphics g) {
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
            if (Handler.get().playerHasSkillLevel(SkillsList.FISHING, Item.regularFish)) {
                Handler.get().sendMsg("Fishing...");
                speakingTurn = 1;
                isFishing = true;
            }else{
                Handler.get().sendMsg("You need a fishing level of " + Handler.get().getSkillResource(SkillsList.FISHING, Item.regularLogs).getLevelRequirement() + " to catch this type of fish.");
            }
        }
    }

    @Override
    public void postRender(Graphics g) {
        if (isFishing) {
            g.drawImage(Assets.fishingIcon, (int) (Handler.get().getPlayer().getX() - Handler.get().getGameCamera().getxOffset()), (int) (Handler.get().getPlayer().getY() - Handler.get().getGameCamera().getyOffset() - 32), width, height, null);
        }

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new FishingSpot(xSpawn, ySpawn));
    }

    @Override
    protected void updateDialogue() {

    }

    public String getName() {
        return "Fishing Spot";
    }


}
