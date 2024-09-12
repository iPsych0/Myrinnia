package dev.ipsych0.myrinnia.ui.windows;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface KeyInput extends Input, KeyListener {
    default void keyTyped(KeyEvent e) {}

    default void keyPressed(KeyEvent e) {}

    default void keyReleased(KeyEvent e) {}
}
