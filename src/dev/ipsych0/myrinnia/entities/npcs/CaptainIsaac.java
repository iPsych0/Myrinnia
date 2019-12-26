package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;

public class CaptainIsaac extends Creature {

    public CaptainIsaac(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void die() {

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
            case "hasCompletedQuests":
                if (Handler.get().questCompleted(QuestList.WaveGoodbye)) {
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
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 0:
                Choice option1 = script.getDialogues().get(1).getOptions().get(0);
                if (Handler.get().getPlayer().getZone() == Zone.ShamrockHarbour) {
                    option1.setText(option1.getText().replaceFirst("Shamrock Harbour", "Port Azure"));
                } else if(Handler.get().getPlayer().getZone() == Zone.PortAzure){
                    option1.setText(option1.getText().replaceFirst("Port Azure", "Shamrock Harbour"));
                }
                break;
            case 4:
                if (Handler.get().getPlayer().getZone() == Zone.ShamrockHarbour) {
                    Handler.get().goToWorld(Zone.PortAzure, 70, 65);
                } else if(Handler.get().getPlayer().getZone() == Zone.PortAzure){
                    Handler.get().goToWorld(Zone.ShamrockHarbour, 12, 13);
                }
                speakingTurn = -1;
                break;
        }
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {

    }
}
