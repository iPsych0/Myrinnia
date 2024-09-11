package dev.ipsych0.myrinnia.quests;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestStep implements Serializable {

    private static final long serialVersionUID = 7195660759638554120L;
    private String objective;
    private boolean finished;

    public QuestStep(String objective) {
        this.objective = objective;
    }
}
