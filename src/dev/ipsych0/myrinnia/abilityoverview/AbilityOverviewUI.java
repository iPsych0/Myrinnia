package dev.ipsych0.myrinnia.abilityoverview;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilityhud.AbilitySlot;
import dev.ipsych0.myrinnia.abilityhud.AbilityTooltip;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AbilityOverviewUI implements Serializable {


    private static final long serialVersionUID = -8142886045140646059L;
    private int x, y, width, height;
    private List<AbilityOverviewUIButton> uiButtons = new ArrayList<>();
    private Rectangle bounds;
    private Rectangle clickableArea;

    public static boolean isOpen;
    public static boolean escapePressed;
    public static boolean hasBeenPressed;

    private Rectangle innerUI;
    private Rectangle exit;
    private List<AbilitySlot> abilitySlots;
    private List<Ability> displayedAbilities;
    private Ability currentSelectedAbility;
    private static final int MAX_SLOTS_VERTICAL = 9;

    private AbilityOverviewUIButton lastCombatTab;
    private AbilityOverviewUIButton lastElementTab;

    private AbilityTooltip abilityTooltip;
    private static Color selectedColor = new Color(0, 255, 255, 62);

    public AbilityOverviewUI() {
        this.width = 460;
        this.height = 460;
        this.x = Handler.get().getWidth() / 2 - width / 2;
        this.y = Handler.get().getHeight() / 2 - height / 2;
        this.bounds = new Rectangle(x, y, width, height);
        this.clickableArea = new Rectangle(bounds.x - 64, bounds.y - 64, width + 128, height + 128);


        uiButtons.add(new AbilityOverviewUIButton(x + width / 2 - (width / 4) - 32, y + 32, CharacterStats.Melee));
        uiButtons.add(new AbilityOverviewUIButton(x + width / 2 - 32, y + 32, CharacterStats.Ranged));
        uiButtons.add(new AbilityOverviewUIButton(x + width / 2 + (width / 4) - 32, y + 32, CharacterStats.Magic));

        uiButtons.add(new AbilityOverviewUIButton(x + width, y + 32, CharacterStats.Fire));
        uiButtons.add(new AbilityOverviewUIButton(x + width, y + 64, CharacterStats.Air));
        uiButtons.add(new AbilityOverviewUIButton(x + width, y + 96, CharacterStats.Water));
        uiButtons.add(new AbilityOverviewUIButton(x + width, y + 128, CharacterStats.Earth));

        innerUI = new Rectangle(x + 32, y + 96, width - 64, height - 128);
        exit = new Rectangle(x + width - 35, y + 10, 24, 24);

        // Initially fill the list with Melee+Fire abilities by default
        displayedAbilities = Handler.get().getAbilityManager().getAbilityByStyleAndElement(CharacterStats.Melee, CharacterStats.Fire);
        lastCombatTab = uiButtons.get(0); // Melee
        lastElementTab = uiButtons.get(3); // Fire

        // Fill the slots
        abilitySlots = new ArrayList<>(displayedAbilities.size());
        updateSlots();

        abilityTooltip = new AbilityTooltip(x - 160, y, 160, 224);
    }

    public void tick() {
        if (isOpen) {
            Rectangle mouse = Handler.get().getMouse();

            // Close when escape pressed
            if (Handler.get().getKeyManager().escape && escapePressed || exit.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                exit();
                return;
            }

            for (AbilityOverviewUIButton uiButton : uiButtons) {
                // Change list of abilities when clicking a new category
                if (uiButton.getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                    hasBeenPressed = false;
                    displayedAbilities.clear();
                    // Handle combat style buttons
                    if (uiButton.getStat() == CharacterStats.Melee || uiButton.getStat() == CharacterStats.Ranged || uiButton.getStat() == CharacterStats.Magic) {
                        displayedAbilities = Handler.get().getAbilityManager().getAbilityByStyleAndElement(uiButton.getStat(), lastElementTab.getStat());
                        lastCombatTab = uiButton;
                    } else {
                        // Handle element buttons
                        displayedAbilities = Handler.get().getAbilityManager().getAbilityByStyleAndElement(lastCombatTab.getStat(), uiButton.getStat());
                        lastElementTab = uiButton;
                    }
                    updateSlots();
                }
            }

            for (AbilitySlot as : abilitySlots) {
                // Check for dragging an ability
                if (as.getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && currentSelectedAbility == null) {
                    hasBeenPressed = false;
                    if (as.getAbility().isUnlocked()) {
                        currentSelectedAbility = as.getAbility();
                    }
                    // If we were dragging and let go, check the slot to put the ability
                } else if (currentSelectedAbility != null && !Handler.get().getMouseManager().isLeftPressed()) {
                    boolean alreadyHasAbility = false;
                    AbilitySlot foundSlot = null;
                    for (AbilitySlot hudSlot : Handler.get().getAbilityManager().getAbilityHUD().getSlottedAbilities()) {
                        if (hudSlot.getBounds().contains(mouse) && foundSlot == null) {
                            foundSlot = hudSlot;
                        }
                        if (hudSlot.getAbility() != null && hudSlot.getAbility() == currentSelectedAbility) {
                            alreadyHasAbility = true;
                            break;
                        }
                    }
                    if (!alreadyHasAbility && foundSlot != null) {
                        foundSlot.setAbility(currentSelectedAbility);
                    }
                    currentSelectedAbility = null;
                    hasBeenPressed = false;
                }
            }
        }
    }

    private void updateSlots() {
        abilitySlots = new ArrayList<>(displayedAbilities.size());
        int index = 0;
        double verticalColumns = Math.ceil((double)displayedAbilities.size() / (double)MAX_SLOTS_VERTICAL);
        if(abilitySlots.size() > verticalColumns * MAX_SLOTS_VERTICAL){
            try {
                throw new Exception();
            } catch (Exception e) {
                System.err.println("TODO: Add page browsing in Ability Window UI when there are more than 99 matches!");
                System.exit(1);
            }
        }
        for (int i = 0; i < verticalColumns; i++) {
            // If we've reached the last column
            if(i == verticalColumns - 1){
                // If we have less than the max number of slots for that column, only fill the remaining ones
                if(displayedAbilities.size() % MAX_SLOTS_VERTICAL != 0) {
                    for (int j = 0; j < displayedAbilities.size() % MAX_SLOTS_VERTICAL; j++) {
                        abilitySlots.add(new AbilitySlot(displayedAbilities.get(index++), x + 56 + (i * 32), y + 120 + (j * 32)));
                    }
                    // Otherwise, fill to the vertical max
                }else{
                    for (int j = 0; j < MAX_SLOTS_VERTICAL; j++) {
                        abilitySlots.add(new AbilitySlot(displayedAbilities.get(index++), x + 56 + (i * 32), y + 120 + (j * 32)));
                    }
                }
                // For the other columns, fill them to the vertical max
            }else {
                for (int j = 0; j < MAX_SLOTS_VERTICAL; j++) {
                    abilitySlots.add(new AbilitySlot(displayedAbilities.get(index++), x + 56 + (i * 32), y + 120 + (j * 32)));
                }
            }
        }
    }

    public static void exit() {
        escapePressed = false;
        isOpen = false;
        hasBeenPressed = false;
    }

    public void render(Graphics g) {
        if (isOpen) {
            g.drawImage(Assets.shopWindow, x, y, width, height, null);
            g.drawImage(Assets.shopWindow, innerUI.x, innerUI.y, innerUI.width, innerUI.height, null);
            Text.drawString(g, "Ability Overview", x + width / 2, y + 16, true, Color.YELLOW, Assets.font14);

            Rectangle mouse = Handler.get().getMouse();

            // test stuff close button
            if (exit.contains(mouse))
                g.drawImage(Assets.genericButton[0], exit.x, exit.y, exit.width, exit.height, null);
            else
                g.drawImage(Assets.genericButton[1], exit.x, exit.y, exit.width, exit.height, null);
            Text.drawString(g, "X", exit.x + 11, exit.y + 11, true, Color.YELLOW, Assets.font20);

            for (AbilityOverviewUIButton button : uiButtons) {
                button.render(g);
            }

            for (AbilitySlot as : abilitySlots) {
                as.render(g);
                if (as.getBounds().contains(mouse)) {
                    abilityTooltip.render(g, as.getAbility());
                }
            }

            if (currentSelectedAbility != null) {
                currentSelectedAbility.render(g, Handler.get().getMouse().x, Handler.get().getMouse().y);
            }

            g.setColor(selectedColor);
            g.fillRoundRect(lastCombatTab.getBounds().x, lastCombatTab.getBounds().y, lastCombatTab.getBounds().width, lastCombatTab.getBounds().height, 4, 4);
            g.fillRoundRect(lastElementTab.getBounds().x, lastElementTab.getBounds().y, lastElementTab.getBounds().width, lastElementTab.getBounds().height, 4, 4);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Rectangle getClickableArea() {
        return clickableArea;
    }

    public Ability getCurrentSelectedAbility() {
        return currentSelectedAbility;
    }
}
