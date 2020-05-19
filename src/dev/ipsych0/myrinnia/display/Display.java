package dev.ipsych0.myrinnia.display;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.audio.AudioManager;
import dev.ipsych0.myrinnia.states.GraphicsState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;


public class Display implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 5463768214564927571L;
    private JFrame frame;
    private Canvas canvas;
    private int windowedX, windowedY, windowedWidth, windowedHeight;
    private int lastWindowWidth, lastWindowHeight;
    private boolean fullScreen;
    private boolean fullScreenSupported;
    private GraphicsDevice gfxCard;

    private String title;
    private int width, height;
    public static double scaleX, scaleY;
    private boolean initialized;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
    }

    private void createDisplay() {
        frame = new JFrame(title);

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gfxCard = env.getDefaultScreenDevice();
        fullScreenSupported = gfxCard.isFullScreenSupported();

        lastWindowWidth = width;
        lastWindowHeight = height;

        // Basic windowed mode settings
        frame.setUndecorated(false);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        gfxCard.setFullScreenWindow(null);
        scaleX = 1.0;
        scaleY = 1.0;

        // For the X (close) button
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    // Close window without visual delay
                    frame.setVisible(false);
                    frame.dispose();

                    // Clean up all the audio references
                    AudioManager.cleanUp();
                } catch (Exception e) {
                    System.err.println("Unexpected crash. Unable to close OpenAL audio context.");
                } finally {
                    System.exit(0);
                }
            }
        });

        addFrameListeners();

        // Window will appear in the center of the user's screen
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.setBackground(Color.BLACK);
        frame.setIgnoreRepaint(true);

        frame.add(canvas);

        frame.pack();

        // If supported, start game in fullscreen, otherwise center the windowed application
        // TODO: INVERT THESE, FOR NOW ALWAYS WINDOWED MODE FOR TESTING
        if (!fullScreenSupported) {
            fullScreen = true;
            setWindowedScreen();
        } else {
            frame.setLocationRelativeTo(null);
        }

        // Save the frame's position
        windowedX = frame.getX();
        windowedY = frame.getY();
        windowedWidth = frame.getWidth();
        windowedHeight = frame.getHeight();
    }

    public void setFullScreen() {
        if (fullScreenSupported) {
            if (!fullScreen) {
                // Save the window's last position before going fullscreen
                lastWindowWidth = frame.getWidth();
                lastWindowHeight = frame.getHeight();

                // Switch to fullscreen mode
                Rectangle preferredWindowedBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                frame.setVisible(false);
                frame.dispose();
                frame.setBounds(windowedX, windowedY, preferredWindowedBounds.width, preferredWindowedBounds.height);
                frame.setUndecorated(true);
                gfxCard.setFullScreenWindow(frame);
                frame.setVisible(true);

                // Scale the window to fullscreen size
                windowedX = frame.getX();
                windowedY = frame.getY();
                windowedWidth = frame.getWidth();
                windowedHeight = frame.getHeight();
                scaleX = (double) frame.getWidth() / (double) width;
                scaleY = (double) frame.getHeight() / (double) height;

                fullScreen = true;

                // Change the selected item in the dropdown to 'fullscreen'
                getGraphicsState().getDisplayModeDropDown().setSelectedIndex(0);
            }
        }
    }

    public void setWindowedScreen() {
        if (fullScreen) {
            // Switch to windowed mode
            frame.dispose();
            frame.setVisible(false);
            gfxCard.setFullScreenWindow(null);
            frame.setUndecorated(false);
            frame.setVisible(true);

            // Set the window back to the last size
            frame.setSize(lastWindowWidth, lastWindowHeight);
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);

            // Scale the window accordingly
            windowedX = frame.getX();
            windowedY = frame.getY();
            windowedWidth = frame.getWidth();
            windowedHeight = frame.getHeight();
            scaleX = (double) frame.getWidth() / (double) width;
            scaleY = (double) frame.getHeight() / (double) height;

            fullScreen = false;

            // Change the selected item in the dropdown to 'windowed'
            getGraphicsState().getDisplayModeDropDown().setSelectedIndex(1);
        }
    }

    private void addFrameListeners() {
        // To save the window dimensions if the window has been moved or resized
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                if (!fullScreen && initialized) {
                    windowedX = frame.getX();
                    windowedY = frame.getY();
                }
            }

            @Override
            public void componentResized(ComponentEvent e) {
                if (!fullScreen && initialized) {
                    windowedWidth = canvas.getWidth();
                    windowedHeight = canvas.getHeight();

                    // Rescale dimension relative to original width/height
                    scaleX = (double) windowedWidth / (double) width;
                    scaleY = (double) windowedHeight / (double) height;
                }
            }
        });

        frame.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                // Maximize window
                if (initialized && (e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                    setFullScreen();
                }
            }
        });
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public GraphicsDevice getGfxCard() {
        return gfxCard;
    }

    public Rectangle getWindowBounds() {
        if (isFullScreen()) {
            return new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        } else {
            Rectangle innerFrame = frame.getContentPane().getBounds();
            innerFrame.setLocation(frame.getX(), frame.getY() + frame.getInsets().top);
            return innerFrame;
        }
    }

    public GraphicsState getGraphicsState() {
        return (GraphicsState) Handler.get().getGame().graphicsState;
    }
}
