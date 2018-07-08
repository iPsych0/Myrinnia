package dev.ipsych0.mygame.puzzles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.Handler;

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
		
		for(int y = 0; y < maxSize; y++) {
			for(int x = 0; x < maxSize; x++) {
				if(x == maxSize-1 && y == maxSize-1) {
					sliderPieces[x][y] = new SliderPiece(handler, x ,y, true);
					break;
				}
				sliderPieces[x][y] = new SliderPiece(handler, x ,y);
			}
		}
		
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
		
//		newPos.setBlank(true);
//		oldPos.setBlank(false);
		
//		oldPos.setxPos(newPos.getxPos());
//		oldPos.setyPos(newPos.getyPos());
//		
//		newPos.setxPos(oldPos.getxPos());
//		newPos.setyPos(oldPos.getyPos());
//	
		
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
		
		
//		
//		SliderPiece temp = sliderPieces[oldXPos][oldYPos];
//		sliderPieces[oldXPos][oldYPos] = sliderPieces[newXPos][newYPos];
//		sliderPieces[newXPos][newYPos] = temp;
		
		
	}
	

}
