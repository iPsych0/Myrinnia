package dev.ipsych0.myrinnia.quests;

import dev.ipsych0.myrinnia.worlds.Zone;
import lombok.Getter;

@Getter
public enum QuestList {

    GettingStarted("Getting Started", Zone.PortAzure),
    GatheringYourStuff("Gathering Your Stuff", Zone.PortAzure),
    PreparingYourJourney("Preparing Your Journey", Zone.PortAzure),
    WaveGoodbye("Wave Goodbye", Zone.PortAzure),
    WeDelvedTooDeep("We Delved Too Deep", Zone.ShamrockTown),
    ExtrememistBeliefs("Extrememist Beliefs", Zone.Celewynn);

    private final Zone zone;
    private final String name;

    QuestList(String name, Zone zone) {
        this.zone = zone;
        this.name = name;
    }



}
