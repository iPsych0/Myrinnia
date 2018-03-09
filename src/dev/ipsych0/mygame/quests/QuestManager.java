package dev.ipsych0.mygame.quests;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;

public class QuestManager{
	
	private Handler handler;
	private ArrayList<Quest> questList;
	private QuestUI questUI;
	private HashMap<QuestList, Quest> questMap = new HashMap<>();
	
	public QuestManager(Handler handler) {
		this.handler = handler;
		questUI = new QuestUI(handler);
		questList = new ArrayList<Quest>();
		
		questList.add(new Quest(handler, "The First Quest"));
		questList.add(new Quest(handler, "The Second Quest"));
		questList.add(new Quest(handler, "The Third Quest"));
		
		// Sort the quest list alphabetically
		Collections.sort(questList, new Comparator<Quest>() {
			@Override
			public int compare(Quest o1, Quest o2) {
				// TODO Auto-generated method stub
				return o1.getQuestName().compareTo(o2.getQuestName());
			}
		});
		
		// Sort the enum list alphabetically as well
		List<QuestList> questEnums = Arrays.asList(QuestList.values());
		Collections.sort(questEnums, new Comparator<QuestList>() {
			@Override
			public int compare(QuestList o1, QuestList o2) {
				// TODO Auto-generated method stub
				return o1.toString().compareTo(o2.toString());
			}
		});
		
		for(int i = 0; i < questList.size(); i++) {
			questMap.put(questEnums.get(i), questList.get(i));
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
