package dev.ipsych0.myrinnia.abilityhud;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemSlot;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class AbilityHUD implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 2357471540127327333L;
    private static final int MAX_SLOTS = 10;
    private ArrayList<AbilitySlot> slottedAbilities = new ArrayList<>();
    private HPBar hpBar;
    private XPBar xpBar;
    private static int x, y, width, height;
    private AbilityTooltip abilityTooltip;
    public static boolean hasBeenPressed;
    public static boolean hasBeenTyped;
    public static char pressedKey;
    public static boolean locked = true;
    private static Ability selectedAbility;
    private static AbilitySlot oldSlot;
    private Rectangle lockButton, unlockButton;
    private static Color lockedColor = new Color(192, 8, 0, 192),
            unlockedColor = new Color(8, 148, 0, 192),
            hoverLockedColor = new Color(250, 8, 0, 192),
            hoverUnlockedColor = new Color(8, 192, 0, 192);
    private Rectangle bounds;

    public AbilityHUD() {
        width = x + ItemSlot.SLOTSIZE * MAX_SLOTS;
        height = y + ItemSlot.SLOTSIZE;
        x = Handler.get().getWidth() / 2 - (width / 2);
        y = Handler.get().getHeight() - ItemSlot.SLOTSIZE - 8;

        for (int i = 0; i < MAX_SLOTS; i++) {
            slottedAbilities.add(new AbilitySlot(null, x + (i * 32), y));
        }

        // Add HP Bar after the last abilitySlot
//		hpBar = new HPBar(Handler.get(), slottedAbilities.get(slottedAbilities.size()-1).getX() + ItemSlot.SLOTSIZE, y);
        // Add XP Bar after HP Bar
//		xpBar = new XPBar(Handler.get(), hpBar.getX() + hpBar.getWidth(), y);

        abilityTooltip = new AbilityTooltip(0, Handler.get().getHeight() / 2, 160, 224);

        lockButton = new Rectangle(x + width + 1, y, 16, 16);
        unlockButton = new Rectangle(x + width + 1, y + 16, 16, 16);
        bounds = new Rectangle(x, y, width + 16, height);
//		this.width = x + xpBar.getX() + xpBar.getWidth();
//		this.height = y + ItemSlot.SLOTSIZE;
    }

    /**
     * Handles the pressed ability slot button
     */
    private void handleKeyEvent() {
        // Get the right index in the ability slots
        // Funky calculation. If 0 is pressed, it should be the last slot instead of first, otherwise the slot is 1-9 pressed -1 by index
        Ability selectedAbility = slottedAbilities.get(pressedKey == 48 ? slottedAbilities.size() - 1 : (pressedKey - 49)).getAbility();
        if (selectedAbility != null) {
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
                return;
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
                for (AbilitySlot as : slottedAbilities) {
                    if (as.getAbility() != null) {
                        if (as.getAbility().isChanneling()) {
                            return;
                        }
                    }
                }
                if (selectedAbility.isOnCooldown()) {
                    Handler.get().sendMsg(selectedAbility.getName() + " is on cooldown.");
                    return;
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

        // Check for input
        if (hasBeenTyped) {
            handleKeyEvent();
            hasBeenTyped = false;
        }

        if (lockButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            if(!locked){
                Handler.get().sendMsg("Ability bar locked.");
            }
            hasBeenPressed = false;
            AbilityHUD.locked = true;
        }
        else if (unlockButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            if(locked){
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
//		hpBar.tick();
//		xpBar.tick();
    }

    public void render(Graphics g) {
        Rectangle mouse = Handler.get().getMouse();

        int index = 0;
        for (AbilitySlot as : slottedAbilities) {
            // Render the slots from 1-9, with the final slot 0
            if (index++ == 9) {
                as.render(g, 0);
            } else {
                as.render(g, index);
            }
            // Render the tooltip when hovering over an ability
            if (as.getBounds().contains(mouse)) {
                if (as.getAbility() != null) {
                    abilityTooltip.render(g, as.getAbility());
                }
            }
            if(!locked) {
                g.setColor(Color.YELLOW);
                g.drawRect(as.getX(), as.getY(), 32, 32);
            }
        }

        if (selectedAbility != null) {
            selectedAbility.render(g, mouse.x, mouse.y);
        }

        if (lockButton.contains(mouse)) {
            g.drawImage(Assets.genericButton[0], lockButton.x, lockButton.y, lockButton.width, lockButton.height, null);
        } else {
            g.drawImage(Assets.genericButton[1], lockButton.x, lockButton.y, lockButton.width, lockButton.height, null);
        }

        if (unlockButton.contains(mouse)) {
            g.drawImage(Assets.genericButton[0], unlockButton.x, unlockButton.y, unlockButton.width, unlockButton.height, null);
        } else {
            g.drawImage(Assets.genericButton[1], unlockButton.x, unlockButton.y, unlockButton.width, unlockButton.height, null);
        }

        if (lockButton.contains(mouse)) {
            g.setColor(hoverLockedColor);
        } else {
            g.setColor(lockedColor);
        }
        g.fillRoundRect(lockButton.x, lockButton.y, lockButton.width, lockButton.height, 2, 2);
        g.drawImage(Assets.locked, lockButton.x, lockButton.y, lockButton.width, lockButton.height, null);

        if (unlockButton.contains(mouse)) {
            g.setColor(hoverUnlockedColor);
        } else {
            g.setColor(unlockedColor);
        }
        g.fillRoundRect(unlockButton.x, unlockButton.y, unlockButton.width, unlockButton.height, 2, 2);
        g.drawImage(Assets.unlocked, unlockButton.x, unlockButton.y, unlockButton.width, unlockButton.height, null);

//		hpBar.render(g);
//		xpBar.render(g);
    }

    public ArrayList<AbilitySlot> getSlottedAbilities() {
        return slottedAbilities;
    }

    public void setSlottedAbilities(ArrayList<AbilitySlot> slottedAbilities) {
        this.slottedAbilities = slottedAbilities;
    }

    public HPBar getHpBar() {
        return hpBar;
    }

    public void setHpBar(HPBar hpBar) {
        this.hpBar = hpBar;
    }

    public XPBar getXpBar() {
        return xpBar;
    }

    public void setXpBar(XPBar xpBar) {
        this.xpBar = xpBar;
    }

    public Rectangle getBounds() {
        return bounds;
    }

}
