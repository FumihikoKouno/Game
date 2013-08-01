/**
 * 攻撃によって壊せる壁のクラス
 * weaponが対応する武器
 * elementが対応する属性
 */

package Game.Sprite;

import Game.Sprite.Player;
import Game.MapData.MapData;
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
		image = Data.image.brokenChipImage;
		life = 1;
		width = Data.CHIP_SIZE;
		height = Data.CHIP_SIZE;
		this.weapon = weapon;
		this.element = element;
	}
	
	public void update(MapData mapdata){
		if(life <= 0) end = true;
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
		}
		return elem && ((weapon&(1<<weaponTmp))>0);
	}
	
	// このスプライトに対してspriteが攻撃したときの関数
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
				System.out.println(life);
			}
		}
	}
	
	// プレイヤーがスプライトに触れたときの関数
	public void touch(Sprite s, int dir, int[] dest){
		if((dir & (1 << DOWN)) > 0){
			s.setVy(dest[DOWN]-s.getY());
			s.land();
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
	
	
	// 描画処理
	public void draw(Graphics g, int screenX, int screenY){
		int ix = 0;
		int iy = 0;
		g.drawImage(image,
			x-screenX, y-screenY, x-screenX+width, y-screenY+height,
			ix, iy, ix+width, iy+height,
			null
		);
	}
}