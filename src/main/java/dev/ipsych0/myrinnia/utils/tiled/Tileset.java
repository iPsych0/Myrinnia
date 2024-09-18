package dev.ipsych0.myrinnia.utils.tiled;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Tileset implements Serializable {
    @SerializedName("firstgid")
    private int firstGid;
    private String source; // Relative path to the tileset
    @SerializedName("tilecount")
    private int tileCount;
    private int columns;
    private String image;
    @SerializedName("imagewidth")
    private int imageWidth;
    @SerializedName("imageheight")
    private int imageHeight;
    @SerializedName("tilewidth")
    private int tileWidth;
    @SerializedName("tileheight")
    private int tileHeight;
    private List<TileCell> tiles;
    private List<Property> properties;
}