package dev.ipsych0.myrinnia.chatwindow;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.devtools.DevToolUI;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.ViewContainer;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

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
    private ViewContainer<TextSlot> view;
    private Set<Filter> filters;

    public ChatWindow() {
        this.width = TextSlot.textWidth;
        this.height = MESSAGE_PER_VIEW * TextSlot.textHeight;
        this.x = 8;
        this.y = Handler.get().getHeight() - height - TextSlot.textHeight;
        this.filters = new HashSet<>();

        // Enable all chat messages
        Collection<Filter> filters = Arrays.asList(Filter.values());
        this.filters.addAll(filters);

        windowBounds = new Rectangle(x, y, width, height);

        view = new ViewContainer.Builder<>(new Rectangle(x, y, width, height), new ArrayList<TextSlot>())
                .withOrientation(ViewContainer.VERTICAL)
                .andScrollBar(MESSAGE_PER_VIEW, new Rectangle(x + width - 24, y + 4, 16, height), true)
                .build();
    }

    public void tick() {
        // Tick dev tool
        if (DevToolUI.isOpen) {
            Handler.get().getDevToolUI().tick();
        }

        if (chatIsOpen) {
            view.tick();
        }
    }

    public void render(Graphics2D g) {
        // Render dev tool
        if (DevToolUI.isOpen) {
            Handler.get().getDevToolUI().render(g);
        }

        if (chatIsOpen) {

            Composite current = g.getComposite();
            if (Handler.get().getGameCamera().isAtLeftBound() && Handler.get().getGameCamera().isAtBottomBound()) {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }

            g.drawImage(Assets.uiWindow, x, y - 19, width, height + 8 + 20, null);
            g.setColor(Color.BLACK);
            g.drawLine(x + 1, y + 1, x + width - 2, y + 1);

            Text.drawString(g, Handler.get().getPlayer().getZone().getName(), x + (width / 2), y - 9, true, Color.YELLOW, Assets.font14);

            view.render(g);

            g.setComposite(current);
        }
    }

    /*
     * Sends a message to the chat log
     */
    public boolean sendMessage(String message, Filter filter) {
        if (filter != null && !filters.contains(filter)) {
            return false;
        }
        // If the chat is full, remove the first element (FIFO)
        if (view.getElements().size() == MAX_MESSAGES) {
            view.getElements().remove(view.getElements().size() - 1);
        }

        // When a new message is added, move up all existing slots by 1 slotsize
        for (TextSlot ts : view.getElements()) {
            ts.setLocation((int) ts.getX(), (int) ts.getY() - TextSlot.textHeight);
        }

        int size = view.getElements().size();
        int yPos = size == 0 ? y + height - TextSlot.textHeight : view.getElements().get(0).y + 16;
        if (filters.contains(Filter.TIMESTAMP)) {
            // Add a timestamp (HH:mm format)
            LocalDateTime ldt = LocalDateTime.now();
            String timeStamp = ldt.toLocalTime().toString().substring(0, 5);

            view.getElements().add(0, new TextSlot(x, yPos,
                    "[" + timeStamp + "]: " + message));
        } else {
            view.getElements().add(0, new TextSlot(x, yPos, message));
        }
        view.updateContents(view.getElements());
        return true;
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

    public Set<Filter> getFilters() {
        return filters;
    }

    public void setFilters(Set<Filter> filters) {
        this.filters = filters;
    }
}
