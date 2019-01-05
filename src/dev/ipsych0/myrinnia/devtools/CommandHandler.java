package dev.ipsych0.myrinnia.devtools;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.entities.creatures.AStarMap;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.Skill;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.io.Serializable;

class CommandHandler implements Serializable {


    private static final long serialVersionUID = 1908102828227319857L;

    void handle(String[] commands, Commands firstCommand) {
        switch (firstCommand) {
            // Command to give items to the player
            case GIVE:
                try {
                    if (Integer.parseInt(commands[2]) <= 0) {
                        Handler.get().sendMsg("Must request at least 1 item.");
                        break;
                    }
                    if (Item.items[Integer.parseInt(commands[1])] == null) {
                        throw new IllegalArgumentException();
                    }
                    Handler.get().giveItem(Item.items[Integer.parseInt(commands[1])], Integer.parseInt(commands[2]));
                } catch (NumberFormatException e) {
                    Handler.get().sendMsg("Error. Syntax: 'give {itemID} {amount}'.");
                } catch (IllegalArgumentException e) {
                    Handler.get().sendMsg("Item with ID: '" + commands[1] + "' does not exist.");
                } catch (Exception e) {
                    Handler.get().sendMsg("Error. Syntax: 'give {itemID} {amount}'.");
                }
                break;
            // Command for teleporting around different maps
            case TELE:
                try {
                    // Tele command to different Zone
                    if (commands.length == 4) {
                        Zone zone = Zone.valueOf(commands[1]);
                        if (Handler.get().getWorldHandler().getWorldsMap().containsKey(zone)) {
                            Integer xPos = Integer.parseInt(commands[2]);
                            Integer yPos = Integer.parseInt(commands[3]);
                            Handler.get().goToWorld(zone, xPos, yPos);
                        } else {
                            Handler.get().sendMsg("World '" + zone.toString() + "' exists, but is not actively managed by the WorldHandler right now.");
                        }
                        // Tele command within same Zone
                    } else if (commands.length == 3) {
                        Integer xPos = Integer.parseInt(commands[1]);
                        Integer yPos = Integer.parseInt(commands[2]);
                        Handler.get().getPlayer().setX(xPos);
                        Handler.get().getPlayer().setY(yPos);
                    }
                } catch (Exception e) {
                    Handler.get().sendMsg("Error. Syntax: 'tele (optional:Zone) {X-coords} {Y-coords}'.");
                }
                break;
            // Noclip command
            case NOCLIP:
                Handler.noclipMode = !Handler.noclipMode;
                Handler.get().sendMsg("No-clip: " + (Handler.noclipMode ? "Enabled." : "Disabled."));
                break;
            // Change level commands
            case SET:
                // Check if the argument is a skill
                boolean isSkill = false;
                for (SkillsList skill : SkillsList.values()) {
                    if (skill.toString().equalsIgnoreCase(commands[1])) {
                        isSkill = true;
                    }
                }
                if (isSkill) {
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
                        Handler.get().sendMsg("Error. Syntax: 'set {skillName} {level}'.");
                    }
                } else {
                    try {
                        // Set combat attributes
                        if (commands[1].equalsIgnoreCase("strength")) {
                            Handler.get().getPlayer().setStrength(Integer.parseInt(commands[2]));
                        } else if (commands[1].equalsIgnoreCase("vitality")) {
                            Handler.get().getPlayer().setVitality(Integer.parseInt(commands[2]));
                        } else if (commands[1].equalsIgnoreCase("defence")) {
                            Handler.get().getPlayer().setDefence(Integer.parseInt(commands[2]));
                        } else if (commands[1].equalsIgnoreCase("movspeed")) {
                            Handler.get().getPlayer().setSpeed(Integer.parseInt(commands[2]));
                        } else if (commands[1].equalsIgnoreCase("atkspeed")) {
                            Handler.get().getPlayer().setAttackSpeed(Integer.parseInt(commands[2]));
                        } else {
                            Handler.get().sendMsg("Syntax error: '" + commands[1] + "' is not a skill or attribute.");
                        }
                    } catch (Exception e) {
                        Handler.get().sendMsg("Error. Syntax: 'set {attribute} {amount}'.");
                    }
                }
                break;
            // Runtime debug commands
            case DEBUG:
                if(commands.length == 2) {
                    if (commands[1].equalsIgnoreCase("a*")) {
                        AStarMap.debugMode = !AStarMap.debugMode;
                    } else {
                        Handler.get().sendMsg("Unknown command: '" + commands[1] + "'. Syntax: 'debug {target}'.");
                    }
                }else{
                    Handler.get().sendMsg("Invalid number of arguments provided. Syntax: 'debug {target}'.");
                }
                break;
            // Unlock Abilities
            case UNLOCK:
                if (commands.length == 2) {
                    if(commands[1].equalsIgnoreCase("all")) {
                        for(Ability a : Handler.get().getAbilityManager().getAllAbilities()){
                            a.setUnlocked(true);
                        }
                        Handler.get().sendMsg("Unlocked all abilities.");
                        break;
                    }
                    if(!commands[1].isEmpty()){
                        for(Ability a : Handler.get().getAbilityManager().getAllAbilities()){
                            if(a.getName().equalsIgnoreCase(commands[1].replace("_"," "))){
                                a.setUnlocked(true);
                                Handler.get().sendMsg("Unlocked ability: " + commands[1]);
                                break;
                            }
                        }
                    }
                    else{
                        Handler.get().sendMsg("Unknown command: '" + commands[1] + "'. Syntax: 'unlock {ability_Name} or 'unlock all'.");
                    }
                }else{
                    Handler.get().sendMsg("Invalid number of arguments provided. Syntax: 'unlock {ability_Name} or 'unlock all'.");
                }
                break;
            default:
                Handler.get().sendMsg("Could not parse command.");
                break;
        }
    }
}
