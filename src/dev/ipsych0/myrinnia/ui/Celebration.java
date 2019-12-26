package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.skills.Skill;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Celebration implements Serializable {

    private static final long serialVersionUID = -3830649550474412935L;
    private Skill skill;
    private Quest quest;
    private String description;
    private boolean pressedNext;

    public Celebration(Quest quest, String description) {
        this.quest = quest;
        this.description = description;
    }

    public Celebration(Skill skill, String description) {
        this.skill = skill;
        this.description = description;
    }

    public boolean hasPressedNext() {
        return pressedNext;
    }

    public void setPressedNext(boolean pressedNext) {
        this.pressedNext = pressedNext;
    }

    public BufferedImage getImg() {
        if (quest != null) {
            return Assets.questsIcon;
        }

        if (skill != null) {
            return skill.getImg();
        }

        return null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
