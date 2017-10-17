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
	
	public int getMapWidth(String path){
		int mapWidth = 0;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(path);
			doc.normalize();
			
			NodeList tags = doc.getElementsByTagName("layer");
			Node layer = tags.item(tags.getLength() - 1);
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
	
	public int getMapHeight(String path){
		int mapHeight = 0;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(path);
			doc.normalize();
			
			NodeList tags = doc.getElementsByTagName("layer");
			Node layer = tags.item(tags.getLength() - 1);
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
	
	public String[] groundTileParser(String path){
		int minItem = 9;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(path);
			doc.normalize();
			
			NodeList maps = doc.getElementsByTagName("*");

			int testLayer = 0;
			while(minItem < maps.getLength()) {
				minItem += 2;
				testLayer++;
			}
			
			String[] mapValues = new String[testLayer];
			
			minItem = 9;
			int i = 0;
			int layer = 0;
			while(minItem + i < maps.getLength()) {
				Node groundMap = maps.item(minItem + i);
				mapValues[layer] = groundMap.getTextContent();
				i += 2;
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
