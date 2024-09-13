package dev.ipsych0.myrinnia.utils.tiled;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TiledMap {
    private int height;
    private int width;
    @SerializedName("tilewidth")
    private int tileWidth;
    @SerializedName("tileheight")
    private int tileHeight;
    private List<TiledLayer> layers = new ArrayList<>();
    private List<Tileset> tilesets = new ArrayList<>();
    private List<Property> properties = new ArrayList<>();
    @SerializedName("tiledversion")
    private String tiledVersion;
}
