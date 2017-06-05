package dev.ipsych0.mygame.mapeditor;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	
	public String groundTileParser(String path){
		String mapValues = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(path);
			doc.normalize();
			
			NodeList maps = doc.getElementsByTagName("*");
			Node groundMap = maps.item(maps.getLength() - 5);
			mapValues = groundMap.getTextContent();
			System.out.println(mapValues);
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
		return mapValues;
	}
	
	public String terrainTileParser(String path){
		String mapValues = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(path);
			doc.normalize();
			
			NodeList maps = doc.getElementsByTagName("*");
			Node terrainMap = maps.item(maps.getLength() - 3);
			mapValues = terrainMap.getTextContent();
			System.out.println(mapValues);
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
		return mapValues;
	}
	
	public String ambianceTileParser(String path){
		String mapValues = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(path);
			doc.normalize();
			
			NodeList maps = doc.getElementsByTagName("*");
			Node terrainMap = maps.item(maps.getLength() - 1);
			mapValues = terrainMap.getTextContent();
			System.out.println(mapValues);
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
		return mapValues;
	}
}
