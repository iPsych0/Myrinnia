package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmingResource extends SkillResource {

    private Item seed;
    private int quantity;
    private long timePlanted;
    private long timeToGrow;
    private Item harvest;
    private int harvestQuantity;
    private int experience;

    public FarmingResource(int requirement, Item seed, int quantity, SkillCategory category, long minutesToGrow, Item harvest, int harvestQuantity, int experience, String description) {
        super(requirement, harvest, category, description);

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

    public FarmingResource(int requirement, Item seed, SkillCategory category, long minutesToGrow, Item harvest, int harvestQuantity, int experience, String description) {
        super(requirement, harvest, category, description);

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
}
