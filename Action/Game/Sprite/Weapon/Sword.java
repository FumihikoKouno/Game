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
	public Sword(int d){
	    super(0,0);
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
		this.x = Data.player.getX()+6;
		this.y = Data.player.getY()-height;
		break;
	    case DOWN:
		this.x = Data.player.getX()+6;
		this.y = Data.player.getY()+Data.player.getHeight();
		break;
	    case LEFT:
		this.x = Data.player.getX()-width;
		this.y = Data.player.getY()+6;
		break;
	    case RIGHT:
		this.x = Data.player.getX()+Data.player.getWidth();
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
			sx = (Data.frame-frame)*width;
			sy = direction * height;
		}else{
			sx = (Data.frame-frame)*width;
			sy = width*2 + (direction-LEFT) * height;
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