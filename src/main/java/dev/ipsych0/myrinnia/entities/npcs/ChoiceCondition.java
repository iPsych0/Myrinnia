package dev.ipsych0.myrinnia.entities.npcs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ChoiceCondition implements Serializable {
    private String condition;
    private int falseId;
}
