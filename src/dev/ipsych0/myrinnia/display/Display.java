package dev.ipsych0.myrinnia.display;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.audio.AudioManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
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
    private GraphicsDevice defaultScreen;
    private Rectangle effectiveScreenArea;

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
        GraphicsConfiguration gc = env.getDefaultScreenDevice().getDefaultConfiguration();
        Rectangle preferredWindowedBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        defaultScreen = env.getDefaultScreenDevice();
        fullScreenSupported = defaultScreen.isFullScreenSupported();

        windowedX = 0;
        windowedY = 0;
        windowedWidth = (int)preferredWindowedBounds.getWidth();
        windowedHeight = (int)preferredWindowedBounds.getHeight();

        if(fullScreenSupported){
            frame.setResizable(false);
            frame.setUndecorated(true);
            defaultScreen.setFullScreenWindow(frame);
            fullScreen = true;
        } else{
            frame.setUndecorated(false);
            frame.setResizable(false);
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
                    Handler.get().getGame().setWidth(windowedWidth);
                    Handler.get().getGame().setHeight(windowedHeight);
                }
            }
        });

        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.setBackground(Color.BLACK);

        frame.add(canvas);

    }

    public void toggleFullScreen() {
        if (fullScreenSupported) {
            if (!fullScreen) {
                // Switch to fullscreen mode
                Rectangle preferredWindowedBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                frame.setVisible(false);
                frame.dispose();
                frame.setBounds(windowedX, windowedY, preferredWindowedBounds.width, preferredWindowedBounds.height);
                frame.setUndecorated(true);
                defaultScreen.setFullScreenWindow(frame);
                frame.setVisible(true);
            } else {
                // Switch to windowed mode
                frame.dispose();
                frame.setVisible(false);
                defaultScreen.setFullScreenWindow(null);
                frame.setUndecorated(false);
                frame.setVisible(true);

                if(effectiveScreenArea == null){
                    effectiveScreenArea = initScreenArea();
                }

//                canvas.setBounds(effectiveScreenArea);
//                canvas.setLocation(effectiveScreenArea.x, effectiveScreenArea.y);
//                Rectangle preferredWindowedBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

                frame.setBounds(0, 0, effectiveScreenArea.width, effectiveScreenArea.height);
                canvas.setBounds(0, 0, effectiveScreenArea.width, effectiveScreenArea.height);
//                frame.pack();

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
