package dev.ipsych0.myrinnia.ui.windows;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.input.MouseManager;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class InputHandler implements Serializable {
    @Setter
    private boolean focus;
    @Getter
    private boolean open;
    @Getter
    private final Point mouse = new Point();
    private final Canvas canvas;
    private final Input input;

    public InputHandler(Input input) {
        this.canvas = Handler.get().getGame().getDisplay().getCanvas();
        this.input = input;

    }

    public void addListeners() {
        if (input instanceof KeyInput keyInput) {
            canvas.addKeyListener(keyInput);
        }
        if (input instanceof MouseInput mouseInput) {
            canvas.addMouseListener(mouseInput);
            canvas.addMouseMotionListener(mouseInput);
            canvas.addMouseWheelListener(mouseInput);
        }
    }

    public void removeListeners() {
        if (input instanceof KeyInput keyInput) {
            canvas.removeKeyListener(keyInput);
        }
        if (input instanceof MouseInput mouseInput) {
            canvas.removeMouseListener(mouseInput);
            canvas.removeMouseMotionListener(mouseInput);
            canvas.removeMouseWheelListener(mouseInput);
        }
    }

    public void open() {
        open = true;
        focus = true;
        addListeners();
    }

    public void close() {
        open = false;
        focus = false;
        removeListeners();
        MouseManager.justClosedUI = true;
    }

    public void mouseMoved(MouseEvent e) {
        mouse.setLocation(e.getX(), e.getY());
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Handler.get().getWindowManager().popWindow();
        }
    }

    public boolean hasFocus() {
        return focus;
    }
}
