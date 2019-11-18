package dev.ipsych0.myrinnia.quests;

import dev.ipsych0.myrinnia.worlds.data.Zone;

public enum QuestList {

    LearningTheRopes("Learning The Ropes", Zone.PortAzure);
//    TheSecondQuest("The Second Quest", Zone.PortAzure),
//    TheThirdQuest("The Third Quest", Zone.PortAzure),
//    TheTestQuest("The Test Quest", Zone.LakeAzure),
//    AMysteriousFinding("A Mysterious Finding", Zone.Myrinnia);

    private Zone zone;
    private String name;

    QuestList(String name, Zone zone) {
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
