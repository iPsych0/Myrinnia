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

    public void master() {
        skipTutorialIsland();
        for (Ability a : Handler.get().getAbilityManager().getAllAbilities()) {
            a.setUnlocked(true);
        }
    }

    public void skipTutorialIsland() {
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
    }
}
