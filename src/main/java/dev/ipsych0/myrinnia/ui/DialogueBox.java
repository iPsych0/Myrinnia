package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.KeyManager;
import dev.ipsych0.myrinnia.utils.Text;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
public class DialogueBox implements Serializable {

    private static final long serialVersionUID = -5830274597655100531L;
    private int x, y, width, height;
    private ArrayList<DialogueButton> buttons;
    private boolean open = false;
    private String[] answers;
    private String param = "";
    private DialogueButton pressedButton = null;
    private TextBox textBox;
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

        if (numbersOnly) {
            // Limit the number to 6 digits (999,999 max) to prevent users from entering digits above Integer.MAX_VALUE
            textBox = new TextBox(x + (width / 2) - (width / 2) + 17, y + height - 96, width - 40, 32, true, 6);
        } else {
            textBox = new TextBox(x + (width / 2) - (width / 2) + 17, y + height - 96, width - 40, 32, false);
        }
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

        this.textBox = textBox;
    }

    public void tick() {

        if (open) {

            Rectangle mouse = Handler.get().getMouse();
            if (textBox != null) {
                textBox.tick();
            }

            uiManager.tick();
            for (DialogueButton db : buttons) {
                db.tick();

                if (db.getButtonBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                    for (int i = 0; i < buttons.size(); i++) {
                        if (db.getText().equals(answers[i]) && pressedButton == null) {
                            pressedButton = db;
                            pressedButton.pressedButton(answers[i], param);
                            open = false;
                            hasBeenPressed = false;
                        }
                    }
                }

            }
        }
    }

    public void render(Graphics2D g) {
        if (open) {
            render(g, Color.YELLOW);
        }
    }

    public void render(Graphics2D g, Color color) {
        if (open) {

            g.drawImage(Assets.uiWindow, x, y, width, height, null);

            String[] text = Text.splitIntoLine(String.valueOf(message), 32);
            for (int i = 0; i < text.length; i++) {
                Text.drawString(g, text[i], x + (width / 2), y + 32 + (i * 16), true, color, Assets.font14);
            }

            uiManager.render(g);

            Rectangle mouse = Handler.get().getMouse();

            for (DialogueButton db : buttons) {
                db.setHovering(db.getButtonBounds().contains(mouse));
            }

            if (textBox != null) {
                textBox.render(g);
            }
        }
    }

    public void open() {
        makingChoice = true;
        open = true;
        hasBeenPressed = false;
        if (textBox != null) {
            textBox.open();
        }
    }

    public void close() {
        open = false;
        hasBeenPressed = false;
        setPressedButton(null);
        makingChoice = false;
        if (textBox != null) {
            textBox.setOpen(false);
            TextBox.enterPressed = false;
            KeyManager.typingFocus = false;
            textBox.getSb().setLength(0);
            textBox.setIndex(0);
            textBox.setCharactersTyped(textBox.getSb().toString());
        }
    }
}
