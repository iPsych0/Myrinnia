package dev.ipsych0.myrinnia.utils.tiled;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Property {
    private String name;
    private String type; // Can be "string", "int", "float", "bool", "color", etc.
    private String value; // Convert this to the actual type in code as needed
}
