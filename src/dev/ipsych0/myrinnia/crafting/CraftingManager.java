package dev.ipsych0.myrinnia.crafting;

import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dev.ipsych0.myrinnia.items.Item.*;
import static dev.ipsych0.myrinnia.items.ui.ItemStack.of;
import static dev.ipsych0.myrinnia.skills.ui.SkillCategory.*;

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

        // Beginner weapons
        recipes.add(new CraftingRecipe(of(simpleSword, 1), 1, 10, 30, false, Weapons, of(palmWood, 2), of(azuriteOre, 5)));
        recipes.add(new CraftingRecipe(of(simpleBow, 1), 1, 10, 30, false, Weapons, of(palmWood, 2), of(azuriteOre, 5)));
        recipes.add(new CraftingRecipe(of(simpleStaff, 1), 1, 10, 30, false, Weapons, of(palmWood, 2), of(azuriteOre, 5)));

        // Tier 5 melee
        recipes.add(new CraftingRecipe(of(ironChainMail, 1), 8, 45, 35, true, Armor, of(ironOre, 8)));
        recipes.add(new CraftingRecipe(of(studdedShield, 1), 5, 30, 35, true, Armor, of(ironOre, 2), of(hardWood, 4)));
        recipes.add(new CraftingRecipe(of(ironSword, 1), 5, 30, 30, true, Weapons, of(ironOre, 5), of(hardWood, 2)));
        recipes.add(new CraftingRecipe(of(ironLegs, 1), 8, 45, 35, true, Armor, of(ironOre, 6)));
        recipes.add(new CraftingRecipe(of(squiresCloak, 1), 7, 40, 35, true, Armor, of(softLeather, 5)));
        recipes.add(new CraftingRecipe(of(ironHelm, 1), 7, 40, 35, true, Armor, of(ironOre, 4)));
        recipes.add(new CraftingRecipe(of(ironBoots, 1), 6, 35, 35, true, Armor, of(ironOre, 3)));
        recipes.add(new CraftingRecipe(of(ironGloves, 1), 6, 35, 35, true, Armor, of(ironOre, 3)));

        // Tier 10 melee
        recipes.add(new CraftingRecipe(of(steelChainmail, 1), 13, 70, 55, true, Armor, of(coalOre, 12)));
        recipes.add(new CraftingRecipe(of(spikedShield, 1), 10, 50, 55, true, Armor, of(coalOre, 3), of(aspenwood, 6)));
        recipes.add(new CraftingRecipe(of(steelSword, 1), 10, 50, 55, true, Weapons, of(coalOre, 7), of(aspenwood, 3)));
        recipes.add(new CraftingRecipe(of(steelLegs, 1), 13, 70, 55, true, Armor, of(coalOre, 9)));
        recipes.add(new CraftingRecipe(of(knightsCloak, 1), 12, 60, 55, true, Armor, of(hardLeather, 7)));
        recipes.add(new CraftingRecipe(of(steelHelm, 1), 12, 60, 55, true, Armor, of(coalOre, 6)));
        recipes.add(new CraftingRecipe(of(steelBoots, 1), 11, 55, 55, true, Armor, of(coalOre, 4)));
        recipes.add(new CraftingRecipe(of(steelGloves, 1), 11, 55, 55, true, Armor, of(coalOre, 4)));

        // Tier 15 melee
        recipes.add(new CraftingRecipe(of(platinumPlatemail, 1), 18, 105, 80, true, Armor, of(platinumOre, 15)));
        recipes.add(new CraftingRecipe(of(reinforcedShield, 1), 15, 75, 80, true, Armor, of(platinumOre, 4), of(teakWood, 8)));
        recipes.add(new CraftingRecipe(of(platinumSword, 1), 15, 75, 80, true, Weapons, of(platinumOre, 9), of(teakWood, 4)));
        recipes.add(new CraftingRecipe(of(platinumLegs, 1), 18, 100, 80, true, Armor, of(platinumOre, 10)));
        recipes.add(new CraftingRecipe(of(championsCloak, 1), 17, 90, 80, true, Armor, of(reinforcedLeather, 8)));
        recipes.add(new CraftingRecipe(of(platinumHelm, 1), 17, 90, 80, true, Armor, of(platinumOre, 8)));
        recipes.add(new CraftingRecipe(of(platinumBoots, 1), 16, 85, 80, true, Armor, of(platinumOre, 5)));
        recipes.add(new CraftingRecipe(of(platinumGloves, 1), 16, 85, 80, true, Armor, of(platinumOre, 5)));

        // Tier 20 melee
        recipes.add(new CraftingRecipe(of(titaniumPlatemail, 1), 23, 170, 120, true, Armor, of(titaniumOre, 18)));
        recipes.add(new CraftingRecipe(of(towerShield, 1), 20, 120, 120, true, Armor, of(titaniumOre, 5), of(mahoganyWood, 10)));
        recipes.add(new CraftingRecipe(of(titaniumSword, 1), 20, 120, 120, true, Weapons, of(titaniumOre, 11), of(mahoganyWood, 5)));
        recipes.add(new CraftingRecipe(of(titaniumLegs, 1), 23, 160, 120, true, Armor, of(titaniumOre, 12)));
        recipes.add(new CraftingRecipe(of(warchiefsCloak, 1), 22, 145, 120, true, Armor, of(studdedLeather, 10)));
        recipes.add(new CraftingRecipe(of(titaniumHelm, 1), 22, 145, 120, true, Armor, of(titaniumOre, 10)));
        recipes.add(new CraftingRecipe(of(titaniumBoots, 1), 21, 140, 120, true, Armor, of(titaniumOre, 6)));
        recipes.add(new CraftingRecipe(of(titaniumGloves, 1), 21, 140, 120, true, Armor, of(titaniumOre, 6)));

        // Tier 25 melee
        recipes.add(new CraftingRecipe(of(obsidianCuirass, 1), 28, 270, 170, true, Armor, of(obsidian, 8)));
        recipes.add(new CraftingRecipe(of(obsidianBuckler, 1), 25, 190, 170, true, Armor, of(obsidian, 4), of(elderWood, 4)));
        recipes.add(new CraftingRecipe(of(obsidianBlade, 1), 25, 190, 170, true, Weapons, of(obsidian, 5), of(elderWood, 3)));
        recipes.add(new CraftingRecipe(of(obsidianLegs, 1), 28, 255, 170, true, Armor, of(obsidian, 6)));
        recipes.add(new CraftingRecipe(of(gladiatorsCloak, 1), 27, 230, 170, true, Armor, of(armoredLeather, 5)));
        recipes.add(new CraftingRecipe(of(obsidianHelm, 1), 27, 230, 170, true, Armor, of(obsidian, 4)));
        recipes.add(new CraftingRecipe(of(obsidianGreaves, 1), 26, 220, 170, true, Armor, of(obsidian, 3)));
        recipes.add(new CraftingRecipe(of(obsidianGauntlets, 1), 26, 220, 170, true, Armor, of(obsidian, 3)));

        // Tier 30 melee
        recipes.add(new CraftingRecipe(of(primordialCuirass, 1), 33, 430, 300, true, Armor, of(primordialIngot, 8)));
        recipes.add(new CraftingRecipe(of(primordialKiteshield, 1), 30, 325, 300, true, Armor, of(primordialIngot, 4), of(ancientWood, 4)));
        recipes.add(new CraftingRecipe(of(primordialBlade, 1), 30, 325, 300, true, Weapons, of(primordialIngot, 5), of(ancientWood, 3)));
        recipes.add(new CraftingRecipe(of(primordialPlatelegs, 1), 33, 430, 300, true, Armor, of(primordialIngot, 6)));
        recipes.add(new CraftingRecipe(of(primordialCloak, 1), 32, 390, 300, true, Armor, of(celenorianLeather, 5)));
        recipes.add(new CraftingRecipe(of(primordialGreathelm, 1), 32, 390, 300, true, Armor, of(primordialIngot, 4)));
        recipes.add(new CraftingRecipe(of(primordialGreaves, 1), 31, 375, 300, true, Armor, of(primordialIngot, 3)));
        recipes.add(new CraftingRecipe(of(primordialGauntlets, 1), 31, 375, 300, true, Armor, of(primordialIngot, 3)));

        // Tier 5 ranged
        recipes.add(new CraftingRecipe(of(softLeatherBody, 1), 8, 45, 35, true, Armor, of(softLeather, 8)));
        recipes.add(new CraftingRecipe(of(ironQuiver, 1), 5, 30, 35, true, Armor, of(ironOre, 2), of(softLeather, 4)));
        recipes.add(new CraftingRecipe(of(hardwoodBow, 1), 5, 30, 30, true, Weapons, of(softLeather, 2), of(hardWood, 5)));
        recipes.add(new CraftingRecipe(of(softLeatherLeggings, 1), 8, 45, 35, true, Armor, of(softLeather, 6)));
        recipes.add(new CraftingRecipe(of(scoutsCloak, 1), 7, 40, 35, true, Armor, of(softLeather, 5)));
        recipes.add(new CraftingRecipe(of(softLeatherCowl, 1), 7, 40, 35, true, Armor, of(softLeather, 4)));
        recipes.add(new CraftingRecipe(of(softLeatherBoots, 1), 6, 35, 35, true, Armor, of(softLeather, 3)));
        recipes.add(new CraftingRecipe(of(softLeatherGloves, 1), 6, 35, 35, true, Armor, of(softLeather, 3)));

        // Tier 10 ranged
        recipes.add(new CraftingRecipe(of(hardLeatherBody, 1), 13, 70, 55, true, Armor, of(hardLeather, 12)));
        recipes.add(new CraftingRecipe(of(steelQuiver, 1), 10, 50, 55, true, Armor, of(coalOre, 3), of(hardLeather, 6)));
        recipes.add(new CraftingRecipe(of(aspenwoodBow, 1), 10, 50, 55, true, Weapons, of(hardLeather, 3), of(aspenwood, 7)));
        recipes.add(new CraftingRecipe(of(hardLeatherLeggings, 1), 13, 70, 55, true, Armor, of(hardLeather, 9)));
        recipes.add(new CraftingRecipe(of(wardensCloak, 1), 12, 60, 55, true, Armor, of(hardLeather, 7)));
        recipes.add(new CraftingRecipe(of(hardLeatherCowl, 1), 12, 60, 55, true, Armor, of(hardLeather, 6)));
        recipes.add(new CraftingRecipe(of(hardLeatherBoots, 1), 11, 55, 55, true, Armor, of(hardLeather, 4)));
        recipes.add(new CraftingRecipe(of(hardLeatherGloves, 1), 11, 55, 55, true, Armor, of(hardLeather, 4)));

        // Tier 15 ranged
        recipes.add(new CraftingRecipe(of(reinforcedBody, 1), 18, 105, 80, true, Armor, of(reinforcedLeather, 15)));
        recipes.add(new CraftingRecipe(of(platinumQuiver, 1), 15, 75, 80, true, Armor, of(platinumOre, 4), of(reinforcedLeather, 8)));
        recipes.add(new CraftingRecipe(of(teakBow, 1), 15, 75, 80, true, Weapons, of(reinforcedLeather, 4), of(teakWood, 9)));
        recipes.add(new CraftingRecipe(of(reinforcedLeggings, 1), 18, 105, 80, true, Armor, of(reinforcedLeather, 10)));
        recipes.add(new CraftingRecipe(of(markmansCloak, 1), 17, 90, 80, true, Armor, of(reinforcedLeather, 8)));
        recipes.add(new CraftingRecipe(of(reinforcedCowl, 1), 17, 90, 80, true, Armor, of(reinforcedLeather, 8)));
        recipes.add(new CraftingRecipe(of(reinforcedBoots, 1), 16, 85, 80, true, Armor, of(reinforcedLeather, 5)));
        recipes.add(new CraftingRecipe(of(reinforcedGloves, 1), 16, 85, 80, true, Armor, of(reinforcedLeather, 5)));

        // Tier 20 ranged
        recipes.add(new CraftingRecipe(of(studdedLeatherBody, 1), 23, 170, 120, true, Armor, of(studdedLeather, 18)));
        recipes.add(new CraftingRecipe(of(titaniumQuiver, 1), 20, 120, 120, true, Armor, of(titaniumOre, 5), of(studdedLeather, 10)));
        recipes.add(new CraftingRecipe(of(mahoganyBow, 1), 20, 120, 120, true, Weapons, of(studdedLeather, 5), of(mahoganyWood, 11)));
        recipes.add(new CraftingRecipe(of(studdedLeatherLeggings, 1), 23, 170, 120, true, Armor, of(studdedLeather, 12)));
        recipes.add(new CraftingRecipe(of(sharpshootersCloak, 1), 22, 145, 120, true, Armor, of(studdedLeather, 10)));
        recipes.add(new CraftingRecipe(of(studdedLeatherCowl, 1), 22, 145, 120, true, Armor, of(studdedLeather, 10)));
        recipes.add(new CraftingRecipe(of(studdedLeatherBoots, 1), 21, 140, 120, true, Armor, of(studdedLeather, 6)));
        recipes.add(new CraftingRecipe(of(studdedLeatherGloves, 1), 21, 140, 120, true, Armor, of(studdedLeather, 6)));

        // Tier 25 ranged
        recipes.add(new CraftingRecipe(of(deadeyesTorso, 1), 28, 270, 170, true, Armor, of(armoredLeather, 8)));
        recipes.add(new CraftingRecipe(of(obsidianQuiver, 1), 25, 190, 170, true, Armor, of(obsidian, 3), of(armoredLeather, 5)));
        recipes.add(new CraftingRecipe(of(deadeyesFlatbow, 1), 25, 190, 170, true, Weapons, of(armoredLeather, 2), of(elderWood, 8)));
        recipes.add(new CraftingRecipe(of(deadeyesLeggings, 1), 28, 270, 170, true, Armor, of(armoredLeather, 6)));
        recipes.add(new CraftingRecipe(of(deadeyesCloak, 1), 27, 230, 170, true, Armor, of(armoredLeather, 5)));
        recipes.add(new CraftingRecipe(of(deadeyesCowl, 1), 27, 230, 170, true, Armor, of(armoredLeather, 4)));
        recipes.add(new CraftingRecipe(of(deadeyesBoots, 1), 26, 220, 170, true, Armor, of(armoredLeather, 3)));
        recipes.add(new CraftingRecipe(of(deadeyesGloves, 1), 26, 220, 170, true, Armor, of(armoredLeather, 3)));

        // Tier 25 ranged
        recipes.add(new CraftingRecipe(of(primevalTorso, 1), 33, 430, 300, true, Armor, of(celenorianLeather, 8)));
        recipes.add(new CraftingRecipe(of(primevalQuiver, 1), 30, 325, 300, true, Armor, of(primordialIngot, 3), of(celenorianLeather, 5)));
        recipes.add(new CraftingRecipe(of(primevalRecurveBow, 1), 30, 325, 300, true, Weapons, of(celenorianLeather, 2), of(ancientWood, 5)));
        recipes.add(new CraftingRecipe(of(primevalLeggings, 1), 33, 430, 300, true, Armor, of(celenorianLeather, 6)));
        recipes.add(new CraftingRecipe(of(primevalCloak, 1), 32, 390, 300, true, Armor, of(celenorianLeather, 5)));
        recipes.add(new CraftingRecipe(of(primevalCowl, 1), 32, 390, 300, true, Armor, of(celenorianLeather, 4)));
        recipes.add(new CraftingRecipe(of(primevalBoots, 1), 31, 375, 300, true, Armor, of(celenorianLeather, 3)));
        recipes.add(new CraftingRecipe(of(primevalGloves, 1), 31, 375, 300, true, Armor, of(celenorianLeather, 3)));

        // Tier 5 magic
        recipes.add(new CraftingRecipe(of(woolenRobeTop, 1), 8, 45, 35, true, Armor, of(stripOfWool, 8)));
        recipes.add(new CraftingRecipe(of(leatherSpellbook, 1), 5, 30, 35, true, Armor, of(softLeather, 2), of(stripOfWool, 4)));
        recipes.add(new CraftingRecipe(of(hardwoodStaff, 1), 5, 30, 30, true, Weapons, of(ironOre, 2), of(hardWood, 5)));
        recipes.add(new CraftingRecipe(of(woolenRobeBottom, 1), 8, 45, 35, true, Armor, of(stripOfWool, 6)));
        recipes.add(new CraftingRecipe(of(apprenticesCloak, 1), 7, 40, 35, true, Armor, of(softLeather, 5)));
        recipes.add(new CraftingRecipe(of(woolenHat, 1), 7, 40, 35, true, Armor, of(stripOfWool, 4)));
        recipes.add(new CraftingRecipe(of(woolenBoots, 1), 6, 35, 35, true, Armor, of(stripOfWool, 3)));
        recipes.add(new CraftingRecipe(of(woolenGloves, 1), 6, 35, 35, true, Armor, of(stripOfWool, 2)));

        // Tier 10 magic
        recipes.add(new CraftingRecipe(of(linenRobeTop, 1), 13, 70, 55, true, Armor, of(stripOfLinen, 12)));
        recipes.add(new CraftingRecipe(of(hardLeatherSpellbook, 1), 10, 50, 55, true, Armor, of(hardLeather, 3), of(stripOfLinen, 6)));
        recipes.add(new CraftingRecipe(of(aspenwoodStaff, 1), 10, 50, 55, true, Weapons, of(coalOre, 3), of(aspenwood, 7)));
        recipes.add(new CraftingRecipe(of(linenRobeBottom, 1), 13, 70, 55, true, Armor, of(stripOfLinen, 9)));
        recipes.add(new CraftingRecipe(of(wizardsCloak, 1), 12, 60, 55, true, Armor, of(softLeather, 7)));
        recipes.add(new CraftingRecipe(of(linenHat, 1), 12, 60, 55, true, Armor, of(stripOfLinen, 6)));
        recipes.add(new CraftingRecipe(of(linenBoots, 1), 11, 55, 55, true, Armor, of(stripOfLinen, 4)));
        recipes.add(new CraftingRecipe(of(linenGloves, 1), 11, 55, 55, true, Armor, of(stripOfLinen, 4)));

        // Tier 15 magic
        recipes.add(new CraftingRecipe(of(silkRobeTop, 1), 18, 105, 80, true, Armor, of(stripOfSilk, 15)));
        recipes.add(new CraftingRecipe(of(sorcerersSpellbook, 1), 15, 75, 80, true, Armor, of(reinforcedLeather, 4), of(stripOfSilk, 8)));
        recipes.add(new CraftingRecipe(of(teakStaff, 1), 15, 75, 80, true, Weapons, of(platinumOre, 4), of(teakWood, 9)));
        recipes.add(new CraftingRecipe(of(silkRobeBottom, 1), 18, 105, 80, true, Armor, of(stripOfSilk, 10)));
        recipes.add(new CraftingRecipe(of(sorcerersCloak, 1), 17, 90, 80, true, Armor, of(reinforcedLeather, 8)));
        recipes.add(new CraftingRecipe(of(silkHat, 1), 17, 90, 80, true, Armor, of(stripOfSilk, 8)));
        recipes.add(new CraftingRecipe(of(silkBoots, 1), 16, 85, 80, true, Armor, of(stripOfSilk, 5)));
        recipes.add(new CraftingRecipe(of(silkGloves, 1), 16, 85, 80, true, Armor, of(stripOfSilk, 5)));

        // Tier 20 magic
        recipes.add(new CraftingRecipe(of(damaskRobeTop, 1), 23, 170, 120, true, Armor, of(stripOfDamask, 18)));
        recipes.add(new CraftingRecipe(of(warlocksSpellbook, 1), 20, 120, 120, true, Armor, of(studdedLeather, 5), of(stripOfDamask, 10)));
        recipes.add(new CraftingRecipe(of(mahoganyStaff, 1), 20, 120, 120, true, Weapons, of(titaniumOre, 5), of(mahoganyWood, 11)));
        recipes.add(new CraftingRecipe(of(damaskRobeBottom, 1), 23, 170, 120, true, Armor, of(stripOfDamask, 12)));
        recipes.add(new CraftingRecipe(of(warlocksCloak, 1), 22, 145, 120, true, Armor, of(studdedLeather, 10)));
        recipes.add(new CraftingRecipe(of(damaskHat, 1), 22, 145, 120, true, Armor, of(stripOfDamask, 10)));
        recipes.add(new CraftingRecipe(of(damaskBoots, 1), 21, 140, 120, true, Armor, of(stripOfDamask, 6)));
        recipes.add(new CraftingRecipe(of(damaskGloves, 1), 21, 140, 120, true, Armor, of(stripOfDamask, 6)));

        // Tier 25 magic
        recipes.add(new CraftingRecipe(of(seersGarb, 1), 28, 270, 170, true, Armor, of(stripOfIntricateCloth, 8)));
        recipes.add(new CraftingRecipe(of(seersBook, 1), 25, 190, 170, true, Armor, of(armoredLeather, 4), of(stripOfIntricateCloth, 4)));
        recipes.add(new CraftingRecipe(of(seersSpire, 1), 25, 190, 170, true, Weapons, of(obsidian, 2), of(elderWood, 8)));
        recipes.add(new CraftingRecipe(of(seersGown, 1), 28, 270, 170, true, Armor, of(stripOfIntricateCloth, 6)));
        recipes.add(new CraftingRecipe(of(seersCloak, 1), 27, 230, 170, true, Armor, of(armoredLeather, 5)));
        recipes.add(new CraftingRecipe(of(seersHat, 1), 27, 230, 170, true, Armor, of(stripOfIntricateCloth, 4)));
        recipes.add(new CraftingRecipe(of(seersBoots, 1), 26, 220, 170, true, Armor, of(stripOfIntricateCloth, 3)));
        recipes.add(new CraftingRecipe(of(seersGloves, 1), 26, 220, 170, true, Armor, of(stripOfIntricateCloth, 3)));

        // Tier 25 magic
        recipes.add(new CraftingRecipe(of(primalGownTop, 1), 33, 430, 300, true, Armor, of(stripOfFarnorCloth, 8)));
        recipes.add(new CraftingRecipe(of(bookOfPrimalMagics, 1), 30, 325, 300, true, Armor, of(celenorianLeather, 4), of(stripOfFarnorCloth, 4)));
        recipes.add(new CraftingRecipe(of(primalSceptre, 1), 30, 325, 300, true, Weapons, of(primordialIngot, 2), of(ancientWood, 5)));
        recipes.add(new CraftingRecipe(of(primalGownBottoms, 1), 33, 430, 300, true, Armor, of(stripOfFarnorCloth, 6)));
        recipes.add(new CraftingRecipe(of(primalCloak, 1), 32, 390, 300, true, Armor, of(celenorianLeather, 5)));
        recipes.add(new CraftingRecipe(of(primalHood, 1), 32, 390, 300, true, Armor, of(stripOfFarnorCloth, 4)));
        recipes.add(new CraftingRecipe(of(primalFootgear, 1), 31, 375, 300, true, Armor, of(stripOfFarnorCloth, 3)));
        recipes.add(new CraftingRecipe(of(primalGloves, 1), 31, 375, 300, true, Armor, of(stripOfFarnorCloth, 3)));

        /*
         * Tools
         */
        recipes.add(new CraftingRecipe(of(simpleAxe, 1), 1, 10, 30, true, Tools, of(palmWood, 2), of(azuriteOre, 3)));
        recipes.add(new CraftingRecipe(of(simplePickaxe, 1), 1, 10, 30, true, Tools, of(palmWood, 2), of(azuriteOre, 3)));
        recipes.add(new CraftingRecipe(of(simpleFishingRod, 1), 1, 10, 30, true, Tools, of(palmWood, 3), of(azuriteOre, 2)));

        recipes.add(new CraftingRecipe(of(copperAxe, 1), 2, 20, 30, true, Tools, of(lightwood, 2), of(copperOre, 3)));
        recipes.add(new CraftingRecipe(of(copperPickaxe, 1), 2, 20, 30, true, Tools, of(lightwood, 2), of(copperOre, 3)));
        recipes.add(new CraftingRecipe(of(copperFishingRod, 1), 2, 20, 30, true, Tools, of(lightwood, 3), of(copperOre, 2)));

        recipes.add(new CraftingRecipe(of(ironAxe, 1), 5, 30, 35, true, Tools, of(hardWood, 2), of(ironOre, 3)));
        recipes.add(new CraftingRecipe(of(ironPickaxe, 1), 5, 30, 35, true, Tools, of(hardWood, 2), of(ironOre, 3)));
        recipes.add(new CraftingRecipe(of(ironFishingRod, 1), 5, 30, 35, true, Tools, of(hardWood, 3), of(ironOre, 2)));

        recipes.add(new CraftingRecipe(of(steelAxe, 1), 10, 50, 55, true, Tools, of(aspenwood, 3), of(coalOre, 4)));
        recipes.add(new CraftingRecipe(of(steelPickaxe, 1), 10, 50, 55, true, Tools, of(aspenwood, 3), of(coalOre, 4)));
        recipes.add(new CraftingRecipe(of(steelFishingRod, 1), 10, 50, 55, true, Tools, of(aspenwood, 4), of(coalOre, 3)));

        recipes.add(new CraftingRecipe(of(platinumAxe, 1), 15, 75, 80, true, Tools, of(teakWood, 3), of(platinumOre, 6)));
        recipes.add(new CraftingRecipe(of(platinumPickaxe, 1), 15, 75, 80, true, Tools, of(teakWood, 3), of(platinumOre, 6)));
        recipes.add(new CraftingRecipe(of(platinumFishingRod, 1), 15, 75, 80, true, Tools, of(teakWood, 4), of(platinumOre, 3)));

        recipes.add(new CraftingRecipe(of(titaniumAxe, 1), 20, 120, 120, true, Tools, of(mahoganyWood, 4), of(titaniumOre, 8)));
        recipes.add(new CraftingRecipe(of(titaniumPickaxe, 1), 20, 120, 120, true, Tools, of(mahoganyWood, 4), of(titaniumOre, 8)));
        recipes.add(new CraftingRecipe(of(titaniumFishingRod, 1), 20, 120, 120, true, Tools, of(mahoganyWood, 6), of(titaniumOre, 4)));

        recipes.add(new CraftingRecipe(of(obsidianAxe, 1), 25, 190, 170, true, Tools, of(elderWood, 6), of(obsidian, 3)));
        recipes.add(new CraftingRecipe(of(obsidianPickaxe, 1), 25, 190, 170, true, Tools, of(elderWood, 6), of(obsidian, 3)));
        recipes.add(new CraftingRecipe(of(obsidianFishingRod, 1), 25, 190, 170, true, Tools, of(elderWood, 6), of(obsidian, 2)));

        recipes.add(new CraftingRecipe(of(primordialAxe, 1), 30, 325, 300, true, Tools, of(ancientWood, 2), of(primordialIngot, 3)));
        recipes.add(new CraftingRecipe(of(primordialPickaxe, 1), 30, 325, 300, true, Tools, of(ancientWood, 2), of(primordialIngot, 3)));
        recipes.add(new CraftingRecipe(of(primordialFishingRod, 1), 30, 325, 300, true, Tools, of(ancientWood, 3), of(primordialIngot, 2)));

        /*
         * Accessories
         */
        recipes.add(new CraftingRecipe(of(azuriteEarrings, 1), 1, 10, 30, true, Trinkets, of(azuriteOre, 2), of(lapisLazuli, 1)));
        recipes.add(new CraftingRecipe(of(azuriteRingL, 1), 2, 15, 30, true, Trinkets, of(azuriteOre, 3), of(lapisLazuli, 1)));
        recipes.add(new CraftingRecipe(of(azuriteRingR, 1), 2, 15, 30, true, Trinkets, of(azuriteOre, 3), of(lapisLazuli, 1)));
        recipes.add(new CraftingRecipe(of(azuriteNecklace, 1), 3, 20, 30, true, Trinkets, of(azuriteOre, 5), of(lapisLazuli, 1)));

        recipes.add(new CraftingRecipe(of(malachiteEarrings, 1), 3, 20, 40, true, Trinkets, of(copperOre, 2), of(malachite, 1)));
        recipes.add(new CraftingRecipe(of(malachiteRingL, 1), 4, 25, 40, true, Trinkets, of(copperOre, 3), of(malachite, 1)));
        recipes.add(new CraftingRecipe(of(malachiteRingR, 1), 5, 25, 40, true, Trinkets, of(copperOre, 3), of(malachite, 1)));
        recipes.add(new CraftingRecipe(of(malachiteAmulet, 1), 5, 30, 40, true, Trinkets, of(copperOre, 5), of(malachite, 1)));

        recipes.add(new CraftingRecipe(of(topazEarrings, 1), 7, 35, 50, true, Trinkets, of(tungstenOre, 2), of(topaz, 1)));
        recipes.add(new CraftingRecipe(of(topazRingL, 1), 8, 40, 50, true, Trinkets, of(tungstenOre, 3), of(topaz, 1)));
        recipes.add(new CraftingRecipe(of(topazRingR, 1), 8, 40, 50, true, Trinkets, of(tungstenOre, 3), of(topaz, 1)));
        recipes.add(new CraftingRecipe(of(topazAmulet, 1), 9, 45, 50, true, Trinkets, of(tungstenOre, 5), of(topaz, 1)));

        recipes.add(new CraftingRecipe(of(pearlEarrings, 1), 12, 60, 60, true, Trinkets, of(silverOre, 2), of(pearl, 1)));
        recipes.add(new CraftingRecipe(of(pearlRingL, 1), 13, 70, 60, true, Trinkets, of(silverOre, 3), of(pearl, 1)));
        recipes.add(new CraftingRecipe(of(pearlRingR, 1), 13, 70, 60, true, Trinkets, of(silverOre, 3), of(pearl, 1)));
        recipes.add(new CraftingRecipe(of(pearlAmulet, 1), 14, 80, 60, true, Trinkets, of(silverOre, 5), of(pearl, 1)));

        recipes.add(new CraftingRecipe(of(amethystEarrings, 1), 17, 110, 70, true, Trinkets, of(goldOre, 2), of(amethyst, 1)));
        recipes.add(new CraftingRecipe(of(amethystRingL, 1), 18, 130, 70, true, Trinkets, of(goldOre, 3), of(amethyst, 1)));
        recipes.add(new CraftingRecipe(of(amethystRingR, 1), 18, 130, 70, true, Trinkets, of(goldOre, 3), of(amethyst, 1)));
        recipes.add(new CraftingRecipe(of(amethystAmulet, 1), 19, 150, 70, true, Trinkets, of(goldOre, 5), of(amethyst, 1)));

        recipes.add(new CraftingRecipe(of(diamondEarrings, 1), 22, 200, 70, true, Trinkets, of(palladiumOre, 2), of(diamond, 1)));
        recipes.add(new CraftingRecipe(of(diamondRingL, 1), 23, 230, 70, true, Trinkets, of(palladiumOre, 3), of(diamond, 1)));
        recipes.add(new CraftingRecipe(of(diamondRingR, 1), 23, 230, 70, true, Trinkets, of(palladiumOre, 3), of(diamond, 1)));
        recipes.add(new CraftingRecipe(of(diamondAmulet, 1), 24, 260, 70, true, Trinkets, of(palladiumOre, 5), of(diamond, 1)));

        recipes.add(new CraftingRecipe(of(onyxEarrings, 1), 27, 360, 80, true, Trinkets, of(cobaltOre, 2), of(onyx, 1)));
        recipes.add(new CraftingRecipe(of(onyxRingL, 1), 28, 400, 80, true, Trinkets, of(cobaltOre, 3), of(onyx, 1)));
        recipes.add(new CraftingRecipe(of(onyxRingR, 1), 28, 400, 80, true, Trinkets, of(cobaltOre, 3), of(onyx, 1)));
        recipes.add(new CraftingRecipe(of(onyxAmulet, 1), 29, 440, 80, true, Trinkets, of(cobaltOre, 5), of(onyx, 1)));

        // Ashes
        recipes.add(new CraftingRecipe(of(pileOfAshes, 2), 1, 2, 15, true, Materials, of(palmWood, 1)));
        recipes.add(new CraftingRecipe(of(pileOfAshes, 3), 1, 3, 15, true, Materials, of(lightwood, 1)));
        recipes.add(new CraftingRecipe(of(pileOfAshes, 4), 1, 4, 15, true, Materials, of(hardWood, 1)));
        recipes.add(new CraftingRecipe(of(pileOfAshes, 5), 1, 5, 15, true, Materials, of(aspenwood, 1)));

        // Crafting refinement
        recipes.add(new CraftingRecipe(of(glass, 1), 1, 5, 15, true, Materials, of(pileOfAshes, 1), of(pileOfSand, 1)));
        recipes.add(new CraftingRecipe(of(lightWoodPlank, 1), 1, 5, 10, true, Materials, of(lightwood, 2)));
        recipes.add(new CraftingRecipe(of(hardWoodPlank, 1), 5, 10, 10, true, Materials, of(hardWood, 2)));
        recipes.add(new CraftingRecipe(of(boneMeal, 1), 1, 5, 10, true, Materials, of(chitin, 1)));
        recipes.add(new CraftingRecipe(of(rope, 1), 1, 5, 15, true, Materials, of(wool, 2)));

        // Cloth
        recipes.add(new CraftingRecipe(of(stripOfWool, 1), 3, 8, 15, true, Materials, of(wool, 2)));
        recipes.add(new CraftingRecipe(of(stripOfLinen, 1), 8, 16, 15, true, Materials, of(flax, 2)));
        recipes.add(new CraftingRecipe(of(stripOfSilk, 1), 13, 24, 15, true, Materials, of(spiderSilk, 2)));
        recipes.add(new CraftingRecipe(of(stripOfDamask, 1), 18, 32, 15, true, Materials, of(stripOfWool, 1), of(stripOfLinen, 1), of(stripOfSilk, 1)));
        recipes.add(new CraftingRecipe(of(stripOfIntricateCloth, 1), 23, 40, 15, true, Materials, of(stripOfDamask, 1), of(highQualityYakFibre, 1)));
        recipes.add(new CraftingRecipe(of(stripOfFarnorCloth, 1), 30, 50, 30, true, Materials, of(stripOfIntricateCloth, 1), of(arcaneThread, 1)));

        // Leather
        recipes.add(new CraftingRecipe(of(reinforcedLeather, 1), 13, 20, 15, true, Materials, of(hardLeather, 1), of(ironOre, 1)));
        recipes.add(new CraftingRecipe(of(studdedLeather, 1), 18, 24, 15, true, Materials, of(reinforcedLeather, 1), of(coalOre, 1)));
        recipes.add(new CraftingRecipe(of(armoredLeather, 1), 23, 28, 15, true, Materials, of(reinforcedLeather, 1), of(steelPlating, 1)));
        recipes.add(new CraftingRecipe(of(celenorianLeather, 1), 30, 50, 30, true, Materials, of(armoredLeather, 1), of(celenorianThread, 1)));

        // Metals
        recipes.add(new CraftingRecipe(of(steelPlating, 1), 20, 30, 20, true, Materials, of(obsidianShard, 1), of(titaniumPlating, 1)));
        recipes.add(new CraftingRecipe(of(highQualityYakFibre, 1), 20, 30, 20, true, Materials, of(yakHair, 5)));
        recipes.add(new CraftingRecipe(of(obsidian, 1), 25, 45, 20, true, Materials, of(obsidianShard, 1), of(titaniumPlating, 1)));
        recipes.add(new CraftingRecipe(of(titaniumPlating, 1), 25, 40, 20, true, Materials, of(obsidianShard, 1), of(titaniumPlating, 1)));
        recipes.add(new CraftingRecipe(of(primordialIngot, 1), 30, 50, 30, true, Materials, of(obsidian, 1), of(primordialCrystal, 1)));

        // Potions
        recipes.add(new CraftingRecipe(of(weakAntidote, 1), 1, 10, 10, true, Potions, of(glass, 1), of(scorpionTail, 1)));
        recipes.add(new CraftingRecipe(of(weakPotionOfMight, 1), 1, 10, 10, true, Potions, of(glass, 1), of(crablingClaw, 1)));
        recipes.add(new CraftingRecipe(of(weakPotionOfPrecision, 1), 1, 10, 10, true, Potions, of(glass, 1), of(owlFeather, 1)));
        recipes.add(new CraftingRecipe(of(weakPotionOfWisdom, 1), 1, 10, 10, true, Potions, of(glass, 1), of(azureBatWing, 1)));
        recipes.add(new CraftingRecipe(of(weakPotionOfVigor, 1), 1, 10, 10, true, Potions, of(glass, 1), of(vineRoot, 1)));
        recipes.add(new CraftingRecipe(of(weakPotionofFortitude, 1), 1, 10, 10, true, Potions, of(glass, 1), of(rockyShell, 1)));

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
