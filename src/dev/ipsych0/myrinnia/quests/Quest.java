package dev.ipsych0.myrinnia.quests;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.ui.Celebration;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Quest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3216607526116365186L;
    private ArrayList<QuestStep> questSteps;
    private int step = 0;
    private String questName;
    private String questStart;
    private QuestState state;
    private QuestRequirement[] requirements;
    private Map<String, Object> customChecks;

    private Zone zone;

    public Quest(String questName, Zone zone, String questStart) {
        this.questName = questName;
        this.zone = zone;
        this.questStart = questStart;

        customChecks = new HashMap<>();
        questSteps = new ArrayList<>();
        state = QuestState.NOT_STARTED;
    }

    public Quest(String questName, Zone zone, String questStart, QuestRequirement... questRequirements) {
        this.questName = questName;
        this.zone = zone;
        this.questStart = questStart;
        this.requirements = questRequirements;

        customChecks = new HashMap<>();
        questSteps = new ArrayList<>();
        state = QuestState.NOT_STARTED;
    }

    public void tick() {

    }

    public void render(Graphics2D g) {

    }

    public int getStep() {
        return step;
    }

    public void nextStep() {
        this.getQuestSteps().get(step).setFinished(true);
        this.step++;
    }

    public void addStep(String objective) {
        questSteps.add(new QuestStep(objective));
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
            Handler.get().playEffect("ui/quest_complete.ogg", 0.1f);
            Handler.get().sendMsg("Completed '" + this.questName + "'!");
            Handler.get().addRecapEvent("Completed '" + this.questName + "'");
            Handler.get().getCelebrationUI().addEvent(new Celebration(this, "You have completed '" + getQuestName() + "'!"));
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

    public Map<String, Object> getCustomChecks() {
        return customChecks;
    }

    public void addNewCheck(String key, Object o) {
        key = key.toLowerCase();
        customChecks.put(key, o);
    }

    public Object getCheckValue(String key) {
        key = key.toLowerCase();
        if (!customChecks.containsKey(key)) {
            throw new IllegalArgumentException("Key '" + key + "' does not exist. Please use Quest::addNewCheck method to add new keys.");
        }
        return customChecks.get(key);
    }

    public String getQuestStart() {
        return questStart;
    }

    public void setQuestStart(String questStart) {
        this.questStart = questStart;
    }
}
