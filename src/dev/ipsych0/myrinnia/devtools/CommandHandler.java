package dev.ipsych0.myrinnia.devtools;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilities.ui.abilityhud.AbilitySlot;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.skills.Skill;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.utils.MapLoader;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.io.Serializable;

public class CommandHandler implements Serializable {


    private static final long serialVersionUID = 1908102828227319857L;

    public void handle(Commands firstCommand, String... commands) {
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
            case SEARCH:
                try {
                    if (commands.length < 1) {
                        throw new Exception();
                    }

                    // Append any spaces in the command search
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < commands.length; i++) {
                        sb.append(commands[i]);
                    }

                    // Find matching items
                    boolean found = false;
                    for (Item i : Item.items) {
                        try {
                            if (i.getName().toLowerCase().contains(sb.toString().toLowerCase())) {
                                found = true;
                                Handler.get().sendMsg(i.getName() + ": " + i.getId() + ".");
                            }
                        } catch (Exception ignored) {
                        }
                    }

                    // If no results found, show user
                    if (!found) {
                        throw new IllegalArgumentException();
                    }
                } catch (IllegalArgumentException e) {
                    Handler.get().sendMsg("No item with that name could be found.");
                } catch (Exception e) {
                    Handler.get().sendMsg("Error. Syntax: 'search {partial-item-name}'.");
                }
                break;
            case SPAWN:
                try {
                    if (commands.length < 1) {
                        throw new Exception();
                    }

                    if (commands.length == 5) {
                        String className = commands[1];
                        int level = Integer.parseInt(commands[2]);
                        int x = Integer.parseInt(commands[3]) * 32;
                        int y = Integer.parseInt(commands[4]) * 32;
                        Entity e = MapLoader.loadEntity(Handler.get().getWorld(), className, x, y, 32, 32, className, level, null, null, "malachiteThug1", null, null);
                        if (e != null) {
                            Handler.get().getWorld().getEntityManager().addRuntimeEntity(e, false);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    Handler.get().sendMsg("Item with ID: '" + commands[1] + "' does not exist.");
                } catch (Exception e) {
                    Handler.get().sendMsg("Error. Syntax: 'spawn {monsterClass} {level} {x} {y} '.");
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
                        // If we are resetting combat, make sure we also reset the base damage and damage exponent
                        if (s.toString().equalsIgnoreCase("Combat")) {
                            Handler.get().getPlayer().setBaseDamage(1);
                            Handler.get().getPlayer().setLevelExponent(1.1);
                            resetSkillPoints();
                        }

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
                            Handler.get().getPlayer().setSpeed(Double.parseDouble(commands[2]));
                        } else if (commands[1].equalsIgnoreCase("atkspd")) {
                            Handler.get().getPlayer().setAttackSpeed(Double.parseDouble(commands[2]));
                        } else if (commands[1].equalsIgnoreCase("hp")) {
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
                    } else if (commands[1].equalsIgnoreCase("collision")) {
                        Handler.debugCollision = !Handler.debugCollision;
                    } else if (commands[1].equalsIgnoreCase("zonetiles")) {
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
                        for (int i = 0; i < Handler.get().getAbilityManager().getAllAbilities().size(); i++) {
                            if (i == 10)
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
            case COMPLETE:
                if (commands.length == 2) {
                    QuestList quest;
                    try {
                        quest = QuestList.valueOf(commands[1]);
                    } catch (Exception e) {
                        Handler.get().sendMsg(commands[1] + " is not a quest.");
                        break;
                    }

                    Handler.get().getQuest(quest).setState(QuestState.COMPLETED);
                    break;
                }
            default:
                Handler.get().sendMsg("Could not parse command.");
                break;
        }
    }


    private void resetSkillPoints() {
        for (CharacterStats stat : CharacterStats.values()) {
            if (stat == CharacterStats.Combat)
                continue;
            if (stat == CharacterStats.Magic || stat == CharacterStats.Melee || stat == CharacterStats.Ranged) {
                Handler.get().getCharacterUI().addBaseStatPoints(stat.getLevel());
            } else {
                Handler.get().getCharacterUI().addElementalStatPoints(stat.getLevel());
            }
            stat.setLevel(0);
        }
    }
}
