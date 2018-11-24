package dev.ipsych0.myrinnia.devtools;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.AStarMap;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.Skill;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.worlds.Zone;

class CommandHandler {

    void handle(String[] commands, Commands firstCommand) {
        switch (firstCommand) {
            // Command to give items to the player
            case GIVE:
                try {
                    if (Integer.parseInt(commands[2]) <= 0) {
                        Handler.get().sendMsg("Must request at least 1 item.");
                        break;
                    }
                    Handler.get().giveItem(Item.items[Integer.parseInt(commands[1])], Integer.parseInt(commands[2]));
                } catch (Exception e) {
                    Handler.get().sendMsg("Error. Syntax: 'give {itemID} {amount}'");
                }
                break;
            // Command for teleporting around different maps
            case TELE:
                try {
                    // Tele command to different Zone
                    if (commands.length == 4) {
                        Zone zone = Zone.valueOf(commands[1]);
                        Integer xPos = Integer.parseInt(commands[2]);
                        Integer yPos = Integer.parseInt(commands[3]);
                        Handler.get().goToWorld(zone, xPos, yPos);
                    // Tele command within same Zone
                    } else if (commands.length == 3) {
                        Integer xPos = Integer.parseInt(commands[1]);
                        Integer yPos = Integer.parseInt(commands[2]);
                        Handler.get().getPlayer().setX(xPos);
                        Handler.get().getPlayer().setY(yPos);
                    }
                } catch (Exception e) {
                    Handler.get().sendMsg("Error. Syntax: 'tele (optional:Zone) {X-coords} {Y-coords}'");
                }
                break;
            // Noclip command
            case NOCLIP:
                Handler.noclipMode = !Handler.noclipMode;
                Handler.get().sendMsg("No-clip: " + (Handler.noclipMode ? "Enabled." : "Disabled."));
                break;
            // Change level commands
            case SET:
                try {

                    // Get the skill and level
                    Skill s = Handler.get().getSkill(SkillsList.valueOf(commands[1].toUpperCase()));
                    int level = Integer.parseInt(commands[2]) - 1;

                    // Reset to level 1 first to do proper scaling
                    s.setLevel(1);
                    s.setNextLevelXp(100);
                    s.setExperience(0);

                    // Set the levels
                    for (int i = 0; i < level; i++) {
                        s.addExperience(s.getNextLevelXp());
                    }
                } catch (Exception e) {
                    Handler.get().sendMsg("Error. Syntax: 'set {skillName} {level}'");
                }
                break;
            case DEBUG:
                if(commands[1].equalsIgnoreCase("a*")){
                    AStarMap.debugMode = !AStarMap.debugMode;
                }
                break;
            default:
                Handler.get().sendMsg("Could not parse command.");
                break;
        }
    }
}
