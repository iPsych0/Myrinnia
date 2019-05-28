package dev.ipsych0.myrinnia.chatwindow;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.devtools.DevToolUI;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.ScrollBar;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private Rectangle windowBounds;
    private ScrollBar scrollBar;
    private List<Filter> filters;

    private LinkedList<TextSlot> textSlots;

    public ChatWindow() {
        this.textSlots = new LinkedList<>();
        this.width = TextSlot.textWidth;
        this.height = MESSAGE_PER_VIEW * TextSlot.textHeight;
        this.x = 8;
        this.y = Handler.get().getHeight() - height - TextSlot.textHeight;
        this.filters = new ArrayList<>();

        windowBounds = new Rectangle(x, y, width, height);
        scrollBar = new ScrollBar(x + width - 24, y + 4, 16, height, 0, MESSAGE_PER_VIEW, windowBounds, true);
    }

    public void tick() {
        if (chatIsOpen) {
            // Tick dev tool
            if (DevToolUI.isOpen) {
                Handler.get().getDevToolUI().tick();
            }

            scrollBar.tick();

            if (scrollBar.hasScrolledUp()) {
                for (int i = 0; i < scrollBar.getScrollMaximum(); i++) {
                    textSlots.get(i).setY(textSlots.get(i).getY() - TextSlot.textHeight);
                }
                scrollBar.setScrolledUp(false);
            } else if (scrollBar.hasScrolledDown()){
                for (int i = 0; i < scrollBar.getScrollMaximum(); i++) {
                    textSlots.get(i).setY(textSlots.get(i).getY() + TextSlot.textHeight);
                }
                scrollBar.setScrolledDown(false);
            }
        }
    }

    public void render(Graphics2D g) {
        if (chatIsOpen) {

            // Render dev tool
            if (DevToolUI.isOpen) {
                Handler.get().getDevToolUI().render(g);
            }

            Stroke originalStroke = g.getStroke();
            g.drawImage(Assets.uiWindow, x, y - 19, width, height + 8 + 20, null);
            g.setStroke(new BasicStroke(2));
            g.setColor(Color.BLACK);
            g.drawLine(x + 1, y + 1, x + width - 2, y + 1);
            g.setStroke(originalStroke);

            Text.drawString(g, Handler.get().getPlayer().getZone().getName(), x + (width / 2), y - 9, true, Color.YELLOW, Assets.font14);

            scrollBar.render(g);

            if (textSlots.size() > 7 && textSlots.size() <= 35) {
                for (int i = scrollBar.getIndex(); i < MESSAGE_PER_VIEW + scrollBar.getIndex(); i++) {
                    textSlots.get(i).render(g);
                }
            } else if (textSlots.size() > 0 && textSlots.size() <= 7) {
                for (int i = 0; i < textSlots.size(); i++) {
                    textSlots.get(i).render(g);
                }
            }
        }
    }

    /*
     * Sends a message to the chat log
     */
    public boolean sendMessage(String message, Filter filter) {
        if(filter != null && filters.contains(filter)){
            return false;
        }
        int offSet = 0;
        // If the chat is full, remove the first element (FIFO)
        if (textSlots.size() == MAX_MESSAGES) {
            textSlots.removeLast();
            // The Y-offset for the new slot based on the current scroll index
            offSet = scrollBar.getIndex();
        }
        // When a new message is added, move up all existing slots by 1 slotsize
        for(TextSlot ts : textSlots){
            ts.setY(ts.getY() - TextSlot.textHeight);
        }

        // Add a timestamp (HH:mm format)
        LocalDateTime ldt = LocalDateTime.now();
        String timeStamp = ldt.toLocalTime().toString().substring(0, 5);

        textSlots.addFirst(new TextSlot(x, y + height - TextSlot.textHeight + (offSet * TextSlot.textHeight),
                "[" + timeStamp + "]: " + message));
        scrollBar.setListSize(textSlots.size());
        scrollBar.setScrollMaximum(textSlots.size());
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
