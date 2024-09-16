package dev.ipsych0.myrinnia.utils.tiled;

import lombok.Data;

import java.util.List;

@Data
public class TileCell {
    private int id;
    private String type;
    private List<Property> properties;
}
