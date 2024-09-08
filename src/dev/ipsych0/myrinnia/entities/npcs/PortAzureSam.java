package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.tiles.Tile;

import java.awt.*;

public class PortAzureSam extends Creature {

    private boolean firstAccess = true;
    private boolean hasSteppedAside;
    private boolean moveSpeedSet;
    private float startX;

    public PortAzureSam(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;
        startX = x;
    }

    @Override
    public void tick() {
        super.tick();
        if (!hasSteppedAside && walker) {
            if (!moveSpeedSet && x > (startX - Tile.TILEWIDTH)) {
                xMove -= speed;
                moveSpeedSet = true;
            } else if (xMove <= (startX - Tile.TILEWIDTH)) {
                hasSteppedAside = true;
            }
            move();
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "hasStartedElderQuest":
                if (Handler.get().getQuest(QuestList.WaveGoodbye).getState() != QuestState.NOT_STARTED) {
                    return true;
                }
                break;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
        return false;
    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new PortAzureSam(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, lastFaced));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 4:
                if (firstAccess) {
                    walker = true;
                    firstAccess = false;
                    script.getDialogues().get(4).setText("Please be careful inside Mt. Azure.");
                }
                break;

        }
    }
}
