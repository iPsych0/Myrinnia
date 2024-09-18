package dev.ipsych0.myrinnia.utils.tiled;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Data
public class TiledLayer implements Serializable {
    private String type; // "tilelayer", "objectgroup", etc.
    private String name;
    private int width;
    private int height;
    private List<Integer> data = new ArrayList<>(); // Only for tile layers, not object layers
    private List<TileObject> objects = new ArrayList<>(); // Only for object layers
    private List<Property> properties = new ArrayList<>();
    private float opacity;
    private boolean visible;
    private String compression;
    private String encoding;


    public boolean hasObjects() {
        return objects != null && !objects.isEmpty();
    }

    public boolean hasTiles() {
        return data != null && !data.isEmpty();
    }

    // Custom deserializer for TiledLayer
    public static class TiledLayerDeserializer implements JsonDeserializer<TiledLayer> {

        @Override
        public TiledLayer deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            TiledLayer tiledLayer = new TiledLayer();
            tiledLayer.setType(jsonObject.get("type").getAsString());
            tiledLayer.setName(jsonObject.get("name").getAsString());
            if (tiledLayer.getType().equalsIgnoreCase("tilelayer")) {
                tiledLayer.setWidth(jsonObject.get("width").getAsInt());
                tiledLayer.setHeight(jsonObject.get("height").getAsInt());
            }
            tiledLayer.setOpacity(jsonObject.get("opacity") != null ? jsonObject.get("opacity").getAsFloat() : 1.0f);
            tiledLayer.setVisible(jsonObject.get("visible").getAsBoolean());
            tiledLayer.setCompression(jsonObject.has("compression") ? jsonObject.get("compression").getAsString() : null);
            tiledLayer.setEncoding(jsonObject.has("encoding") ? jsonObject.get("encoding").getAsString() : null);

            // Handle tile data if present and if encoding/compression is specified
            if (jsonObject.has("data") && "tilelayer".equals(tiledLayer.getType())) {
                String compression = tiledLayer.getCompression();
                String encoding = tiledLayer.getEncoding();

                if ("base64".equals(encoding) && "gzip".equals(compression)) {
                    String encodedData = jsonObject.get("data").getAsString();
                    try {
                        // Decode the base64 and decompress the GZIP data
                        byte[] compressedData = Base64.getDecoder().decode(encodedData.getBytes());
                        byte[] decompressedData = decompressGzip(compressedData);
                        tiledLayer.setData(convertToIntegerList(decompressedData));
                    } catch (IOException e) {
                        throw new JsonParseException("Failed to decode and decompress data", e);
                    }
                } else {
                    Type objectListType = new TypeToken<List<Integer>>(){}.getType();
                    tiledLayer.setData(context.deserialize(jsonObject.get("data"), objectListType));
                }
            }

            if (jsonObject.has("objects")) {
                Type objectListType = new TypeToken<List<TileObject>>(){}.getType();
                tiledLayer.setObjects(context.deserialize(jsonObject.get("objects"), objectListType));
            }
            if (jsonObject.has("properties")) {
                Type propertyListType = new TypeToken<List<Property>>(){}.getType();
                tiledLayer.setProperties(context.deserialize(jsonObject.get("properties"), propertyListType));
            }

            return tiledLayer;
        }

        // Method to decompress GZIP-compressed byte array
        private static byte[] decompressGzip(byte[] compressedData) throws IOException {
            try (ByteArrayInputStream byteStream = new ByteArrayInputStream(compressedData);
                 GZIPInputStream gzipStream = new GZIPInputStream(byteStream);
                 ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {

                int res = 0;
                byte[] buf = new byte[1024];
                while (res >= 0) {
                    res = gzipStream.read(buf, 0, buf.length);
                    if (res > 0) {
                        outStream.write(buf, 0, res);
                    }
                }
                return outStream.toByteArray();
            }
        }

        // Convert the decompressed byte array to a List of Integers
        private static List<Integer> convertToIntegerList(byte[] byteArray) {
            List<Integer> integerList = new ArrayList<>();
            ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);

            // If your data uses little-endian order, set it here
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

            // Read each 4-byte integer from the byte array
            while (byteBuffer.remaining() >= 4) {
                integerList.add(byteBuffer.getInt()); // Read next 4 bytes as an integer
            }

            return integerList;
        }
    }

}
