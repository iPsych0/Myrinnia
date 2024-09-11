package dev.ipsych0.myrinnia.ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface Window extends MouseListener, MouseMotionListener, KeyListener {
    void tick();

    void render(Graphics2D g);

    void open();

    void close();

    void addListeners();

    void removeListeners();

    boolean isOpen();

    void setFocus(boolean focus);

    void mouseMoved(MouseEvent e);

    void mousePressed(MouseEvent e);

    void keyPressed(KeyEvent e);

    /*
     * Implement as needed
     */

    default void keyTyped(KeyEvent e) {}

    default void keyReleased(KeyEvent e) {}

    default void mouseClicked(MouseEvent e) {}

    default void mouseReleased(MouseEvent e) {}

    default void mouseDragged(MouseEvent e) {}

    /*
     * Probably never needed
     */
    default void mouseEntered(MouseEvent e) {}

    default void mouseExited(MouseEvent e) {}
}
