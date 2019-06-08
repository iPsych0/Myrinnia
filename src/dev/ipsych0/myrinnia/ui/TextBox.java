package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.devtools.DevToolUI;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.KeyManager;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.states.ControlsState;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

public class TextBox implements KeyListener, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2516956275598468379L;
    public int x, y, width, height;
    private String charactersTyped = "";
    private boolean numbersOnly = false;
    private Rectangle bounds;
    public static boolean focus = false;
    private int index = 0;
    private StringBuilder sb;
    public static boolean enterPressed = false;
    private boolean isOpen = false;
    public static boolean openKeyPressed;
    private int blinkTimer = 0;
    private String cursor = "|";
    private int MAX_CHARACTERS = 100;

    public TextBox(int x, int y, int width, int height, boolean numbersOnly) {
        this(x, y, width, height, numbersOnly, 100);
    }

    public TextBox(int x, int y, int width, int height, boolean numbersOnly, int characterLimit) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.numbersOnly = numbersOnly;
        this.MAX_CHARACTERS = characterLimit;

        bounds = new Rectangle(x, y, width, height);

        sb = new StringBuilder(charactersTyped);

        setKeyListeners();
    }

    public void tick() {
        if (isOpen) {
            Rectangle mouse = Handler.get().getMouse();

            // Sets focus when the textfield is clicked
            if (bounds.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
                KeyManager.typingFocus = true;
                focus = true;
            }

            // Removes focus when clicked outside the textfield
            if (!bounds.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
                KeyManager.typingFocus = false;
                focus = false;
            }
        }
    }

    public void setKeyListeners() {
        Handler.get().getGame().getDisplay().getFrame().removeKeyListener(this);
        Handler.get().getGame().getDisplay().getFrame().addKeyListener(this);
    }

    public void render(Graphics2D g) {
        if (isOpen) {

            g.drawImage(Assets.uiWindow, x, y, width, height, null);

            // If we have focus, draw the cursor and keep track of the pause
            if (focus) {

                blinkTimer++;

                g.drawImage(Assets.genericButton[0], x, y, width, height, null);

                if (blinkTimer >= 0 && blinkTimer < 60) {
                    cursor = "|";
                    if (!charactersTyped.isEmpty()) {
                        int textWidth = g.getFontMetrics().stringWidth(charactersTyped);
                        Text.drawString(g, cursor, (x + (width / 2)) + textWidth / 2 + 2, y + 17, true, Color.YELLOW, Assets.font20);
                    } else {
                        Text.drawString(g, cursor, x + (width / 2), y + 17, true, Color.YELLOW, Assets.font20);
                    }
                } else if (blinkTimer == 60) {
                    cursor = "";
                    Text.drawString(g, cursor, x + (width / 2), y + 17, true, Color.YELLOW, Assets.font20);
                } else if (blinkTimer >= 120) {
                    blinkTimer = 0;
                }
            } else {
                g.drawImage(Assets.genericButton[2], x, y, width, height, null);
            }

            if (!charactersTyped.isEmpty())
                Text.drawString(g, charactersTyped, x + (width / 2), y + 16, true, Color.YELLOW, Assets.font14);

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (isOpen) {
            // If escape is pressed, exit the textbox
            if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                sb.setLength(0);
                index = 0;
                charactersTyped = sb.toString();
                focus = false;
                isOpen = false;
                KeyManager.typingFocus = false;
                DevToolUI.isOpen = false;
                return;
            }
            if (focus) {
                // If enter is pressed, handle the input
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    enterPressed = true;
                    focus = false;
                    if (charactersTyped.isEmpty()) {
                        return;
                    }
                    charactersTyped = sb.toString();
                    sb.setLength(0);
                    index = 0;
                    return;
                }else{
                    Handler.get().playEffect("ui/textbox_type.wav");
                }

                // If backspace is pressed, remove the last index
                if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    if (index > 0) {
                        sb.deleteCharAt(index - 1);
                        index--;
                        charactersTyped = sb.toString();
                    }
                    return;
                }

                // If numbersOnly is true, only digits allowed
                if (numbersOnly) {
                    if (!Character.isDigit(e.getKeyChar())) {
                    } else {
                        if (index < 10) {
                            sb.append(e.getKeyChar());
                            index++;
                            charactersTyped = sb.toString();
                        }
                    }
                } else {
                    // Otherwise all characters allowed
                    if (index < MAX_CHARACTERS) {
                        sb.append(e.getKeyChar());
                        index++;
                        charactersTyped = sb.toString();
                    }
                }
            }
        } else {
            sb.setLength(0);
            charactersTyped = sb.toString();
            index = 0;

        }

        openKeyPressed = false;
    }

    public StringBuilder getSb() {
        return sb;
    }

    public void setSb(StringBuilder sb) {
        this.sb = sb;
    }

    public String getCharactersTyped() {
        return charactersTyped;
    }

    public void setCharactersTyped(String charactersTyped) {
        this.charactersTyped = charactersTyped;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}