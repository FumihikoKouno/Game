package Game.Common;

import Game.MessageWindow;

public class Data{
	public static final byte TITLE = 0;
	public static final byte PLAYING = 1;
	public static final byte MENU = 2;
	public static final byte PAUSE = 3;
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static int frame;
	public static final byte fps = 30;
	public static int gravity = 1;
	public static final byte CHIP_SIZE = 32;
	public static final byte CHIP_BIT = 5;

	public static final byte ELEMENT_NUM = 4;
	public static final byte WEAPON_NUM = 2;
	public static final byte BODY_NUM = 2;
	
	public static final byte CD_DIFF = 4;
	
	public static final byte SCREEN_OUT = 32;
	
	public static byte gameStatus = PLAYING;
	
	public static MessageWindow mw;
	
	public static final ImageData image = new ImageData();
	public static final SEData se = new SEData();
	
	private static int debugCount;
	
	public static void debugPrint(String s){
		debugCount++;
		System.out.println("frame " + frame + " : " + s);
	}
	
}