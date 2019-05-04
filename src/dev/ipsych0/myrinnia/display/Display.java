package dev.ipsych0.myrinnia.display;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.audio.AudioManager;

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
    private boolean fullScreen;
    private boolean fullScreenSupported;
    private GraphicsDevice defaultScreen;
    private Rectangle effectiveScreenArea;

    private String title;
    private int width, height;
    public static double scaleX, scaleY;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();

    }

    private void createDisplay() {
        frame = new JFrame(title);
        frame.setSize(width, height);

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle preferredWindowedBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        defaultScreen = env.getDefaultScreenDevice();
        fullScreenSupported = defaultScreen.isFullScreenSupported();

        windowedX = 0;
        windowedY = 0;
        windowedWidth = width;
        windowedHeight = height;

//        if (fullScreenSupported) {
//            frame.setResizable(false);
//            frame.setUndecorated(true);
//            defaultScreen.setFullScreenWindow(frame);
//            fullScreen = true;
            scaleX = 1.0;
            scaleY = 1.0;
//        } else {
//            frame.setUndecorated(false);
//            frame.setResizable(false);
//            defaultScreen.setFullScreenWindow(null); // windowed mode
//            frame.setSize(new Dimension(windowedWidth, windowedHeight));
//            fullScreen = false;
//            scaleX = 1.0;
//            scaleY = 1.0;
//        }
        fullScreen = false;
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
                    scaleX = (double)frame.getWidth() / (double)width;
                    scaleY = (double)frame.getHeight() / (double)height;
                }
            }
        });

        frame.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                // minimized
                if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED) {
                    System.out.println("min");
                }
                // maximized
                else if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                    toggleFullScreen();
                }
            }
        });

        // Window will appear in the center of the user's screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);

        frame.pack();
        frame.setResizable(true);

        //

//        frame.setVisible(true);
//
//        canvas = new Canvas();
//        canvas.setMaximumSize(new Dimension(width, height));
//        canvas.setPreferredSize(new Dimension(width, height));
//        canvas.setSize(new Dimension(width, height));
//        canvas.setFocusable(false);
//        canvas.setBackground(Color.BLACK);
//
//        System.out.println("---_- Original Size -_---");
//        System.out.println(frame.getWidth());
//        System.out.println(frame.getHeight());
//        System.out.println(canvas.getWidth());
//        System.out.println(canvas.getHeight());
//        System.out.println("_--- Resized Size ---_");
//
        initScreenArea();
//        frame.add(canvas);

    }

    public void toggleFullScreen() {
        if (fullScreenSupported) {
            if (!fullScreen) {
                // Switch to fullscreen mode
                Rectangle preferredWindowedBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                frame.setVisible(false);
                frame.dispose();
//                frame.setSize(preferredWindowedBounds.width, preferredWindowedBounds.height);
                frame.setBounds(windowedX, windowedY, preferredWindowedBounds.width, preferredWindowedBounds.height);
                frame.setUndecorated(true);
                defaultScreen.setFullScreenWindow(frame);
                frame.setVisible(true);
                windowedWidth = frame.getWidth();
                windowedHeight = frame.getHeight();
                scaleX = (double)frame.getWidth() / (double)width;
                scaleY = (double)frame.getHeight() / (double)height;
            } else {
                // Switch to windowed mode
                frame.dispose();
                frame.setVisible(false);
                defaultScreen.setFullScreenWindow(null);
                frame.setUndecorated(false);
                frame.setVisible(true);
                frame.setSize(effectiveScreenArea.width, effectiveScreenArea.height);
                frame.setLocationRelativeTo(null);
                frame.setResizable(true);
                scaleX = (double)frame.getWidth() / (double)width;
                scaleY = (double)frame.getHeight() / (double)height;
                System.out.println(frame.getWidth());
                System.out.println(frame.getHeight());
                System.out.println(canvas.getWidth());
                System.out.println(canvas.getHeight());
                canvas.setSize(new Dimension(frame.getWidth(), frame.getHeight()));
                System.out.println("--- New Canvas Size ---");
                System.out.println(canvas.getWidth());
                System.out.println(canvas.getHeight());
                Handler.get().getGame().setWidth(frame.getWidth());
                Handler.get().getGame().setHeight(frame.getHeight());
            }
            fullScreen = !fullScreen;
        }
    }

    private Rectangle initScreenArea() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = env.getDefaultScreenDevice().getDefaultConfiguration();
        Rectangle bounds = gc.getBounds();

        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

        effectiveScreenArea = new Rectangle();

        effectiveScreenArea.x = bounds.x + screenInsets.left;
        effectiveScreenArea.y = bounds.y + screenInsets.top;
        effectiveScreenArea.height = bounds.height - screenInsets.top - screenInsets.bottom;
        effectiveScreenArea.width = bounds.width - screenInsets.left - screenInsets.right;

        return effectiveScreenArea;
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

    public GraphicsDevice getDefaultScreen() {
        return defaultScreen;
    }
}
