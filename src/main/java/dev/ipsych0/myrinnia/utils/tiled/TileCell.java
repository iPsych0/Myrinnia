package dev.ipsych0.myrinnia.utils.tiled;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TileCell implements Serializable {
    private int id;
    private String type;
    private List<Property> properties;
}
