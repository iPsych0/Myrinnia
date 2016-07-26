package dev.ipsych0.mygame.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;

	// Tile images
	public static BufferedImage lava, dirt, grass, stone, water, cave, ice, sand, snow, black;
	public static BufferedImage pebblegrassTopLeft, pebblegrassTopMiddle, pebblegrassTopRight, pebblegrassMiddleLeft,
								pebblegrassMiddleMiddle, pebblegrassMiddleRight, pebblegrassDownLeft, pebblegrassDownMiddle,
								pebblegrassDownRight, pebblegrassSmall, pebblegrassBig, grassonly;   
	public static BufferedImage hillgrassTopLeft, hillgrassTopMiddle, hillgrassMiddleMiddle, hillgrassMiddleRight, hillgrassDownLeft,
								hillgrassDownMiddle, hillgrassDownRight, hillgrassOutcrop, hillgrassFourway, hillgrassMiddleLeft,
								hillgrassTopRight;
	
	
	// Map item images (trees, rocks, etc)
	public static BufferedImage tree, rock;
	
	// Player animation images
	public static BufferedImage[] player_down, player_up, player_left, player_right;
	
	// Player attack images
	public static BufferedImage[] player_attackingLeft, player_attackingRight, player_attackingDown, player_attackingUp;
	
	// Menu images
	public static BufferedImage[] btn_start;
	
	// Item images
	public static BufferedImage wood, ore;
	
	// Enemy images
	public static BufferedImage scorpion;
	
	// NPC images
	
	public static BufferedImage lorraine;
	
	// Green House images
	public static BufferedImage greenHouseRoof, greenHouseWall, greenHouseEntrance;
	//tryoutpaths
	public static BufferedImage pathup;

	public static BufferedImage pathup2; 
	
	
	public static void init(){
		SpriteSheet texture_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/textures.png"));
		SpriteSheet player_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/herosprites.png"));
		SpriteSheet menu_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/menusprites.png"));
		SpriteSheet item_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/itemsprites.png"));
		SpriteSheet enemy_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/enemysprites.png"));
		SpriteSheet house_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/housesprites.png"));
		SpriteSheet lorraine_sprites = new SpriteSheet(ImageLoader.loadImage("/textures/lorrainesprites.png"));
		SpriteSheet texture_sheet2 = new SpriteSheet(ImageLoader.loadImage("/textures/npcgrid.png"));
		SpriteSheet texture_tile = new SpriteSheet(ImageLoader.loadImage("/textures/SpritesheetTiles.png"));
		
		// http://www.online-image-editor.com/ to remove white background from sprites, save as .png!
		
		// Menu sprites
		
		btn_start = new BufferedImage[2];
		
		btn_start[0] = menu_sheet.crop(0, 0, width * 7, height * 3);
		btn_start[1] = menu_sheet.crop(width * 7, 0, width * 7, height * 3);
		
		// Item Sprites
		
		wood = item_sheet.crop(0, 0, width, height);
		ore = item_sheet.crop(0, height, width, height);
		
		// Enemy Sprites
		
		scorpion = enemy_sheet.crop(0, 0, width, height);
		
		// NPC Sprites
		
		lorraine = lorraine_sprites.crop(width * 7, 0, width, height);
		
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
		black = texture_sheet.crop(width * 3, height * 6, width, height);
		
		pathup = texture_sheet2.crop(0, 0, width, height);
		pathup2 = texture_sheet2.crop(0, height, width, height);
		
		pebblegrassTopLeft = texture_tile.crop(width * 9, height * 1, width, height);
		pebblegrassTopMiddle = texture_tile.crop(width * 10, height * 1, width, height);
		pebblegrassTopRight = texture_tile.crop(width * 11, height * 1, width, height);
		pebblegrassMiddleLeft = texture_tile.crop(width * 9, height * 2, width, height);
		pebblegrassMiddleMiddle = texture_tile.crop(width * 10, height * 2, width, height);
		pebblegrassMiddleRight = texture_tile.crop(width * 11, height * 2, width, height);
		pebblegrassDownLeft = texture_tile.crop(width * 9, height * 3, width, height);
		pebblegrassDownMiddle = texture_tile.crop(width * 10, height * 3, width, height);
		pebblegrassDownRight = texture_tile.crop(width * 11, height * 3, width, height);
		pebblegrassSmall = texture_tile.crop(width * 9, height * 0, width, height);
		pebblegrassBig = texture_tile.crop(width * 11, height * 0, width, height);
		grassonly = texture_tile.crop(width * 10, height * 0, width, height);
		hillgrassTopLeft = texture_tile.crop(width * 6, height * 5, width, height);
		hillgrassTopMiddle = texture_tile.crop(width * 7, height * 5, width, height);
		hillgrassTopRight = texture_tile.crop(width * 8, height * 5, width, height);
		hillgrassMiddleLeft = texture_tile.crop(width * 6, height * 6, width, height);
		hillgrassMiddleMiddle = texture_tile.crop(width * 7, height * 6, width, height);
		hillgrassMiddleRight = texture_tile.crop(width * 8, height * 6, width, height);
		hillgrassDownLeft = texture_tile.crop(width * 6, height * 7, width, height);
		hillgrassDownMiddle = texture_tile.crop(width * 7, height * 7, width, height);
		hillgrassDownRight = texture_tile.crop(width * 8, height * 7, width, height);
		hillgrassOutcrop = texture_tile.crop(width * 6, height * 4, width, height);
		hillgrassFourway = texture_tile.crop(width * 8, height * 4, width, height);
		
		// Map icons like trees, rocks, etc
		
		tree = texture_sheet.crop(0, height, width, height);
		rock = texture_sheet.crop(0, height * 3, width, height);
		
		// House Spites
		
		greenHouseRoof = house_sheet.crop(width * 8, height, width, height);
		greenHouseWall = house_sheet.crop(width * 8, height * 3, width, height);
		greenHouseEntrance = house_sheet.crop(width * 10, height * 3, width, height);
		
	}
	
}
