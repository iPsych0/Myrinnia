package dev.ipsych0.myrinnia.ui.windows;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public interface MouseInput extends Input, MouseListener, MouseMotionListener, MouseWheelListener {

    void mouseMoved(MouseEvent e);

    void mousePressed(MouseEvent e);

    /*
     * Implement as needed
     */

    default void mouseClicked(MouseEvent e) {}

    default void mouseReleased(MouseEvent e) {}

    default void mouseDragged(MouseEvent e) {}

    default void mouseWheelMoved(MouseWheelEvent e) {}


    /*
     * Probably never needed
     */
    default void mouseEntered(MouseEvent e) {}

    default void mouseExited(MouseEvent e) {}
}
