package dev.ipsych0.myrinnia.quests;

import dev.ipsych0.myrinnia.skills.SkillsList;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class QuestRequirement implements Serializable {

    private static final long serialVersionUID = 5167302310948105649L;
    private String requirement;
    private SkillsList skill;
    private int level;
    private QuestList quest;
    private String description;
    private boolean taskDone;
    private boolean mandatory;

    public QuestRequirement(SkillsList skill, int level) {
        this.level = level;
        this.skill = skill;
        String s = skill.toString().substring(0, 1).toUpperCase() + skill.toString().substring(1).toLowerCase();
        this.requirement = s + " level " + level;
    }

    public QuestRequirement(QuestList quest) {
        this.quest = quest;
        this.requirement = "Complete " + quest.getName();
    }

    public QuestRequirement(String description) {
        this(description, true);
    }

    public QuestRequirement(String description, boolean mandatory) {
        this.mandatory = mandatory;
        this.requirement = description;
        this.description = description;
        // If it's mandatory, task = not done, if it isn't mandatory, task = done.
        this.taskDone = !mandatory;
    }
}
