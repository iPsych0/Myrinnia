package dev.ipsych0.myrinnia.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Utils {

	private static Gson gson;
	
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

	public static Gson getGson(){
		if(gson == null){
			gson = new GsonBuilder().setPrettyPrinting().create();
		}
		return gson;
	}

	public static <T> T fromJson(String path, final Class<T> clazz){
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File '" + path + "' not found.");
			System.exit(1);
		}
		try {
			final Gson gson = getGson();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			return gson.fromJson(reader, clazz);
		} catch (final Exception e) {
			e.printStackTrace();
			System.err.println("Json file could not be loaded.");
			System.exit(1);
		}
		return null;
	}

}
