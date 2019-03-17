package dev.ipsych0.myrinnia.input;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilityhud.AbilityHUD;
import dev.ipsych0.myrinnia.abilityhud.AbilitySlot;
import dev.ipsych0.myrinnia.abilityoverview.AbilityOverviewUI;
import dev.ipsych0.myrinnia.character.CharacterUI;
import dev.ipsych0.myrinnia.crafting.ui.CraftingUI;
import dev.ipsych0.myrinnia.devtools.DevToolUI;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.chatwindow.ChatWindow;
import dev.ipsych0.myrinnia.equipment.EquipmentWindow;
import dev.ipsych0.myrinnia.items.ui.InventoryWindow;
import dev.ipsych0.myrinnia.quests.QuestHelpUI;
import dev.ipsych0.myrinnia.quests.QuestUI;
import dev.ipsych0.myrinnia.shops.AbilityShopWindow;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.skills.ui.SkillsOverviewUI;
import dev.ipsych0.myrinnia.skills.ui.SkillsUI;
import dev.ipsych0.myrinnia.ui.TextBox;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

public class KeyManager implements KeyListener, Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -1536796173877883719L;
    private boolean[] keys, justPressed, cantPress;
    public boolean up, down, left, right;
    public boolean chat;
    public boolean pause;
    public boolean talk;
    public boolean escape;
    public static boolean typingFocus = false;
    private int lastUIKeyPressed = -1;
    private static int upKey, downKey, leftKey, rightKey, chatWindowKey, questWindowKey, skillsWindowKey,
    statsWindowKey, mapWindowKey, inventoryWindowKey, interactKey, pauseKey, abilityWindowKey;

    public KeyManager() {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public void loadKeybinds(){
        upKey = KeyEvent.getExtendedKeyCodeForChar(Handler.get().loadProperty("upKey").charAt(0));
        downKey = KeyEvent.getExtendedKeyCodeForChar(Handler.get().loadProperty("downKey").charAt(0));
        leftKey = KeyEvent.getExtendedKeyCodeForChar(Handler.get().loadProperty("leftKey").charAt(0));
        rightKey = KeyEvent.getExtendedKeyCodeForChar(Handler.get().loadProperty("rightKey").charAt(0));
        chatWindowKey = KeyEvent.getExtendedKeyCodeForChar(Handler.get().loadProperty("chatWindowKey").charAt(0));
        questWindowKey = KeyEvent.getExtendedKeyCodeForChar(Handler.get().loadProperty("questWindowKey").charAt(0));
        skillsWindowKey = KeyEvent.getExtendedKeyCodeForChar(Handler.get().loadProperty("skillsWindowKey").charAt(0));
        statsWindowKey = KeyEvent.getExtendedKeyCodeForChar(Handler.get().loadProperty("statsWindowKey").charAt(0));
        mapWindowKey = KeyEvent.getExtendedKeyCodeForChar(Handler.get().loadProperty("mapWindowKey").charAt(0));
        inventoryWindowKey = KeyEvent.getExtendedKeyCodeForChar(Handler.get().loadProperty("inventoryKey").charAt(0));
        interactKey = KeyEvent.getExtendedKeyCodeForChar(Handler.get().loadProperty("interactKey").charAt(0));
        pauseKey = KeyEvent.getExtendedKeyCodeForChar(Handler.get().loadProperty("pauseKey").charAt(0));
        abilityWindowKey = KeyEvent.getExtendedKeyCodeForChar(Handler.get().loadProperty("abilitiesKey").charAt(0));
    }

    public void tick() {

        if (!typingFocus) {
            // Movement keys
            up = keys[upKey];
            down = keys[downKey];
            left = keys[leftKey];
            right = keys[rightKey];

            if (up || down || left || right) {
                Player.isMoving = true;
            }

            if (!up && !down && !left && !right) {
                Player.isMoving = false;
            }


            // Interaction keys
            chat = keys[chatWindowKey];
            pause = keys[KeyEvent.VK_P];
            talk = keys[interactKey];

            // UI keys
            escape = keys[KeyEvent.VK_ESCAPE];
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!typingFocus) {
            if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) {
                return;
            }

            keys[e.getKeyCode()] = true;

            if (e.getKeyCode() == pauseKey) {
                Player.debugButtonPressed = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                lastUIKeyPressed = -1;
                SkillsUI.escapePressed = true;
                QuestUI.escapePressed = true;
                ShopWindow.escapePressed = true;
                CharacterUI.escapePressed = true;
                AbilityOverviewUI.escapePressed = true;
            }

            // Inventory toggle
            if (e.getKeyCode() == inventoryWindowKey && !ShopWindow.isOpen) {
                InventoryWindow.isOpen = !InventoryWindow.isOpen;
                EquipmentWindow.isOpen = !EquipmentWindow.isOpen;
            }

            // Chat window toggle
            if (e.getKeyCode() == chatWindowKey) {
                ChatWindow.chatIsOpen = !ChatWindow.chatIsOpen;
            }

            // QuestWindow toggle
            if (e.getKeyCode() == questWindowKey) {
                lastUIKeyPressed = questWindowKey;
                if (!QuestUI.isOpen) {
                    QuestUI.isOpen = true;
                    CharacterUI.isOpen = false;
                    CraftingUI.isOpen = false;
                    SkillsUI.isOpen = false;
                    SkillsOverviewUI.isOpen = false;
                } else {
                    QuestUI.isOpen = false;
                    QuestHelpUI.isOpen = false;
                    QuestUI.renderingQuests = false;
                }
            }

            // CharacterUI toggle
            if (e.getKeyCode() == statsWindowKey) {
                lastUIKeyPressed = statsWindowKey;
                if (!CharacterUI.isOpen) {
                    CharacterUI.isOpen = true;
                    QuestUI.isOpen = false;
                    QuestHelpUI.isOpen = false;
                    QuestUI.renderingQuests = false;
                    CraftingUI.isOpen = false;
                    SkillsUI.isOpen = false;
                    SkillsOverviewUI.isOpen = false;
                } else {
                    CharacterUI.isOpen = false;
                }
            }

            // Skills window toggle
            if (e.getKeyCode() == skillsWindowKey) {
                lastUIKeyPressed = skillsWindowKey;
                if (!SkillsUI.isOpen) {
                    SkillsUI.isOpen = true;
                    CharacterUI.isOpen = false;
                    QuestUI.isOpen = false;
                    QuestHelpUI.isOpen = false;
                    QuestUI.renderingQuests = false;
                    CraftingUI.isOpen = false;
                } else {
                    SkillsUI.isOpen = false;
                    SkillsOverviewUI.isOpen = false;
                }
            }

            if (e.getKeyCode() == abilityWindowKey) {
                if (ShopWindow.lastOpenedWindow != null) {
                    ShopWindow.lastOpenedWindow.exit();
                }
                if (AbilityShopWindow.lastOpenedWindow != null) {
                    AbilityShopWindow.lastOpenedWindow.exit();
                }
                Handler.get().getBankUI().exit();
                Handler.get().getCraftingUI().exit();
                AbilityOverviewUI.isOpen = !AbilityOverviewUI.isOpen;
            }

            // Toggle dev tool window
            if (e.getKeyCode() == KeyEvent.VK_T) {
                if (!DevToolUI.isOpen) {
                    TextBox.focus = true;
                    typingFocus = true;
                    DevToolUI.initialized = false;
                }
                DevToolUI.isOpen = !DevToolUI.isOpen;
                TextBox.isOpen = !TextBox.isOpen;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!typingFocus) {
            if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) {
                return;
            }
            keys[e.getKeyCode()] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!typingFocus) {
            if (Character.isDigit(e.getKeyChar())) {
                // Invalidate input while channeling
                for (AbilitySlot as : Handler.get().getAbilityManager().getAbilityHUD().getSlottedAbilities()) {
                    if (as.getAbility() != null) {
                        if (as.getAbility().isChanneling()) {
                            return;
                        }
                    }
                }

                // Set the pressed key for the ability bar
                AbilityHUD.hasBeenTyped = true;
                AbilityHUD.pressedKey = e.getKeyChar();
            }
            if (e.getKeyChar() == interactKey && Entity.isCloseToNPC) {
                Player.hasInteracted = false;
            }
        }
    }

    public void setTextBoxTyping(boolean textBoxTyping) {
        KeyManager.typingFocus = textBoxTyping;
    }

    public int getLastUIKeyPressed() {
        return lastUIKeyPressed;
    }

    public void setLastUIKeyPressed(int lastUIKeyPressed) {
        this.lastUIKeyPressed = lastUIKeyPressed;
    }

}
