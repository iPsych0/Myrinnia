package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.*;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.util.HashMap;

public class ControlsState extends State {

    /**
     *
     */
    private static final long serialVersionUID = 8517192489288492030L;
    private UIManager uiManager;
    private UIImageButton returnButton;
    private UIImageButton defaultButton;
    private Rectangle overlay;
    private UIImageButton upKey, leftKey, downKey, rightKey, invKey, chatKey, questKey,
            mapKey, statsKey, skillsKey, interactKey, abilityKey, pauseKey, hudKey;
    private TextBox tb;
    private HashMap<UIObject, String> keys = new HashMap<>();
    private static UIObject selectedButton;
    public static boolean initialized;
    private static boolean errorShown;
    private static int errorTimer;

    private DialogueBox keysDBox;
    private DialogueBox defaultDBox;
    private static String[] keysAnswers = {"Set", "Cancel"};
    private static String keysMessage = "Please enter the new key.";
    private static String defaultConfirmMessage = "Do you wish to reset to the default key binds?";
    private static String[] confirmAnswers = {"Yes", "No"};
    public static boolean escapePressed;

    private static final String UP_SHORTCUT = "w", LEFT_SHORTCUT = "a", RIGHT_SHORTCUT = "d", DOWN_SHORTCUT = "s",
                                INV_SHORTCUT = "i", INTERACT_SHORTCUT = " ", QUEST_SHORTCUT = "q", MAP_SHORTCUT = "m",
                                SKILL_SHORTCUT = "l", STATS_SHORTCUT = "k", PAUSE_SHORTCUT = "p", ABILITY_SHORTCUT = "b",
                                CHAT_SHORTCUT = "c", HUD_SHORTCUT = "h";

    public ControlsState() {
        this.uiManager = new UIManager();

        overlay = new Rectangle(Handler.get().getWidth() / 2 - 320, 160, 640, 417);

        invKey = new UIImageButton(overlay.x + 24, overlay.y + 32, 32, 32, Assets.genericButton);
        questKey = new UIImageButton(overlay.x + 24, overlay.y + 72, 32, 32, Assets.genericButton);
        skillsKey = new UIImageButton(overlay.x + 24, overlay.y + 112, 32, 32, Assets.genericButton);
        mapKey = new UIImageButton(overlay.x + 24, overlay.y + 152, 32, 32, Assets.genericButton);
        statsKey = new UIImageButton(overlay.x + 24, overlay.y + 192, 32, 32, Assets.genericButton);
        chatKey = new UIImageButton(overlay.x + 24, overlay.y + 232, 32, 32, Assets.genericButton);
        abilityKey = new UIImageButton(overlay.x + 24, overlay.y + 272, 32, 32, Assets.genericButton);
        pauseKey = new UIImageButton(overlay.x + 24, overlay.y + 312, 32, 32, Assets.genericButton);
        hudKey = new UIImageButton(overlay.x + 24, overlay.y + 352, 32, 32, Assets.genericButton);

        // UI buttons
        uiManager.addObject(invKey);
        uiManager.addObject(questKey);
        uiManager.addObject(skillsKey);
        uiManager.addObject(mapKey);
        uiManager.addObject(statsKey);
        uiManager.addObject(chatKey);
        uiManager.addObject(abilityKey);
        uiManager.addObject(pauseKey);
        uiManager.addObject(hudKey);

        upKey = new UIImageButton(overlay.x + 224, overlay.y + 32, 32, 32, Assets.genericButton);
        leftKey = new UIImageButton(overlay.x + 224, overlay.y + 72, 32, 32, Assets.genericButton);
        downKey = new UIImageButton(overlay.x + 224, overlay.y + 112, 32, 32, Assets.genericButton);
        rightKey = new UIImageButton(overlay.x + 224, overlay.y + 152, 32, 32, Assets.genericButton);
        interactKey = new UIImageButton(overlay.x + 224, overlay.y + 192, 64, 32, Assets.genericButton);

        // Player buttons
        uiManager.addObject(upKey);
        uiManager.addObject(leftKey);
        uiManager.addObject(downKey);
        uiManager.addObject(rightKey);
        uiManager.addObject(interactKey);

        setKeys();

        // DefaultButton to reset all keybinds
        defaultButton = new UIImageButton(overlay.x + overlay.width - 96, overlay.y + overlay.height - 64, 64, 32, Assets.genericButton);
        uiManager.addObject(defaultButton);

        /*
         * The return button to the main menu
         */
        returnButton = new UIImageButton(Handler.get().getWidth() / 2 - 112, Handler.get().getHeight() - 112, 224, 96, Assets.genericButton);
        uiManager.addObject(returnButton);

        tb = new TextBox(overlay.x + overlay.width / 2 - 48, overlay.y + overlay.height / 2 - 16, 96, 32, false, 1);
        keysDBox = new DialogueBox(tb.x - 96, tb.y - 64, tb.width + 192, tb.height + 128, keysAnswers, keysMessage, tb);
        defaultDBox = new DialogueBox(tb.x - 96, tb.y - 64, tb.width + 192, tb.height + 128, confirmAnswers, defaultConfirmMessage, null);

    }

