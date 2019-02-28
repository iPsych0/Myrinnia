package dev.ipsych0.myrinnia.entities.npcs;

public class Choice {

    private String text;
    private int nextId;

    public Choice(String text, int nextId) {
        this.text = text;
        this.nextId = nextId;
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
}
