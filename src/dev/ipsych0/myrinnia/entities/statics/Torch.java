package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.npcs.StozarPhyrrus;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.utils.Timer;
import dev.ipsych0.myrinnia.utils.TimerHandler;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Torch extends StaticEntity {

    private final Animation flameAnimation;
    private boolean lit;
    private static int numTorchesLit;
    private static final int NUM_TORCHES = 8;

    public Torch(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = false;
        attackable = false;
        isNpc = true;

        flameAnimation = new Animation(125, Assets.torchFlame);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {
        if (lit) {
            flameAnimation.tick();
            g.drawImage(flameAnimation.getCurrentFrame(),
                    (int) (this.getX() - Handler.get().getGameCamera().getxOffset()),
                    (int) (this.getY() - Handler.get().getGameCamera().getyOffset()),
                    Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
        }
    }

    @Override
    protected void die() {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        if ("hasFireSource".equals(condition)) {
            return Handler.get().playerHasItemType(ItemType.FIRE_SOURCE);
        }
        return false;
    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Torch(x, y, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 0:
                if (!StozarPhyrrus.hasSpoken) {
                    speakingTurn = 3;
                }
                if (lit) {
                    speakingTurn = 4;
                }
                break;
            case 2:
                if (!lit) {
                    lit = true;
                    numTorchesLit++;

                    if (numTorchesLit == (NUM_TORCHES - 1)) {
                        // If we've lit all torches we're done
                        StozarPhyrrus.hasLitTorches = true;
                    }

                    TimerHandler.get().addTimer(new Timer(15, TimeUnit.SECONDS, () -> {
                        // If after 15 seconds, not all torches are lit yet, then unlight this torch
                        if (numTorchesLit < (NUM_TORCHES - 1)) {
                            numTorchesLit--;
                            lit = false;
                        }
                    }));
                }
                speakingTurn = -1;
                break;
        }
    }
}
