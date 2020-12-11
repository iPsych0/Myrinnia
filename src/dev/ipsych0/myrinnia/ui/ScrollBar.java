package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

public class ScrollBar<T extends UIObject> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8698572954217286128L;
    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle arrowUp, arrowDown;
    private int scrollMinimum, scrollMaximum;
    private int index;
    private int itemsPerWindow;
    private List<T> elements;
    private int listSize;
    public static int clickTimer;
    public static int scrollTimer;
    public static boolean scrolledUp, scrolledDown;
    private boolean open;
    private Rectangle windowToScrollIn;
    private boolean reversedScroll;
    private Font fontSize;
    private boolean hasScrolledUp, hasScrolledDown;

    public ScrollBar(int x, int y, int width, int height, List<T> elements, int itemsPerWindow, Rectangle windowToScrollIn, boolean reversedScroll) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.listSize = elements.size();
        this.elements = elements;
        this.itemsPerWindow = itemsPerWindow;
        this.windowToScrollIn = windowToScrollIn;
        this.reversedScroll = reversedScroll;

        arrowUp = new Rectangle(x, y, width, width);
        arrowDown = new Rectangle(x, y + height - width, width, width);

        if (width == ItemSlot.SLOTSIZE) {
            fontSize = Assets.font32;
        } else {
            fontSize = Assets.font14;
        }

        updateContents(elements);
    }

    public ScrollBar(int x, int y, int width, int height, List<T> elements, int itemsPerWindow, Rectangle windowToScrollIn) {
        this(x, y, width, height, elements, itemsPerWindow, windowToScrollIn, false);
    }

    public void tick() {
        if (open) {
            Rectangle mouse = Handler.get().getMouse();

            // If we're scrolling in the window, update the shown list
            if (windowToScrollIn.contains(mouse)) {
                if (!reversedScroll) {
                    if (arrowUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
                        scrollClickUp();
                    } else if (arrowDown.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
                        scrollClickDown();
                    }
                } else {
                    if (arrowUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
                        scrollClickDown();
                    } else if (arrowDown.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
                        scrollClickUp();
                    }
                }

                if (!reversedScroll) {
                    if (scrolledUp) {
                        scrollUp();
                        scrolledUp = false;
                    } else if (scrolledDown) {
                        scrollDown();
                        scrolledDown = false;
                    }
                } else {
                    if (scrolledUp) {
                        scrollDown();
                        scrolledUp = false;
                    } else if (scrolledDown) {
                        scrollUp();
                        scrolledDown = false;
                    }
                }
            }
        }
    }

    private void scrollUp() {
        // Move up once per scroll
        if (index > scrollMinimum && listSize > itemsPerWindow) {
            updateUpPos();
            index--;
            hasScrolledUp = true;
        }
    }

    private void scrollDown() {
        // Move down once per scroll
        if (index < (scrollMaximum - itemsPerWindow) && listSize > itemsPerWindow) {
            updateDownPos();
            index++;
            hasScrolledDown = true;
        }

    }

    private void scrollClickUp() {
        // The first click, move it up once
        if (clickTimer == 0) {
            if (listSize < itemsPerWindow) {
                return;
            }
            if (index == scrollMinimum) {
                return;
            } else {
                updateUpPos();
                index--;
                hasScrolledUp = true;
                Handler.get().playEffect("ui/ui_button_click.ogg");
            }
        }
        clickTimer++;

        // After .5 seconds of pressing, start scrolling
        if (clickTimer >= 30) {
            scrollTimer++;
            if (scrollTimer % 6 == 0) {
                if (listSize < itemsPerWindow) {
                    return;
                }
                if (index == scrollMinimum) {
                    return;
                } else {
                    updateUpPos();
                    index--;
                    hasScrolledUp = true;
                    Handler.get().playEffect("ui/ui_button_click.ogg");
                }
            }
        }
    }

    public void render(Graphics2D g) {
        if (open) {
            Rectangle mouse = Handler.get().getMouse();

            if (listSize > itemsPerWindow) {

                if (arrowUp.contains(mouse)) {
                    g.drawImage(Assets.scrollUpButton[0], arrowUp.x, arrowUp.y, arrowUp.width, arrowUp.height, null);
                } else {
                    g.drawImage(Assets.scrollUpButton[1], arrowUp.x, arrowUp.y, arrowUp.width, arrowUp.height, null);
                }

                if (arrowDown.contains(mouse)) {
                    g.drawImage(Assets.scrollDownButton[0], arrowDown.x, arrowDown.y, arrowDown.width, arrowDown.height, null);
                } else {
                    g.drawImage(Assets.scrollDownButton[1], arrowDown.x, arrowDown.y, arrowDown.width, arrowDown.height, null);
                }

            }
        }
    }

    private void scrollClickDown() {
        // The first click, move it down once
        if (clickTimer == 0) {
            if (listSize < itemsPerWindow) {
                return;
            }
            if (index == scrollMaximum - itemsPerWindow) {
                return;
            } else {
                updateDownPos();
                index++;
                hasScrolledDown = true;
                Handler.get().playEffect("ui/ui_button_click.ogg");
            }
        }
        clickTimer++;

        // After .5 seconds of pressing, start scrolling
        if (clickTimer >= 30) {
            scrollTimer++;
            if (scrollTimer % 6 == 0) {
                if (listSize < itemsPerWindow) {
                    return;
                }
                if (index != (scrollMaximum - itemsPerWindow)) {
                    updateDownPos();
                    index++;
                    hasScrolledDown = true;
                    Handler.get().playEffect("ui/ui_button_click.ogg");
                }
            }
        }
    }

    private void updateDownPos(){
        for (T t : elements) {
            // Shift up/down the position of the element depending on the index
            double y = t.getY();
            if (reversedScroll) {
                t.setLocation((int) t.getX(), (int) (y + (t.getHeight())));
            } else {
                t.setLocation((int) t.getX(), (int) (y - (t.getHeight())));
            }
        }
    }

    private void updateUpPos(){
        for (T t : elements) {
            // Shift up/down the position of the element depending on the index
            double y = t.getY();
            if (reversedScroll) {
                t.setLocation((int) t.getX(), (int) (y - (t.getHeight())));
            } else {
                t.setLocation((int) t.getX(), (int) (y + (t.getHeight())));
            }
        }
    }

    void updateContents(List<T> elements) {
        this.elements = elements;
        listSize = elements.size();
        scrollMaximum = listSize;
        open = listSize > itemsPerWindow;
    }

    public void setScrollMaximum(int scrollMaximum) {
        this.scrollMaximum = scrollMaximum;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public int getItemsPerWindow() {
        return itemsPerWindow;
    }

    public void setItemsPerWindow(int itemsPerWindow) {
        this.itemsPerWindow = itemsPerWindow;
    }

    public boolean hasScrolledUp() {
        return hasScrolledUp;
    }

    public boolean hasScrolledDown() {
        return hasScrolledDown;
    }

    public void setScrolledUp(boolean hasScrolledUp) {
        this.hasScrolledUp = hasScrolledUp;
    }

    public void setScrolledDown(boolean hasScrolledDown) {
        this.hasScrolledDown = hasScrolledDown;
    }

    public int getScrollMinimum() {
        return scrollMinimum;
    }

    public void setScrollMinimum(int scrollMinimum) {
        this.scrollMinimum = scrollMinimum;
    }

    public int getScrollMaximum() {
        return scrollMaximum;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getListSize() {
        return listSize;
    }

}
