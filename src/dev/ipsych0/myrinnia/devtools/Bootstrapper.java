package dev.ipsych0.myrinnia.devtools;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.chatwindow.Filter;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.items.Item;

public class Bootstrapper {

    private Handler handler;
    private Player player;
    private CommandHandler commandHandler;

    public Bootstrapper setup_lvl_15() {
        skipTutorialIsland();
        commandHandler = new CommandHandler();
        commandHandler.handle(Commands.SET, "set", "combat", "15");
        commandHandler.handle(Commands.UNLOCK, "unlock", "all");

        // Give lvl 15 gear sets
        handler.giveItem(Item.coins, 1000);

        handler.giveItem(Item.platinumSword, 1);
        handler.giveItem(Item.teakBow, 1);
        handler.giveItem(Item.teakStaff, 1);

        handler.giveItem(Item.platinumPlatemail, 1);
        handler.giveItem(Item.platinumLegs, 1);
        handler.giveItem(Item.platinumHelm, 1);
        handler.giveItem(Item.reinforcedShield, 1);

        handler.giveItem(Item.reinforcedBody, 1);
        handler.giveItem(Item.reinforcedLeggings, 1);
        handler.giveItem(Item.reinforcedCowl, 1);
        handler.giveItem(Item.platinumQuiver, 1);

        handler.giveItem(Item.silkRobeTop, 1);
        handler.giveItem(Item.silkRobeBottom, 1);
        handler.giveItem(Item.silkHat, 1);
        handler.giveItem(Item.sorcerersSpellbook, 1);

        handler.giveItem(Item.pearlAmulet, 1);
        handler.giveItem(Item.pearlEarrings, 1);

        handler.giveItem(Item.platinumAxe, 1);
        handler.giveItem(Item.platinumPickaxe, 1);
        handler.giveItem(Item.platinumFishingRod, 1);
        return this;
    }

    public Bootstrapper skipTutorialIsland() {
        handler = Handler.get();

        commandHandler = new CommandHandler();

        // Auto-disable tutorial tips
        handler.getChatWindow().getFilters().remove(Filter.SHOWTIPS);
        handler.getGame().getGeneralSettingsState().getActiveButtons().remove(7);

        // Set skill levels
        commandHandler.handle(Commands.SET, "set", "mining", "2");
        commandHandler.handle(Commands.SET, "set", "crafting", "2");
        commandHandler.handle(Commands.SET, "set", "fishing", "2");
        commandHandler.handle(Commands.SET, "set", "woodcutting", "2");
        commandHandler.handle(Commands.SET, "set", "combat", "5");

        // Complete Tutorial quests
        commandHandler.handle(Commands.COMPLETE, "complete", "GettingStarted");
        commandHandler.handle(Commands.COMPLETE, "complete", "GatheringYourStuff");
        commandHandler.handle(Commands.COMPLETE, "complete", "PreparingYourJourney");
        commandHandler.handle(Commands.COMPLETE, "complete", "WaveGoodbye");

        // Unlock Abilities
        commandHandler.handle(Commands.UNLOCK, "unlock", "Arcane_Renewal");
        commandHandler.handle(Commands.UNLOCK, "unlock", "Ice_Ball");
        commandHandler.handle(Commands.UNLOCK, "unlock", "Healing_Spring");
        commandHandler.handle(Commands.UNLOCK, "unlock", "Frost_Jab");
        commandHandler.handle(Commands.UNLOCK, "unlock", "Mend_Wounds");
        commandHandler.handle(Commands.UNLOCK, "unlock", "Glacial_Shot");
        return this;
    }

    public Bootstrapper giveStarterItems(){
        // Give starter items
        handler.giveItem(Item.coins, 100);

        handler.giveItem(Item.simpleSword, 1);
        handler.giveItem(Item.simpleBow, 1);
        handler.giveItem(Item.simpleStaff, 1);
        handler.giveItem(Item.simpleQuiver, 1);
        handler.giveItem(Item.simpleShield, 1);
        handler.giveItem(Item.simpleSpellBook, 1);
        handler.giveItem(Item.azuriteNecklace, 1);
        handler.giveItem(Item.simpleSandals, 1);
        handler.giveItem(Item.simpleGloves, 1);

        handler.giveItem(Item.simpleAxe, 1);
        handler.giveItem(Item.simplePickaxe, 1);
        handler.giveItem(Item.simpleFishingRod, 1);

        handler.giveItem(Item.palmWood, 5);
        handler.giveItem(Item.azuriteOre, 5);
        handler.giveItem(Item.mackerelFish, 5);
        handler.giveItem(Item.lapisLazuli, 1);

        return this;
    }
}
