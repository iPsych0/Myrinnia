package dev.ipsych0.mygame.puzzles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.utils.Text;

public class SliderPuzzle extends Puzzle {
	
	private int x, y, width, height;
	private int maxSize;
	private SliderPiece[][] sliderPieces;
	private Handler handler;
	public static boolean hasBeenPressed = false;
	
	public SliderPuzzle(Handler handler, int maxSize) {
		this.handler = handler;
		this.maxSize = maxSize;
		
		x = handler.getWidth() / 4;
		y = handler.getHeight() / 4;
		width = maxSize * 32;
		height = maxSize * 32;
		
		sliderPieces = new SliderPiece[maxSize][maxSize];
		
		int id = 0;
		for(int y = 0; y < maxSize; y++) {
			for(int x = 0; x < maxSize; x++) {
				if(x == maxSize-1 && y == maxSize-1) {
					sliderPieces[x][y] = new SliderPiece(handler, x ,y, true, id++);
					break;
				}
				sliderPieces[x][y] = new SliderPiece(handler, x ,y, id++);
			}
		}
		
		shuffle(sliderPieces, maxSize, new Random());
		
	}
	
	public void tick() {
		Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);

		for(int y = 0; y < maxSize; y++) {
			for(int x = 0; x < maxSize; x++) {
				if(sliderPieces[x][y] != null) {
					
					sliderPieces[x][y].tick();
					
					if(sliderPieces[x][y].getBounds().contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
						checkMove(x, y);
						hasBeenPressed = false;
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		for(int y = 0; y < maxSize; y++) {
			for(int x = 0; x < maxSize; x++) {
				if(sliderPieces[x][y] != null)
					sliderPieces[x][y].render(g);
			}
		}
		
		if(completed) {
			Text.drawString(g, "CONGRATULATIONS, YOU HAVE SOLVED THE PUZZLE!", handler.getWidth() / 2, handler.getHeight() / 2, true, Color.YELLOW, Assets.font32);
		}
	}
	
	private void checkMove(int x, int y) {
		if(sliderPieces[x][y].isBlank()) {
			return;
		}
		
		if((x - 1) >= 0 && sliderPieces[x - 1][y].isBlank()) {
			move(sliderPieces[x][y], sliderPieces[x - 1][y]);
		}
		else if((x + 1) < maxSize && sliderPieces[x + 1][y].isBlank()) {
			move(sliderPieces[x][y], sliderPieces[x + 1][y]);
		}
		else if((y - 1) >= 0 && sliderPieces[x][y - 1].isBlank()) {
			move(sliderPieces[x][y], sliderPieces[x][y - 1]);
		}
		else if((y + 1) < maxSize && sliderPieces[x][y + 1].isBlank()) {
			move(sliderPieces[x][y], sliderPieces[x][y + 1]);
		}
		
	}
	
	private void move(SliderPiece oldPos, SliderPiece newPos) {
		
		int oldX = oldPos.getxPos();
		int oldY = oldPos.getyPos();
		
		
		oldPos.setxPos(newPos.getxPos());
		oldPos.setyPos(newPos.getyPos());
		
		SliderPiece temp = oldPos;
		oldPos = newPos;
		newPos = temp;
		
		newPos.setxPos(oldX);
		newPos.setyPos(oldY);
		
		newPos.setBlank(true);
		oldPos.setBlank(false);
		
		BufferedImage temp2 = oldPos.getTexture();
		oldPos.setTexture(newPos.getTexture());
		newPos.setTexture(temp2);
		
		int temp3 = oldPos.getId();
		oldPos.setId(newPos.getId());
		newPos.setId(temp3);
		
		checkSolution();

	}
	
	public void checkSolution() {
		List<Integer> ids = new ArrayList<>();
		for(int y = 0; y < maxSize; y++) {
			for(int x = 0; x < maxSize; x++) {
				ids.add(sliderPieces[x][y].getId());
			}
		}
		
		boolean result = true;
		for(int i = 0; i < ids.size() - 1; i++) {
			if(ids.get(i) >= ids.get(i+1)) {
				result = false;
				break;
			}
		}
		this.setCompleted(result);
	}
	
	public void shuffle(SliderPiece[][] matrix, int columns, Random rnd) {
	    int size = matrix.length * columns;
	    for (int i = size; i > 1; i--)
	        swap(matrix, columns, i - 1, rnd.nextInt(i));
	}
	
	public void swap(SliderPiece[][] matrix, int columns, int i, int j) {
		SliderPiece old = matrix[i / columns][i % columns];
		SliderPiece target = matrix[j / columns][j % columns];
		
		int oldX = old.getxPos();
		int oldY = old.getyPos();
		
		old.setxPos(target.getxPos());
		old.setyPos(target.getyPos());
		
		SliderPiece tmp = old;
	    old = target;
	    target = tmp;
	    
	    target.setxPos(oldX);
	    target.setyPos(oldY);
	    
	    if(old.getId() == 24 || target.getId() == 24) {
		    old.setBlank(!old.isBlank());
		    target.setBlank(!target.isBlank());
	    }
	    
	    BufferedImage temp2 = old.getTexture();
	    old.setTexture(target.getTexture());
	    target.setTexture(temp2);
	    
	    int temp3 = old.getId();
	    old.setId(target.getId());
	    target.setId(temp3);
	    
	}
	

}