    private void setKeys() {
        keys.put(invKey, Handler.get().loadProperty("inventoryKey"));
        keys.put(questKey, Handler.get().loadProperty("questWindowKey"));
        keys.put(skillsKey, Handler.get().loadProperty("skillsWindowKey"));
        keys.put(mapKey, Handler.get().loadProperty("mapWindowKey"));
        keys.put(statsKey, Handler.get().loadProperty("statsWindowKey"));
        keys.put(chatKey, Handler.get().loadProperty("chatWindowKey"));
        keys.put(abilityKey, Handler.get().loadProperty("abilitiesKey"));
        keys.put(pauseKey, Handler.get().loadProperty("pauseKey"));
        keys.put(upKey, Handler.get().loadProperty("upKey"));
        keys.put(leftKey, Handler.get().loadProperty("leftKey"));
        keys.put(downKey, Handler.get().loadProperty("downKey"));
        keys.put(rightKey, Handler.get().loadProperty("rightKey"));
        keys.put(interactKey, Handler.get().loadProperty("interactKey"));
        keys.put(hudKey, Handler.get().loadProperty("hudKey"));
    }

    private void setDefaultKeys() {
        keys.put(invKey, INV_SHORTCUT);
        keys.put(questKey, QUEST_SHORTCUT);
        keys.put(skillsKey, SKILL_SHORTCUT);
        keys.put(mapKey, MAP_SHORTCUT);
        keys.put(statsKey, STATS_SHORTCUT);
        keys.put(chatKey, CHAT_SHORTCUT);
        keys.put(abilityKey, ABILITY_SHORTCUT);
        keys.put(pauseKey, PAUSE_SHORTCUT);
        keys.put(upKey, UP_SHORTCUT);
        keys.put(leftKey, LEFT_SHORTCUT);
        keys.put(downKey, DOWN_SHORTCUT);
        keys.put(rightKey, RIGHT_SHORTCUT);
        keys.put(interactKey, INTERACT_SHORTCUT);
        keys.put(hudKey, HUD_SHORTCUT);

        Handler.get().saveProperty("inventoryKey", INV_SHORTCUT);
        Handler.get().saveProperty("chatWindowKey", CHAT_SHORTCUT);
        Handler.get().saveProperty("questWindowKey", QUEST_SHORTCUT);
        Handler.get().saveProperty("mapWindowKey", MAP_SHORTCUT);
        Handler.get().saveProperty("statsWindowKey", STATS_SHORTCUT);
        Handler.get().saveProperty("skillsWindowKey", SKILL_SHORTCUT);
        Handler.get().saveProperty("interactKey", INTERACT_SHORTCUT);
        Handler.get().saveProperty("abilityKey", ABILITY_SHORTCUT);
        Handler.get().saveProperty("pauseKey", PAUSE_SHORTCUT);
        Handler.get().saveProperty("upKey", UP_SHORTCUT);
        Handler.get().saveProperty("leftKey", LEFT_SHORTCUT);
        Handler.get().saveProperty("downKey", DOWN_SHORTCUT);
        Handler.get().saveProperty("rightKey", RIGHT_SHORTCUT);
        Handler.get().saveProperty("hudKey", HUD_SHORTCUT);

        Handler.get().getKeyManager().loadKeybinds();
    }

