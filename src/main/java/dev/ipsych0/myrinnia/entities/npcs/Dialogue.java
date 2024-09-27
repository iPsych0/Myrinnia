package dev.ipsych0.myrinnia.entities.npcs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class Dialogue implements Serializable {
    private int id;
    private int nextId;
    private String text;
    private List<Choice> options;
    private ChoiceCondition choiceCondition;
    private String action;
}
