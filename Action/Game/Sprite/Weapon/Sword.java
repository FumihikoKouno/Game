/**
 * 武器の基盤クラス
 */

package Game.Sprite.Weapon;

import Game.Common.Data;
import Game.MapData.MapData;

import java.awt.Graphics;

public class Sword extends Weapon{
	public int frame;
	
	public Sword(){super(0,0);}
	public Sword(int x, int y, int d){
		super(x,y);
		direction = d;
		if(direction <= DOWN){
			width = 20;
			height = 30;
		}else{
			width = 30;
			height = 20;
		}
		power = 1;
		frame = Data.frame;
		switch(direction){
		case UP:
			this.x += 6;
			this.y -= height;
			break;
		case DOWN:
			this.x += 6;
			break;
		case LEFT:
			this.x -= width;
			this.y += 6;
			break;
		case RIGHT:
			this.y += 6;
			break;
		}
	}
	// 武器の状態のupdate(矢は飛ぶし、剣でも何フレーム出てるかとか)
	public void update(MapData mapData){
		if(Data.frame - frame >= 5) end = true;
	}
	// 描画処理
	public void draw(Graphics g, int screenX, int screenY){
		int sx, sy;
		if(direction <= DOWN){
			sx = 0;
			sy = direction * height;
		}else{
			sx = height;
			sy = (direction-LEFT) * height;
		}
		int dx = x - screenX;
		int dy = y - screenY;
		g.drawImage(Data.image.swordImage,
			dx, dy,
			dx + width, dy + height,
			sx, sy, sx + width, sy + height,
			null
		);
	}
}