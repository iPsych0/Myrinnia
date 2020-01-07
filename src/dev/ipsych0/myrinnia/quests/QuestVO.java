package dev.ipsych0.myrinnia.quests;

import java.io.Serializable;
import java.util.List;

public class QuestVO implements Serializable {

    private static final long serialVersionUID = -6320073288953863389L;
    private String questName;
    private String questStart;
    private List<String> objectives;

    public QuestVO(String questName, String questStart, List<String> objectives) {
        this.questName = questName;
        this.questStart = questStart;
        this.objectives = objectives;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public String getQuestStart() {
        return questStart;
    }

    public void setQuestStart(String questStart) {
        this.questStart = questStart;
    }

    public List<String> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<String> objectives) {
        this.objectives = objectives;
    }
}
