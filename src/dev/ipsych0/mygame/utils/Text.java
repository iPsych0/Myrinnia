package dev.ipsych0.mygame.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
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
		g.setColor(color);
		g.setFont(font);
		int x = xPos;
		int y = yPos;
		
		if(center) {
			FontMetrics fm = g.getFontMetrics(font);
			x = xPos - fm.stringWidth(text) / 2;
			y = (yPos - fm.getHeight() / 2) + fm.getAscent();
		}
		
		g.drawString(text, x, y);
	}
	
	/**
	 * Wraps a String around a given max characterwidth
	 * @param input - input the String to be split
	 * @param maxCharInLine - input the max number of characters per line
	 * @return - String[] of split lines
	 */
	public static String[] splitIntoLine(String input, int maxCharInLine){

	    StringTokenizer tok = new StringTokenizer(input, " ");
	    StringBuilder output = new StringBuilder(input.length());
	    int lineLen = 0;
	    while (tok.hasMoreTokens()) {
	        String word = tok.nextToken();

	        while(word.length() > maxCharInLine){
	            output.append(word.substring(0, maxCharInLine-lineLen) + "\n");
	            word = word.substring(maxCharInLine-lineLen);
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
