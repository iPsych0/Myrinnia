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
    public boolean pickUp;
    public boolean position;
    public boolean talk;
    public boolean escape;
    public static boolean typingFocus = false;
    private int lastUIKeyPressed = -1;

    public KeyManager() {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public void tick() {

        if (!typingFocus) {
            // Movement keys
            up = keys[KeyEvent.VK_W];
            down = keys[KeyEvent.VK_S];
            left = keys[KeyEvent.VK_A];
            right = keys[KeyEvent.VK_D];

            if (up || down || left || right) {
                Player.isMoving = true;
            }

            if (!up && !down && !left && !right) {
                Player.isMoving = false;
            }


            // Interaction keys
            chat = keys[KeyEvent.VK_C];
            pickUp = keys[KeyEvent.VK_F];

            // Coordinate keys
            position = keys[KeyEvent.VK_P];
            talk = keys[KeyEvent.VK_SPACE];

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

            if (e.getKeyCode() == KeyEvent.VK_P) {
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
            if (e.getKeyCode() == KeyEvent.VK_I && !ShopWindow.isOpen) {
                InventoryWindow.isOpen = !InventoryWindow.isOpen;
                EquipmentWindow.isOpen = !EquipmentWindow.isOpen;
            }

            // Chat window toggle
            if (e.getKeyCode() == KeyEvent.VK_C) {
                ChatWindow.chatIsOpen = !ChatWindow.chatIsOpen;
            }

            // QuestWindow toggle
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                lastUIKeyPressed = KeyEvent.VK_Q;
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
            if (e.getKeyCode() == KeyEvent.VK_K) {
                lastUIKeyPressed = KeyEvent.VK_K;
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
            if (e.getKeyCode() == KeyEvent.VK_L) {
                lastUIKeyPressed = KeyEvent.VK_L;
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

            if (e.getKeyCode() == KeyEvent.VK_B) {
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
            if (e.getKeyChar() == KeyEvent.VK_SPACE && Entity.isCloseToNPC) {
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
