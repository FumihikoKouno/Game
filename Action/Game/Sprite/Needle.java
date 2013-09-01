/**
 * 攻撃によって壊せる壁のクラス
 * weaponが対応する武器
 * elementが対応する属性
 */

package Game.Sprite;

import Game.Sprite.Player;
import Game.Common.Data;
import Game.Sprite.Weapon.*;

import java.awt.Graphics;
import java.awt.Image;

public class Needle extends Sprite{
	public Needle(int x, int y, int d){
		super(x,y);
		IMAGE_X = 128;
		IMAGE_Y = 0;
		width = Data.CHIP_SIZE;
		height = Data.CHIP_SIZE;
		direction = d;
	}
	
	public void update(){
		vx = 0;
		vy = 0;
	}
	
	// プレイヤーがスプライトに触れたときの関数
	public void touch(Sprite s, int dir, int[] dest){
		if(s instanceof Player){
			Player tmp = (Player)s;
			if((dir & (1 << HIT_DIRECT)) > 0){
				tmp.damage(1000);
				return;
			}
			if((dir & (1 << DOWN)) > 0){
				s.setVy(dest[DOWN]-s.getY()+1);
				return;
			}
			if((dir & (1 << LEFT)) > 0){
				s.setVx(dest[LEFT]-s.getX()-1);
				return;
			}
			if((dir & (1 << RIGHT)) > 0){
				s.setVx(dest[RIGHT]-s.getX()+1);
				return;
			}
			if((dir & (1 << UP)) > 0){
				s.setVy(dest[UP]-s.getY()-1);
				return;
			}
		}else{
			if((dir & (1 << DOWN)) > 0){
				s.setVy(dest[DOWN]-s.getY());
				return;
			}
			if((dir & (1 << LEFT)) > 0){
				s.setVx(dest[LEFT]-s.getX());
				return;
			}
			if((dir & (1 << RIGHT)) > 0){
				s.setVx(dest[RIGHT]-s.getX());
				return;
			}
			if((dir & (1 << UP)) > 0){
				s.setVy(dest[UP]-s.getY());
				return;
			}
		}
	}

}