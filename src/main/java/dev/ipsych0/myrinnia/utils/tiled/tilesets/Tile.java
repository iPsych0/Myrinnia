package dev.ipsych0.myrinnia.utils.tiled.tilesets;

import dev.ipsych0.myrinnia.utils.tiled.Property;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Tile {
    private int id;
    private List<Property> properties = new ArrayList<>();
    private Set<AnimationFrame> animation = new HashSet<>();
    private ObjectGroup objectgroup;
}
