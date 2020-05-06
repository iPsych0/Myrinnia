package dev.ipsych0.myrinnia.crafting;

import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CraftingManager implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 5224910817694774668L;
    private List<CraftingRecipe> recipes;

    /*
     * Puts all recipes in the list
     */
    public CraftingManager() {

        recipes = new ArrayList<>();

        recipes.add(new CraftingRecipe(1, 10, false, SkillCategory.Weapons, new ItemStack(Item.palmWood, 2), new ItemStack(Item.azuriteOre, 5), new ItemStack(Item.simpleSword, 1)));
        recipes.add(new CraftingRecipe(1, 10, false, SkillCategory.Weapons, new ItemStack(Item.palmWood, 2), new ItemStack(Item.azuriteOre, 5), new ItemStack(Item.simpleBow, 1)));
        recipes.add(new CraftingRecipe(1, 10, false, SkillCategory.Weapons, new ItemStack(Item.palmWood, 2), new ItemStack(Item.azuriteOre, 5), new ItemStack(Item.simpleStaff, 1)));
        recipes.add(new CraftingRecipe(5, 30, true, SkillCategory.Weapons, new ItemStack(Item.ironOre, 5), new ItemStack(Item.hardWood, 2), new ItemStack(Item.ironSword, 1)));
        recipes.add(new CraftingRecipe(5, 30, true, SkillCategory.Weapons, new ItemStack(Item.softLeather, 2), new ItemStack(Item.hardWood, 5), new ItemStack(Item.hardwoodBow, 1)));
        recipes.add(new CraftingRecipe(5, 30, true, SkillCategory.Weapons, new ItemStack(Item.ironOre, 2), new ItemStack(Item.hardWood, 5), new ItemStack(Item.hardwoodStaff, 1)));

        recipes.add(new CraftingRecipe(8, 45, true, SkillCategory.Armor, new ItemStack(Item.ironOre, 8), new ItemStack(Item.ironChainMail, 1)));
        recipes.add(new CraftingRecipe(5, 30, true, SkillCategory.Armor, new ItemStack(Item.ironOre, 2), new ItemStack(Item.hardWood, 4), new ItemStack(Item.studdedShield, 1)));
        recipes.add(new CraftingRecipe(8, 45, true, SkillCategory.Armor, new ItemStack(Item.ironOre, 6), new ItemStack(Item.ironLegs, 1)));
        recipes.add(new CraftingRecipe(7, 40, true, SkillCategory.Armor, new ItemStack(Item.softLeather, 5), new ItemStack(Item.squiresCloak, 1)));
        recipes.add(new CraftingRecipe(7, 40, true, SkillCategory.Armor, new ItemStack(Item.ironOre, 4), new ItemStack(Item.ironHelm, 1)));
        recipes.add(new CraftingRecipe(6, 35, true, SkillCategory.Armor, new ItemStack(Item.ironOre, 3), new ItemStack(Item.ironBoots, 1)));
        recipes.add(new CraftingRecipe(6, 35, true, SkillCategory.Armor, new ItemStack(Item.ironOre, 3), new ItemStack(Item.ironGloves, 1)));

        recipes.add(new CraftingRecipe(8, 45, true, SkillCategory.Armor, new ItemStack(Item.softLeather, 8), new ItemStack(Item.softLeatherBody, 1)));
        recipes.add(new CraftingRecipe(5, 30, true, SkillCategory.Armor, new ItemStack(Item.ironOre, 2), new ItemStack(Item.softLeather, 4), new ItemStack(Item.ironQuiver, 1)));
        recipes.add(new CraftingRecipe(8, 45, true, SkillCategory.Armor, new ItemStack(Item.softLeather, 6), new ItemStack(Item.softLeatherLeggings, 1)));
        recipes.add(new CraftingRecipe(7, 40, true, SkillCategory.Armor, new ItemStack(Item.softLeather, 5), new ItemStack(Item.scoutsCloak, 1)));
        recipes.add(new CraftingRecipe(7, 40, true, SkillCategory.Armor, new ItemStack(Item.softLeather, 4), new ItemStack(Item.softLeatherCowl, 1)));
        recipes.add(new CraftingRecipe(6, 35, true, SkillCategory.Armor, new ItemStack(Item.softLeather, 3), new ItemStack(Item.softLeatherBoots, 1)));
        recipes.add(new CraftingRecipe(6, 35, true, SkillCategory.Armor, new ItemStack(Item.softLeather, 3), new ItemStack(Item.softLeatherGloves, 1)));

        recipes.add(new CraftingRecipe(8, 45, true, SkillCategory.Armor, new ItemStack(Item.stripOfCloth, 8), new ItemStack(Item.woolenRobeTop, 1)));
        recipes.add(new CraftingRecipe(5, 30, true, SkillCategory.Armor, new ItemStack(Item.softLeather, 2), new ItemStack(Item.stripOfCloth, 4), new ItemStack(Item.leatherSpellbook, 1)));
        recipes.add(new CraftingRecipe(8, 45, true, SkillCategory.Armor, new ItemStack(Item.stripOfCloth, 6), new ItemStack(Item.woolenRobeBottom, 1)));
        recipes.add(new CraftingRecipe(7, 40, true, SkillCategory.Armor, new ItemStack(Item.softLeather, 5), new ItemStack(Item.apprenticesCloak, 1)));
        recipes.add(new CraftingRecipe(7, 40, true, SkillCategory.Armor, new ItemStack(Item.stripOfCloth, 4), new ItemStack(Item.woolenHat, 1)));
        recipes.add(new CraftingRecipe(6, 35, true, SkillCategory.Armor, new ItemStack(Item.stripOfCloth, 3), new ItemStack(Item.woolenBoots, 1)));
        recipes.add(new CraftingRecipe(6, 35, true, SkillCategory.Armor, new ItemStack(Item.stripOfCloth, 2), new ItemStack(Item.woolenGloves, 1)));

        recipes.add(new CraftingRecipe(1, 10, true, SkillCategory.Tools, new ItemStack(Item.palmWood, 2), new ItemStack(Item.azuriteOre, 3), new ItemStack(Item.simpleAxe, 1)));
        recipes.add(new CraftingRecipe(1, 10, true, SkillCategory.Tools, new ItemStack(Item.palmWood, 2), new ItemStack(Item.azuriteOre, 3), new ItemStack(Item.simplePickaxe, 1)));
        recipes.add(new CraftingRecipe(1, 10, true, SkillCategory.Tools, new ItemStack(Item.palmWood, 3), new ItemStack(Item.azuriteOre, 2), new ItemStack(Item.simpleFishingRod, 1)));
        recipes.add(new CraftingRecipe(2, 20, true, SkillCategory.Tools, new ItemStack(Item.lightwood, 2), new ItemStack(Item.copperOre, 3), new ItemStack(Item.copperAxe, 1)));
        recipes.add(new CraftingRecipe(2, 20, true, SkillCategory.Tools, new ItemStack(Item.lightwood, 2), new ItemStack(Item.copperOre, 3), new ItemStack(Item.copperPickaxe, 1)));
        recipes.add(new CraftingRecipe(2, 20, true, SkillCategory.Tools, new ItemStack(Item.lightwood, 3), new ItemStack(Item.copperOre, 2), new ItemStack(Item.copperFishingRod, 1)));
        recipes.add(new CraftingRecipe(5, 30, true, SkillCategory.Tools, new ItemStack(Item.hardWood, 2), new ItemStack(Item.ironOre, 3), new ItemStack(Item.ironAxe, 1)));
        recipes.add(new CraftingRecipe(5, 30, true, SkillCategory.Tools, new ItemStack(Item.hardWood, 2), new ItemStack(Item.ironOre, 3), new ItemStack(Item.ironPickaxe, 1)));
        recipes.add(new CraftingRecipe(6, 30, true, SkillCategory.Tools, new ItemStack(Item.hardWood, 3), new ItemStack(Item.ironOre, 2), new ItemStack(Item.ironFishingRod, 1)));

        recipes.add(new CraftingRecipe(1, 10, true, SkillCategory.Trinkets, new ItemStack(Item.azuriteOre, 2), new ItemStack(Item.lapisLazuli, 1), new ItemStack(Item.azuriteEarrings, 1)));
        recipes.add(new CraftingRecipe(2, 15, true, SkillCategory.Trinkets, new ItemStack(Item.azuriteOre, 3), new ItemStack(Item.lapisLazuli, 1), new ItemStack(Item.azuriteRingL, 1)));
        recipes.add(new CraftingRecipe(2, 15, true, SkillCategory.Trinkets, new ItemStack(Item.azuriteOre, 3), new ItemStack(Item.lapisLazuli, 1), new ItemStack(Item.azuriteRingR, 1)));
        recipes.add(new CraftingRecipe(3, 20, true, SkillCategory.Trinkets, new ItemStack(Item.azuriteOre, 5), new ItemStack(Item.lapisLazuli, 1), new ItemStack(Item.azuriteNecklace, 1)));
        recipes.add(new CraftingRecipe(3, 20, true, SkillCategory.Trinkets, new ItemStack(Item.copperOre, 2), new ItemStack(Item.malachite, 1), new ItemStack(Item.malachiteEarrings, 1)));
        recipes.add(new CraftingRecipe(4, 25, true, SkillCategory.Trinkets, new ItemStack(Item.copperOre, 3), new ItemStack(Item.malachite, 1), new ItemStack(Item.malachiteRingL, 1)));
        recipes.add(new CraftingRecipe(5, 25, true, SkillCategory.Trinkets, new ItemStack(Item.copperOre, 3), new ItemStack(Item.malachite, 1), new ItemStack(Item.malachiteRingR, 1)));
        recipes.add(new CraftingRecipe(5, 30, true, SkillCategory.Trinkets, new ItemStack(Item.copperOre, 5), new ItemStack(Item.malachite, 1), new ItemStack(Item.malachiteAmulet, 1)));

        recipes.add(new CraftingRecipe(1, 2, true, SkillCategory.Materials, new ItemStack(Item.palmWood, 1), new ItemStack(Item.pileOfAshes, 2)));
        recipes.add(new CraftingRecipe(1, 3, true, SkillCategory.Materials, new ItemStack(Item.lightwood, 1), new ItemStack(Item.pileOfAshes, 3)));
        recipes.add(new CraftingRecipe(1, 4, true, SkillCategory.Materials, new ItemStack(Item.hardWood, 1), new ItemStack(Item.pileOfAshes, 4)));
        recipes.add(new CraftingRecipe(1, 5, true, SkillCategory.Materials, new ItemStack(Item.aspenwood, 1), new ItemStack(Item.pileOfAshes, 5)));

        recipes.add(new CraftingRecipe(1, 5, true, SkillCategory.Materials, new ItemStack(Item.pileOfAshes, 1), new ItemStack(Item.pileOfSand, 1), new ItemStack(Item.glass, 1)));
        recipes.add(new CraftingRecipe(1, 5, true, SkillCategory.Materials, new ItemStack(Item.lightwood, 2), new ItemStack(Item.lightWoodPlank, 1)));
        recipes.add(new CraftingRecipe(5, 10, true, SkillCategory.Materials, new ItemStack(Item.hardWood, 2), new ItemStack(Item.hardWoodPlank, 1)));
        recipes.add(new CraftingRecipe(1, 5, true, SkillCategory.Materials, new ItemStack(Item.chitin, 1), new ItemStack(Item.boneMeal, 1)));
        recipes.add(new CraftingRecipe(1, 5, true, SkillCategory.Materials, new ItemStack(Item.wool, 2), new ItemStack(Item.rope, 1)));
        recipes.add(new CraftingRecipe(3, 8, true, SkillCategory.Materials, new ItemStack(Item.wool, 4), new ItemStack(Item.stripOfCloth, 1)));

        recipes.add(new CraftingRecipe(1, 10, true, SkillCategory.Potions, new ItemStack(Item.glass, 1), new ItemStack(Item.scorpionTail, 1), new ItemStack(Item.weakAntidote, 1)));
        recipes.add(new CraftingRecipe(1, 10, true, SkillCategory.Potions, new ItemStack(Item.glass, 1), new ItemStack(Item.crablingClaw, 1), new ItemStack(Item.weakPotionOfMight, 1)));
        recipes.add(new CraftingRecipe(1, 10, true, SkillCategory.Potions, new ItemStack(Item.glass, 1), new ItemStack(Item.owlFeather, 1), new ItemStack(Item.weakPotionOfPrecision, 1)));
        recipes.add(new CraftingRecipe(1, 10, true, SkillCategory.Potions, new ItemStack(Item.glass, 1), new ItemStack(Item.azureBatWing, 1), new ItemStack(Item.weakPotionOfWisdom, 1)));
        recipes.add(new CraftingRecipe(1, 10, true, SkillCategory.Potions, new ItemStack(Item.glass, 1), new ItemStack(Item.vineRoot, 1), new ItemStack(Item.weakPotionOfVigor, 1)));
        recipes.add(new CraftingRecipe(1, 10, true, SkillCategory.Potions, new ItemStack(Item.glass, 1), new ItemStack(Item.rockyShell, 1), new ItemStack(Item.weakPotionofFortitude, 1)));

        recipes.sort((o1, o2) -> {
            Integer a = o1.getRequiredLevel();
            Integer b = o2.getRequiredLevel();
            return a.compareTo(b);
        });

    }

    /**
     * Returns a Recipe by the given Item input
     *
     * @param item - recipe result item to parse for
     * @return - recipe / null if not found
     */
    public CraftingRecipe getRecipeByItem(Item item) {
        return recipes
                .stream()
                .filter(x -> x.getResult().getItem().getId() == item.getId())
                .findAny()
                .orElse(null);
    }

    /**
     * Returns a sub-list of the overall list by splitting it by category
     *
     * @param category - the parameter to return a list by
     * @return - returns a sublist of the overall list, split by category / null if category not found
     */
    public List<CraftingRecipe> getListByCategory(SkillCategory category) {
        return recipes
                .stream()
                .filter(x -> x.getCategory() == category)
                .collect(Collectors.toList());
    }

    public List<CraftingRecipe> getRecipes() {
        return recipes;
    }
}
