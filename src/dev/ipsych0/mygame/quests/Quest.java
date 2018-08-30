package dev.ipsych0.mygame.quests;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.worlds.Zone;

public class Quest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<QuestStep> questSteps;
	private int step = 0;
	private String questName;
	private QuestState state;
	private QuestRequirement[] requirements;
	
	private Zone zone;
	
	public enum QuestState{
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
		if(state == QuestState.COMPLETED) {
			Handler.get().sendMsg("Completed '" + this.questName + "'!");
			Handler.get().addRecapEvent("Completed '" + this.questName + "'");
			questSteps.clear();
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
