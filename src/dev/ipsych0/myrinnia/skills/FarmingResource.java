package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

public class FarmingResource extends SkillResource{

    private Item seed;
    private int quantity;
    private long timePlanted;
    private long timeToGrow;
    private Item harvest;
    private int harvestQuantity;
    private int experience;

    public FarmingResource(int requirement, Item seed, int quantity, SkillCategory category, long minutesToGrow, Item harvest, int harvestQuantity, int experience) {
        super(requirement, harvest, category);

        if (!seed.isType(ItemType.SEED)) {
            throw new IllegalArgumentException("Farming resources must use seeds. '" + seed.getName() + "' is not a seed.");
        }

        this.seed = seed;
        this.quantity = quantity;
        this.timeToGrow = minutesToGrow * 60L;
        this.harvest = harvest;
        this.harvestQuantity = harvestQuantity;
        this.experience = experience;
    }

    public FarmingResource(int requirement, Item seed, SkillCategory category, long minutesToGrow, Item harvest, int harvestQuantity, int experience) {
        super(requirement, harvest, category);

        if (!seed.isType(ItemType.SEED)) {
            throw new IllegalArgumentException("Farming resources must use seeds. '" + seed.getName() + "' is not a seed.");
        }

        this.quantity = 1;
        this.seed = seed;
        this.timeToGrow = minutesToGrow * 60L;
        this.harvest = harvest;
        this.harvestQuantity = harvestQuantity;
        this.experience = experience;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getHarvestQuantity() {
        return harvestQuantity;
    }

    public void setHarvestQuantity(int harvestQuantity) {
        this.harvestQuantity = harvestQuantity;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
