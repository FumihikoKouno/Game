package Game.Common;
public class KeyStatus{
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	public static boolean jump;
	public static boolean attack;
	public static boolean menu;
	public static boolean pause;
	public static boolean dash;
	public static void setAll(boolean b){
		up = b;
		down = b;
		left = b;
		right = b;
		jump = b;
		attack = b;
		menu = b;
		pause = b;
		dash = b;
	}
}