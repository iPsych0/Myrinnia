package dev.ipsych0.myrinnia.items;

import dev.ipsych0.myrinnia.character.CharacterStats;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ItemRequirement implements Serializable {
    private static final long serialVersionUID = -3896881180268598158L;
    private CharacterStats stat;
    private int level;
}
