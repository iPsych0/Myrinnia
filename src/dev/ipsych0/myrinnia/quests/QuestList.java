package dev.ipsych0.myrinnia.quests;

import dev.ipsych0.myrinnia.worlds.Zone;

public enum QuestList {
	
	TheFirstQuest("The First Quest", Zone.Island),
	TheSecondQuest("The Second Quest", Zone.Island),
	TheThirdQuest("The Third Quest", Zone.Island),
	TheTestQuest("The Test Quest", Zone.IslandUnderground);
	
	private Zone zone;
	private String name;
	
	QuestList(String name, Zone zone){
		this.zone = zone;
		this.name = name;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}