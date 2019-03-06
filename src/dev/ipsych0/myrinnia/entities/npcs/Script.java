package dev.ipsych0.myrinnia.entities.npcs;

import java.io.Serializable;
import java.util.List;

public class Script implements Serializable {

    private static final long serialVersionUID = 4858953981738296238L;
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
