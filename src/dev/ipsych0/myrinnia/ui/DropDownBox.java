package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static dev.ipsych0.myrinnia.ui.ScrollBar.scrolledDown;
import static dev.ipsych0.myrinnia.ui.ScrollBar.scrolledUp;

public class DropDownBox<T> {

    private int x, y;
    private int width, height;
    private List<T> items;
    private int itemsPerView;
    private int currentIndex, selectedIndex;
    private int scrollMin, scrollMax;
    private List<UIImageButton> elements;
    private UIManager uiManager;
    private boolean open;

    public DropDownBox(int x, int y, int width, int height, List<T> items) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scrollMax = items.size();
        this.items = items;
        this.elements = new ArrayList<>();
        this.uiManager = new UIManager();

        if (items.size() <= 5) {
            itemsPerView = items.size();
        } else {
            itemsPerView = 6;
        }

        for(int i = 0; i < items.size(); i++){
            UIImageButton btn = new UIImageButton(x, y + (i * 16), width, 16, Assets.genericButton);
            uiManager.addObject(btn);
        }
    }

    public void tick() {
        if(open) {
            Rectangle mouse = Handler.get().getMouse();

            if (scrolledUp) {
                scrollUp();
                scrolledUp = false;
            } else if (scrolledDown) {
                scrollDown();
                scrolledDown = false;
            }

            uiManager.tick();
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
        if(open) {
            for (int i = currentIndex; i < scrollMax; i++) {
                elements.get(i).render(g);
                Text.drawString(g, items.get(i).toString(), x, elements.get(i).y + 1, false, Color.YELLOW, Assets.font14);
            }
        }
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
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
