package dev.ipsych0.myrinnia.quests;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.npcs.CelenorPorewit;
import dev.ipsych0.myrinnia.entities.npcs.PortAzureDuncan;
import dev.ipsych0.myrinnia.entities.statics.CelenorGrottoWater;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.*;

public class QuestManager implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4508062817810741935L;

    private QuestUI questUI;

    // Quest Lists per zone
    private List<Quest> azurealIslandQuests = new ArrayList<>();
    private List<Quest> shamrockTownQuests = new ArrayList<>();
    private List<Quest> celenorQuests = new ArrayList<>();
//    private ArrayList<Quest> testQuests = new ArrayList<>();
//    private ArrayList<Quest> mainQuests = new ArrayList<>();

    // Get Quests by Enum value
    private EnumMap<QuestList, Quest> questMap = new EnumMap<>(QuestList.class);

    // Put all Quest Lists per zone into a List of all Quest Lists
    private List<List<Quest>> allQuestLists = new ArrayList<>();

    // Map Zones to Quest Lists
    private EnumMap<Zone, List<Quest>> zoneMap = new EnumMap<>(Zone.class);

    public QuestManager() {
        questUI = new QuestUI();

        // Initializes all the lists of quests and zones
        initLists();

        // Fills the lists with Quest objects and sorts them alphabetically
        fillLists();

        // Maps zones to lists
        mapLists();

    }

    private void initLists() {
        // Filling allQuestLists with ALL lists of quests
        allQuestLists.add(azurealIslandQuests);
        allQuestLists.add(shamrockTownQuests);
        allQuestLists.add(celenorQuests);
    }

    private void fillLists() {

        // Island Quests
        azurealIslandQuests.add(new Quest(Zone.PortAzure, "gettingstarted.json",
                (OnCompletion & Serializable) () -> {
                    Handler.get().getSkill(SkillsList.BOUNTYHUNTER).addExperience(150);
                }));

        azurealIslandQuests.add(new Quest(Zone.PortAzure, "gatheringyourstuff.json", Arrays.asList(new QuestRequirement(QuestList.GettingStarted)),
                (OnCompletion & Serializable) () -> {
                    Handler.get().getSkill(SkillsList.WOODCUTTING).addExperience(50);
                    Handler.get().getSkill(SkillsList.FISHING).addExperience(50);
                }));
        azurealIslandQuests.add(new Quest(Zone.PortAzure, "preparingyourjourney.json", Arrays.asList(new QuestRequirement(QuestList.GatheringYourStuff)),
                (OnCompletion & Serializable) () -> {
                    PortAzureDuncan.unlockRecipes();
                    Handler.get().getSkill(SkillsList.CRAFTING).addExperience(50);
                    Handler.get().getSkill(SkillsList.MINING).addExperience(80);
                }));
        azurealIslandQuests.add(new Quest(Zone.PortAzure, "wavegoodbye.json", Arrays.asList(new QuestRequirement(QuestList.PreparingYourJourney)),
                (OnCompletion & Serializable) () -> {
                    Handler.get().getSkill(SkillsList.COMBAT).addExperience(100);
                }));

        shamrockTownQuests.add(new Quest(Zone.ShamrockTown, "wedelvedtoodeep.json", Arrays.asList(new QuestRequirement(SkillsList.MINING, 5), new QuestRequirement(SkillsList.COMBAT, 7)),
                (OnCompletion & Serializable) () -> {
                    Handler.get().giveItem(Item.dustyScroll, 1);
                    Handler.get().getSkill(SkillsList.COMBAT).addExperience(100);
                    Handler.get().getSkill(SkillsList.MINING).addExperience(150);
                }));

        celenorQuests.add(new Quest(Zone.Celewynn, "extrememist_beliefs.json", Arrays.asList(new QuestRequirement(SkillsList.COMBAT, 8)),
                (OnCompletion & Serializable) () -> {
                    Handler.get().getSkill(SkillsList.COMBAT).addExperience(400);
                    Handler.get().getSkill(SkillsList.BOUNTYHUNTER).addExperience(200);
                    CelenorGrottoWater.cleanse();
                    CelenorPorewit.removeFog();
                }));

        // Test Quests
//        testQuests.add(new Quest("The Test Quest", Zone.LakeAzure));

        //Main Quests
//        mainQuests.add(new Quest("A Mysterious Finding", Zone.Myrinnia, new QuestRequirement("Talk to the Ability Master to learn about the use of magic in Myrinnia.")));

        // Sorts every list's quests by name, alphabetically
        for (int i = 0; i < allQuestLists.size(); i++) {
            allQuestLists.get(i).sort(Comparator.comparing(Quest::getQuestName));
        }

        // Sort the enum list of quests alphabetically as well
        List<QuestList> questEnums = Arrays.asList(QuestList.values());
        questEnums.sort(Comparator.comparing(Enum::toString));

        // Maps the QuestList enums to the Quest objects
        for (int i = 0; i < allQuestLists.size(); i++) {
            for (int j = 0; j < allQuestLists.get(i).size(); j++) {
                try {
                    for (int k = 0; k < questEnums.size(); k++) {
                        if (questEnums.get(k).getName().equalsIgnoreCase(allQuestLists.get(i).get(j).getQuestName())) {
                            questMap.put(questEnums.get(k), allQuestLists.get(i).get(j));
                            break;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error mapping quests to enums. Please check if you have added the quest to 'QuestList.java'.");
                    System.exit(1);
                }
            }
        }
    }

    private void mapLists() {
        // Sort the Zone list by Zone Name
        List<Zone> zoneEnums = Arrays.asList(Zone.values());
        zoneEnums.sort(Comparator.comparing(Enum::toString));


        // Sort the allQuestLists by zone as well
        allQuestLists.sort(Comparator.comparing(o -> o.get(0).getZone().toString()));

        // Mapping the Zones together with the correct list of quests
        for (int i = 0; i < allQuestLists.size(); i++) {
            for (int j = 0; j < zoneEnums.size(); j++) {
                // Only put in the map Zones that we actually have in our 'all' quests list
                if (allQuestLists.get(i).get(0).getZone().toString().equalsIgnoreCase(zoneEnums.get(j).toString())) {
                    zoneMap.put(zoneEnums.get(j), allQuestLists.get(i));
                    break;
                }
            }
        }
    }

    public void tick() {
        if (QuestUI.isOpen)
            questUI.tick();
    }

    public void render(Graphics2D g) {
        if (QuestUI.isOpen)
            questUI.render(g);
    }

    public EnumMap<QuestList, Quest> getQuestMap() {
        return questMap;
    }

    public void setQuestMap(EnumMap<QuestList, Quest> questMap) {
        this.questMap = questMap;
    }

    public QuestUI getQuestUI() {
        return questUI;
    }

    public void setQuestUI(QuestUI questUI) {
        this.questUI = questUI;
    }

    public List<List<Quest>> getAllQuestLists() {
        return allQuestLists;
    }

    public void setAllQuestLists(List<List<Quest>> allQuestLists) {
        this.allQuestLists = allQuestLists;
    }

    public EnumMap<Zone, List<Quest>> getZoneMap() {
        return zoneMap;
    }

    public void setZoneMap(EnumMap<Zone, List<Quest>> zoneMap) {
        this.zoneMap = zoneMap;
    }


}
