package dev.ipsych0.myrinnia.entities.npcs;

import java.util.List;

public class Dialogue {
    private int id;
    private int nextId;
    private String text;
    private List<Choice> options;

    public Dialogue(int id, int nextId, String text) {
        this.id = id;
        this.nextId = nextId;
        this.text = text;
    }

    public Dialogue(int id, int nextId, String text, List<Choice> options) {
        this.id = id;
        this.nextId = nextId;
        this.text = text;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Choice> getOptions() {
        return options;
    }

    public void setOptions(List<Choice> options) {
        this.options = options;
    }
}
