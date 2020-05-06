package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.entities.statics.CelenorEarthCrystal;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;

public class CelenorFloatingRock extends Creature {

    private Animation floatAnim;
    private Player player;
    private boolean moving;
    private boolean hardPush;
    private int pxMoved;
    private static Rectangle leftPatch = new Rectangle(19 * 32, 28 * 32, 32, 32),
            middlePatch = new Rectangle(25 * 32, 33 * 32, 32, 32),
            rightPatch = new Rectangle(31 * 32, 28 * 32, 32, 32);
    private static boolean leftPatchDone, middlePatchDone, rightPatchDone;

    public CelenorFloatingRock(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = false;
        attackable = false;
        walker = false;
        isNpc = true;
        speed = 1.0;

        floatAnim = new Animation(250, Assets.floatingRock);
        player = Handler.get().getPlayer();
    }

    @Override
    public void tick() {
        floatAnim.tick();
        if (moving) {
            move();
            pxMoved++;
            // Only move 1 tile at a time
            int maxMovement = Tile.TILEWIDTH;
            if (hardPush) {
                maxMovement = Tile.TILEWIDTH * 3;
            }
            if (pxMoved == maxMovement) {
                stopMovement();
            }
        }

        // Check if we put the rock on the patch
        if ((int) leftPatch.getX() == (int) x && (int) leftPatch.getY() == (int) y) {
            leftPatchDone = true;
            stopMovement();
        }
        if ((int) middlePatch.getX() == (int) x && (int) middlePatch.getY() == (int) y) {
            middlePatchDone = true;
            stopMovement();
        }
        if ((int) rightPatch.getX() == (int) x && (int) rightPatch.getY() == (int) y) {
            rightPatchDone = true;
            stopMovement();
        }

        if (leftPatchDone && middlePatchDone && rightPatchDone) {
            CelenorEarthCrystal.puzzleCompleted = true;
        }
    }

    private void stopMovement() {
        // Round off to nearest int value
        this.x = (int) (this.x + 0.5);
        this.y = (int) (this.y + 0.5);
        moving = false;
        pxMoved = 0;
        xMove = 0;
        yMove = 0;
        hardPush = false;
    }


    @Override
    public void render(Graphics2D g) {
        g.drawImage(floatAnim.getCurrentFrame(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new CelenorFloatingRock(x, y, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile, direction));
    }

    @Override
    protected void updateDialogue() {
        double xPos = player.getX();
        double yPos = player.getY();

        switch (speakingTurn) {
            case 0:
                if (leftPatchDone && middlePatchDone && rightPatchDone) {
                    speakingTurn = -1;
                    return;
                }
                if (yPos >= (y + height - 24) && xPos > (x - 12) && xPos < (x + width + 12)) {
                    script.getDialogues().get(1).getOptions().get(0).setText("Push the rock up.");
                    script.getDialogues().get(1).getOptions().get(1).setText("Push the rock up hard.");
                } else if (yPos <= y && xPos > (x - 12) && xPos < (x + width + 12)) {
                    script.getDialogues().get(1).getOptions().get(0).setText("Push the rock down.");
                    script.getDialogues().get(1).getOptions().get(1).setText("Push the rock down hard.");
                } else if (xPos <= x + 8 && yPos > y - 8 && yPos < (y + height - 24)) {
                    script.getDialogues().get(1).getOptions().get(0).setText("Push the rock right.");
                    script.getDialogues().get(1).getOptions().get(1).setText("Push the rock right hard.");
                } else if (xPos >= (x + width - 24) && yPos > y - 8 && yPos < (y + height - 24)) {
                    script.getDialogues().get(1).getOptions().get(0).setText("Push the rock left.");
                    script.getDialogues().get(1).getOptions().get(1).setText("Push the rock left hard.");
                } else {
                    speakingTurn = 5;
                }
                break;
            case 2:
                setMovement(xPos, yPos);
                break;
            case 3:
                setMovement(xPos, yPos);
                hardPush = true;
                break;
            case 4:
                stopMovement();
                this.x = xSpawn;
                this.y = ySpawn;
                speakingTurn = -1;
                break;
        }
    }

    private void setMovement(double xPos, double yPos) {
        if (yPos >= (y + height - 24) && xPos > (x - 12) && xPos < (x + width + 12)) {
            // If down the rock, and between the left/right bound, move up
            yMove = -speed;
            moving = true;
        } else if (yPos <= y && xPos > (x - 12) && xPos < (x + width + 12)) {
            // If above the rock, and between the left/right bound, move down
            yMove = speed;
            moving = true;
        } else if (xPos <= x + 8 && yPos > y - 8 && yPos < (y + height - 24)) {
            // If left of the rock, and between the top/bottom bound, move right
            xMove = +speed;
            moving = true;
        } else if (xPos >= (x + width - 24) && yPos > y - 8 && yPos < (y + height - 24)) {
            // If right of the rock, and between the top/bottom bound, move left
            xMove = -speed;
            moving = true;
        }
        speakingTurn = -1;
    }

    /*
     * Checks the collision for Entities
     * @returns: true if collision, false if no collision
     */
    @Override
    protected boolean checkEntityCollisions(double xOffset, double yOffset) {
        if (Handler.noclipMode)
            return false;
        for (Entity e : Handler.get().getWorld().getEntityManager().getEntities()) {
            if (e.equals(this))
                continue;
            if (e.equals(player))
                continue;
            if (e.getVerticality() == this.verticality && e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
                return true;
        }
        return false;
    }
}
