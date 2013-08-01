/**
 * すべてのイベントの基盤クラス
 * 敵、仕掛け(スイッチとか？)、扉とか
 * プレイヤーの行動によって何か変化が生じるものは
 * このクラスを継承して作る
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
	public void touch(Sprite s, int dir, int[] dest){
		if(s instanceof Player){
			Player tmp = (Player)s;
			if((dir & (1 << HIT_DIRECT)) > 0){
				tmp.damage(power);
			}else{
				if((dir & (1 << UP)) > 0){
					tmp.setVy(dest[UP]-tmp.getY()-1+vy);
				}
				if((dir & (1 << DOWN)) > 0){
					tmp.setVy(dest[DOWN]-tmp.getY()+1+vy);
				}
				if((dir & (1 << LEFT)) > 0){
					tmp.setVx(dest[LEFT]-tmp.getX()-1+vx);
				}
				if((dir & (1 << RIGHT)) > 0){
					tmp.setVx(dest[RIGHT]-tmp.getX()+1+vx);
				}
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
		if((Data.frame&1)==0) vy += Data.gravity;
		if(vy >= Data.CHIP_SIZE) vy = Data.CHIP_SIZE - 1;
		if(vy < 0) vy = 0;
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
	
	// このイベントに対して武器ID weapon, 属性ID elementで攻撃したときの関数
	public void attacked(Sprite s){
		/**
		 * spriteIDはそれぞれ固有の物なので
		 * くらったときの武器のspriteIDを保持しておき、
		 * 違うspriteIDのときだけダメージ処理をすることによって
		 * 同じ武器での多段ヒットをなくしている
		 */
		if(s instanceof Weapon){
			Weapon tmp = (Weapon)s;
			if(attackedSpriteID != tmp.getSpriteID()){
				attackedSpriteID = tmp.getSpriteID();
				life -= tmp.power;
			}
		}
	}
}