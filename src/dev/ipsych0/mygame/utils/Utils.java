package dev.ipsych0.mygame.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	
	/**
	 * Loads the contents of a file as a String
	 * @param path - location of the file
	 * @return - Full length String of the file's contents
	 */
	public static String loadFileAsString(String path){
		StringBuilder builder = new StringBuilder();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null)
				builder.append(line + "\n");
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	/**
	 * Parses Strings to Integers for reading Tiles
	 * @param number - String parameter to parse
	 * @return - Returns a number if it's valid, otherwise return a default black tile.
	 */
	public static int parseInt(String number){
		try{
			return Integer.parseInt(number);
		}catch(NumberFormatException e){
			e.printStackTrace();
			System.out.println("Couldn't load tile with ID: " + number);
			return 28;
		}
	}

}
