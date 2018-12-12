package dev.ipsych0.myrinnia.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilityhud.AbilitySlot;
import dev.ipsych0.myrinnia.abilityhud.PlayerHUD;
import dev.ipsych0.myrinnia.abilityoverview.AbilityOverviewUI;
import dev.ipsych0.myrinnia.bank.BankUI;
import dev.ipsych0.myrinnia.character.CharacterUI;
import dev.ipsych0.myrinnia.crafting.CraftingUI;
import dev.ipsych0.myrinnia.devtools.DevToolUI;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.entities.npcs.ChatWindow;
import dev.ipsych0.myrinnia.items.EquipmentWindow;
import dev.ipsych0.myrinnia.items.InventoryWindow;
import dev.ipsych0.myrinnia.quests.QuestHelpUI;
import dev.ipsych0.myrinnia.quests.QuestUI;
import dev.ipsych0.myrinnia.shops.AbilityShopWindow;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.skills.SkillsOverviewUI;
import dev.ipsych0.myrinnia.skills.SkillsUI;
import dev.ipsych0.myrinnia.ui.TextBox;
import dev.ipsych0.myrinnia.utils.Text;

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


//		for(int i = 0; i < keys.length; i++){
//			if(cantPress[i] && !keys[i]){
//				cantPress[i] = false;
//			}else if(justPressed[i]){
//				cantPress[i] = true;
//				justPressed[i] = false;
//			}
//			if(!cantPress[i] && keys[i]){
//				justPressed[i] = true;
//			}
//		}
//		
//		if(keyJustPressed(KeyEvent.VK_E)){
//			// Maybe hier optimaliseren van interfaces
//		}


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
                if(ShopWindow.lastOpenedWindow != null){
                    ShopWindow.lastOpenedWindow.exit();
                }
                if(AbilityShopWindow.lastOpenedWindow != null){
                    AbilityShopWindow.lastOpenedWindow.exit();
                }
                Handler.get().getBankUI().exit();
                Handler.get().getCraftingUI().closeCraftingUI();
                AbilityOverviewUI.isOpen = !AbilityOverviewUI.isOpen;
            }

            // Toggle dev tool window
            if (e.getKeyCode() == KeyEvent.VK_T) {
                DevToolUI.isOpen = !DevToolUI.isOpen;
                TextBox.isOpen = !TextBox.isOpen;
                TextBox.focus = !TextBox.focus;
                typingFocus = !typingFocus;
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
                for (AbilitySlot as : Handler.get().getAbilityManager().getPlayerHUD().getSlottedAbilities()) {
                    if (as.getAbility() != null) {
                        if (as.getAbility().isChanneling()) {
                            return;
                        }
                    }
                }

                // Set the pressed key for the ability bar
                PlayerHUD.hasBeenTyped = true;
                PlayerHUD.pressedKey = e.getKeyChar();
            }
            if (e.getKeyChar() == KeyEvent.VK_SPACE && Entity.isCloseToNPC) {
                Player.hasInteracted = false;
            }
        }
    }

    public boolean keyJustPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= keys.length) {
            return false;
        }
        return justPressed[keyCode];
    }

    public boolean isTextBoxTyping() {
        return typingFocus;
    }

    public void setTextBoxTyping(boolean textBoxTyping) {
        this.typingFocus = textBoxTyping;
    }

    public int getLastUIKeyPressed() {
        return lastUIKeyPressed;
    }

    public void setLastUIKeyPressed(int lastUIKeyPressed) {
        this.lastUIKeyPressed = lastUIKeyPressed;
    }

}
