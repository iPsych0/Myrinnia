package dev.ipsych0.myrinnia.quests;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Quest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3216607526116365186L;
    private ArrayList<QuestStep> questSteps;
    private int step = 0;
    private String questName;
    private QuestState state;
    private QuestRequirement[] requirements;

    private Zone zone;

    public enum QuestState {
        NOT_STARTED, IN_PROGRESS, COMPLETED
    }

    public Quest(String questName, Zone zone) {
        this.questName = questName;
        this.zone = zone;
        questSteps = new ArrayList<QuestStep>();
        state = QuestState.NOT_STARTED;
    }

    public Quest(String questName, Zone zone, QuestRequirement... questRequirements) {
        this.questName = questName;
        this.zone = zone;
        this.requirements = questRequirements;
        questSteps = new ArrayList<QuestStep>();
        state = QuestState.NOT_STARTED;
    }

    public void tick() {

    }

    public void render(Graphics g) {

    }

    public int getStep() {
        return step;
    }

    public void nextStep() {
        this.getQuestSteps().get(step).setFinished(true);
        this.step++;
    }

    public ArrayList<QuestStep> getQuestSteps() {
        return questSteps;
    }

    public void setQuestSteps(ArrayList<QuestStep> steps) {
        this.questSteps = steps;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public QuestState getState() {
        return state;
    }

    public void setState(QuestState state) {
        this.state = state;
        if (state == QuestState.COMPLETED) {
            Handler.get().playEffect("ui/quest_complete.wav", 0.1f);
            Handler.get().sendMsg("Completed '" + this.questName + "'!");
            Handler.get().addRecapEvent("Completed '" + this.questName + "'");
        }
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public QuestRequirement[] getRequirements() {
        return requirements;
    }

    public void setRequirements(QuestRequirement[] requirements) {
        this.requirements = requirements;
    }

}
