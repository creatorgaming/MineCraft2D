package dev.project.game.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {



		private  static final int  width = 64 , height = 64 ;

		public static Font font28;
		
		public static BufferedImage tree, grass,ground,concrete,rock;
		public static BufferedImage wood;
		public static BufferedImage[] player_down, player_up, player_left, player_right;
		public static BufferedImage[] btn_start;
		public static BufferedImage inventoryScreen;
		
		public static void init(){
			
			font28 = FontLoader.loadFont("res/fonts/slkscr.ttf", 28);
			
			SpriteSheet sheet = new SpriteSheet(ImageLoader.PutImage("/textures/Sheet.png"));
			
			inventoryScreen = ImageLoader.PutImage("/textures/inventoryScreen.png");
			
			wood = sheet.crop(width, height, width, height);
			
			btn_start = new BufferedImage[2];
			
			btn_start[0] = sheet.crop(width * 6, height * 2, width * 2, height);
			btn_start[1] = sheet.crop(width * 6, height * 3, width * 2, height);
			
			
			player_down = new BufferedImage[2];
			player_up = new BufferedImage[2];
			player_left = new BufferedImage[2];
			player_right = new BufferedImage[2];
			
			player_down[0] = sheet.crop(width * 4, 0, width, height);
			player_down[1] = sheet.crop(width * 5, 0, width, height);
			
			player_up[0] = sheet.crop(width * 6, 0, width, height);
			player_up[1] = sheet.crop(width * 7, 0, width, height);
			
			player_right[0] = sheet.crop(width * 4, height, width, height);
			player_right[1] = sheet.crop(width * 5, height, width, height);
			
			player_left[0] = sheet.crop(width * 6, height, width, height);
			player_left[1] = sheet.crop(width * 7, height, width, height);
			
			
			tree = sheet.crop(0, 0, width, height);
			ground = sheet.crop(width * 3, 0, width, height);
			grass = sheet.crop(width, 0, width, height);
			concrete = sheet.crop(width*2,0, width, height);
			rock = sheet.crop(0, height, width, height);
		}
}
