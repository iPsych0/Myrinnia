package dev.ipsych0.myrinnia.display;

import dev.ipsych0.myrinnia.audio.AudioManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;


public class Display implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 5463768214564927571L;
    private JFrame frame;
    private Canvas canvas;

    private String title;
    private int width, height;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        createDisplay();

    }

    private void createDisplay() {
        frame = new JFrame(title);
        frame.setSize(width, height);

        // For the X (close) button
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    AudioManager.cleanUp();
                } catch (Exception e) {
                    System.err.println("Unexpected crash. Unable to close OpenAL audio context.");
                } finally {
                    System.exit(0);
                }
            }
        });
        frame.setResizable(false);

        // Settings for fullscreen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        // Window will appear in the center of the user's screen --- Uncomment for windowed-mode
//		frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);

        frame.pack();
        frame.setResizable(false);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }
}
