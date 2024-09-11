package dev.ipsych0.myrinnia.ui;

import java.awt.*;
import java.util.Stack;

public class WindowManager {
    private final Stack<Window> windows = new Stack<>();

    public void tick() {
        windows.forEach(Window::tick);
    }

    public void render(Graphics2D g) {
        windows.forEach(window -> window.render(g));
    }

    public void pushWindow(Window window) {
        // Adds a new window with focus onto the stack and removes focus from the last window
        if (!windows.isEmpty()) {
            windows.peek().setFocus(false);
        }
        windows.push(window);
        window.setFocus(true);
        window.open();
    }

    public void popWindow() {
        // Remove the focus from the last window
        if (!windows.isEmpty()) {
            Window latest = windows.peek();
            latest.setFocus(false);
            latest.close();
            windows.pop();
            if (!windows.isEmpty()) {
                // Reactivate focus on the last window
                windows.peek().setFocus(true);
            }
        }
    }

    public int size() {
        return windows.size();
    }


}
