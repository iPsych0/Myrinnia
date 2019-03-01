package dev.ipsych0.myrinnia.entities.npcs;

public class ChoiceCondition {
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
