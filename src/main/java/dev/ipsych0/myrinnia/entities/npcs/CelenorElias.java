package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;

public class CelenorElias extends Entity {

    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);

    public CelenorElias() {
        
        solid = true;
        attackable = false;
        isNpc = true;
        this.stats.setMovementSpeed(1.0);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    public void postRender(Graphics2D g) {
        if (Handler.get().hasQuestReqs(QuestList.ExtrememistBeliefs) && quest.getState() == QuestState.NOT_STARTED) {
            g.drawImage(Assets.exclamationIcon, (int) (x - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - 32 - Handler.get().getGameCamera().getyOffset()), null);
        }
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 0:
                if (quest.getState() == QuestState.IN_PROGRESS) {
                    speakingTurn = 5;
                } else if (quest.getState() == QuestState.COMPLETED) {
                    speakingTurn = 8;
                }
                break;
            case 5:
                // Start the quest
                if (quest.getState() == QuestState.NOT_STARTED) {
                    quest.setState(QuestState.IN_PROGRESS);

                    quest.addNewCheck("clue1", false);
                    quest.addNewCheck("clue2", false);

                    // Add the choice option to progress the quest to Elenthir
                    Entity elenthir = Handler.get().getEntityByZoneAndName(Zone.CelewynnInside, "Elenthir");
                    Script script = elenthir.getScript();
                    script.getDialogues().get(1).getOptions().add(0, new Choice("Sorry to bother, I am looking for the Forest Warden.", 2, null));
                }
                break;
        }
    }
}
