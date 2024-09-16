package dev.ipsych0.myrinnia.utils.tiled;

import dev.ipsych0.myrinnia.utils.tiled.tilesets.Polypoint;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class TileObject {
    private int id;
    private String name;
    private String type;
    private float x;
    private float y;
    private int width;
    private int height;
    private boolean visible;
    public List<Polypoint> polygon = new ArrayList<>();
    private List<Property> properties = new ArrayList<>();
}
