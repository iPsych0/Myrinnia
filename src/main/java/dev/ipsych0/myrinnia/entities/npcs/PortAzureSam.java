package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.tiles.Tile;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class PortAzureSam extends Entity {

    private boolean firstAccess = true;
    private boolean hasSteppedAside;
    private boolean moveSpeedSet;
    private double startX;

    public PortAzureSam() {
        
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
                xMove -= this.stats.getMovementSpeed();
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
