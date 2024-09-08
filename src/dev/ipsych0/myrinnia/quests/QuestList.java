package dev.ipsych0.myrinnia.quests;

import dev.ipsych0.myrinnia.worlds.Zone;

public enum QuestList {

    GettingStarted("Getting Started", Zone.PortAzure),
    GatheringYourStuff("Gathering Your Stuff", Zone.PortAzure),
    PreparingYourJourney("Preparing Your Journey", Zone.PortAzure),
    WaveGoodbye("Wave Goodbye", Zone.PortAzure),
    WeDelvedTooDeep("We Delved Too Deep", Zone.ShamrockTown),
    ExtrememistBeliefs("Extrememist Beliefs", Zone.Celewynn);

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
