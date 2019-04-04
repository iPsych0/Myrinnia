package dev.ipsych0.myrinnia.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class MapLoader implements Serializable {

    /**
     * Util class to parse TMX maps
     */
    private static final long serialVersionUID = 5948158902228537298L;
    private static SAXParser saxParser;
    private static DocumentBuilder builder;
    public static HashMap<Integer, Boolean> solidTiles = new HashMap<>();
    public static HashMap<Integer, Boolean> postRenderTiles = new HashMap<>();
    public static HashMap<Integer, String> polygonTiles = new HashMap<>();

    static {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
            saxParser = saxFactory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    private MapLoader() {}

    /*
     * Returns the width of the map from Tiled
     * @params: String path in OS
     */
    public static void setSolidTiles(String path) {
//        try {
//            // Creates new DocumentBuilder on the file
//            Document doc = builder.parse(path);
//            doc.normalize();
//
//            // Gets the tag 'tileset' and then the list of tiles
//            NodeList tilesets = doc.getElementsByTagName("tileset");
//
//            // Go over all TileSets
//            for (int i = 0; i < tilesets.getLength(); i++) {
//                Node tileset = tilesets.item(i);
//                NodeList inner = tileset.getChildNodes();
//                for (int j = 0; j < inner.getLength(); j++) {
//                    Node tile = inner.item(j);
//                    // Find every element called 'tile'
//                    if (tile.getNodeName().equalsIgnoreCase("tile")) {
//                        // Save the ID for later
//                        int id = 1 + Integer.parseInt(tile.getAttributes().getNamedItem("id").getTextContent());
//                        NodeList properties = tile.getChildNodes();
//                        if (properties.getLength() == 0) {
//                            solidTiles.put(id, false);
//                            postRenderTiles.put(id, false);
//                        }
//                        for (int k = 0; k < properties.getLength(); k++) {
//                            Node property = properties.item(k);
//                            // Check if the tile has properties
//                            if (property.getNodeName().equalsIgnoreCase("properties")) {
//                                NodeList innerProps = property.getChildNodes();
//                                for (int l = 0; l < innerProps.getLength(); l++) {
//                                    Node propItem = innerProps.item(l);
//                                    if(!propItem.hasAttributes()){
//                                        continue;
//                                    }
//                                    if (propItem.getAttributes().getNamedItem("name").getTextContent().equalsIgnoreCase("solid")) {
//                                        // Mark that tile as solid
//                                        solidTiles.put(id, Boolean.parseBoolean(propItem.getAttributes().getNamedItem("value").getTextContent()));
//                                    } else if (propItem.getAttributes().getNamedItem("name").getTextContent().equalsIgnoreCase("postRendered")) {
//                                        postRenderTiles.put(id, Boolean.parseBoolean(propItem.getAttributes().getNamedItem("value").getTextContent()));
//                                    }
//                                }
//                            }
//                            // Check if the tile has polygon collision
//                            else if (property.getNodeName().equalsIgnoreCase("objectgroup")) {
//                                NodeList objects = property.getChildNodes();
//                                for (int l = 0; l < objects.getLength(); l++) {
//                                    Node object = objects.item(l);
//                                    NodeList polyPoints = object.getChildNodes();
//                                    for (int m = 0; m < polyPoints.getLength(); m++) {
//                                        Node polyPointValues = polyPoints.item(m);
//                                        // Find the nested element 'polyline'
//                                        if (polyPointValues.getNodeName().equalsIgnoreCase("polyline")) {
//                                            // Store the polygon points as is
//                                            polygonTiles.put(id, polyPointValues.getAttributes().getNamedItem("points").getTextContent());
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            // The invisible tile always has false properties
//            solidTiles.put(0, false);
//            postRenderTiles.put(0, false);
//
//        } catch (SAXException | IOException e) {
//            e.printStackTrace();
//        }
        try {

            DefaultHandler handler = new DefaultHandler() {

                boolean solidPropertyFound = false;
                boolean postRenderedPropertyFound = false;
                boolean solid;
                boolean postRender;
                private int currentId;

                public void startElement(String uri, String localName,String qName,
                                         Attributes attributes) throws SAXException {

                    if(qName.equalsIgnoreCase("tile")){
                        currentId = 1 + Integer.parseInt(attributes.getValue("id"));
                    }
                    else if (qName.equalsIgnoreCase("property")) {
                        if(attributes.getValue("name").equalsIgnoreCase("solid")) {
                            solidPropertyFound = true;
                            solid = Boolean.parseBoolean(attributes.getValue("value"));
                        } else if(attributes.getValue("name").equalsIgnoreCase("postRendered")) {
                            postRenderedPropertyFound = true;
                            postRender = Boolean.parseBoolean(attributes.getValue("value"));
                        }
                    }

                }

                public void characters(char ch[], int start, int length) throws SAXException {

                    if (solidPropertyFound) {
                        solidTiles.put(currentId, solid);
                        solidPropertyFound = false;
                    }

                    if (postRenderedPropertyFound) {
                        postRenderTiles.put(currentId, postRender);
                        postRenderedPropertyFound = false;
                    }

                }

            };

            saxParser.parse(path, handler);

            solidTiles.put(0, false);
            postRenderTiles.put(0, false);

            System.out.println(solidTiles);
            System.out.println(postRenderTiles);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Returns the width of the map from Tiled
     * @params: String path in OS
     */
    public static int getMapWidth(String path) {
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapWidth;
    }

    /*
     * Returns the height of the map from Tiled
     * @params: String path in OS
     */
    public static int getMapHeight(String path) {
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapHeight;
    }

    /*
     * Returns all the tile IDs from the map
     * @param: path - input path from OS to read in the .tmx file
     * @returns: String[] mapValues - all Tile IDs per layer
     */
    public static String[] getMapTiles(String path) {
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
            while (layer < mapValues.length) {
                Node groundMap = maps.item(layer);
                mapValues[layer] = groundMap.getTextContent();
                layer++;
            }

            return mapValues;

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

            for (int i = 0; i < tilesets.getLength(); i++) {
                Node n = tilesets.item(i);
                Node inner = n.getAttributes().getNamedItem("firstgid");
                firstGids[i] = Integer.parseInt(inner.getNodeValue());
            }
            return firstGids;

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

            for (int i = 0; i < tilesets.getLength(); i++) {
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

                if (imageSource.equals(imagePath)) {
                    return i;
                }
            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Couldn't find the index of the image path for world: " + worldPath + " - in: MapLoader::getImageIndex");
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
            e.printStackTrace();
        } catch (IOException e) {
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Artifact 'columns' not found for path: " + worldPath + " in: MapLoader::getTileColumns");
        return columns;
    }
}
