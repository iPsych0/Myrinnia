package dev.ipsych0.myrinnia.devtools;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilities.ui.abilityhud.AbilitySlot;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.Skill;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.io.Serializable;

class CommandHandler implements Serializable {


    private static final long serialVersionUID = 1908102828227319857L;

    void handle(String[] commands, Commands firstCommand) {
        switch (firstCommand) {
            // Command to give items to the player
            case GIVE:
                try {
                    if (commands.length != 3) {
                        throw new Exception();
                    }
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
                    } else {
                        throw new Exception();
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
                        if (commands[1].equalsIgnoreCase("str")) {
                            Handler.get().getPlayer().setStrength(Integer.parseInt(commands[2]));
                        } else if (commands[1].equalsIgnoreCase("dex")) {
                            Handler.get().getPlayer().setDexterity(Integer.parseInt(commands[2]));
                        } else if (commands[1].equalsIgnoreCase("int")) {
                            Handler.get().getPlayer().setIntelligence(Integer.parseInt(commands[2]));
                        } else if (commands[1].equalsIgnoreCase("vit")) {
                            Handler.get().getPlayer().setVitality(Integer.parseInt(commands[2]));
                        } else if (commands[1].equalsIgnoreCase("def")) {
                            Handler.get().getPlayer().setDefence(Integer.parseInt(commands[2]));
                        } else if (commands[1].equalsIgnoreCase("movspd")) {
                            Handler.get().getPlayer().setSpeed(Integer.parseInt(commands[2]));
                        } else if (commands[1].equalsIgnoreCase("atkspd")) {
                            Handler.get().getPlayer().setAttackSpeed(Integer.parseInt(commands[2]));
                        } else if (commands[1].equalsIgnoreCase("maxhp")) {
                            Handler.get().getPlayer().setMaxHealth(Integer.parseInt(commands[2]));
                            Handler.get().getPlayer().setHealth(Handler.get().getPlayer().getMaxHealth());
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
                if (commands.length == 2) {
                    if (commands[1].equalsIgnoreCase("a*")) {
                        Handler.debugAStar = !Handler.debugAStar;
                    } else if(commands[1].equalsIgnoreCase("collision")){
                        Handler.debugCollision = !Handler.debugCollision;
                    } else if(commands[1].equalsIgnoreCase("zonetiles")){
                        Handler.debugZones = !Handler.debugZones;
                    } else {
                        Handler.get().sendMsg("Unknown command: '" + commands[1] + "'. Syntax: 'debug {target}'.");
                    }
                } else {
                    Handler.get().sendMsg("Invalid number of arguments provided. Syntax: 'debug {target}'.");
                }
                break;
            // Unlock Abilities
            case UNLOCK:
                if (commands.length == 2) {
                    if (commands[1].equalsIgnoreCase("all")) {
                        for (Ability a : Handler.get().getAbilityManager().getAllAbilities()) {
                            a.setUnlocked(true);
                        }
                        Handler.get().sendMsg("Unlocked all abilities.");
                        for(int i = 0; i < Handler.get().getAbilityManager().getAllAbilities().size(); i++) {
                            if(i == 10)
                                break;
                            Handler.get().getAbilityManager().getAbilityHUD().getSlottedAbilities().get(i).setAbility(Handler.get().getAbilityManager().getAllAbilities().get(i));
                        }
                        break;
                    }
                    if (!commands[1].isEmpty()) {
                        for (Ability a : Handler.get().getAbilityManager().getAllAbilities()) {
                            if (a.getName().equalsIgnoreCase(commands[1].replace("_", " "))) {
                                a.setUnlocked(true);
                                Handler.get().sendMsg("Unlocked ability: " + commands[1]);
                                break;
                            }
                        }
                    } else {
                        Handler.get().sendMsg("Unknown command: '" + commands[1] + "'. Syntax: 'unlock {ability_Name} or 'unlock all'.");
                    }
                } else {
                    Handler.get().sendMsg("Invalid number of arguments provided. Syntax: 'unlock {ability_Name} or 'unlock all'.");
                }
                break;
            case RESET:
                if (commands.length == 2) {
                    if (commands[1].equalsIgnoreCase("inv")) {
                        Handler.get().getInventory().empty();
                    } else if (commands[1].equalsIgnoreCase("equip")) {
                        Handler.get().getEquipment().empty();
                    } else if (commands[1].equalsIgnoreCase("abilities")) {
                        for (Ability a : Handler.get().getAbilityManager().getAllAbilities()) {
                            a.setUnlocked(false);
                        }
                        for (AbilitySlot as : Handler.get().getAbilityManager().getAbilityHUD().getSlottedAbilities()) {
                            as.setAbility(null);
                        }
                    }
                }
                break;
            default:
                Handler.get().sendMsg("Could not parse command.");
                break;
        }
    }
}
