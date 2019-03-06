package dev.ipsych0.myrinnia.entities.npcs;

import java.io.Serializable;

public class Choice implements Serializable {


    private static final long serialVersionUID = -4854392724760684905L;
    private String text;
    private int nextId;
    private ChoiceCondition choiceCondition;

    public Choice(String text, int nextId, ChoiceCondition choiceCondition) {
        this.text = text;
        this.nextId = nextId;
        this.choiceCondition = choiceCondition;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public ChoiceCondition getChoiceCondition() {
        return choiceCondition;
    }

    public void setChoiceCondition(ChoiceCondition choiceCondition) {
        this.choiceCondition = choiceCondition;
    }
}
