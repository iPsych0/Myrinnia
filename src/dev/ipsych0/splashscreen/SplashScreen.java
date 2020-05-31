package dev.ipsych0.splashscreen;

import dev.ipsych0.myrinnia.gfx.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class SplashScreen {

    private JFrame frame;
    private static JProgressBar progressBar;
    private static JLabel loadingMsg;
    private static double loadedCount;
    private static final double APPROX_MAX_COUNT = 67149d;

    public SplashScreen() {
        createGUI();
        addProgressBar();
        addMessage();
        loadGame();
    }

    private void createGUI() {
        frame = new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.CYAN);
        frame.setVisible(true);
    }

    private void addProgressBar() {
        progressBar = new JProgressBar();
        progressBar.setBounds(100, 280, 400, 30);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(Color.BLACK);
        progressBar.setValue(0);
        frame.add(progressBar);
    }

    private void addMessage() {
        loadingMsg = new JLabel();
        loadingMsg.setBounds(220, 320, 200, 40);//Setting the size and location of the label
        loadingMsg.setForeground(Color.black);//Setting foreground Color
        loadingMsg.setFont(new Font("arial", Font.BOLD, 15));//Setting font properties
        frame.add(loadingMsg);//adding label to the frame
    }

    private void loadGame() {
        Assets.init();

        // Finish the splash screen
        setProgress(100);
        setMessage("Done!");

        // Close the splash screen and start the game!
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public static void addLoadedElement() {
        loadedCount++;
        // Update the progress bar
        setProgress((int) (loadedCount / APPROX_MAX_COUNT * 100d));
    }

    public static void setProgress(int percent) {
        progressBar.setValue(percent);
    }

    public static void setMessage(String msg) {
        loadingMsg.setText(msg);
    }
}
