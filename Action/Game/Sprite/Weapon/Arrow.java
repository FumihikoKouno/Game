/**
 * 武器の基盤クラス
 */

package Game.Sprite.Weapon;

import Game.Common.Data;
import Game.MapData.MapData;

import java.awt.Graphics;

public class Arrow extends Weapon{
	public int frame;
	
	public Arrow(){super(0,0);}
	public Arrow(int x, int y, int d){
		super(x,y);
		direction = d;
		if(direction <= DOWN){
			width = 15;
			height = 50;
		}else{
			width = 50;
			height = 15;
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
			vx = -12;
			this.y += 6;
			break;
		case RIGHT:
			this.y += 6;
			vx = 12;
			break;
		}
	}
	
	// 引数はどの方向に当たったかとその寸前の位置
	public void mapHit(int dir, int dest){
		frame = Data.frame - 7;
		switch(dir){
		case UP:
		case DOWN:
			vy = dest - y;
			break;
		case LEFT:
		case RIGHT:
			vx = dest - x;
			break;
		}
	}
	// 武器の状態のupdate(矢は飛ぶし、剣でも何フレーム出てるかとか)
	public void update(MapData mapData){
		move();
		if(Data.frame - frame >= 10) end = true;
	}
	// 描画処理
	public void draw(Graphics g, int screenX, int screenY){
		int sx, sy;
		if(direction <= DOWN){
			sx = 0;
			sy = direction * height;
		}else{
			sx = 0;
			sy = width*2 + (direction-LEFT) * height;
		}
		int dx = x - screenX;
		int dy = y - screenY;
		g.drawImage(Data.image.arrowImage,
			dx, dy,
			dx + width, dy + height,
			sx, sy, sx + width, sy + height,
			null
		);
	}
}