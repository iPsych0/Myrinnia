package dev.ipsych0.myrinnia.devtools;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.input.KeyManager;
import dev.ipsych0.myrinnia.ui.TextBox;

import java.awt.*;

public class DevToolUI {

    private static int x, y, width, height;
    public static boolean isOpen = false;
    private static TextBox textBox;
    private CommandHandler commandHandler;
    private boolean initialized = false;

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
            if(!initialized) {
                textBox.setKeyListeners();
                initialized = true;
            }
            textBox.tick();
            if (TextBox.enterPressed) {

                // Perform the typed command
                performAction(textBox.getCharactersTyped());

                // Reset the text box
                TextBox.enterPressed = false;
                Handler.get().getKeyManager().setTextBoxTyping(false);
                KeyManager.typingFocus = false;
                textBox.getSb().setLength(0);
                textBox.setIndex(0);
                textBox.setCharactersTyped(textBox.getSb().toString());
                TextBox.isOpen = false;
                DevToolUI.isOpen = false;
                initialized = false;
                textBox.removeListeners();
            }
        }
    }

    public void render(Graphics g) {
        if (isOpen) {
            textBox.render(g);
        }
    }

    /**
     * Performs the mod command
     * @param command - the command written in the command line in-game
     */
    public void performAction(String command){
        String[] commands = command.split(" ");
        Commands firstCommand = null;
        try{
            firstCommand = Enum.valueOf(Commands.class, commands[0].toUpperCase());
        }catch (Exception e){
            Handler.get().sendMsg("'"+commands[0] + "' is not a command.");
            return;
        }
        commandHandler.handle(commands, firstCommand);
    }
}
