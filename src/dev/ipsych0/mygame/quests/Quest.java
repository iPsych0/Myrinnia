package dev.ipsych0.mygame.quests;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;

public class Quest {
	
	protected Handler handler;
	protected ArrayList<QuestStep> questSteps;
	protected int step = 0;
	private String questName;
	private QuestState state;
	
	public enum QuestState{
		NOT_STARTED, IN_PROGRESS, COMPLETED
	}
	
	public Quest(Handler handler, String questName) {
		this.handler = handler;
		this.questName = questName;
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
			handler.sendMsg("Completed '" + this.questName + "'!");
			questSteps.clear();
		}
	}

}
