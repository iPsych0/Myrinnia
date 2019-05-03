package dev.ipsych0.myrinnia.display;

import dev.ipsych0.myrinnia.audio.AudioManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
    private static int windowedX, windowedY, windowedWidth, windowedHeight;
    private boolean fullScreen;
    private boolean fullScreenSupported;

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

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = env.getDefaultScreenDevice();
        fullScreenSupported = defaultScreen.isFullScreenSupported();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        windowedX = 0;
        windowedY = 0;
        windowedWidth = (int)screenSize.getWidth();
        windowedHeight = (int)screenSize.getHeight();

        if(fullScreenSupported){
            frame.setResizable(false);
            frame.setUndecorated(true);
            defaultScreen.setFullScreenWindow(frame);
            fullScreen = true;
        } else{
            frame.setUndecorated(false);
            frame.setResizable(true);
            defaultScreen.setFullScreenWindow(null); // windowed mode
            frame.setBounds(windowedX, windowedY, windowedWidth, windowedHeight);
            fullScreen = false;
        }

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

        // To save the window width and height if the window has been resized.
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                if (!fullScreen) {
                    windowedX = frame.getX();
                    windowedY = frame.getY();
                }
            }

            @Override
            public void componentResized(ComponentEvent e) {
                if (!fullScreen) {
                    windowedWidth = frame.getWidth();
                    windowedHeight = frame.getHeight();
                }
            }
        });

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
