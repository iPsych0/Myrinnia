package dev.ipsych0.mygame.utils;

import java.io.IOException;
import java.io.Serializable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MapLoader implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static DocumentBuilderFactory factory;
	private static DocumentBuilder builder;
	static {
		factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MapLoader(){
	}
	
	/*
	 * Returns the width of the map from Tiled
	 * @params: String path in OS
	 */
	public static int getMapWidth(String path){
		int mapWidth = 0;
		try {
			// Creates new DocumentBuilder on the file
			Document doc = builder.parse(path);
			doc.normalize();
			
			// Gets the tag 'layer' 
			NodeList tags = doc.getElementsByTagName("layer");
			Node layer = tags.item(tags.getLength() - 1);
			// Gets the width
			mapWidth = Integer.parseInt(layer.getAttributes().getNamedItem("width").getTextContent());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapWidth;
	}
	
	/*
	 * Returns the height of the map from Tiled
	 * @params: String path in OS
	 */
	public static int getMapHeight(String path){
		int mapHeight = 0;
		try {
			Document doc = builder.parse(path);
			doc.normalize();
			
			// Gets the tag 'layer' 
			NodeList tags = doc.getElementsByTagName("layer");
			Node layer = tags.item(tags.getLength() - 1);
			// Gets the height
			mapHeight = Integer.parseInt(layer.getAttributes().getNamedItem("height").getTextContent());

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapHeight;
	}
	
	/*
	 * Returns all the tile IDs from the map
	 * @param: path - input path from OS to read in the .tmx file
	 * @returns: String[] mapValues - all Tile IDs per layer
	 */
	public static String[] getMapTiles(String path){
		try {
			Document doc = builder.parse(path);
			doc.normalize();
			
			// Get all tags
			NodeList maps = doc.getElementsByTagName("layer");
			
			// Index the String[] at the size of the number of layers
			String[] mapValues = new String[maps.getLength()];
			
			// Set variables to iterate over the maps
			int layer = 0;
			
			// Fill the layers in the String[] (The entire String with all Tile IDs per layer)
			while(layer < mapValues.length) {
				Node groundMap = maps.item(layer);
				mapValues[layer] = groundMap.getTextContent();
				layer++;
			}
			
			return mapValues;

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static int[] getTiledFirstGid(String path) {
		try {
			Document doc = builder.parse(path);
			doc.normalize();

			// Get all tileset objects
			NodeList tilesets = doc.getElementsByTagName("tileset");
			
			int[] firstGids = new int[tilesets.getLength()];
			
			for(int i = 0; i < tilesets.getLength(); i++) {
				Node n = tilesets.item(i);
				Node inner = n.getAttributes().getNamedItem("firstgid");
				firstGids[i] = Integer.parseInt(inner.getNodeValue());
			}
			return firstGids;

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Couldn't load in the Tiled firstGIDs. MapLoader::getFirstGids");
		return null;
	}
	
	public static int getImageIndex(String worldPath, String imagePath) {
		String imageSource = null;
		
		try {
			Document doc = builder.parse(worldPath);
			doc.normalize();

			// Get all tileset objects
			NodeList tilesets = doc.getElementsByTagName("tileset");
			
			for(int i = 0; i < tilesets.getLength(); i++) {
				// Get the tileset property
				Node tilesetObject = tilesets.item(i);
				
				// Get the contents of a tileset
				NodeList tilesetContents = tilesetObject.getChildNodes();
				
				// Get index 1 (always the image property)
				Node imageObject = tilesetContents.item(1);
				
				// Get the source attribute
				Node sourceObject = imageObject.getAttributes().getNamedItem("source");
				
				// Get the source path and remove the first two dots
				imageSource = sourceObject.getNodeValue().substring(2);
				
				if(imageSource.equals(imagePath)) {
					return i;
				}
			}

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Couldn't find the index of the image path for world: "+worldPath+" - in: MapLoader::getImageIndex");
		System.out.println(imagePath + " - is not a tileset used in Tiled Map Editor.");
		return -1;
	}
	
	public static int getTileCount(String worldPath, int imageIndex) {
		int tileCount = -1;
		
		try {
			Document doc = builder.parse(worldPath);
			doc.normalize();

			// Get all tileset objects
			NodeList tilesets = doc.getElementsByTagName("tileset");
			
			Node n = tilesets.item(imageIndex);
			Node inner = n.getAttributes().getNamedItem("tilecount");
			tileCount = Integer.parseInt(inner.getNodeValue());
			return tileCount;

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Artifact 'tileCount' not found for path: " + worldPath + " in: MapLoader::getTileCount");
		return -1;
	}
	
	public static int getTileColumns(String worldPath, int imageIndex) {

		int columns = -1;
		
		try {
			Document doc = builder.parse(worldPath);
			doc.normalize();

			// Get all tileset objects
			NodeList tilesets = doc.getElementsByTagName("tileset");
			
			Node n = tilesets.item(imageIndex);
			Node inner = n.getAttributes().getNamedItem("columns");
			columns = Integer.parseInt(inner.getNodeValue());
			return columns;

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Artifact 'columns' not found for path: " + worldPath + " in: MapLoader::getTileColumns");
		return columns;
	}
}
