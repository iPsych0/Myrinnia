package dev.ipsych0.myrinnia.entities.npcs;

import java.io.Serializable;

public class ChoiceCondition implements Serializable {

    private static final long serialVersionUID = -3575379862319618444L;
    private String condition;
    private int falseId;

    public ChoiceCondition(String condition, int falseId) {
        this.condition = condition;
        this.falseId = falseId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getFalseId() {
        return falseId;
    }

    public void setFalseId(int falseId) {
        this.falseId = falseId;
    }
}
