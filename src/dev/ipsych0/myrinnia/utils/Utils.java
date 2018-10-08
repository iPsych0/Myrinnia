package dev.ipsych0.myrinnia.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;

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

	public static <T> T fromJson(String path, final Class<?> clazz){
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File("src/dev/ipsych0/myrinnia/abilities/json/" + path.toLowerCase()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File '" + path.toLowerCase() + "' not found.");
			System.exit(1);
		}
		try {
			final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			return getGson().fromJson(reader, (Type) clazz);
		} catch (final Exception e) {
			e.printStackTrace();
			System.err.println("Json file could not be loaded.");
			System.exit(1);
		}
		return null;
	}

	public static Class<?> getClassFromString(String jsonFile){
        FileInputStream inputStream = null;
        String name = null;
        try {
            inputStream = new FileInputStream(new File("src/dev/ipsych0/myrinnia/abilities/json/" + jsonFile.toLowerCase()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File '" + jsonFile.toLowerCase() + "' not found.");
            System.exit(1);
        }
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null){
                if(line.contains("className")){
                    name = line.substring(16, line.length()-1);
                    break;
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
            System.err.println("Json file could not be loaded.");
            System.exit(1);
        }


	    Class<?> clazz = null;
        try {
            clazz = Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Class: " + name + ", is not an existing class.");
            System.exit(1);
        }
        return clazz;
    }

}
