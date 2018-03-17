package dev.ipsych0.mygame.quests;

import dev.ipsych0.mygame.worlds.Zone;

public enum QuestList {
	
	TheFirstQuest(Zone.Island), TheSecondQuest(Zone.Island), TheThirdQuest(Zone.Island), TheTestQuest(Zone.Mainland);
	
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
