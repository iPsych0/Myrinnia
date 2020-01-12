package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

public class FarmingResource extends SkillResource{

    private int levelRequirement;
    private Item seed;
    private long timePlanted;
    private long timeToGrow = 60L * 60L;
    private Item harvest;
    private boolean composted;
    private boolean watered;

    public FarmingResource(int requirement, Item seed, SkillCategory category, long timeToGrow, Item harvest) {
        super(requirement, harvest, category);

        if (!seed.isType(ItemType.SEED)) {
            throw new IllegalArgumentException("Farming resources must use seeds. '" + seed.getName() + "' is not a seed.");
        }
        this.seed = seed;
        this.timeToGrow = timeToGrow;
        this.harvest = harvest;
    }

    public Item getSeed() {
        return seed;
    }

    public void setSeed(Item seed) {
        this.seed = seed;
    }

    public long getTimePlanted() {
        return timePlanted;
    }

    public void setTimePlanted(long timePlanted) {
        this.timePlanted = timePlanted;
    }

    public long getTimeToGrow() {
        return timeToGrow;
    }

    public void setTimeToGrow(long timeToGrow) {
        this.timeToGrow = timeToGrow;
    }

    public Item getHarvest() {
        return harvest;
    }

    public void setHarvest(Item harvest) {
        this.harvest = harvest;
    }

    public boolean isComposted() {
        return composted;
    }

    public void setComposted(boolean composted) {
        this.composted = composted;
    }

    public boolean isWatered() {
        return watered;
    }

    public void setWatered(boolean watered) {
        this.watered = watered;
    }
}
