package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class SkillResource implements Serializable {
    private static final long serialVersionUID = 242384514442352183L;

    protected int levelRequirement;
    protected Item item;
    protected SkillCategory category;
    protected String description;

    @Override
    public String toString() {
        return description;
    }
}
