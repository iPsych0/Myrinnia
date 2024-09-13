package dev.ipsych0.myrinnia.utils.tiled.tilesets;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class TsjTileset {
    private String name;
    private String image;
    private int columns;

    @SerializedName("imagewidth")
    private int imageWidth;

    @SerializedName("imageheight")
    private int imageHeight;

    @SerializedName("tilecount")
    private int tileCount;

    @SerializedName("tilewidth")
    private int tileWidth;

    @SerializedName("tileheight")
    private int tileHeight;


    private List<Tile> tiles;
}
