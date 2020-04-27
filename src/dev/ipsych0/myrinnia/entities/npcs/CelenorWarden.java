package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.worlds.World;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;

public class CelenorWarden extends Creature {

    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);

    public CelenorWarden(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {

        }
    }
}
