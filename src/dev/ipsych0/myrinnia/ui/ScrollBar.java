package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class ScrollBar implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8698572954217286128L;
    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle arrowUp, arrowDown;
    private int scrollMinimum = 0, scrollMaximum;
    private int index = 0;
    private int itemsPerWindow;
    private int listSize;
    public static int clickTimer = 0;
    public static int scrollTimer = 0;
    public static boolean scrolledUp, scrolledDown;

    public ScrollBar(int x, int y, int width, int height, int listSize, int itemsPerWindow) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.listSize = listSize;
        this.itemsPerWindow = itemsPerWindow;

        arrowUp = new Rectangle(x, y, 32, 32);
        arrowDown = new Rectangle(x, y + height - 32, 32, 32);
    }

    public void tick() {
        Rectangle mouse = Handler.get().getMouse();

        if (arrowUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
            scrollClickUp();
        } else if (arrowDown.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
            scrollClickDown();
        }

        if (scrolledUp) {
            scrollUp();
            scrolledUp = false;
        } else if (scrolledDown) {
            scrollDown();
            scrolledDown = false;
        }
    }

    private void scrollUp() {
        // Move up once per scroll
        if (index > scrollMinimum && listSize > itemsPerWindow) {
            index--;
        }
    }

    private void scrollDown() {
        // Move down once per scroll
        if (index < (scrollMaximum - itemsPerWindow) && listSize > itemsPerWindow) {
            index++;
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
                index--;
                Handler.get().playEffect("ui/ui_button_click.wav");
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
                } else {
                    index--;
                    Handler.get().playEffect("ui/ui_button_click.wav");
                }
            }
        }
    }

    public void render(Graphics2D g) {
        Rectangle mouse = Handler.get().getMouse();

        if (listSize > itemsPerWindow) {
            if (arrowUp.contains(mouse)) {
                g.drawImage(Assets.genericButton[0], arrowUp.x, arrowUp.y, arrowUp.width, arrowUp.height, null);
                Text.drawString(g, "^", arrowUp.x + 16, arrowUp.y + 24, true, Color.YELLOW, Assets.font32);
            } else {
                g.drawImage(Assets.genericButton[1], arrowUp.x, arrowUp.y, arrowUp.width, arrowUp.height, null);
                Text.drawString(g, "^", arrowUp.x + 16, arrowUp.y + 24, true, Color.YELLOW, Assets.font32);
            }

            if (arrowDown.contains(mouse)) {
                g.drawImage(Assets.genericButton[0], arrowDown.x, arrowDown.y, arrowDown.width, arrowDown.height, null);
                Text.drawString(g, "v", arrowDown.x + 16, arrowDown.y + 16, true, Color.YELLOW, Assets.font32);
            } else {
                g.drawImage(Assets.genericButton[1], arrowDown.x, arrowDown.y, arrowDown.width, arrowDown.height, null);
                Text.drawString(g, "v", arrowDown.x + 16, arrowDown.y + 16, true, Color.YELLOW, Assets.font32);
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
                index++;
                Handler.get().playEffect("ui/ui_button_click.wav");
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
                if (index == scrollMaximum - itemsPerWindow) {
                } else {
                    index++;
                    Handler.get().playEffect("ui/ui_button_click.wav");
                }
            }
        }
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

    public void setScrollMaximum(int scrollMaximum) {
        this.scrollMaximum = scrollMaximum;
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

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

}
