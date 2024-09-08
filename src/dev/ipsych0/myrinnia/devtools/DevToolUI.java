package dev.ipsych0.myrinnia.devtools;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.ui.TextBox;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class DevToolUI implements Serializable {

    private static final long serialVersionUID = 518181399399230861L;
    private static int x, y, width, height;
    public static boolean isOpen;
    private transient TextBox textBox;
    private CommandHandler commandHandler;
    public static boolean escapePressed;

    public DevToolUI() {
        x = Handler.get().getChatWindow().getX();
        y = Handler.get().getChatWindow().getY() - 64;
        width = Handler.get().getChatWindow().getWidth();
        height = 32;

        commandHandler = new CommandHandler();

        textBox = new TextBox(x, y, width, height, false);
    }

    public void tick() {
        if (isOpen) {

            if (!textBox.isOpen()) {
                textBox.open();
            }

            if (Handler.get().getKeyManager().escape && escapePressed) {
                escapePressed = false;
                close();
                return;
            }

            textBox.tick();

            if (TextBox.enterPressed) {

                if (!textBox.getCharactersTyped().isEmpty()) {
                    // Perform the typed command
                    performAction(textBox.getCharactersTyped());
                }

                // Reset the text box
                close();
            }
        }
    }

    private void close() {
        isOpen = false;
        // Reset the text box
        textBox.close();
    }

    public void render(Graphics2D g) {
        if (isOpen) {
            textBox.render(g);
        }
    }

    /**
     * Performs the mod command
     *
     * @param command - the command written in the command line in-game
     */
    private void performAction(String command) {
        if (command.trim().isEmpty()) {
            return;
        }
        String[] commands = command.split(" ");
        Commands firstCommand = null;
        try {
            firstCommand = Commands.valueOf(commands[0].toUpperCase());
        } catch (Exception e) {
            Handler.get().sendMsg("'" + commands[0] + "' is not a command.");
            return;
        }
        try {
            commandHandler.handle(firstCommand, commands);
        } catch (Exception e) {
            e.getStackTrace();
            Handler.get().sendMsg("Something went wrong submitting this command.");
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        textBox = new TextBox(x, y, width, height, false);
    }
}
