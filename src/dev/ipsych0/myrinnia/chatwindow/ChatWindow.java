package dev.ipsych0.myrinnia.chatwindow;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.devtools.DevToolUI;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ChatWindow implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -5673402739754368073L;

    public static boolean chatIsOpen = true;

    private int x, y;
    private int width, height;

    private static final int MESSAGE_PER_VIEW = 7;
    private static final int MAX_MESSAGES = 35;
    private int currentIndex;
    private Rectangle windowBounds;

    private LinkedList<TextSlot> textSlots;

    public ChatWindow() {
        this.textSlots = new LinkedList<>();
        this.width = TextSlot.textWidth;
        this.height = MESSAGE_PER_VIEW * TextSlot.textHeight;
        this.x = 8;
        this.y = Handler.get().getHeight() - height - 16;

        windowBounds = new Rectangle(x, y, width, height);
    }

    public void tick() {
        if (chatIsOpen) {

            // Tick dev tool
            if (DevToolUI.isOpen) {
                Handler.get().getDevToolUI().tick();
            }
        }
    }

    public void render(Graphics2D g) {
        if (chatIsOpen) {

            // Render dev tool
            if (DevToolUI.isOpen) {
                Handler.get().getDevToolUI().render(g);
            }

            g.drawImage(Assets.chatwindow, x, y, width, height + 8, null);
            g.drawImage(Assets.chatwindowTop, x, y - 19, width, 20, null);

            Text.drawString(g, Handler.get().getPlayer().getZone().getName(), x + (width / 2), y - 9, true, Color.YELLOW, Assets.font14);

            if (textSlots.size() > 7 && textSlots.size() <= 35) {
                for (int i = currentIndex; i < MESSAGE_PER_VIEW + currentIndex; i++) {
                    textSlots.get(i).render(g);
                }
            } else if(textSlots.size() > 0 && textSlots.size() <= 7){
                for (int i = 0; i < textSlots.size(); i++) {
                    textSlots.get(i).render(g);
                }
            }
        }
    }

    /*
     * Sends a message to the chat log
     */
    public boolean sendMessage(String message) {
        if (textSlots.size() == MAX_MESSAGES) {
            textSlots.removeLast();
        }
        for(TextSlot ts : textSlots){
            ts.setY(ts.getY() - 16);
        }
        textSlots.addFirst(new TextSlot(x, y + height - 16, message));
        return true;
    }

    private List<TextSlot> getTextSlots() {
        return textSlots;
    }

    public Rectangle getWindowBounds() {
        return windowBounds;
    }

    public void setWindowBounds(Rectangle windowBounds) {
        this.windowBounds = windowBounds;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
