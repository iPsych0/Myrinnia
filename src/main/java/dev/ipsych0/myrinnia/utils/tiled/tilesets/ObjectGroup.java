package dev.ipsych0.myrinnia.utils.tiled.tilesets;

import dev.ipsych0.myrinnia.utils.tiled.TileObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ObjectGroup {
    private String draworder;
    private String name;
    private List<TileObject> objects = new ArrayList<>();
    private int rotation;
    private double opacity;
    private String type;
    private boolean visible;
    private int x;
    private int y;
}
