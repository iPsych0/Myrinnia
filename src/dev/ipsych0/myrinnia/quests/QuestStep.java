package dev.ipsych0.myrinnia.quests;

import java.io.Serializable;

public class QuestStep implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7195660759638554120L;
	private String objective;
	private boolean finished;
	
	public QuestStep(String objective) {
		this.objective = objective;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}
