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
	public static boolean hasBeenPressed = false;
	
	public SliderPuzzle(int maxSize) {
		if(maxSize > 10 || maxSize < 3) {
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.println("SliderPuzzle size must be between 3x3 and 10x10");
				System.exit(1);
			}
		}
		
		this.maxSize = maxSize;
		
		x = Handler.get().getWidth() / 2 - maxSize * 32 / 2;
		y = Handler.get().getHeight() / 2 - maxSize * 32 / 2;
		width = maxSize * 32;
		height = maxSize * 32;
		
		sliderPieces = new SliderPiece[maxSize][maxSize];
		
		int id = 0;
		for(int y = 0; y < maxSize; y++) {
			for(int x = 0; x < maxSize; x++) {
				if(x == maxSize-1 && y == maxSize-1) {
					sliderPieces[x][y] = new SliderPiece(x ,y, true, id++);
					sliderPieces[x][y].setWindowX(this.x);
					sliderPieces[x][y].setWindowY(this.y);
					break;
				}
				sliderPieces[x][y] = new SliderPiece(x ,y, id++);
				sliderPieces[x][y].setWindowX(this.x);
				sliderPieces[x][y].setWindowY(this.y);
			}
		}
		
		shuffle(sliderPieces, maxSize, new Random());
		
		while(!isSolvable(getPuzzleIds())) {
			shuffle(sliderPieces, maxSize, new Random());
		}		
	}
	
	public int[] getPuzzleIds() {
		int[] ids = new int[maxSize*maxSize];
		int index = 0;
		for(int y = 0; y < maxSize; y++){
			for(int x = 0; x < maxSize; x++) {
				ids[index++] = sliderPieces[x][y].getId();
			}
		}
		return ids;
	}
	
	public void tick() {
		Rectangle mouse = new Rectangle(Handler.get().getMouseManager().getMouseX(), Handler.get().getMouseManager().getMouseY(), 1, 1);

		for(int y = 0; y < maxSize; y++) {
			for(int x = 0; x < maxSize; x++) {
				if(sliderPieces[x][y] != null) {
					
					sliderPieces[x][y].tick();
					
					if(sliderPieces[x][y].getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
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
			Text.drawString(g, "CONGRATULATIONS, YOU HAVE SOLVED THE PUZZLE!", Handler.get().getWidth() / 2, Handler.get().getHeight() / 2, true, Color.YELLOW, Assets.font32);
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
	
	private void checkSolution() {
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
	
	private boolean isSolvable(int[] puzzle) {
	    int parity = 0;
	    int gridWidth = (int) Math.sqrt(puzzle.length);
	    int row = 0; // the current row we are on
	    int blankRow = 0; // the row with the blank tile

	    for (int i = 0; i < puzzle.length; i++) {
	        if (i % gridWidth == 0) { // advance to next row
	            row++;
	        }
	        if (puzzle[i] == maxSize*maxSize-1) { // the blank tile
	            blankRow = row; // save the row on which encountered
	            continue;
	        }
	        for (int j = i + 1; j < puzzle.length; j++){
	            if (puzzle[i] > puzzle[j] && puzzle[j] != (maxSize*maxSize-1)){
	                parity++;
	            }
	        }
	    }

	    if (gridWidth % 2 == 0) { // even grid
	        if (blankRow % 2 == 0) { // blank on odd row; counting from bottom
	            return parity % 2 == 0;
	        } else { // blank on even row; counting from bottom
	            return parity % 2 != 0;
	        }
	    } else { // odd grid
	        return parity % 2 == 0;
	    }
	}
	
	private void shuffle(SliderPiece[][] matrix, int columns, Random rnd) {
	    int size = matrix.length * columns;
	    for (int i = size; i > 1; i--)
	        swap(matrix, columns, i - 1, rnd.nextInt(i));
	}
	
	private void swap(SliderPiece[][] matrix, int columns, int i, int j) {
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
	    
	    if(old.getId() == maxSize*maxSize-1 || target.getId() == maxSize*maxSize-1) {
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
