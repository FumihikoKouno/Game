/**
 * ����̊�ՃN���X
 */

package Game.Sprite.Weapon;

import Game.Sprite.Sprite;
import Game.Common.*;

import java.awt.Graphics;

public class Weapon extends Sprite{
	public static final int SWORD = 0;
	public Weapon(int x, int y){
		super(x,y);
	}
	// ����̍U����
	public int power;
	// �����ID(�K�v���ǂ����͕�����Ȃ����ǁc)
	public int weaponID;
	// ����̑���
	public int element;
}