package dev.ipsych0.myrinnia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class DropTableEntry implements Serializable {
    private String itemName;
    private int itemId;
    private int amount;
    private int weight;
}
