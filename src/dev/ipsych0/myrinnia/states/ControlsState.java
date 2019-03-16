package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.devtools.DevToolUI;
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
    private UIImageButton w, a, s, d, i, c, q, m, k, l, space;
    private static TextBox tb;
    private static UIObject selectedButton;
    public static boolean initialized;

    public ControlsState() {
        this.uiManager = new UIManager();

        overlay = new Rectangle(Handler.get().getWidth() / 2 - 260, 232, 520, 313);

        // Interface rectangles
        i = new UIImageButton(overlay.x + 24, overlay.y + 24, 32, 32, Assets.genericButton);
        q = new UIImageButton(overlay.x + 24, overlay.y + 72, 32, 32, Assets.genericButton);
        l = new UIImageButton(overlay.x + 24, overlay.y + 120, 32, 32, Assets.genericButton);
        m = new UIImageButton(overlay.x + 24, overlay.y + 168, 32, 32, Assets.genericButton);
        k = new UIImageButton(overlay.x + 24, overlay.y + 216, 32, 32, Assets.genericButton);
        c = new UIImageButton(overlay.x + 24, overlay.y + 264, 32, 32, Assets.genericButton);

        // Interface buttons
        uiManager.addObject(i);
        uiManager.addObject(q);
        uiManager.addObject(l);
        uiManager.addObject(m);
        uiManager.addObject(k);
        uiManager.addObject(c);

        // WASD rectangles
        w = new UIImageButton(overlay.x + 224, overlay.y + 48, 32, 32, Assets.genericButton);
        a = new UIImageButton(overlay.x + 176, overlay.y + 96, 32, 32, Assets.genericButton);
        s = new UIImageButton(overlay.x + 224, overlay.y + 96, 32, 32, Assets.genericButton);
        d = new UIImageButton(overlay.x + 272, overlay.y + 96, 32, 32, Assets.genericButton);

        // WASD buttons
        uiManager.addObject(w);
        uiManager.addObject(a);
        uiManager.addObject(s);
        uiManager.addObject(d);

        // Spacebar
        space = new UIImageButton(overlay.x + 192, overlay.y + 192, 96, 32, Assets.genericButton);
        uiManager.addObject(space);

        /*
         * The return button to the main menu
         */
        returnButton = new UIImageButton(Handler.get().getWidth() / 2 - 113, 584, 226, 96, Assets.genericButton);
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
                    if(selectedButton == i){
                        Handler.get().saveProperty("inventoryKey", tb.getCharactersTyped());
                    }else if(selectedButton == c){
                        Handler.get().saveProperty("chatWindowKey", tb.getCharactersTyped());
                    }else if(selectedButton == q){
                        Handler.get().saveProperty("questWindowKey", tb.getCharactersTyped());
                    }else if(selectedButton == m){
                        Handler.get().saveProperty("mapWindowKey", tb.getCharactersTyped());
                    }else if(selectedButton == k){
                        Handler.get().saveProperty("statsWindowKey", tb.getCharactersTyped());
                    }else if(selectedButton == l){
                        Handler.get().saveProperty("skillsWindowKey", tb.getCharactersTyped());
                    }else if(selectedButton == space){
                        Handler.get().saveProperty("interactKey", tb.getCharactersTyped());
                    }else if(selectedButton == w){
                        Handler.get().saveProperty("upKey", tb.getCharactersTyped());
                    }else if(selectedButton == a){
                        Handler.get().saveProperty("leftKey", tb.getCharactersTyped());
                    }else if(selectedButton == s){
                        Handler.get().saveProperty("rightKey", tb.getCharactersTyped());
                    }else if(selectedButton == d){
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
//			g.drawImage(Assets.craftWindow, -40, -40, 1040, 800, null);
        g.drawImage(Assets.genericButton[2], overlay.x, overlay.y, overlay.width, overlay.height, null);
        this.uiManager.render(g);

//			Text.drawString(g, "Welcome to Myrinnia", 480, 180, true, Color.YELLOW, Assets.font32);

        Text.drawString(g, "Movement keys:", i.x + 216, i.y, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "W", w.x + 16, w.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "A", a.x + 16, a.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "S", s.x + 16, s.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "D", d.x + 16, d.y + 16, true, Color.YELLOW, Assets.font14);

        Text.drawString(g, "I", i.x + 16, i.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Inventory", i.x + 48, i.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "C", c.x + 16, c.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Chatwindow", c.x + 48, c.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Q", q.x + 16, q.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Quests", q.x + 48, q.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "M", m.x + 16, m.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Map", m.x + 48, m.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "K", k.x + 16, k.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Character", k.x + 48, k.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "L", l.x + 16, l.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Skills", l.x + 48, l.y + 20, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Spacebar", space.x + 48, space.y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Interact with NPCs/Objects:", space.x + 48, space.y - 24, true, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Left click:", overlay.x + overlay.width - 160, 396, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "- Attack", overlay.x + overlay.width - 160, 416, false, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Right click:", overlay.x + overlay.width - 160, 448, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "- Pick up item", overlay.x + overlay.width - 160, 468, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "- Equip item", overlay.x + overlay.width - 160, 488, false, Color.YELLOW, Assets.font14);


        Text.drawString(g, "Return", Handler.get().getWidth() / 2, 632, true, Color.YELLOW, Assets.font32);

        if(selectingNewKey) {
            g.drawImage(Assets.genericButton[1], tb.x - 32, tb.y - 32, tb.width + 64, tb.height + 64, null);
            tb.render(g);
        }
    }


}
