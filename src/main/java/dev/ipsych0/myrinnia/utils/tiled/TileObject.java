package dev.ipsych0.myrinnia.utils.tiled;

import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class TileObject {
    private int id;
    private String name;
    private String type;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible;
    public List<Point> polygon = new ArrayList<>();
    private List<Property> properties = new ArrayList<>();
}
