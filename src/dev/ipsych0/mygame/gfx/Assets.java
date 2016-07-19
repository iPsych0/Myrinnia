package dev.ipsych0.mygame.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;

	// Tile images
	public static BufferedImage lava, dirt, grass, stone, water, cave, ice, sand, snow;
	
	// Map item images (trees, rocks, etc)
	public static BufferedImage tree, rock;
	
	// Player animation images
	public static BufferedImage[] player_down, player_up, player_left, player_right;
	
	// Player attack images
	public static BufferedImage[] player_attackingLeft, player_attackingRight, player_attackingDown, player_attackingUp;
	
	// Menu images
	public static BufferedImage[] btn_start;
	
	// Item images
	public static BufferedImage wood;
	public static BufferedImage ore;
	
	public static void init(){
		SpriteSheet texture_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/textures.png"));
		SpriteSheet player_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/herosprites.png"));
		SpriteSheet menu_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/menusprites.png"));
		SpriteSheet item_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/itemsprites.png"));
		// http://www.online-image-editor.com/ to remove white background from sprites, save as .png!
		
		// Menu sprites
		
		btn_start = new BufferedImage[2];
		
		btn_start[0] = menu_sheet.crop(0, 0, width * 7, height * 3);
		btn_start[1] = menu_sheet.crop(width * 7, 0, width * 7, height * 3);
		
		// Item Sprites
		
		wood = item_sheet.crop(0, 0, width, height);
		ore = item_sheet.crop(0, height, width, height);
		
		// Player Sprites
		
		player_attackingLeft = new BufferedImage[2];
		player_attackingRight = new BufferedImage[2];
		player_attackingDown = new BufferedImage[2];
		player_attackingUp = new BufferedImage[2];
		
		player_down = new BufferedImage[3];
		player_up = new BufferedImage[3];
		player_left = new BufferedImage[3];
		player_right = new BufferedImage[3];
		
		player_attackingLeft[0] = player_sheet.crop(0, height * 4, width, height);
		player_attackingLeft[1] = player_sheet.crop(width, height * 4, width, height);
		
		player_attackingRight[0] = player_sheet.crop(0, height * 5, width, height);
		player_attackingRight[1] = player_sheet.crop(width, height * 5, width, height);
		
		player_attackingDown[0] = player_sheet.crop(0, height * 6, width, height);
		player_attackingDown[1] = player_sheet.crop(width, height * 6, width, height);
		
		player_attackingUp[0] = player_sheet.crop(0, height * 7, width, height);
		player_attackingUp[1] = player_sheet.crop(width, height * 7, width, height);
		
		player_down[0] = player_sheet.crop(0, 0, width, height);
		player_down[1] = player_sheet.crop(width, 0, width, height);
		player_down[2] = player_sheet.crop(width * 2, 0, width, height);
		
		player_up[0] = player_sheet.crop(width * 6, height * 3, width, height);
		player_up[1] = player_sheet.crop(width * 7, height * 3, width, height);
		player_up[2] = player_sheet.crop(width * 8, height * 3, width, height);
		
		player_left[0] = player_sheet.crop(0, height, width, height);
		player_left[1] = player_sheet.crop(width, height, width, height);
		player_left[2] = player_sheet.crop(width * 2, height, width, height);
		
		player_right[0] = player_sheet.crop(0, height * 2, width, height);
		player_right[1] = player_sheet.crop(width, height * 2, width, height);
		player_right[2] = player_sheet.crop(width * 2, height * 2, width, height);
		
		// Tile Sprites
		
		lava = texture_sheet.crop(0, 0, width, height);
		dirt = texture_sheet.crop(width, 0, width, height);
		grass = texture_sheet.crop(width * 2, 0, width, height);
		stone = texture_sheet.crop(width * 3, 0, width, height);
		water = texture_sheet.crop(width * 3, height, width, height);
		cave = texture_sheet.crop(width * 3, height * 2, width, height);
		ice = texture_sheet.crop(width * 3, height * 3, width, height);
		sand = texture_sheet.crop(width * 3, height * 4, width, height);
		snow = texture_sheet.crop(width * 3, height * 5, width, height);
		
		// Map icons like trees, rocks, etc
		
		tree = texture_sheet.crop(0, height, width, height);
		rock = texture_sheet.crop(0, height * 3, width, height);
		
	}
	
}
