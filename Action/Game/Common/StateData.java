/**
 * ゲームの進行具合によって変わる変数群
 * セーブデータはこのクラスの値を全て保持しておけば
 * セーブできるはず
 */

package Game.Common;
import Game.Sprite.Player;
import Game.MapData.MapData;

public class StateData{
	// 各配列要素が属性付加アイテムの数
	public static byte[] gotElement =  new byte[Data.ELEMENT_NUM];
	// プレイヤー
	public static Player player;
	// マップデータ
	public static MapData mapData;
	// フラグ
	public static Flag flag;
}