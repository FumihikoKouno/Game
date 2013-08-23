package Game.Sprite;

import Game.Sprite.Player;
import Game.Common.Data;

import java.awt.Graphics;

public class FroatingStage extends Sprite{
	public static final int UP_DOWN = 0;
	public static final int LEFT_RIGHT = 1;
	private int scale;
	private int kind;
	private int dir;
	private int offset;
	public FroatingStage(int x, int y, int kind, int scale, int dir){
		super(x,y);
		offset = 0;
		this.kind = kind;
		this.scale = scale;
		this.dir = dir;
		image = Data.image.froatingStageImage;
		width = 40;
		height = 13;
		switch(kind){
		case UP_DOWN:
			if(dir == UP){
				vx = 0;
				vy = -2;
			}else{
				vx = 0;
				vy = 2;
			}
			break;
		case LEFT_RIGHT:
			if(dir == LEFT){
				vx = -2;
				vy = 0;
			}else{
				vx = 2;
				vy = 0;
			}
			break;
		default:
			break;
		}
	}
	// スプライトのupdate
	public void update(){
		switch(kind){
		case UP_DOWN:
			if(offset > scale || offset < 0){
				vy = -vy;
			}
			break;
		case LEFT_RIGHT:
			if(offset > scale || offset < 0){
				vx = -vx;
			}
			break;
		default:
			break;
		}
		switch(kind){
		case UP_DOWN:
			if(dir == UP){
				offset -= vy;
			}else{
				offset += vy;
			}
			break;
		case LEFT_RIGHT:
			if(dir == LEFT){
				offset -= vx;
			}else{
				offset += vx;
			}
			break;
		default:
			break;
		}
	}
	// プレイヤーがスプライトに触れたときの関数
	public void touch(Sprite s, int dir, int[] dest){
		if((dir & (1 << DOWN)) > 0){
			s.setV(s.getVx()+vx,y+vy-s.getHeight()+Data.CD_DIFF-s.getY());
			s.land();
			return;
		}
		if((dir & (1 << LEFT)) > 0){
			s.setVx(x+vx+width-Data.CD_DIFF-s.getX());
			return;
		}
		if((dir & (1 << RIGHT)) > 0){
			s.setVx(x+vx-s.getWidth()+Data.CD_DIFF-s.getX());
			return;
		}
		if((dir & (1 << UP)) > 0){
			s.setVy(y+vy+height-Data.CD_DIFF-s.getY());
			return;
		}
		if(s.getY() == y-s.getHeight()+Data.CD_DIFF && s.getVy() >= 0){
			s.land();
		}
	}
	
	// 描画処理
	public void draw(Graphics g, int screenX, int screenY){
		int ix = 0;
		int iy = 0;
		int anime = Data.frame%15;
		if(anime < 7) anime = 0;
		else anime = 13;
		g.drawImage(image,
			x-screenX, y-screenY, x-screenX+width, y-screenY+height,
			ix, iy+anime, ix+width, iy+height+anime,
			null
		);
	}
}