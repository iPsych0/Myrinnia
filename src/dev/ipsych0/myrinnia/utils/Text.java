package dev.ipsych0.myrinnia.utils;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.StringTokenizer;

public class Text {

    /*
     * Private constructor to prevent accessing Text util class in non-static way
     */
    private Text() {

    }

    /*
     * Draws a String to the screen with specified font, colour and centred/not centred
     */
    public static void drawString(Graphics g, String text, int xPos, int yPos, boolean center, Color color, Font font) {
        int x = xPos;
        int y = yPos;

        if (center) {
            FontMetrics fm = g.getFontMetrics(font);
            x = xPos - fm.stringWidth(text) / 2;
            y = (yPos - fm.getHeight() / 2) + fm.getAscent();
        }

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(text, x + 1, y + 1);
        g.setColor(color);
        g.drawString(text, x, y);
    }

    /*
     * Draws a String to the screen with specified font, colour and centred/not centred
     */
    public static void drawStringStrikeThru(Graphics g, String text, int xPos, int yPos, boolean center, Color color, Font font) {
        int x = xPos;
        int y = yPos;

        if (center) {
            FontMetrics fm = g.getFontMetrics(font);
            x = xPos - fm.stringWidth(text) / 2;
            y = (yPos - fm.getHeight() / 2) + fm.getAscent();
        }

        AttributedString as = new AttributedString(text);

        as.addAttribute(TextAttribute.FONT, font);
        as.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(as.getIterator(), x + 1, y + 1);
        g.setColor(color);
        g.drawString(as.getIterator(), x, y);
    }

    public static int getStringWidth(Graphics g, String text, Font font) {
        FontMetrics fm = g.getFontMetrics(font);
        return fm.stringWidth(text);
    }

    /**
     * Wraps a String around a given max characterwidth
     *
     * @param input         - input the String to be split
     * @param maxCharInLine - input the max number of characters per line
     * @return - String[] of split lines
     */
    public static String[] splitIntoLine(String input, int maxCharInLine) {

        StringTokenizer tok = new StringTokenizer(input, " ");
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            while (word.length() > maxCharInLine) {
                output.append(word.substring(0, maxCharInLine - lineLen) + "\n");
                word = word.substring(maxCharInLine - lineLen);
                lineLen = 0;
            }

            if (lineLen + word.length() > maxCharInLine) {
                output.append("\n");
                lineLen = 0;
            }
            output.append(word + " ");

            lineLen += word.length() + 1;
        }
        // output.split();
        // return output.toString();
        return output.toString().split("\n");
    }

}
