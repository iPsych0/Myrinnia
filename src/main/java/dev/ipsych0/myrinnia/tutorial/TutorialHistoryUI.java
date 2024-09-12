package dev.ipsych0.myrinnia.tutorial;


import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.ui.windows.KeyInput;
import dev.ipsych0.myrinnia.ui.windows.MouseInput;
import dev.ipsych0.myrinnia.ui.windows.Window;
import dev.ipsych0.myrinnia.ui.windows.InputHandler;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.Text;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

@Slf4j
public class TutorialHistoryUI implements Window, KeyInput, MouseInput, Serializable {
    private final Rectangle box;
    private final UIManager uiManager;
    private final UIImageButton exitButton;
    @Getter
    private final InputHandler inputHandler;

    public TutorialHistoryUI() {
        int width = 460;
        int height = 460;
        int x = Handler.get().getWidth() / 2 - width / 2;
        int y = Handler.get().getHeight() / 2 - height / 2;
        box = new Rectangle(x, y, width, height);
        exitButton = new UIImageButton(x + width - 40, y + 8, 32, 32, Assets.genericButton);
        uiManager = new UIManager();
        uiManager.addObject(exitButton);
        inputHandler = new InputHandler(this);
    }

    @Override
    public void tick() {
        if (!inputHandler.isOpen()) {
            return;
        }

        uiManager.tick();
    }

    @Override
    public void render(Graphics2D g) {
        if (!inputHandler.isOpen()) {
            return;
        }

        g.drawImage(Assets.uiWindow, box.x, box.y, box.width, box.height, null);
        Text.drawString(g, "All tutorial tips", box.x + box.width / 2, box.y + 20, true, Color.YELLOW, Assets.font20);

        uiManager.render(g);

        List<String> messages = new ArrayList<>();
        List<Rectangle> msgBounds = new ArrayList<>();
        List<TutorialTip> history = Handler.get().getTutorialTipManager().getHistory();
        ListIterator<TutorialTip> iterator = history.listIterator(history.size());
        while (iterator.hasPrevious()) {
            List<String> split = Stream.of(Text.splitIntoLine(iterator.previous().getTip(), 40)).toList().reversed();
            split.forEach(s -> msgBounds.add(new Rectangle(box.x + 16, box.y + 16 + 12, box.width - 32, 24)));
            messages.addAll(split);
        }
        for (int i = msgBounds.size() - 1; i >= 0; i--) {
            Rectangle rect = msgBounds.get(i);
            g.setColor(Colors.selectedColor);
            g.fillRoundRect(rect.x, rect.y + (messages.size() - i) * rect.height, rect.width, rect.height, 4, 4);
            Text.drawString(g, messages.get(i), box.x + box.width / 2, box.y + 16 + 24 + (messages.size() - i) * 24, true, Color.YELLOW, Assets.font20);
        }
        for (int i = messages.size() - 1; i >= 0; i--) {
            Text.drawString(g, messages.get(i), box.x + box.width / 2, box.y + 16 + 24 + (messages.size() - i) * 24, true, Color.YELLOW, Assets.font20);
        }

        Text.drawString(g, "X", exitButton.x + exitButton.width / 2, exitButton.y + exitButton.height / 2, true, Color.YELLOW, Assets.font14);
    }

    @Override
    public void open() {
        this.inputHandler.open();
    }

    @Override
    public void close() {
        this.inputHandler.close();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.inputHandler.keyPressed(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!hasFocus()) {
            return;
        }
        if (exitButton.contains(getMouse()) && e.getButton() == MouseEvent.BUTTON1) {
            Handler.get().getWindowManager().popWindow();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        inputHandler.mouseMoved(e);
    }
}
