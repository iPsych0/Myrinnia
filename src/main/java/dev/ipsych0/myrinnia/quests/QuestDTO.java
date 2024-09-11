package dev.ipsych0.myrinnia.quests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestDTO implements Serializable {

    private static final long serialVersionUID = -6320073288953863389L;
    private String questName;
    private String questStart;
    private List<String> objectives;
}

