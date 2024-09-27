package dev.ipsych0.myrinnia.entities.npcs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Choice implements Serializable {
    private String text;
    private int nextId;
    private ChoiceCondition choiceCondition;
}
