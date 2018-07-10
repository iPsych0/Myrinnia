package dev.ipsych0.mygame.puzzles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

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
		
		int temp3 = oldPos.getId();
		oldPos.setId(newPos.getId());
		newPos.setId(temp3);
		
		
//		
//		SliderPiece temp = sliderPieces[oldXPos][oldYPos];
//		sliderPieces[oldXPos][oldYPos] = sliderPieces[newXPos][newYPos];
//		sliderPieces[newXPos][newYPos] = temp;
		
		
	}
	
	public void shuffle(SliderPiece[][] matrix, int columns, Random rnd) {
	    int size = matrix.length * columns;
	    for (int i = size; i > 1; i--)
	        swap(matrix, columns, i - 1, rnd.nextInt(i));
	}
	
	public void swap(SliderPiece[][] matrix, int columns, int i, int j) {
		int oldX = matrix[i / columns][i % columns].getxPos();
		int oldY = matrix[i / columns][i % columns].getyPos();
		
		matrix[i / columns][i % columns].setxPos(matrix[j / columns][j % columns].getxPos());
		matrix[i / columns][i % columns].setyPos(matrix[j / columns][j % columns].getyPos());
		
		SliderPiece tmp = matrix[i / columns][i % columns];
	    matrix[i / columns][i % columns] = matrix[j / columns][j % columns];
	    matrix[j / columns][j % columns] = tmp;
	    
	    matrix[j / columns][j % columns].setxPos(oldX);
	    matrix[j / columns][j % columns].setyPos(oldY);
	    
	    if(matrix[i / columns][i % columns].getId() == 24 || matrix[j / columns][j % columns].getId() == 24) {
		    matrix[i / columns][i % columns].setBlank(!matrix[i / columns][i % columns].isBlank());
		    matrix[j / columns][j % columns].setBlank(!matrix[j / columns][j % columns].isBlank());
	    }
	    
	    BufferedImage temp2 = matrix[i / columns][i % columns].getTexture();
	    matrix[i / columns][i % columns].setTexture(matrix[j / columns][j % columns].getTexture());
	    matrix[j / columns][j % columns].setTexture(temp2);
	    
	    int temp3 = matrix[i / columns][i % columns].getId();
	    matrix[i / columns][i % columns].setId(matrix[j / columns][j % columns].getId());
	    matrix[j / columns][j % columns].setId(temp3);
	    
		
	    
	}
	

}
