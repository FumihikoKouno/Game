/**
 * 武器の基盤クラス
 */

package Game.Sprite.Weapon;

import Game.Sprite.Sprite;
import Game.Common.*;

import java.awt.Graphics;

public class Weapon extends Sprite{
	public static final int SWORD = 0;
	public static final int ARROW = 1;
	public Weapon(int x, int y){
		super(x,y);
	}
	public void setDirection(int d){
		direction = d;
	}
	// 実際に武器を出現させる
	public void appear(){assert(false);}
	
	// 武器の攻撃力
	public int power;
	// 武器のID(必要かどうかは分からないけど…)
	public int weaponID;
	// 武器の属性
	public int element;
	// 敵の武器かどうか
	protected boolean enemy;
}