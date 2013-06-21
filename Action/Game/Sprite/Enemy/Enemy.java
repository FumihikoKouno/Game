/**
 * ���ׂẴC�x���g�̊�ՃN���X
 * �G�A�d�|��(�X�C�b�`�Ƃ��H)�A���Ƃ�
 * �v���C���[�̍s���ɂ���ĉ����ω�����������̂�
 * ���̃N���X���p�����č��
 */

package Game.Sprite.Enemy;

import Game.Sprite.Player;
import Game.Common.*;
import Game.MapData.MapData;
import Game.Sprite.*;
import Game.Sprite.Weapon.*;

import java.awt.Graphics;

public class Enemy extends Sprite{
	public int enemyID;
	protected int life;
	protected int attackedSpriteID = -1;
	protected int power = 1;
	public Enemy(int x, int y){
		super(x,y);
	}
	public void touch(Sprite s, int dir, int dest){
		if(s instanceof Player){
			Player tmp = (Player)s;
			if(!tmp.invisible){
				tmp.damage(power);
			}
		}
	}
	public void update(MapData mapData){
		if(life <= 0){
			end = true;
			mapData.spriteList.add(new Coin(x+1,y+1));
			return;
		}
		animationUpdate(15);
		vy += Data.gravity;
		if(vy >= Data.CHIP_SIZE) vy = Data.CHIP_SIZE - 1;
	}
	
	public void mapHit(int dir, int dest){
		switch(dir){
		case UP:
		case DOWN:
			vy = dest - y;
			break;
		case LEFT:
			direction = Sprite.RIGHT;
			vx = dest - x;
			break;
		case RIGHT:
			direction = Sprite.LEFT;
			vx = dest - x;
			break;
		}
	}
	
	// ���̃C�x���g�ɑ΂��ĕ���ID weapon, ����ID element�ōU�������Ƃ��̊֐�
	public void attacked(Sprite s){
		/**
		 * spriteID�͂��ꂼ��ŗL�̕��Ȃ̂�
		 * ��������Ƃ��̕����spriteID��ێ����Ă����A
		 * �ႤspriteID�̂Ƃ������_���[�W���������邱�Ƃɂ����
		 * ��������ł̑��i�q�b�g���Ȃ����Ă���
		 */
		if(s instanceof Weapon){
			Weapon tmp = (Weapon)s;
			if(attackedSpriteID != tmp.spriteID){
				attackedSpriteID = tmp.spriteID;
				life -= tmp.power;
			}
		}
	}
}