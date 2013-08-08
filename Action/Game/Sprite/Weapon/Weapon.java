/**
 * ����̊�ՃN���X
 */

package Game.Sprite.Weapon;

import Game.Sprite.Sprite;
import Game.Common.*;

import java.awt.Graphics;

public class Weapon extends Sprite{
	public static final int SWORD = 0;
	public static final int ARROW = 1;
	public static final int SHOT = 2;
	public Weapon(int x, int y){
		super(x,y);
	}
	public void setDirection(int d){
		direction = d;
	}
	// ���ۂɕ�����o��������
	public void appear(){assert(false);}
	
	// ����̍U����
	public int power;
	// �����ID(�K�v���ǂ����͕�����Ȃ����ǁc)
	public int weaponID;
	// ����̑���
	public int element;
	// �G�̕��킩�ǂ���
	protected boolean enemy;
}