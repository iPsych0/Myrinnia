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

        recipes.add(new CraftingRecipe(1, 25, true, SkillCategory.CraftingOther, new ItemStack(Item.regularLogs, 5), new ItemStack(Item.regularOre, 5), new ItemStack(Item.testSword, 1)));
        recipes.add(new CraftingRecipe(1, 50, false, SkillCategory.CraftingOther, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularOre, 2), new ItemStack(Item.regularLogs, 5), new ItemStack(Item.coins, 1), new ItemStack(Item.purpleSword, 1)));
        recipes.add(new CraftingRecipe(2, 100, false, SkillCategory.CraftingOther, new ItemStack(Item.coins, 1), new ItemStack(Item.testSword, 1)));
        recipes.add(new CraftingRecipe(3, 5, false, SkillCategory.Leatherwork, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularLogs, 1)));
        recipes.add(new CraftingRecipe(3, 5, false, SkillCategory.Leatherwork, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularFish, 1), new ItemStack(Item.coins, 1), new ItemStack(Item.regularLogs, 1)));
recipes.add(new CraftingRecipe(1, 25, true, SkillCategory.CraftingOther, new ItemStack(Item.regularLogs, 5), new ItemStack(Item.regularOre, 5), new ItemStack(Item.testSword, 1)));
        recipes.add(new CraftingRecipe(1, 50, false, SkillCategory.CraftingOther, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularOre, 2), new ItemStack(Item.regularLogs, 5), new ItemStack(Item.coins, 1), new ItemStack(Item.purpleSword, 1)));
        recipes.add(new CraftingRecipe(2, 100, false, SkillCategory.CraftingOther, new ItemStack(Item.coins, 1), new ItemStack(Item.testSword, 1)));
        recipes.add(new CraftingRecipe(3, 5, false, SkillCategory.Leatherwork, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularLogs, 1)));
        recipes.add(new CraftingRecipe(3, 5, false, SkillCategory.Leatherwork, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularFish, 1), new ItemStack(Item.coins, 1), new ItemStack(Item.regularLogs, 1)));
recipes.add(new CraftingRecipe(1, 25, true, SkillCategory.CraftingOther, new ItemStack(Item.regularLogs, 5), new ItemStack(Item.regularOre, 5), new ItemStack(Item.testSword, 1)));
        recipes.add(new CraftingRecipe(1, 50, false, SkillCategory.CraftingOther, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularOre, 2), new ItemStack(Item.regularLogs, 5), new ItemStack(Item.coins, 1), new ItemStack(Item.purpleSword, 1)));
        recipes.add(new CraftingRecipe(2, 100, false, SkillCategory.CraftingOther, new ItemStack(Item.coins, 1), new ItemStack(Item.testSword, 1)));
        recipes.add(new CraftingRecipe(3, 5, false, SkillCategory.Leatherwork, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularLogs, 1)));
        recipes.add(new CraftingRecipe(3, 5, false, SkillCategory.Leatherwork, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularFish, 1), new ItemStack(Item.coins, 1), new ItemStack(Item.regularLogs, 1)));
recipes.add(new CraftingRecipe(1, 25, true, SkillCategory.CraftingOther, new ItemStack(Item.regularLogs, 5), new ItemStack(Item.regularOre, 5), new ItemStack(Item.testSword, 1)));
        recipes.add(new CraftingRecipe(1, 50, false, SkillCategory.CraftingOther, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularOre, 2), new ItemStack(Item.regularLogs, 5), new ItemStack(Item.coins, 1), new ItemStack(Item.purpleSword, 1)));
        recipes.add(new CraftingRecipe(2, 100, false, SkillCategory.CraftingOther, new ItemStack(Item.coins, 1), new ItemStack(Item.testSword, 1)));
        recipes.add(new CraftingRecipe(3, 5, false, SkillCategory.Leatherwork, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularLogs, 1)));
        recipes.add(new CraftingRecipe(3, 5, false, SkillCategory.Leatherwork, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularFish, 1), new ItemStack(Item.coins, 1), new ItemStack(Item.regularLogs, 1)));
recipes.add(new CraftingRecipe(1, 25, true, SkillCategory.CraftingOther, new ItemStack(Item.regularLogs, 5), new ItemStack(Item.regularOre, 5), new ItemStack(Item.testSword, 1)));
        recipes.add(new CraftingRecipe(1, 50, false, SkillCategory.CraftingOther, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularOre, 2), new ItemStack(Item.regularLogs, 5), new ItemStack(Item.coins, 1), new ItemStack(Item.purpleSword, 1)));
        recipes.add(new CraftingRecipe(2, 100, false, SkillCategory.CraftingOther, new ItemStack(Item.coins, 1), new ItemStack(Item.testSword, 1)));
        recipes.add(new CraftingRecipe(3, 5, false, SkillCategory.Leatherwork, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularLogs, 1)));
        recipes.add(new CraftingRecipe(3, 5, false, SkillCategory.Leatherwork, new ItemStack(Item.testSword, 1), new ItemStack(Item.regularFish, 1), new ItemStack(Item.coins, 1), new ItemStack(Item.regularLogs, 1)));


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
