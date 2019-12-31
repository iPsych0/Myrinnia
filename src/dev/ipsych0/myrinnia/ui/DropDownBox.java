package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.util.List;

public class DropDownBox extends UIImageButton {

    private List<String> items;
    private int itemsPerView;
    private int currentIndex, selectedIndex;
    private int scrollMin, scrollMax;
    private UIManager uiManager;
    private boolean open;
    private Color selectedColor = new Color(0, 255, 255, 62);
    public static boolean hasScrolledUp, hasScrolledDown;
    private boolean itemChanged;
    private int initialHeight;

    /*
     * Create DropDown with custom selected index
     */
    public DropDownBox(int x, int y, int width, int height, List<String> items, int selectedIndex) {
        super(x, y, width, height, Assets.genericButton);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scrollMax = items.size();
        this.items = items;
        this.uiManager = new UIManager();
        this.selectedIndex = selectedIndex;
        this.initialHeight = height;

        if (items.size() <= 5) {
            itemsPerView = items.size();
        } else {
            itemsPerView = 6;
        }

        for (int i = 0; i < items.size(); i++) {
            UIImageButton btn = new UIImageButton(x, y + (i * height), width, height, Assets.genericButton);
            uiManager.addObject(btn);
        }
    }

    /*
     * Create default DropDown with default selected item as the first element
     */
    public DropDownBox(int x, int y, int width, int height, List<String> items) {
        this(x, y, width, height, items, 0);
    }

    public void tick() {
        uiManager.tick();

        Rectangle mouse = Handler.get().getMouse();
        if (open) {
            for (int i = currentIndex; i < scrollMax; i++) {
                Rectangle bounds = uiManager.getObjects().get(i);
                if (bounds.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                    selectedIndex = i;
                    itemChanged = true;
                    closeDropDown();
                    Handler.get().playEffect("ui/ui_button_click.ogg");
                    break;
                }
            }

            if (!this.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                closeDropDown();
            }

            if (hasScrolledUp) {
                scrollUp();
                hasScrolledUp = false;
            } else if (hasScrolledDown) {
                scrollDown();
                hasScrolledDown = false;
            }
        } else {
            if (this.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                openDropDown();
                Handler.get().playEffect("ui/ui_button_click.ogg");
            }
        }
    }

    private void closeDropDown() {
        if (open) {
            open = false;
            hasBeenPressed = false;
            this.height = initialHeight;
            for (int i = 0; i < scrollMax; i++) {
                UIObject btn = uiManager.getObjects().get(i);
                uiManager.getObjects().get(i).setLocation(btn.x, btn.y + initialHeight * currentIndex);
            }
            currentIndex = 0;
        }
    }

    private void openDropDown() {
        if (!open) {
            this.height = initialHeight * itemsPerView;
            hasBeenPressed = false;
            open = true;
            currentIndex = 0;
        }
    }

    private void scrollUp() {
        // Move up once per scroll
        if (currentIndex > scrollMin && items.size() > itemsPerView) {
            currentIndex--;
            for (int i = 0; i < scrollMax; i++) {
                UIObject btn = uiManager.getObjects().get(i);
                uiManager.getObjects().get(i).setLocation(btn.x, btn.y + initialHeight);
            }
        }
    }

    private void scrollDown() {
        // Move down once per scroll
        if (currentIndex < (scrollMax - itemsPerView) && items.size() > itemsPerView) {
            currentIndex++;
            for (int i = 0; i < scrollMax; i++) {
                UIObject btn = uiManager.getObjects().get(i);
                uiManager.getObjects().get(i).setLocation(btn.x, btn.y - initialHeight);
            }
        }
    }

    public void render(Graphics2D g) {
        Stroke originalStroke = g.getStroke();
        if (open) {
            for (int i = currentIndex; i < itemsPerView + currentIndex; i++) {
                UIObject o = uiManager.getObjects().get(i);
                o.render(g);
                if (i == selectedIndex) {
                    g.setColor(selectedColor);
                    Rectangle bounds = o.getBounds();
                    g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
                }

                Text.drawString(g, items.get(i), x + o.width / 2, o.y + o.height / 2, true, Color.YELLOW, Assets.font14);

                if (i == 0) {
                    Text.drawString(g, "^", x + width - 9, y + o.height / 2, true, Color.YELLOW, Assets.font14);
                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.BLACK);
                    g.drawLine(x + width - 18, y + 1, x + width - 18, y + height - 2);
                    g.setStroke(originalStroke);
                }
            }
        } else {
            UIObject o = uiManager.getObjects().get(0);
            o.render(g);
            g.setStroke(new BasicStroke(1));
            g.setColor(Color.BLACK);
            g.drawLine(x + width - 18, y + 1, x + width - 18, y + height - 2);
            g.setStroke(originalStroke);
            Text.drawString(g, items.get(selectedIndex), x + o.width / 2, y + o.height / 2, true, Color.YELLOW, Assets.font14);
            Text.drawString(g, "v", x + width - 9, y + o.height / 2 - 2, true, Color.YELLOW, Assets.font14);
        }
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isItemChanged() {
        return itemChanged;
    }

    public void setItemChanged(boolean itemChanged) {
        this.itemChanged = itemChanged;
    }
}
