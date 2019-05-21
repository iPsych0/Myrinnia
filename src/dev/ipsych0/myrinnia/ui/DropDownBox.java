package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.util.List;

import static dev.ipsych0.myrinnia.ui.ScrollBar.scrolledDown;
import static dev.ipsych0.myrinnia.ui.ScrollBar.scrolledUp;

public class DropDownBox extends UIImageButton {

    private List<String> items;
    private int itemsPerView;
    private int currentIndex, selectedIndex;
    private int scrollMin, scrollMax;
    private UIManager uiManager;
    private boolean open;
    private Color selectedColor = new Color(0, 255, 255, 62);

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

        if (items.size() <= 5) {
            itemsPerView = items.size();
        } else {
            itemsPerView = 6;
        }

        for (int i = 0; i < items.size(); i++) {
            UIImageButton btn = new UIImageButton(x, y + (i * 16), width, 16, Assets.genericButton);
            uiManager.addObject(btn);
        }
    }

    public DropDownBox(int x, int y, int width, int height, List<String> items) {
        this(x, y, width, height, items, 0);
    }

    public void tick() {
        uiManager.tick();

        Rectangle mouse = Handler.get().getMouse();
        if (!this.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            open = false;
            hasBeenPressed = false;
            this.height = 16;
        }

        if (open) {

            for (int i = currentIndex; i < scrollMax; i++) {
                Rectangle bounds = uiManager.getObjects().get(i);
                if (bounds.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                    selectedIndex = i;
                    hasBeenPressed = false;
                    open = false;
                    break;
                }
            }

            if (scrolledUp) {
                scrollUp();
                scrolledUp = false;
            } else if (scrolledDown) {
                scrollDown();
                scrolledDown = false;
            }
        } else {
            if (this.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                open = true;
                this.height = 16 * itemsPerView;
                hasBeenPressed = false;
            }
        }
    }

    private void scrollUp() {
        // Move up once per scroll
        if (currentIndex > scrollMin && items.size() > itemsPerView) {
            currentIndex--;
        }
    }

    private void scrollDown() {
        // Move down once per scroll
        if (currentIndex < (scrollMax - itemsPerView) && items.size() > itemsPerView) {
            currentIndex++;
        }
    }

    public void render(Graphics2D g) {
        if (open) {
            for (int i = currentIndex; i < itemsPerView; i++) {
                uiManager.getObjects().get(i).render(g);
                if (i == selectedIndex) {
                    g.setColor(selectedColor);
                    Rectangle bounds = uiManager.getObjects().get(i).getBounds();
                    g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
                }
                Text.drawString(g, items.get(i), x, uiManager.getObjects().get(i).y + 14, false, Color.YELLOW, Assets.font14);
            }
        } else {
            uiManager.getObjects().get(selectedIndex).render(g);
            Text.drawString(g, items.get(selectedIndex), x, y + 14, false, Color.YELLOW, Assets.font14);
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
}
