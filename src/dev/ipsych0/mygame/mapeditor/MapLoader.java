package dev.ipsych0.mygame.mapeditor;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dev.ipsych0.mygame.tiles.Tiles;

public class MapLoader {
	
	public MapLoader(){
		
	}
	
	/*
	 * Returns the width of the map from Tiled
	 * @params: String path in OS
	 */
	public int getMapWidth(String path){
		int mapWidth = 0;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			// Creates new DocumentBuilder on the file
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(path);
			doc.normalize();
			
			// Gets the tag 'layer' 
			NodeList tags = doc.getElementsByTagName("layer");
			Node layer = tags.item(tags.getLength() - 1);
			// Gets the width
			mapWidth = Integer.parseInt(layer.getAttributes().getNamedItem("width").getTextContent());
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
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
	public int getMapHeight(String path){
		int mapHeight = 0;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			// Creates new DocumentBuilder on the file
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(path);
			doc.normalize();
			
			// Gets the tag 'layer' 
			NodeList tags = doc.getElementsByTagName("layer");
			Node layer = tags.item(tags.getLength() - 1);
			// Gets the height
			mapHeight = Integer.parseInt(layer.getAttributes().getNamedItem("height").getTextContent());
			
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
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
	public String[] groundTileParser(String path){
		// Creates a DocumentBuilder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(path);
			doc.normalize();
			
			// Get all tags
			NodeList maps = doc.getElementsByTagName("layer");
			for(int i = 0; i < maps.getLength(); i++) {
				System.out.println(maps.item(i).getNodeName());
			}
			System.out.println(" ");

			// Calculate number of layers
			int testLayer = 0;
			while(testLayer < maps.getLength()) {
				testLayer++;
			}
			
			// Index the String[] at the size of the number of layers
			String[] mapValues = new String[testLayer];
			
			// Set variables to iterate over the maps
			int layer = 0;
			
			// Fill the layers in the String[] (The entire String with all Tile IDs per layer)
			while(layer < maps.getLength()) {
				Node groundMap = maps.item(layer);
				mapValues[layer] = groundMap.getTextContent();
				layer++;
			}
			
			return mapValues;
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
