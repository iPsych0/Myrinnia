package dev.ipsych0.myrinnia.abilities.ui.abilityhud;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.StatusTooltip;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Colors;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AbilityHUD implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 2357471540127327333L;
    private static final int MAX_SLOTS = 10;
    private static final int SLOT_PADDING = 8;
    private List<AbilitySlot> slottedAbilities = new ArrayList<>();
    private static int x, y, width, height;
    private AbilityTooltip abilityTooltip;
    public static boolean hasBeenPressed;
    public static boolean hasBeenTyped;
    public static char pressedKey;
    private static boolean locked = true;
    private static Ability selectedAbility;
    private static AbilitySlot oldSlot;
    private UIImageButton lockButton, unlockButton;
    private Rectangle bounds;
    private UIManager uiManager;
    private StatusTooltip statusTooltip;

    // Initialize default keys
    private Map<Character, Integer> keyBindMap = new LinkedHashMap<>(){{
        put('0', 9);
        put('1', 0);
        put('2', 1);
        put('3', 2);
        put('4', 3);
        put('5', 4);
        put('6', 5);
        put('7', 6);
        put('8', 7);
        put('9', 8);
    }};

    public AbilityHUD() {
        width = x + ItemSlot.SLOTSIZE * MAX_SLOTS + ((MAX_SLOTS - 1) * SLOT_PADDING);
        height = y + ItemSlot.SLOTSIZE;
        x = Handler.get().getWidth() / 2 - (width / 2);
        y = Handler.get().getHeight() - ItemSlot.SLOTSIZE - 8;

        for (int i = 0; i < MAX_SLOTS; i++) {
            slottedAbilities.add(new AbilitySlot(null, x + (i * 32) + (i * SLOT_PADDING), y));
        }

        abilityTooltip = new AbilityTooltip(0, Handler.get().getHeight() / 2 - 64);

        lockButton = new UIImageButton(x + width + 8, y, 16, 16, Assets.genericButton);
        unlockButton = new UIImageButton(x + width + 8, y + 16, 16, 16, Assets.genericButton);
        bounds = new Rectangle(x, y, width + 16, height);

        uiManager = new UIManager();

        uiManager.addObject(lockButton);
        uiManager.addObject(unlockButton);

        statusTooltip = new StatusTooltip(0, Handler.get().getHeight() / 2 - 32);
    }

    public boolean compatibleWeaponType(Ability selectedAbility, boolean sendMsg) {
        if (Handler.get().getPlayer().getMainHandWeapon() == null) {
            if (sendMsg) {
                Handler.get().sendMsg("You must wield a " + selectedAbility.getCombatStyle().toString().toLowerCase() + " weapon to use this ability.");
            }
            return false;
        }
        if (selectedAbility.getCombatStyle() == CharacterStats.Melee) {
            if (!Handler.get().getPlayer().getMainHandWeapon().isType(ItemType.MELEE_WEAPON)) {
                if (sendMsg) {
                    Handler.get().sendMsg("You must wield a " + selectedAbility.getCombatStyle().toString().toLowerCase() + " weapon to use this ability.");
                }
                return false;
            }
        } else if (selectedAbility.getCombatStyle() == CharacterStats.Ranged) {
            if (!Handler.get().getPlayer().getMainHandWeapon().isType(ItemType.RANGED_WEAPON)) {
                if (sendMsg) {
                    Handler.get().sendMsg("You must wield a " + selectedAbility.getCombatStyle().toString().toLowerCase() + " weapon to use this ability.");
                }
                return false;
            }
        } else if (selectedAbility.getCombatStyle() == CharacterStats.Magic) {
            if (!Handler.get().getPlayer().getMainHandWeapon().isType(ItemType.MAGIC_WEAPON)) {
                if (sendMsg) {
                    Handler.get().sendMsg("You must wield a " + selectedAbility.getCombatStyle().toString().toLowerCase() + " weapon to use this ability.");
                }
                return false;
            }
        }

        return true;
    }

    /**
     * Handles the pressed ability slot button
     */
    private void handleKeyEvent() {
        // Get the right index in the ability slots
        // Funky calculation. If 0 is pressed, it should be the last slot instead of first, otherwise the slot is 1-9 pressed -1 by index
        Integer index = keyBindMap.get(pressedKey);
        if(index == null)
            return;
        Ability selectedAbility = slottedAbilities.get(index).getAbility();
        if (selectedAbility != null) {

            if (!compatibleWeaponType(selectedAbility, true)) {
                return;
            }

            for (AbilitySlot as : slottedAbilities) {
                if (as.getAbility() != null) {
                    if (as.getAbility().isChanneling()) {
                        return;
                    } else if (as.getAbility().isSelectable() && as.getAbility().isSelected()) {
                        as.getAbility().setSelected(false);
                        as.getAbility().setActivated(false);
                    }
                }
            }
            if (selectedAbility.isOnCooldown()) {
                Handler.get().sendMsg(selectedAbility.getName() + " is on cooldown.");
            } else {
                if (selectedAbility.isSelectable()) {
                    selectedAbility.setSelected(true);
                }
                if (!Handler.get().getAbilityManager().getActiveAbilities().contains(selectedAbility)) {
                    Handler.get().getAbilityManager().getActiveAbilities().add(selectedAbility);
                    selectedAbility.setCaster(Handler.get().getPlayer());
                }
            }
        }
    }

    private void handleClickEvent(AbilitySlot slot) {
        if (locked) {
            Ability selectedAbility = slot.getAbility();
            if (selectedAbility != null) {

                if (!compatibleWeaponType(selectedAbility, true)) {
                    return;
                }

                for (AbilitySlot as : slottedAbilities) {
                    if (as.getAbility() != null) {
                        if (as.getAbility().isChanneling()) {
                            return;
                        }
                    }
                }
                if (selectedAbility.isOnCooldown()) {
                    Handler.get().sendMsg(selectedAbility.getName() + " is on cooldown.");
                } else {
                    if (selectedAbility.isSelectable()) {
                        selectedAbility.setSelected(true);
                    }
                    if (!Handler.get().getAbilityManager().getActiveAbilities().contains(selectedAbility)) {
                        Handler.get().getAbilityManager().getActiveAbilities().add(selectedAbility);
                        selectedAbility.setCaster(Handler.get().getPlayer());
                    }
                }
            }
        }
    }

    private void handleDrag(AbilitySlot abilitySlot) {
        if (!locked) {
            if (Handler.get().getAbilityOverviewUI().getCurrentSelectedAbility() == null) {
                if (Handler.get().getMouseManager().isLeftPressed()) {
                    if (selectedAbility == null) {
                        selectedAbility = abilitySlot.getAbility();
                        abilitySlot.setAbility(null);
                        oldSlot = abilitySlot;
                        return;
                    }
                } else if (!Handler.get().getMouseManager().isLeftPressed() && selectedAbility != null) {
                    for (AbilitySlot as : slottedAbilities) {
                        if (as.getBounds().contains(Handler.get().getMouse())) {
                            if (as.getAbility() == null) {
                                as.setAbility(selectedAbility);
                                selectedAbility = null;
                            } else {
                                oldSlot.setAbility(as.getAbility());
                                as.setAbility(selectedAbility);
                                selectedAbility = null;
                            }
                        }
                    }
                }
            }
            hasBeenPressed = false;
        }
    }

    public static void unlock() {
        AbilityHUD.locked = false;
    }

    public static void lock() {
        AbilityHUD.locked = true;
    }

    public void tick() {
        Rectangle mouse = Handler.get().getMouse();

        uiManager.tick();

        // Check for input
        if (hasBeenTyped) {
            handleKeyEvent();
            hasBeenTyped = false;
        }

        if (lockButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            if (!locked) {
                Handler.get().sendMsg("Ability bar locked.");
            }
            hasBeenPressed = false;
            AbilityHUD.locked = true;
        } else if (unlockButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            if (locked) {
                Handler.get().sendMsg("Ability bar unlocked.");
            }
            hasBeenPressed = false;
            AbilityHUD.locked = false;
        }

        // Tick the tooltip and other bars
        for (AbilitySlot as : slottedAbilities) {
            as.tick();
            if (as.getBounds().contains(mouse)) {
                abilityTooltip.tick();
                if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                    if (!Handler.get().getMouseManager().isDragged()) {
                        handleClickEvent(as);
                    }
                    hasBeenPressed = false;
                }
                handleDrag(as);
            }
        }

    }

    public void render(Graphics2D g) {
        Rectangle mouse = Handler.get().getMouse();

        uiManager.render(g);

        int index = 0;
        List<Character> chars = new ArrayList<>(keyBindMap.keySet());
        for (AbilitySlot as : slottedAbilities) {
            as.render(g, chars.get(index++));
            // Render the tooltip when hovering over an ability
            if (as.getBounds().contains(mouse)) {
                if (as.getAbility() != null) {
                    abilityTooltip.render(g, as.getAbility());
                }
            }
            if (!locked) {
                g.setColor(Color.YELLOW);
                g.drawRect(as.getBounds().x + 1, as.getBounds().y + 1, as.getBounds().width - 4, as.getBounds().height - 3);
            }
        }

        if (selectedAbility != null) {
            selectedAbility.renderIcon(g, mouse.x, mouse.y);
        }

        if (lockButton.contains(mouse)) {
            g.setColor(Colors.hoverLockedColor);
        } else {
            g.setColor(Colors.lockedColor);
        }
        g.fillRect(lockButton.x, lockButton.y, lockButton.width, lockButton.height);
        g.drawImage(Assets.locked, lockButton.x, lockButton.y, lockButton.width, lockButton.height, null);
        g.setColor(Color.BLACK);
        g.drawRect(lockButton.x, lockButton.y, lockButton.width, lockButton.height);

        if (unlockButton.contains(mouse)) {
            g.setColor(Colors.hoverUnlockedColor);
        } else {
            g.setColor(Colors.unlockedColor);
        }
        g.fillRect(unlockButton.x, unlockButton.y, unlockButton.width, unlockButton.height);
        g.drawImage(Assets.unlocked, unlockButton.x, unlockButton.y, unlockButton.width, unlockButton.height, null);
        g.setColor(Color.BLACK);
        g.drawRect(unlockButton.x, unlockButton.y, unlockButton.width, unlockButton.height);

    }

    public List<AbilitySlot> getSlottedAbilities() {
        return slottedAbilities;
    }

    public void setSlottedAbilities(List<AbilitySlot> slottedAbilities) {
        this.slottedAbilities = slottedAbilities;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public StatusTooltip getStatusTooltip() {
        return statusTooltip;
    }

    public Map<Character, Integer> getKeyBindMap() {
        return keyBindMap;
    }

    public void setKeyBindMap(Map<Character, Integer> keyBindMap) {
        this.keyBindMap = keyBindMap;
    }
}
