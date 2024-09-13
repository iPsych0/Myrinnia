package dev.ipsych0.myrinnia.utils.tiled.tilesets;

import dev.ipsych0.myrinnia.utils.tiled.Property;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Tile {
    private int id;
    private List<Property> properties = new ArrayList<>();
    private List<AnimationFrame> animation = new ArrayList<>();
    private ObjectGroup objectgroup;
}
