package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.tiles.Tile;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Map;

@Slf4j
public class CelenorRopeRock extends Entity {

    private Player player;
    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);
    private boolean ropeUsed;

    public CelenorRopeRock(double x, double y, int width, int height, Map<String,String> props) {
        super(x, y, width, height, props);
        solid = true;
        attackable = false;
        isNpc = true;
        staticNpc = true;
        player = Handler.get().getPlayer();
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        if (ropeUsed) {
            g.drawImage(Assets.celenorRopeRock, (int) (x - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - Handler.get().getGameCamera().getyOffset()), null);
        }
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "questStep":
                return quest.getQuestSteps().get(4).isFinished();
            case "hasRope":
                return Handler.get().playerHasItem(Item.rope, 1);
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    protected void onDialogueShown(String action) {
        switch (speakingTurn) {
            case 0:
                if (ropeUsed) {
                    speakingTurn = 4;
                }
                break;
            case 3:
                if (!ropeUsed) {
                    ropeUsed = true;
                    Handler.get().removeItem(Item.rope, 1);
                    Handler.get().getWorld().getEntityManager().addRuntimeEntity(new CelenorRopeUp(
                            x + Tile.TILEWIDTH * 2.0, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2,
                            Map.of("name", "Rope","jsonFile", "celenor_rope_rock.json"))
                    );
                }
                player.setX(58 * Tile.TILEWIDTH);
                player.setY(6 * Tile.TILEHEIGHT);
                speakingTurn = -1;
                break;
        }
    }
}
