package Game.Common;
public class Data{
	public static final int TITLE = 0;
	public static final int PLAYING = 1;
	public static final int MENU = 2;
	public static final int PAUSE = 3;
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static int frame;
	public static final int fps = 30;
	public static int gravity = 1;
	public static int CHIP_SIZE = 32;
	
	public static int gameStatus = PLAYING;
	
	public static final ImageData image = new ImageData();
	public static final SEData se = new SEData();
	
	private static int debugCount;
	
	public static void debugPrint(String s){
		debugCount++;
		System.out.println("debug" + debugCount + " : " + s);
	}
	
}