    @Override
    public void tick() {

        Rectangle mouse = Handler.get().getMouse();

        if(Handler.get().getKeyManager().escape && escapePressed && keysDBox.isOpen() ||
                Handler.get().getKeyManager().escape && escapePressed && defaultDBox.isOpen()){
            escapePressed = false;
            closeTextBox();
            return;
        }

        for (UIObject btn : uiManager.getObjects()) {
            if (btn.isHovering() && !btn.equals(returnButton) && !btn.equals(defaultButton) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed && !keysDBox.isMakingChoice()) {
                hasBeenPressed = false;
                initialized = false;
                defaultDBox.close();
                keysDBox.open();
                selectedButton = btn;
                break;
            }
        }

        if(defaultButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed && !defaultDBox.isMakingChoice()) {
                hasBeenPressed = false;
                defaultDBox.open();
            }
        }

        // If player is making a choice, show the dialoguebox
        if (defaultDBox.isMakingChoice()) {
            defaultDBox.tick();
            confirmDefaultKeyBinds();
        }


        if (returnButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                hasBeenPressed = false;
                selectedButton = null;
                closeTextBox();
                State.setState(new UITransitionState(Handler.get().getGame().settingState));
            }
        }

        this.uiManager.tick();

        if (keysDBox.isMakingChoice()) {
            keysDBox.tick();

            checkSubmit();

            if (TextBox.enterPressed) {
                keysDBox.setPressedButton(keysDBox.getButtons().get(0));
                keysDBox.getPressedButton().getButtonParam()[0] = "Set";
                checkKeys();
            }
        }

    }

    private void confirmDefaultKeyBinds() {
        if (defaultDBox.isMakingChoice() && defaultDBox.getPressedButton() != null) {
            if ("Yes".equalsIgnoreCase(defaultDBox.getPressedButton().getButtonParam()[0])) {
                Handler.get().playEffect("ui/ui_button_click.wav");
                setDefaultKeys();
            } else if ("No".equalsIgnoreCase(defaultDBox.getPressedButton().getButtonParam()[0])) {
                Handler.get().playEffect("ui/ui_button_click.wav");
            }
            defaultDBox.close();
            hasBeenPressed = false;
        }
    }

    private void checkKeys() {
        if (!tb.getCharactersTyped().isEmpty() && selectedButton != null) {
            if (keys.containsValue(tb.getCharactersTyped().toLowerCase())) {
                errorShown = true;
                errorTimer = 0;
                closeTextBox();
                return;
            }

            if (" ".equalsIgnoreCase(tb.getCharactersTyped().toLowerCase())) {
                keys.clear();
                for (UIObject o : uiManager.getObjects()) {
                    if (!o.equals(returnButton) && !o.equals(defaultButton)) {
                        o.width = 32;
                    }
                }
                selectedButton.width = 64;
                setKeys();
            }else{
                keys.clear();
                for (UIObject o : uiManager.getObjects()) {
                    if (!o.equals(returnButton) && !o.equals(defaultButton)) {
                        o.width = 32;
                    }
                }
                setKeys();
            }

            if (selectedButton == invKey) {
                Handler.get().saveProperty("inventoryKey", tb.getCharactersTyped().toLowerCase());
            } else if (selectedButton == chatKey) {
                Handler.get().saveProperty("chatWindowKey", tb.getCharactersTyped().toLowerCase());
            } else if (selectedButton == questKey) {
                Handler.get().saveProperty("questWindowKey", tb.getCharactersTyped().toLowerCase());
            } else if (selectedButton == mapKey) {
                Handler.get().saveProperty("mapWindowKey", tb.getCharactersTyped().toLowerCase());
            } else if (selectedButton == statsKey) {
                Handler.get().saveProperty("statsWindowKey", tb.getCharactersTyped().toLowerCase());
            } else if (selectedButton == skillsKey) {
                Handler.get().saveProperty("skillsWindowKey", tb.getCharactersTyped().toLowerCase());
            } else if (selectedButton == interactKey) {
                Handler.get().saveProperty("interactKey", tb.getCharactersTyped().toLowerCase());
            } else if (selectedButton == abilityKey) {
                Handler.get().saveProperty("abilityKey", tb.getCharactersTyped().toLowerCase());
            } else if (selectedButton == pauseKey) {
                Handler.get().saveProperty("pauseKey", tb.getCharactersTyped().toLowerCase());
            } else if (selectedButton == upKey) {
                Handler.get().saveProperty("upKey", tb.getCharactersTyped().toLowerCase());
            } else if (selectedButton == leftKey) {
                Handler.get().saveProperty("leftKey", tb.getCharactersTyped().toLowerCase());
            } else if (selectedButton == downKey) {
                Handler.get().saveProperty("downKey", tb.getCharactersTyped().toLowerCase());
            } else if (selectedButton == rightKey) {
                Handler.get().saveProperty("rightKey", tb.getCharactersTyped().toLowerCase());
            }

            keys.replace(selectedButton, tb.getCharactersTyped().toLowerCase());

            Handler.get().getKeyManager().loadKeybinds();
        }

        closeTextBox();
    }

    private void checkSubmit(){
        if (keysDBox.isMakingChoice() && keysDBox.getPressedButton() != null) {
            if ("Set".equalsIgnoreCase(keysDBox.getPressedButton().getButtonParam()[0])) {
                checkKeys();
            } else if ("Cancel".equalsIgnoreCase(keysDBox.getPressedButton().getButtonParam()[0])) {
                closeTextBox();
            }
            Handler.get().playEffect("ui/ui_button_click.wav");
        }
    }

    private void closeTextBox() {
        hasBeenPressed = false;
        initialized = false;
        selectedButton = null;
        keysDBox.close();
        defaultDBox.close();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.uiWindow, overlay.x, overlay.y, overlay.width, overlay.height, null);
        this.uiManager.render(g);

        for (UIObject o : uiManager.getObjects()) {
            if (!o.equals(returnButton) && !o.equals(defaultButton)) {
                if(" ".equalsIgnoreCase(keys.get(o))){
                    Text.drawString(g, "Space", o.x + o.width / 2, o.y + 16, true, Color.YELLOW, Assets.font14);
                }else {
                    Text.drawString(g, keys.get(o).toUpperCase(), o.x + o.width / 2, o.y + 16, true, Color.YELLOW, Assets.font14);
                }
            }
        }

        // Player keys

        Text.drawString(g, "Player keys:", upKey.x, upKey.y - 8, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Move up", upKey.x + upKey.width + 16, upKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Move left", leftKey.x + leftKey.width + 16, leftKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Move down", downKey.x + downKey.width + 16, downKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Move right", rightKey.x + rightKey.width + 16, rightKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Interact", interactKey.x + interactKey.width + 16, interactKey.y + 20, false, Color.YELLOW, Assets.font14);


        // UI Keys

        Text.drawString(g, "UI keys:", invKey.x, invKey.y - 8, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Inventory", invKey.x + invKey.width + 16, invKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Chat", chatKey.x + chatKey.width + 16, chatKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Quests", questKey.x + questKey.width + 16, questKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Map", mapKey.x + mapKey.width + 16, mapKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Character", statsKey.x + statsKey.width + 16, statsKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Skills", skillsKey.x + skillsKey.width + 16, skillsKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Abilities", abilityKey.x + abilityKey.width + 16, abilityKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Pause Game", pauseKey.x + pauseKey.width + 16, pauseKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "HUD", hudKey.x + hudKey.width + 16, hudKey.y + 20, false, Color.YELLOW, Assets.font14);

        // Mouse controls

        Text.drawString(g, "Mouse controls:", overlay.x + 424, overlay.y + 24, false, Color.YELLOW, Assets.font14);

        g.drawImage(Assets.uiWindow, overlay.x + 424, overlay.y + 28, 120, 132, null);
        Text.drawString(g, "Left click:", overlay.x + 432, overlay.y + 48, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "- Attack", overlay.x + 440, overlay.y + 68, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Right click:", overlay.x + 432, overlay.y + 112, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "- Pick up item", overlay.x + 440, overlay.y + 132, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "- Equip item", overlay.x + 440, overlay.y + 152, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Default", defaultButton.x + defaultButton.width / 2, defaultButton.y + defaultButton.height / 2, true, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Return", returnButton.x + returnButton.width / 2, returnButton.y + returnButton.height / 2, true, Color.YELLOW, Assets.font32);

        if (keysDBox.isMakingChoice()) {
            keysDBox.render(g);
        }

        // If player is making a choice, show the dialoguebox
        if (defaultDBox.isMakingChoice()) {
            defaultDBox.render(g);
        }

        if (errorShown) {
//            g.drawImage(Assets.genericButton[0], overlay.x, overlay.y - 32, overlay.width, 32, null);
            Text.drawString(g, "That key is already in use", overlay.x + overlay.width / 2, overlay.y + overlay.height / 2, true, Color.RED, Assets.font32);
            errorTimer++;
            if (errorTimer >= 120) {
                errorShown = false;
                errorTimer = 0;
            }
        }
    }


}
