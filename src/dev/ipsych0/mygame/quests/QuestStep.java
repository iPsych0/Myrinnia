package dev.ipsych0.mygame.quests;

import java.io.Serializable;

public class QuestStep implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String objective;
	
	public QuestStep(String objective) {
		this.objective = objective;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

}
