package dev.ipsych0.myrinnia.utils.tiled;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TiledLayer {
    private String type; // "tilelayer", "objectgroup", etc.
    private String name;
    private int width;
    private int height;
    private List<Integer> data = new ArrayList<>(); // Only for tile layers, not object layers
    private List<TileObject> objects = new ArrayList<>(); // Only for object layers
    private List<Property> properties = new ArrayList<>();
    private float opacity;
    private boolean visible;

    public boolean hasObjects() {
        return objects != null && !objects.isEmpty();
    }

    public boolean hasTiles() {
        return data != null && !data.isEmpty();
    }

}
