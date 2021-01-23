package dev.ipsych0.splashscreen;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.FileUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class SplashScreen {

    private JFrame frame;
    private static JProgressBar progressBar;
    private static JLabel loadingMsg;
    private static JLabel animLabel;
    private static boolean initialized;
    private static final int WIDTH = 600, HEIGHT = 400;

    // Estimation, because the actual number of operations may differ.
    // See: loadedCount for a rough estimation of the total
    private static final double APPROX_MAX_COUNT = 34852d;
    private static double loadedCount;

    private Timer timer;
    private static int index;
    private static BufferedImage[] images;

    public SplashScreen() {
        createGUI();
        addHeader();
        addProgressBar();
        addMessage();
        addAnimation();
        loadGame();
    }

    private void createGUI() {
        frame = new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);
        java.util.List<Image> icons = new ArrayList<>();
        icons.add(new ImageIcon(FileUtils.getResourcePath("/settings/myrinnia.png")).getImage());
        icons.add(new ImageIcon(FileUtils.getResourcePath("/settings/myrinnia.png")).getImage());
        frame.setIconImages(icons);
        frame.setVisible(true);
    }

    private void addHeader() {
        JLabel header = new JLabel();
        header.setText("Launching Elements of Myrinnia...");
        header.setBounds(148, 40, 400, 40);
        header.setForeground(Color.YELLOW);//Setting foreground Color
        header.setFont(new Font("arial", Font.BOLD, 20));//Setting font properties
        frame.add(header);//adding label to the frame
    }

    private void addProgressBar() {
        progressBar = new JProgressBar();
        progressBar.setBounds(100, 280, 400, 30);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.BLACK);
        progressBar.setForeground(Color.YELLOW);
        progressBar.setValue(0);
        frame.add(progressBar, BorderLayout.CENTER);
    }

    private void addMessage() {
        loadingMsg = new JLabel("<html><div style='text-align: center;'>" + "Loading fonts..." + "</div></html>", SwingConstants.CENTER);
        loadingMsg.setBounds(200, 320, 200, 40);//Setting the size and location of the label
        loadingMsg.setForeground(Color.YELLOW);//Setting foreground Color
        loadingMsg.setFont(new Font("arial", Font.BOLD, 14));//Setting font properties
        frame.add(loadingMsg, BorderLayout.CENTER);//adding label to the frame
    }

    private void addAnimation() {
        animLabel = new JLabel(new ImageIcon(images[index]));
        animLabel.setBounds(200, 88, 200, 160);
        animLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(animLabel, BorderLayout.CENTER);

        SwingUtilities.invokeLater(() -> {
            ActionListener animate = e -> {
                if (progressBar.getValue() < 100) {
                    updateFrames();
                } else {
                    timer.stop();
                }
            };

            timer = new Timer(125, animate);
            timer.start();
        });
    }

    private void updateFrames() {
        index++;
        if (index >= images.length) {
            index = 0;
        }
        animLabel.setIcon(new ImageIcon(images[index]));
    }

    private void loadGame() {
        initialized = true;

        long before = System.currentTimeMillis();
        Assets.init();
        long now = (System.currentTimeMillis() - before);

        System.out.println("Total startup time: " + ((double) now / 1000d));

        // Finish the splash screen
        setProgress(100);
        setMessage("Done!");

        // Close the splash screen and start the game!
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public static void addLoadedElement() {
        if (initialized) {
            loadedCount++;
            // Update the progress bar
            setProgress((int) (loadedCount / APPROX_MAX_COUNT * 100d));
        }
    }

    public static void setProgress(int percent) {
        progressBar.setValue(percent);
    }

    public static void setMessage(String msg) {
        loadingMsg.setText("<html><div style='text-align: center;'>" + msg + "</div></html>");
        loadingMsg.setHorizontalAlignment(SwingConstants.CENTER);
    }


    /*
     * Load the images at the start
     */
    static {
        BufferedImage animSheet = null;
        String fixedFile;
        InputStream input;
        String path = "/textures/animations/ability_animations.png";

        try {
            fixedFile = FileUtils.getResourcePath(path);
            input = new FileInputStream(fixedFile);

            animSheet = ImageIO.read(input);
            animSheet.setAccelerationPriority(1);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        images = new BufferedImage[]{
                animSheet.getSubimage(0 * Assets.WIDTH, 19 * Assets.HEIGHT, Assets.WIDTH * 2, Assets.HEIGHT * 2),
                animSheet.getSubimage(2 * Assets.WIDTH, 19 * Assets.HEIGHT, Assets.WIDTH * 2, Assets.HEIGHT * 2),
                animSheet.getSubimage(4 * Assets.WIDTH, 19 * Assets.HEIGHT, Assets.WIDTH * 2, Assets.HEIGHT * 2),
                animSheet.getSubimage(6 * Assets.WIDTH, 19 * Assets.HEIGHT, Assets.WIDTH * 2, Assets.HEIGHT * 2),
                animSheet.getSubimage(8 * Assets.WIDTH, 19 * Assets.HEIGHT, Assets.WIDTH * 2, Assets.HEIGHT * 2),
                animSheet.getSubimage(10 * Assets.WIDTH, 19 * Assets.HEIGHT, Assets.WIDTH * 2, Assets.HEIGHT * 2),
                animSheet.getSubimage(12 * Assets.WIDTH, 19 * Assets.HEIGHT, Assets.WIDTH * 2, Assets.HEIGHT * 2),
                animSheet.getSubimage(14 * Assets.WIDTH, 19 * Assets.HEIGHT, Assets.WIDTH * 2, Assets.HEIGHT * 2)
        };
    }
}
