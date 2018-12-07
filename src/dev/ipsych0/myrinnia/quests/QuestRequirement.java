package dev.ipsych0.myrinnia.quests;

import dev.ipsych0.myrinnia.skills.SkillsList;

import java.io.Serializable;

public class QuestRequirement implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5167302310948105649L;
    private String requirement;
    private SkillsList skill;
    private int level;
    private QuestList quest;
    private String description;
    private boolean taskDone;

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
        this.requirement = description;
        this.description = description;
        this.taskDone = false;
    }

    public String getRequirement() {
        return requirement;
    }

    public SkillsList getSkill() {
        return skill;
    }

    public void setSkill(SkillsList skill) {
        this.skill = skill;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public QuestList getQuest() {
        return quest;
    }

    public void setQuest(QuestList quest) {
        this.quest = quest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTaskDone() {
        return taskDone;
    }

    public void setTaskDone(boolean taskDone) {
        this.taskDone = taskDone;
    }
}
