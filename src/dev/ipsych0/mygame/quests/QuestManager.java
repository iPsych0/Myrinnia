package dev.ipsych0.mygame.quests;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;

public class QuestManager {
	
	private Handler handler;
	private ArrayList<Quest> questList;
	
	public QuestManager(Handler handler) {
		this.handler = handler;
		questList = new ArrayList<Quest>();
		
	}
	
	public void tick() {
		for(Quest q : questList) {
			q.tick();
		}
	}
	
	public void render(Graphics g) {
		for(Quest q : questList) {
			q.render(g);
		}
	}

	public ArrayList<Quest> getQuestList() {
		return questList;
	}

	public void setQuestList(ArrayList<Quest> questList) {
		this.questList = questList;
	}
	
	

}
