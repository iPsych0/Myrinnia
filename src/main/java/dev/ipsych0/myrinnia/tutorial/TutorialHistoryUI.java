package dev.ipsych0.myrinnia.tutorial;


import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

public class TutorialHistoryUI implements Serializable {
    private int x, y, width, height;
    private Rectangle bounds;
    private UIManager uiManager;
    private static final int PANEL_WIDTH = 400, PANEL_HEIGHT = 64;
    public static boolean isOpen;
    public static boolean escapePressed;
    private UIImageButton exitButton;

    public TutorialHistoryUI() {
        width = 460;
        height = 460;
        x = Handler.get().getWidth() / 2 - width / 2;
        y = Handler.get().getHeight() / 2 - height / 2;
        bounds = new Rectangle(x, y, width, height);
        exitButton = new UIImageButton(x + width - 40, y + 8, 32, 32, Assets.genericButton);
        uiManager = new UIManager();
        uiManager.addObject(exitButton);
    }

    public void tick() {
        uiManager.tick();

        Rectangle mouse = Handler.get().getMouse();

        // Closing the window
        if (Handler.get().getKeyManager().escape && escapePressed ||
                exitButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
            close();
        }
    }

    public void close() {
        if (Handler.get().getMouseManager().isLeftPressed()) {
            MouseManager.justClosedUI = true;
        }
        isOpen = false;
        escapePressed = false;
    }

    public void render(Graphics2D g) {
        g.drawImage(Assets.uiWindow, x, y, width, height, null);
        Text.drawString(g, "All tutorial tips", x + width / 2, y + 20, true, Color.YELLOW, Assets.font20);

        uiManager.render(g);

        List<String> messages = new ArrayList<>();
        List<Rectangle> msgBounds = new ArrayList<>();
        List<TutorialTip> history = Handler.get().getTutorialTipManager().getHistory();
        ListIterator<TutorialTip> iterator = history.listIterator(history.size());
        while (iterator.hasPrevious()) {
            List<String> split = Stream.of(Text.splitIntoLine(iterator.previous().getTip(), 40)).toList().reversed();
            split.forEach(s -> msgBounds.add(new Rectangle(x + 16, y + 16 + 12, width - 32, 24)));
            messages.addAll(split);
        }
        for (int i = msgBounds.size() - 1; i >= 0; i--) {
            Rectangle rect = msgBounds.get(i);
            g.setColor(Colors.selectedColor);
            g.fillRoundRect(rect.x, rect.y + (messages.size() - i) * rect.height, rect.width, rect.height, 4, 4);
            Text.drawString(g, messages.get(i), x + width / 2, y + 16 + 24 + (messages.size() - i) * 24, true, Color.YELLOW, Assets.font20);
        }
        for (int i = messages.size() - 1; i >= 0; i--) {
            Text.drawString(g, messages.get(i), x + width / 2, y + 16 + 24 + (messages.size() - i) * 24, true, Color.YELLOW, Assets.font20);
        }

        Text.drawString(g, "X", exitButton.x + exitButton.width / 2, exitButton.y + exitButton.height / 2, true, Color.YELLOW, Assets.font14);
    }
}
