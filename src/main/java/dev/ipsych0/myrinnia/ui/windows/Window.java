package dev.ipsych0.myrinnia.ui.windows;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface Window {
    void tick();

    void render(Graphics2D g);

    void open();

    void close();

    InputHandler getInputHandler();

    /*
     * Default window behavior
     */

    default void addListeners() {
        getInputHandler().addListeners();
    }

    default void removeListeners() {
        getInputHandler().removeListeners();
    }

    default boolean isOpen() {
        return getInputHandler().isOpen();
    }

    default void setFocus(boolean focus) {
        getInputHandler().setFocus(focus);
    }

    default boolean hasFocus() {
        return getInputHandler().hasFocus();
    }

    default Point getMouse() {
        return getInputHandler().getMouse();
    }
}
