package dev.ipsych0.myrinnia.devtools;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.worlds.Zone;

class CommandHandler {

    void handle(String[] commands, Commands firstCommand) {
        switch (firstCommand) {
            // Command to give things to the player
            case GIVE:
                try {
                    if (Integer.parseInt(commands[1]) < 0 || Integer.parseInt(commands[1]) >= Integer.MAX_VALUE) {
                        System.err.println("Cannot provide negative or above max integer Item ID.");
                        break;
                    }
                    if (Integer.parseInt(commands[2]) <= 0 || Integer.parseInt(commands[2]) >= 999_999_999) {
                        System.err.println("Cannot provide negative amount or above 999,999,999 of an item.");
                        break;
                    }
                    Handler.get().giveItem(Item.items[Integer.parseInt(commands[1])], Integer.parseInt(commands[2]));
                } catch (Exception e) {
                    System.err.println("Could not parse command.");
                }
                break;
            // Command for teleporting around different maps
            case TELE:
                try {
                    if(commands.length == 4) {
                        Zone zone = Zone.valueOf(commands[1]);
                        Integer xPos = Integer.parseInt(commands[2]);
                        Integer yPos = Integer.parseInt(commands[3]);
                        Handler.get().goToWorld(zone, xPos, yPos);
                    }else if(commands.length == 3){
                        Integer xPos = Integer.parseInt(commands[1]);
                        Integer yPos = Integer.parseInt(commands[2]);
                        Handler.get().getPlayer().setX(xPos);
                        Handler.get().getPlayer().setY(yPos);
                    }
                } catch (Exception e) {
                    System.err.println("Could not parse command.");
                }
                break;
            // Noclip argument
            case NOCLIP:
                Handler.debugMode = !Handler.debugMode;
                break;
        }
    }
}
