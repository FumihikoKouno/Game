/**
 * 武器の基盤クラス
 */

package Game.Sprite.Weapon;

import Game.Common.*;
import Game.MapData.MapData;
import Game.Sprite.Player;
import Game.Sprite.Sprite;

import java.awt.Graphics;
import java.awt.Color;

public class Shot extends Weapon{
	public int frame;
	
	public Shot(){
		super(0,0);
		this.vx = 0;
		this.vy = 0;
		power = 1;
		enemy = false;
	}
	
	public Shot(int x, int y, int vx, int vy, int size){
		super(x,y);
		setV(vx,vy);
		power = 1;
		enemy = true;
		this.width = size;
		this.height = size;
		appear();
	}

	public void appear(){
		frame = Data.frame;
		if(!enemy){
			switch(direction){
			case UP:
				x = StateData.player.getX() + StateData.player.getWidth()/2;
				y = StateData.player.getY();
				vy = -4;
				break;
			case DOWN:
				x = StateData.player.getX() + StateData.player.getWidth()/2;
				y = StateData.player.getY() + StateData.player.getHeight();
				vy = 4;
				break;
			case LEFT:
				x = StateData.player.getX();
				y = StateData.player.getY() + StateData.player.getHeight()/2;
				vx = -4;
				break;
			case RIGHT:
				x = StateData.player.getX() + StateData.player.getWidth();
				y = StateData.player.getY() + StateData.player.getHeight()/2;
				vx = 4;
				break;
			}
		}
	}
	
	public void touch(Sprite s, int dir, int[] dest){
		if(!enemy) return;
		if(s instanceof Player){
			if((dir & (1<<HIT_DIRECT)) > 0){
				StateData.player.damage(power);
				end = true;
			}else{
				if((dir & (1<<UP))>0){
					vy = dest[UP]-y-height+Data.CD_DIFF+2;
				}
				if((dir & (1<<DOWN))>0){
					vy = dest[DOWN]-y+s.getHeight()-Data.CD_DIFF-2;
				}
				if((dir & (1<<LEFT))>0){
					vx = dest[LEFT]-x-width+Data.CD_DIFF+2;
				}
				if((dir & (1<<RIGHT))>0){
					vx = dest[RIGHT]-x+s.getWidth()-Data.CD_DIFF-2;
				}
			}
		}
	}
	
	// 引数はどの方向に当たったかとその寸前の位置
	public void mapHit(int dir, int dest){
//		end = true;
	}
	// 武器の状態のupdate(矢は飛ぶし、剣でも何フレーム出てるかとか)
	public void update(MapData mapData){
		if(!enemy) move();
	}
	
	public void screenOut(){
		end = true;
	}
	
	// 描画処理
	public void draw(Graphics g, int screenX, int screenY){
		g.setColor(Color.WHITE);
		g.fillOval(x-screenX,y-screenY,width,height);
	}
}