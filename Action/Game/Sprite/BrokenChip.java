/**
 * �U���ɂ���ĉ󂹂�ǂ̃N���X
 * weapon���Ή����镐��
 * element���Ή����鑮��
 */

package Game.Sprite;

import Game.Sprite.Player;
import Game.Common.Data;
import Game.Sprite.Weapon.*;

import java.awt.Graphics;
import java.awt.Image;

public class BrokenChip extends Sprite{
	private int attackedSpriteID;
	private int weapon;
	private int element;
	private int life;
	public BrokenChip(int x, int y, int weapon, int element){
		super(x,y);
		IMAGE_X = 0;
		IMAGE_Y = 77;
		life = 1;
		width = Data.CHIP_SIZE;
		height = Data.CHIP_SIZE;
		this.weapon = (1<<weapon);
		this.element = (1<<element);
	}
	
	public void update(){
		if(life <= 0) end = true;
		vx = 0;
		vy = 0;
	}
	
	private boolean damageable(Sprite sprite){
		Weapon w;
		w = (Weapon)sprite;
		boolean elem = ((element&(1<<w.element)) > 0);
		int weaponTmp=0;
		if(sprite instanceof Sword){
			weaponTmp = Weapon.SWORD;
		}else if(sprite instanceof Arrow){
			weaponTmp = Weapon.ARROW;
		}else if(sprite instanceof Shot){
			weaponTmp = Weapon.SHOT;
		}
		return elem && ((weapon&(1<<weaponTmp))>0);
	}
	
	// ���̃X�v���C�g�ɑ΂���sprite���U�������Ƃ��̊֐�
	public void attacked(Sprite sprite){
		if(attackedSpriteID == sprite.getSpriteID()){
			return;
		}else{
			attackedSpriteID = sprite.getSpriteID();
		}
		Weapon w;
		if(sprite instanceof Weapon){
			if(damageable(sprite)){
				w = (Weapon)sprite;
				life -= w.power;
			}
		}
	}
	
	// �v���C���[���X�v���C�g�ɐG�ꂽ�Ƃ��̊֐�
	public void touch(Sprite s, int dir, int[] dest){
		if((dir & (1 << DOWN)) > 0){
			s.mapHit(DOWN,dest[DOWN]);
//			s.setVy(dest[DOWN]-s.getY());
//			s.land();
			return;
		}
		if((dir & (1 << LEFT)) > 0){
			s.mapHit(LEFT,dest[LEFT]);
//			s.setVx(dest[LEFT]-s.getX());
			return;
		}
		if((dir & (1 << RIGHT)) > 0){
			s.mapHit(RIGHT,dest[RIGHT]);
//			s.setVx(dest[RIGHT]-s.getX());
			return;
		}
		if((dir & (1 << UP)) > 0){
			s.mapHit(UP,dest[UP]);
//			s.setVy(dest[UP]-s.getY());
			return;
		}
	}
	
	
	// �`�揈��
	public void draw(Graphics g, int screenX, int screenY){
		int w=-1;
		int e=-1;
		for(int i = 0; i < 32; i++){
			if(((weapon>>i)&1)>0 && w<0) w = i;
			if(((element>>i)&1)>0 && e<0) e = i;
		}
		int ix = IMAGE_X+width*w;
		int iy = IMAGE_Y+height*e;
		g.drawImage(image,
			x-screenX, y-screenY, x-screenX+width, y-screenY+height,
			ix, iy, ix+width, iy+height,
			null
		);
	}
}