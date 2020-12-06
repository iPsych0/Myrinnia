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

        recipes.add(new CraftingRecipe(ItemStack.of(Item.simpleSword, 1), 1, 10, 30, false, SkillCategory.Weapons, ItemStack.of(Item.palmWood, 2), ItemStack.of(Item.azuriteOre, 5)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.simpleBow, 1), 1, 10, 30, false, SkillCategory.Weapons, ItemStack.of(Item.palmWood, 2), ItemStack.of(Item.azuriteOre, 5)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.simpleStaff, 1), 1, 10, 30, false, SkillCategory.Weapons, ItemStack.of(Item.palmWood, 2), ItemStack.of(Item.azuriteOre, 5)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.ironSword, 1), 5, 30, 30, true, SkillCategory.Weapons, ItemStack.of(Item.ironOre, 5), ItemStack.of(Item.hardWood, 2)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.hardwoodBow, 1), 5, 30, 30, true, SkillCategory.Weapons, ItemStack.of(Item.softLeather, 2), ItemStack.of(Item.hardWood, 5)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.hardwoodStaff, 1), 5, 30, 30, true, SkillCategory.Weapons, ItemStack.of(Item.ironOre, 2), ItemStack.of(Item.hardWood, 5)));

        recipes.add(new CraftingRecipe(ItemStack.of(Item.ironChainMail, 1), 8, 45, 35, true, SkillCategory.Armor, ItemStack.of(Item.ironOre, 8)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.studdedShield, 1), 5, 30, 35, true, SkillCategory.Armor, ItemStack.of(Item.ironOre, 2), ItemStack.of(Item.hardWood, 4)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.ironLegs, 1), 8, 45, 35, true, SkillCategory.Armor, ItemStack.of(Item.ironOre, 6)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.squiresCloak, 1), 7, 40, 35, true, SkillCategory.Armor, ItemStack.of(Item.softLeather, 5)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.ironHelm, 1), 7, 40, 35, true, SkillCategory.Armor, ItemStack.of(Item.ironOre, 4)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.ironBoots, 1), 6, 35, 35, true, SkillCategory.Armor, ItemStack.of(Item.ironOre, 3)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.ironGloves, 1), 6, 35, 35, true, SkillCategory.Armor, ItemStack.of(Item.ironOre, 3)));

        recipes.add(new CraftingRecipe(ItemStack.of(Item.softLeatherBody, 1), 8, 45, 35, true, SkillCategory.Armor, ItemStack.of(Item.softLeather, 8)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.ironQuiver, 1), 5, 30, 35, true, SkillCategory.Armor, ItemStack.of(Item.ironOre, 2), ItemStack.of(Item.softLeather, 4)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.softLeatherLeggings, 1), 8, 45, 35, true, SkillCategory.Armor, ItemStack.of(Item.softLeather, 6)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.scoutsCloak, 1), 7, 40, 35, true, SkillCategory.Armor, ItemStack.of(Item.softLeather, 5)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.softLeatherCowl, 1), 7, 40, 35, true, SkillCategory.Armor, ItemStack.of(Item.softLeather, 4)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.softLeatherBoots, 1), 6, 35, 35, true, SkillCategory.Armor, ItemStack.of(Item.softLeather, 3)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.softLeatherGloves, 1), 6, 35, 35, true, SkillCategory.Armor, ItemStack.of(Item.softLeather, 3)));

        recipes.add(new CraftingRecipe(ItemStack.of(Item.woolenRobeTop, 1), 8, 45, 35, true, SkillCategory.Armor, ItemStack.of(Item.stripOfWool, 8)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.leatherSpellbook, 1), 5, 30, 35, true, SkillCategory.Armor, ItemStack.of(Item.softLeather, 2), ItemStack.of(Item.stripOfWool, 4)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.woolenRobeBottom, 1), 8, 45, 35, true, SkillCategory.Armor, ItemStack.of(Item.stripOfWool, 6)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.apprenticesCloak, 1), 7, 40, 35, true, SkillCategory.Armor, ItemStack.of(Item.softLeather, 5)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.woolenHat, 1), 7, 40, 35, true, SkillCategory.Armor, ItemStack.of(Item.stripOfWool, 4)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.woolenBoots, 1), 6, 35, 35, true, SkillCategory.Armor, ItemStack.of(Item.stripOfWool, 3)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.woolenGloves, 1), 6, 35, 35, true, SkillCategory.Armor, ItemStack.of(Item.stripOfWool, 2)));

        recipes.add(new CraftingRecipe(ItemStack.of(Item.simpleAxe, 1), 1, 10, 30, true, SkillCategory.Tools, ItemStack.of(Item.palmWood, 2), ItemStack.of(Item.azuriteOre, 3)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.simplePickaxe, 1), 1, 10, 30, true, SkillCategory.Tools, ItemStack.of(Item.palmWood, 2), ItemStack.of(Item.azuriteOre, 3)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.simpleFishingRod, 1), 1, 10, 30, true, SkillCategory.Tools, ItemStack.of(Item.palmWood, 3), ItemStack.of(Item.azuriteOre, 2)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.copperAxe, 1), 2, 20, 30, true, SkillCategory.Tools, ItemStack.of(Item.lightwood, 2), ItemStack.of(Item.copperOre, 3)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.copperPickaxe, 1), 2, 20, 30, true, SkillCategory.Tools, ItemStack.of(Item.lightwood, 2), ItemStack.of(Item.copperOre, 3)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.copperFishingRod, 1), 2, 20, 30, true, SkillCategory.Tools, ItemStack.of(Item.lightwood, 3), ItemStack.of(Item.copperOre, 2)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.ironAxe, 1), 5, 30, 35, true, SkillCategory.Tools, ItemStack.of(Item.hardWood, 2), ItemStack.of(Item.ironOre, 3)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.ironPickaxe, 1), 5, 30, 35, true, SkillCategory.Tools, ItemStack.of(Item.hardWood, 2), ItemStack.of(Item.ironOre, 3)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.ironFishingRod, 1), 6, 30, 35, true, SkillCategory.Tools, ItemStack.of(Item.hardWood, 3), ItemStack.of(Item.ironOre, 2)));

        recipes.add(new CraftingRecipe(ItemStack.of(Item.azuriteEarrings, 1), 1, 10, 30, true, SkillCategory.Trinkets, ItemStack.of(Item.azuriteOre, 2), ItemStack.of(Item.lapisLazuli, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.azuriteRingL, 1), 2, 15, 30, true, SkillCategory.Trinkets, ItemStack.of(Item.azuriteOre, 3), ItemStack.of(Item.lapisLazuli, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.azuriteRingR, 1), 2, 15, 30, true, SkillCategory.Trinkets, ItemStack.of(Item.azuriteOre, 3), ItemStack.of(Item.lapisLazuli, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.azuriteNecklace, 1), 3, 20, 30, true, SkillCategory.Trinkets, ItemStack.of(Item.azuriteOre, 5), ItemStack.of(Item.lapisLazuli, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.malachiteEarrings, 1), 3, 20, 30, true, SkillCategory.Trinkets, ItemStack.of(Item.copperOre, 2), ItemStack.of(Item.malachite, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.malachiteRingL, 1), 4, 25, 30, true, SkillCategory.Trinkets, ItemStack.of(Item.copperOre, 3), ItemStack.of(Item.malachite, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.malachiteRingR, 1), 5, 25, 30, true, SkillCategory.Trinkets, ItemStack.of(Item.copperOre, 3), ItemStack.of(Item.malachite, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.malachiteAmulet, 1), 5, 30, 30, true, SkillCategory.Trinkets, ItemStack.of(Item.copperOre, 5), ItemStack.of(Item.malachite, 1)));

        recipes.add(new CraftingRecipe(ItemStack.of(Item.pileOfAshes, 2), 1, 2, 15, true, SkillCategory.Materials, ItemStack.of(Item.palmWood, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.pileOfAshes, 3), 1, 3, 15, true, SkillCategory.Materials, ItemStack.of(Item.lightwood, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.pileOfAshes, 4), 1, 4, 15, true, SkillCategory.Materials, ItemStack.of(Item.hardWood, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.pileOfAshes, 5), 1, 5, 15, true, SkillCategory.Materials, ItemStack.of(Item.aspenwood, 1)));

        recipes.add(new CraftingRecipe(ItemStack.of(Item.glass, 1), 1, 5, 15, true, SkillCategory.Materials, ItemStack.of(Item.pileOfAshes, 1), ItemStack.of(Item.pileOfSand, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.lightWoodPlank, 1), 1, 5, 10, true, SkillCategory.Materials, ItemStack.of(Item.lightwood, 2)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.hardWoodPlank, 1), 5, 10, 10, true, SkillCategory.Materials, ItemStack.of(Item.hardWood, 2)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.boneMeal, 1), 1, 5, 10, true, SkillCategory.Materials, ItemStack.of(Item.chitin, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.rope, 1), 1, 5, 15, true, SkillCategory.Materials, ItemStack.of(Item.wool, 2)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.stripOfWool, 1), 3, 8, 15, true, SkillCategory.Materials, ItemStack.of(Item.wool, 4)));

        recipes.add(new CraftingRecipe(ItemStack.of(Item.weakAntidote, 1), 1, 10, 10, true, SkillCategory.Potions, ItemStack.of(Item.glass, 1), ItemStack.of(Item.scorpionTail, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.weakPotionOfMight, 1), 1, 10, 10, true, SkillCategory.Potions, ItemStack.of(Item.glass, 1), ItemStack.of(Item.crablingClaw, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.weakPotionOfPrecision, 1), 1, 10, 10, true, SkillCategory.Potions, ItemStack.of(Item.glass, 1), ItemStack.of(Item.owlFeather, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.weakPotionOfWisdom, 1), 1, 10, 10, true, SkillCategory.Potions, ItemStack.of(Item.glass, 1), ItemStack.of(Item.azureBatWing, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.weakPotionOfVigor, 1), 1, 10, 10, true, SkillCategory.Potions, ItemStack.of(Item.glass, 1), ItemStack.of(Item.vineRoot, 1)));
        recipes.add(new CraftingRecipe(ItemStack.of(Item.weakPotionofFortitude, 1), 1, 10, 10, true, SkillCategory.Potions, ItemStack.of(Item.glass, 1), ItemStack.of(Item.rockyShell, 1)));

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
