package dev.ipsych0.myrinnia.entities.npcs;

import java.util.List;

public class Script {
    private List<Dialogue> dialogues;

    public Script(List<Dialogue> dialogues) {
        this.dialogues = dialogues;
    }

    public List<Dialogue> getDialogues() {
        return dialogues;
    }

    public void setDialogues(List<Dialogue> dialogues) {
        this.dialogues = dialogues;
    }
}
