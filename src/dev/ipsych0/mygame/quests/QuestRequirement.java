package dev.ipsych0.mygame.quests;

import dev.ipsych0.mygame.skills.Skill;

public class QuestRequirement {
	
	private String requirement;
	private Skill skill;
	private int level;
	private QuestList quest;
	
	public QuestRequirement(Skill skill, int level) {
		this.level = level;
		this.skill = skill;
		this.requirement = skill.toString() + " level " + level;
	}
	
	public QuestRequirement(QuestList quest) {
		this.quest = quest;
		this.requirement = "Complete "+quest.getName();
	}
	
	public QuestRequirement(String description) {
		this.requirement = description;
	}

	public String getRequirement() {
		return requirement;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
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
	
}
