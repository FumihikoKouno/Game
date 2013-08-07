/**
 * ゲームの進行具合によって変わる変数群
 * セーブデータはこのクラスの値を全て保持しておけば
 * セーブできるはず
 */

package Game.Common;
import Game.Sprite.Player;
import Game.MapData.MapData;

public class StateData{
	// 各ビットがその武器を入手したかどうかのフラグ
	public static int gotWeapon;
	// 各配列要素が属性付加アイテムの数
	public static int[] gotElement =  new int[Data.ELEMENT_NUM];
	// プレイヤー
	public static Player player;
	// マップデータ
	public static MapData mapData;
	
}