package dev.ipsych0.mygame.quests;

import dev.ipsych0.mygame.skills.Skill;
import dev.ipsych0.mygame.skills.SkillsList;

public class QuestRequirement {
	
	private String requirement;
	private SkillsList skill;
	private int level;
	private QuestList quest;
	
	public QuestRequirement(SkillsList skill, int level) {
		this.level = level;
		this.skill = skill;
		String s = skill.toString().substring(0, 1).toUpperCase() + skill.toString().substring(1).toLowerCase(); 
		this.requirement =  s + " level " + level;
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
	
}
