/**
 * キーが押されているかどうかのフラグを持つクラス
 * 実際のゲームではこれらのフラグを見てキーの判定をする
 */
public class KeyStatus{
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	public static boolean change;
	public static boolean scroll;
	public static boolean enter;
	public static boolean toTitle;
	public static boolean retry;
	public static boolean hard;
	public static void setAll(boolean b){
		up = b;
		down = b;
		left = b;
		right = b;
		change = b;
		scroll = b;
		enter = b;
		toTitle = b;
		retry = b;
		hard = b;
	}
}