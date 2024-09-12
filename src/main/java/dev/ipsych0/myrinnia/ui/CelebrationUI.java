package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.ui.abilityhud.AbilityTooltip;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.items.ui.ItemTooltip;
import dev.ipsych0.myrinnia.ui.windows.InputHandler;
import dev.ipsych0.myrinnia.ui.windows.KeyInput;
import dev.ipsych0.myrinnia.ui.windows.MouseInput;
import dev.ipsych0.myrinnia.ui.windows.Window;
import dev.ipsych0.myrinnia.utils.Text;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.LinkedList;

@Getter
@Setter
public class CelebrationUI implements Window, KeyInput, MouseInput, Serializable {

    private static final long serialVersionUID = -8053693452272349138L;
    private int x, y;
    private int width, height;
    private LinkedList<Celebration> events;
    private UIImageButton nextButton;
    private UIImageButton closeAllButton;
    private UIManager uiManager;
    private Rectangle bounds;
    private AbilityTooltip abilityTooltip;
    private ItemTooltip itemTooltip;
    private final InputHandler inputHandler;

    public CelebrationUI() {
        width = 384;
        height = 256;
        x = Handler.get().getWidth() / 2 - width / 2;
        y = Handler.get().getHeight() / 2 - height / 2;

        abilityTooltip = new AbilityTooltip(x - AbilityTooltip.BASE_WIDTH, y);
        itemTooltip = new ItemTooltip(x - 160, y);

        events = new LinkedList<>();

        uiManager = new UIManager();
        nextButton = new UIImageButton(x + width - ItemSlot.SLOTSIZE * 3, y + height - 48, ItemSlot.SLOTSIZE * 2, ItemSlot.SLOTSIZE, Assets.genericButton);
        closeAllButton = new UIImageButton(x + width / 2 - ItemSlot.SLOTSIZE, y + height - 48, ItemSlot.SLOTSIZE * 2, ItemSlot.SLOTSIZE, Assets.genericButton);

        uiManager.addObject(nextButton);

        bounds = new Rectangle(x, y, width, height);
        inputHandler = new InputHandler(this);
    }

    public void addEvent(Celebration celebration) {
        events.addLast(celebration);
        if (!isOpen()) {
            Handler.get().getWindowManager().pushWindow(this);
        }
    }

    public void tick() {
        if (!isOpen()) {
            return;
        }
        if (events.isEmpty()) {
            Handler.get().getWindowManager().popWindow();
            return;
        }

        uiManager.tick();
    }

    public void render(Graphics2D g) {
        if (!isOpen()) {
            return;
        }
        if (!events.isEmpty()) {
            g.drawImage(Assets.uiWindow, x, y, width, height, null);
            uiManager.render(g);
            if (events.size() == 1) {
                Text.drawString(g, "Close", nextButton.x + nextButton.width / 2, nextButton.y + nextButton.height / 2, true, Color.YELLOW, Assets.font14);
            } else {
                Text.drawString(g, "Next", nextButton.x + nextButton.width / 2, nextButton.y + nextButton.height / 2, true, Color.YELLOW, Assets.font14);
            }

            if (events.size() > 1) {
                closeAllButton.tick();
                closeAllButton.render(g);
                Text.drawString(g, "Close all", closeAllButton.x + closeAllButton.width / 2, closeAllButton.y + closeAllButton.height / 2, true, Color.YELLOW, Assets.font14);
            }

            Text.drawString(g, "Congratulations!", x + width / 2, y + 32, true, Color.YELLOW, Assets.font24);

            Celebration currentEvent = events.getFirst();

            if (currentEvent.getAbility() != null) {
                currentEvent.getAbility().renderIcon(g, x + width / 2 - 16, y + 64);
                if (new Rectangle(x + width / 2 - 16, y + 64, 32, 32).contains(Handler.get().getMouse())) {
                    abilityTooltip.render(g, currentEvent.getAbility());
                }
            } else if (currentEvent.getRecipe() != null) {
                g.drawImage(currentEvent.getImg(), x + width / 2 - 16, y + 64, null);
                if (new Rectangle(x + width / 2 - 16, y + 64, 32, 32).contains(Handler.get().getMouse())) {
                    itemTooltip.render(currentEvent.getRecipe().getResult().getItem(), g);
                }
            } else {
                g.drawImage(currentEvent.getImg(), x + width / 2 - 16, y + 64, null);
            }

            String[] text = Text.splitIntoLine(currentEvent.getDescription(), 38);
            for (int i = 0; i < text.length; i++) {
                Text.drawString(g, text[i], x + width / 2, y + 120 + (i * 22), true, Color.YELLOW, Assets.font20);
            }

        }
    }

    @Override
    public void open() {
        inputHandler.open();
    }

    @Override
    public void close() {
        inputHandler.close();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        inputHandler.mouseMoved(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!hasFocus()) {
            return;
        }

        // Press next/close
        if (nextButton.contains(getMouse())) {
            events.removeFirst();
            MouseManager.justClosedUI = true;
        }

        if (closeAllButton.contains(getMouse())) {
            events.clear();
            MouseManager.justClosedUI = true;
            Handler.get().getWindowManager().popWindow();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!hasFocus()) {
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Handler.get().getWindowManager().popWindow();
        }
    }
}
