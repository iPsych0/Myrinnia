package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.KeyManager;
import dev.ipsych0.myrinnia.ui.*;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class ControlsState extends State {

    /**
     *
     */
    private static final long serialVersionUID = 8517192489288492030L;
    private UIManager uiManager;
    private boolean selectingNewKey = false;
    private UIImageButton returnButton;
    private Rectangle overlay;
    private UIImageButton upKey, leftKey, downKey, rightKey, invKey, chatKey, questKey,
            mapKey, statsKey, skillsKey, interactKey, abilityKey, pauseKey;
    private static TextBox tb;
    private static UIObject selectedButton;
    public static boolean initialized;

    public ControlsState() {
        this.uiManager = new UIManager();

        overlay = new Rectangle(Handler.get().getWidth() / 2 - 320, 232, 640, 417);

        invKey = new UIImageButton(overlay.x + 24, overlay.y + 32, 32, 32, Assets.genericButton);
        questKey = new UIImageButton(overlay.x + 24, overlay.y + 72, 32, 32, Assets.genericButton);
        skillsKey = new UIImageButton(overlay.x + 24, overlay.y + 112, 32, 32, Assets.genericButton);
        mapKey = new UIImageButton(overlay.x + 24, overlay.y + 152, 32, 32, Assets.genericButton);
        statsKey = new UIImageButton(overlay.x + 24, overlay.y + 192, 32, 32, Assets.genericButton);
        chatKey = new UIImageButton(overlay.x + 24, overlay.y + 232, 32, 32, Assets.genericButton);
        abilityKey = new UIImageButton(overlay.x + 24, overlay.y + 272, 32, 32, Assets.genericButton);
        pauseKey = new UIImageButton(overlay.x + 24, overlay.y + 312, 32, 32, Assets.genericButton);

        // UI buttons
        uiManager.addObject(invKey);
        uiManager.addObject(questKey);
        uiManager.addObject(skillsKey);
        uiManager.addObject(mapKey);
        uiManager.addObject(statsKey);
        uiManager.addObject(chatKey);
        uiManager.addObject(abilityKey);
        uiManager.addObject(pauseKey);

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

        /*
         * The return button to the main menu
         */
        returnButton = new UIImageButton(Handler.get().getWidth() / 2 - 113, 688, 226, 96, Assets.genericButton);
        uiManager.addObject(returnButton);

        tb = new TextBox(overlay.x + overlay.width / 2 - 48, overlay.y + overlay.height / 2 - 16, 96, 32, false, 1);

    }

    @Override
    public void tick() {

        Rectangle mouse = Handler.get().getMouse();

        for(UIObject btn : uiManager.getObjects()){
            if(btn.isHovering() && !btn.equals(returnButton) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed && !selectingNewKey){
                hasBeenPressed = false;
                selectingNewKey = true;
                TextBox.isOpen = true;
                selectedButton = btn;
            }
        }

        if (returnButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                State.setState(new UITransitionState(Handler.get().getGame().settingState));
                hasBeenPressed = false;
                selectingNewKey = false;
                selectedButton = null;
                closeTextBox();
            }
        }

        this.uiManager.tick();

        if(selectingNewKey) {
            tb.tick();
            if (!initialized) {
                tb.setKeyListeners();
                initialized = true;
            }
            if (TextBox.enterPressed) {

                if(!tb.getCharactersTyped().isEmpty()){
                    if(selectedButton == invKey){
                        Handler.get().saveProperty("inventoryKey", tb.getCharactersTyped());
                    }else if(selectedButton == chatKey){
                        Handler.get().saveProperty("chatWindowKey", tb.getCharactersTyped());
                    }else if(selectedButton == questKey){
                        Handler.get().saveProperty("questWindowKey", tb.getCharactersTyped());
                    }else if(selectedButton == mapKey){
                        Handler.get().saveProperty("mapWindowKey", tb.getCharactersTyped());
                    }else if(selectedButton == statsKey){
                        Handler.get().saveProperty("statsWindowKey", tb.getCharactersTyped());
                    }else if(selectedButton == skillsKey){
                        Handler.get().saveProperty("skillsWindowKey", tb.getCharactersTyped());
                    }else if(selectedButton == interactKey){
                        Handler.get().saveProperty("interactKey", tb.getCharactersTyped());
                    }else if(selectedButton == abilityKey){
                        Handler.get().saveProperty("abilityKey", tb.getCharactersTyped());
                    }else if(selectedButton == pauseKey){
                        Handler.get().saveProperty("pauseKey", tb.getCharactersTyped());
                    }else if(selectedButton == upKey){
                        Handler.get().saveProperty("upKey", tb.getCharactersTyped());
                    }else if(selectedButton == leftKey){
                        Handler.get().saveProperty("leftKey", tb.getCharactersTyped());
                    }else if(selectedButton == downKey){
                        Handler.get().saveProperty("rightKey", tb.getCharactersTyped());
                    }else if(selectedButton == rightKey){
                        Handler.get().saveProperty("downKey", tb.getCharactersTyped());
                    }

                    Handler.get().getKeyManager().loadKeybinds();
                }

                closeTextBox();
            }
        }

    }

    private void closeTextBox() {
        TextBox.enterPressed = false;
        Handler.get().getKeyManager().setTextBoxTyping(false);
        KeyManager.typingFocus = false;
        tb.getSb().setLength(0);
        tb.setIndex(0);
        tb.setCharactersTyped(tb.getSb().toString());
        TextBox.isOpen = false;
        initialized = false;
        selectingNewKey = false;
        selectedButton = null;
        tb.removeListeners();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Handler.get().getWidth(), Handler.get().getHeight());
        g.drawImage(Assets.genericButton[2], overlay.x, overlay.y, overlay.width, overlay.height, null);
        this.uiManager.render(g);

        Text.drawString(g, "Controls", Handler.get().getWidth() / 2, 180, true, Color.YELLOW, Assets.font32);

        // Player keys

        Text.drawString(g, "Player keys:", upKey.x, upKey.y - 8, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "W", upKey.x + 16, upKey.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Move up", upKey.x + 48, upKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "A", leftKey.x + 16, leftKey.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Move left", leftKey.x + 48, leftKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "S", downKey.x + 16, downKey.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Move down", downKey.x + 48, downKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "D", rightKey.x + 16, rightKey.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Move right", rightKey.x + 48, rightKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Space", interactKey.x + 32, interactKey.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Interact", interactKey.x + interactKey.width + 16, interactKey.y + 20, false, Color.YELLOW, Assets.font14);

        // UI Keys

        Text.drawString(g, "UI keys:", invKey.x, invKey.y - 8, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "I", invKey.x + 16, invKey.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Inventory", invKey.x + 48, invKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "C", chatKey.x + 16, chatKey.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Chat", chatKey.x + 48, chatKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Q", questKey.x + 16, questKey.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Quests", questKey.x + 48, questKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "M", mapKey.x + 16, mapKey.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Map", mapKey.x + 48, mapKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "K", statsKey.x + 16, statsKey.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Character", statsKey.x + 48, statsKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "L", skillsKey.x + 16, skillsKey.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Skills", skillsKey.x + 48, skillsKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "B", abilityKey.x + 16, abilityKey.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Abilities", abilityKey.x + 48, abilityKey.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "P", pauseKey.x + 16, pauseKey.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Pause Game", pauseKey.x + 48, pauseKey.y + 20, false, Color.YELLOW, Assets.font14);

        // Mouse controls

        Text.drawString(g, "Mouse controls:", overlay.x + 424, overlay.y + 24, false, Color.YELLOW, Assets.font14);

        g.drawImage(Assets.genericButton[1], overlay.x + 420, overlay.y + 28, 120, 132, null);
        Text.drawString(g, "Left click:", overlay.x + 424, overlay.y + 48, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "- Attack", overlay.x + 432, overlay.y + 68, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Right click:", overlay.x + 424, overlay.y + 112, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "- Pick up item", overlay.x + 432, overlay.y + 132, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "- Equip item", overlay.x + 432, overlay.y + 152, false, Color.YELLOW, Assets.font14);


        Text.drawString(g, "Return", returnButton.x + returnButton.width / 2, returnButton.y + returnButton.height / 2, true, Color.YELLOW, Assets.font32);

        if(selectingNewKey) {
            g.drawImage(Assets.genericButton[1], tb.x - 32, tb.y - 32, tb.width + 64, tb.height + 64, null);
            tb.render(g);
        }
    }


}
