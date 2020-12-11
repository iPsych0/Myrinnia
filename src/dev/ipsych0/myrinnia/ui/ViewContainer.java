package dev.ipsych0.myrinnia.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ViewContainer<T extends UIObject> {

    public static final int HORIZONTAL = 0, VERTICAL = 1;

    private List<T> elements;
    private int x, y, width, height;
    private int orientation = VERTICAL;
    private ScrollBar<T> scrollBar;
    private List<T> toBeRemoved = new ArrayList<>();

    private ViewContainer(Rectangle window, List<T> elements, ScrollBar<T> scrollBar, int orientation) {
        this.x = window.x;
        this.y = window.y;
        this.width = window.width;
        this.height = window.height;
        this.elements = elements;
        this.scrollBar = scrollBar;
        this.orientation = orientation;

        if (orientation == HORIZONTAL) {
            throw new IllegalArgumentException("Unsupported horizontal type. Coming soon.");
        }
    }

    public void tick() {
        if (elements.isEmpty())
            return;

        if (elements.size() < scrollBar.getItemsPerWindow()) {
            for (T element : elements) {
                element.tick();
            }
        } else {
            scrollBar.tick();
            for (int i = scrollBar.getIndex(); i < scrollBar.getIndex() + scrollBar.getItemsPerWindow(); i++) {
                T element = elements.get(i);
                element.tick();
            }
        }

        elements.removeAll(toBeRemoved);
    }

    public void render(Graphics2D g) {
        if (elements.isEmpty())
            return;
        if (elements.size() < scrollBar.getItemsPerWindow()) {
            for (T element : elements) {
                element.render(g);
            }
        } else {
            scrollBar.render(g);
            for (int i = scrollBar.getIndex(); i < scrollBar.getIndex() + scrollBar.getItemsPerWindow(); i++) {
                T element = elements.get(i);
                element.render(g);
            }
        }
    }

    public void add(T t) {
        elements.add(t);
    }

    public void remove(T t) {
        toBeRemoved.add(t);
    }

    public void updateContents(List<T> elements) {
        this.elements = elements;
        scrollBar.updateContents(elements);
    }

    public ScrollBar<T> getScrollBar() {
        return scrollBar;
    }

    public void setScrollBar(ScrollBar<T> scrollBar) {
        this.scrollBar = scrollBar;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public static class Builder<T extends UIObject> {
        private List<T> elements;
        private Rectangle window;
        private ScrollBar<T> scrollBar;
        private int orientation = 1;

        public Builder(Rectangle window, List<T> elements) {
            this.window = window;
            this.elements = elements;
            this.scrollBar = new ScrollBar<>(window.x + window.width - 32, window.y + 32, 32, window.height - 64, elements, 10, window);

        }

        public Builder<T> andScrollBar(int itemsPerView, Rectangle position, boolean reverseScroll) {
            this.scrollBar = new ScrollBar<>(position.x, position.y, position.width, position.height, elements, itemsPerView, window, reverseScroll);
            return this;
        }

        public Builder<T> withOrientation(int orientation) {
            this.orientation = orientation;
            return this;
        }

        public ViewContainer<T> build() {
            return new ViewContainer<>(window, elements, scrollBar, orientation);
        }
    }
}


