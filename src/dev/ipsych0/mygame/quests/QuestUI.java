package dev.ipsych0.mygame.quests;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.quests.Quest.QuestState;
import dev.ipsych0.mygame.utils.Text;

public class QuestUI {
	
	public static boolean isOpen = false;
	private int x, y, width, height;
	private Handler handler;
	
	public QuestUI(Handler handler) {
		this.handler = handler;
		this.x = 200;
		this.y = 150;
		this.width = 200;
		this.height = 400;
	}
	
	public void tick() {
		if(isOpen) {
			
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			g.setColor(Color.GRAY);
			g.fillRect(x, y, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
			
			for(int i = 0; i < handler.getQuestManager().getQuestList().size(); i++) {
				Color color;
				if(handler.getQuestManager().getQuestList().get(i).getState() == QuestState.NOT_STARTED)
					color = Color.RED;
				else if(handler.getQuestManager().getQuestList().get(i).getState() == QuestState.IN_PROGRESS)
					color = Color.YELLOW;
				else
					color = Color.GREEN;
				Text.drawString(g, handler.getQuestManager().getQuestList().get(i).getQuestName(), x + (width / 2), y + 16 + (i * 16), true, color, Assets.font14);
			}
		}
	}

}
