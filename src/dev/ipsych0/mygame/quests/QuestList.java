package dev.ipsych0.mygame.quests;

import java.io.Serializable;

import dev.ipsych0.mygame.worlds.Zone;

public enum QuestList implements Serializable{
	
	TheFirstQuest(Zone.Island), TheSecondQuest(Zone.Island), TheThirdQuest(Zone.Island), TheTestQuest(Zone.IslandUnderground);
	
	private Zone zone;
	
	QuestList(Zone zone){
		this.zone = zone;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	

}
