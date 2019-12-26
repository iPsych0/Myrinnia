package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.KeyManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class DialogueBox implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5830274597655100531L;
    private int x, y, width, height;
    private ArrayList<DialogueButton> buttons;
    private boolean isOpen = false;
    private String[] answers;
    private String param = "";
    private DialogueButton pressedButton = null;
    private TextBox tb;
    private String message;
    private UIManager uiManager;
    public static boolean hasBeenPressed;
    private boolean makingChoice;

    public DialogueBox(int x, int y, int width, int height, String[] answers, String message, boolean numbersOnly) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.answers = answers;
        this.message = message;

        buttons = new ArrayList<>();
        uiManager = new UIManager();

        for (int i = 0; i < answers.length; i++) {
            buttons.add(new DialogueButton(x + (width / answers.length) - 32 - (32 / answers.length) + (i * 64), y + height - 48, 32, 32, answers[i]));
            uiManager.addObject(buttons.get(i));
        }

        tb = new TextBox(x + (width / 2) - (width / 2) + 17, y + height - 96, width - 40, 32, numbersOnly);
    }

    public DialogueBox(int x, int y, int width, int height, String[] answers, String message, TextBox textBox) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.answers = answers;
        this.message = message;

        buttons = new ArrayList<>();
        uiManager = new UIManager();

        for (int i = 0; i < answers.length; i++) {
            buttons.add(new DialogueButton(x + (width / answers.length) - 32 - (32 / answers.length) + (i * 64), y + height - 48, 32, 32, answers[i]));
            uiManager.addObject(buttons.get(i));
        }

        this.tb = textBox;
    }

    public void tick() {

        if (isOpen) {

            Rectangle mouse = Handler.get().getMouse();
            if (tb != null) {
                tb.tick();
            }

            uiManager.tick();
            for (DialogueButton db : buttons) {
                db.tick();

                if (db.getButtonBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                    for (int i = 0; i < buttons.size(); i++) {
                        if (db.getText().equals(answers[i]) && pressedButton == null) {
                            pressedButton = db;
                            pressedButton.pressedButton(answers[i], param);
                            isOpen = false;
                            hasBeenPressed = false;
                        }
                    }
                }

            }
        }
    }

    public void render(Graphics2D g) {
        if (isOpen) {
            render(g, Color.YELLOW);
        }
    }

    public void render(Graphics2D g, Color color) {
        if (isOpen) {

            g.drawImage(Assets.uiWindow, x, y, width, height, null);

            String[] text = Text.splitIntoLine(String.valueOf(message), 32);
            for (int i = 0; i < text.length; i++) {
                Text.drawString(g, text[i], x + (width / 2), y + 32 + (i * 16), true, color, Assets.font14);
            }

            uiManager.render(g);

            Rectangle mouse = Handler.get().getMouse();

            for (DialogueButton db : buttons) {
                if (db.getButtonBounds().contains(mouse)) {
                    db.setHovering(true);
                } else {
                    db.setHovering(false);
                }
            }

            if (tb != null) {
                tb.render(g);
            }
        }
    }

    public void open() {
        makingChoice = true;
        isOpen = true;
        hasBeenPressed = false;
        if (tb != null) {
            tb.open();
        }
    }

    public void close() {
        isOpen = false;
        hasBeenPressed = false;
        setPressedButton(null);
        makingChoice = false;
        if (tb != null) {
            tb.setOpen(false);
            TextBox.enterPressed = false;
            KeyManager.typingFocus = false;
            tb.getSb().setLength(0);
            tb.setIndex(0);
            tb.setCharactersTyped(tb.getSb().toString());
        }
    }

    public ArrayList<DialogueButton> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<DialogueButton> buttons) {
        this.buttons = buttons;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public DialogueButton getPressedButton() {
        return pressedButton;
    }

    public void setPressedButton(DialogueButton pressedButton) {
        this.pressedButton = pressedButton;
    }

    public TextBox getTextBox() {
        return tb;
    }

    public void setTextBox(TextBox tb) {
        this.tb = tb;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isMakingChoice() {
        return makingChoice;
    }

    public void setMakingChoice(boolean makingChoice) {
        this.makingChoice = makingChoice;
    }
}
