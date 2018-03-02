package dev.ipsych0.mygame.quests;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import dev.ipsych0.mygame.Handler;

public class QuestManager {
	
	private Handler handler;
	private ArrayList<Quest> questList;
	private QuestUI questUI;
	private HashMap<QuestList, Quest> questMap = new HashMap<>();
	
	public QuestManager(Handler handler) {
		this.handler = handler;
		questUI = new QuestUI(handler);
		questList = new ArrayList<Quest>();
		questList.add(new Quest(handler, "Tutorial Quest!"));
		questList.add(new Quest(handler, "Test 2"));
		questList.add(new Quest(handler, "This one should become yellow!"));
		
		for(int i = 0; i < questList.size(); i++) {
			questMap.put(QuestList.values()[i], questList.get(i));
		}
		
		
	}
	
	public void tick() {
		if(QuestUI.isOpen)
			questUI.tick();
		
		for(Quest q : questList) {
			q.tick();
		}
	}
	
	public void render(Graphics g) {
		if(QuestUI.isOpen)
			questUI.render(g);
		
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

	public HashMap<QuestList, Quest> getQuestMap() {
		return questMap;
	}

	public void setQuestMap(HashMap<QuestList, Quest> questMap) {
		this.questMap = questMap;
	}
	
	

}
