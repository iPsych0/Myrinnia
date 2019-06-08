package dev.ipsych0.myrinnia.chatwindow;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class ChatDialogue implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -8130149340424276218L;
    private int x, y, width, height;
    private ArrayList<ChatOption> chatOptions;
    public static boolean hasBeenPressed = false;
    private String[] menuOptions;
    private ChatOption chosenOption;
    private Rectangle bounds;
    private UIManager uiManager;

    public ChatDialogue(String[] menuOptions) {
        this.width = Handler.get().getChatWindow().getWidth();
        this.height = Handler.get().getChatWindow().getHeight();
        this.x = Handler.get().getWidth() - width - 8;
        this.y = Handler.get().getHeight() - height - 16;
        this.menuOptions = menuOptions;

        chatOptions = new ArrayList<>();
        uiManager = new UIManager();

        for (int i = 0; i < menuOptions.length; i++) {
            if (menuOptions.length == 1) {
                chatOptions.add(new ChatOption(x + (width / 2) - 50, y + 12 + (20 * 4), menuOptions[i]));
            } else {
                chatOptions.add(new ChatOption(x + 16, y + 11 + (20 * i), i, menuOptions[i]));
            }
            uiManager.addObject(chatOptions.get(i));
        }

        bounds = new Rectangle(x, y, width, height);
    }

    public void tick() {
        Rectangle mouse = Handler.get().getMouse();

        uiManager.tick();

        for (ChatOption option : chatOptions) {

            option.tick();

            Rectangle optionSlot = option.getBounds();

            if (optionSlot.contains(mouse)) {
                option.setHovering(true);
                if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed && !Handler.get().getMouseManager().isDragged()) {
                    hasBeenPressed = false;
                    chosenOption = option;
                    Player.hasInteracted = false;
                    MouseManager.justClosedUI = true;
                }
            } else {
                option.setHovering(false);
            }
        }

    }

    public void render(Graphics2D g) {
        Stroke originalStroke = g.getStroke();
        g.drawImage(Assets.uiWindow, x, y - 19, width, height + 8 + 20, null);
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.BLACK);
        g.drawLine(x + 1, y + 1, x + width - 2, y + 1);
        g.setStroke(originalStroke);

        uiManager.render(g);

        if (menuOptions.length == 1) {
            for (int i = 0; i < Text.splitIntoLine(menuOptions[0], 56).length; i++) {
                Text.drawString(g, Text.splitIntoLine(menuOptions[0], 56)[i], x + (width / 2), y + 20 + (i * 16), true, Color.YELLOW, Assets.font14);
            }
        }

        Text.drawString(g, Handler.get().getPlayer().getClosestEntity().getName(), x + (width / 2), y - 9, true, Color.YELLOW, Assets.font14);
    }

    public ArrayList<ChatOption> getChatOptions() {
        return chatOptions;
    }

    public void setChatOptions(ArrayList<ChatOption> chatOptions) {
        this.chatOptions = chatOptions;
    }

    public ChatOption getChosenOption() {
        return chosenOption;
    }

    public void setChosenOption(ChatOption chosenOption) {
        this.chosenOption = chosenOption;
    }

    public String[] getMenuOptions() {
        return menuOptions;
    }

    public void setMenuOptions(String[] menuOptions) {
        this.menuOptions = menuOptions;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

}
